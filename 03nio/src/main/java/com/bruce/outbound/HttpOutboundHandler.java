package com.bruce.outbound;

import com.bruce.filter.HeaderHttpResponseFilter;
import com.bruce.filter.HttpRequestFilter;
import com.bruce.filter.HttpResponseFilter;
import com.bruce.router.HttpEndpointRouter;
import com.bruce.router.RandomHttpEndpointRouter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * @ClassName: HttpOutboundHandler
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/20/21 5:26 PM
 * @Version 1.0
 */
public class HttpOutboundHandler {

    private CloseableHttpAsyncClient httpClient;
    private ExecutorService executorService;
    private final List<String> backendUrls;

    HttpResponseFilter filter = new HeaderHttpResponseFilter();
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    public HttpOutboundHandler(List<String> proxyServers) {

        this.backendUrls = proxyServers;

        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 2048;

        executorService = new ThreadPoolExecutor(
                cores,
                cores,
                keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize),
                new ThreadFactoryBuilder().setNameFormat("proxyService").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        IOReactorConfig ioconfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();

        httpClient = HttpAsyncClients.custom()
                .setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioconfig)
                .setKeepAliveStrategy((response, context) -> 6000)
                .build();

        httpClient.start();
    }


    public void handle(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx, HttpRequestFilter filter) {

        String backendUrl = router.route(this.backendUrls);

        final String url = backendUrl + fullHttpRequest.uri();

        filter.filter(fullHttpRequest, ctx);

        executorService.submit(() -> {
            fetchGet(fullHttpRequest, ctx, url);
        });

    }

    private void fetchGet(final FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        httpGet.setHeader("mao", inbound.headers().get("mao"));


        httpClient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse endPointResponse) {
                try {
                    handleResponse(inbound, ctx, endPointResponse);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


            @Override
            public void failed(Exception e) {
                httpGet.abort();
                e.printStackTrace();
            }

            @Override
            public void cancelled() {
                httpGet.abort();
            }
        });
    }

    private void handleResponse(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpResponse endPointResponse) {

        FullHttpResponse response = null;

        try {
            byte[] body = EntityUtils.toByteArray(endPointResponse.getEntity());

            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", endPointResponse.getFirstHeader("Content-Type").getValue());
            // response.headers().set("Transfer-Encoding", endPointResponse.getFirstHeader("Transfer-Encoding").getValue());
            response.headers().set("Content-Length", response.content().readableBytes());

            filter.filter(response);
        } catch (IOException e) {
            e.printStackTrace();

            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {

            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }

            ctx.flush();
        }
    }

    private void exceptionCaught(ChannelHandlerContext ctx, IOException cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
