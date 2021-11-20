package com.bruce.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @ClassName: HeaderHttpResponseFilter
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/20/21 5:56 PM
 * @Version 1.0
 */
public class HeaderHttpResponseFilter implements HttpResponseFilter {


    @Override
    public void filter(FullHttpResponse response) {

        response.headers().add("x-user-id", "header_value_01");
    }
}
