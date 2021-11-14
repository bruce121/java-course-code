package nio01;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName: nio01.HttpInitializer
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/14/21 9:37 PM
 * @Version 1.0
 */
public class HttpInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(1024*1024));
        pipeline.addLast(new HttpHandler());
    }
}
