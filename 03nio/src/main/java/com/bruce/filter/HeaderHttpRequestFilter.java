package com.bruce.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @ClassName: HeaderHttpRequestFilter
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/20/21 5:28 PM
 * @Version 1.0
 */
public class HeaderHttpRequestFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

        fullRequest.headers().set("mao", "soul");
    }
}
