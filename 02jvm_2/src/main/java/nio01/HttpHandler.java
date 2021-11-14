package nio01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @ClassName: nio01.HttpHandler
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/14/21 9:38 PM
 * @Version 1.0
 */
public class HttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        FullHttpRequest fullHttpRequest = null;
        FullHttpResponse response = null;
        try {
            fullHttpRequest = (FullHttpRequest) msg;

            String uri = fullHttpRequest.uri();
            String value = "hello world, bruce!";

            ByteBuf byteBuf = Unpooled.wrappedBuffer(value.getBytes());

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, byteBuf);

            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            ex.printStackTrace();
        } finally {

            if (fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                    ctx.flush();
                }
            }
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
