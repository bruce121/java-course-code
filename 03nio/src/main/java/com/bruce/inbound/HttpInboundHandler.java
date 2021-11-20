package com.bruce.inbound;

import com.bruce.outbound.HttpOutboundHandler;
import com.bruce.filter.HeaderHttpRequestFilter;
import com.bruce.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * @ClassName: HttpInboundHandler
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/20/21 5:21 PM
 * @Version 1.0
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private final List<String> proxyServers;
    private HttpOutboundHandler handler;
    private HttpRequestFilter filter = new HeaderHttpRequestFilter();

    public HttpInboundHandler(List<String> proxyServers) {
        this.proxyServers = proxyServers;
        this.handler = new HttpOutboundHandler(this.proxyServers);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;

            handler.handle(fullHttpRequest, ctx, filter);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
