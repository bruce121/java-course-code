package com.bruce.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @ClassName: HttpResponseFilter
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/20/21 5:56 PM
 * @Version 1.0
 */
public interface HttpResponseFilter {

    void filter(FullHttpResponse response);
}
