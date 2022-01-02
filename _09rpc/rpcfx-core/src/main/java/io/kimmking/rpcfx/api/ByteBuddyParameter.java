package io.kimmking.rpcfx.api;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: ByteBuddyParameter
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 2022/1/2 5:47 PM
 * @Version 1.0
 */
@Getter
@Setter
public class ByteBuddyParameter {
    private Class<?> serviceClass;
    private String url;
    private Filter[] filters;

    public <T> ByteBuddyParameter(Class<T> serviceClass, String url, Filter... filters) {
        this.serviceClass = serviceClass;
        this.url = url;
        this.filters = filters;
    }
}
