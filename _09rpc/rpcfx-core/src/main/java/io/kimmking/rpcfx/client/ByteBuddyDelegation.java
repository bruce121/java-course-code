package io.kimmking.rpcfx.client;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.ByteBuddyParameter;
import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.netty.HttpSnoopClient;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.Method;

import static io.kimmking.rpcfx.client.Rpcfx.RpcfxInvocationHandler.JSONTYPE;

/**
 * @ClassName: ByteBuddyDelegation
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 2022/1/2 5:53 PM
 * @Version 1.0
 */
public class ByteBuddyDelegation {

    private final ByteBuddyParameter param;

    public ByteBuddyDelegation(ByteBuddyParameter param) {
        this.param = param;
    }

    @RuntimeType
    public Object invoke(@This Object proxy, @Origin Method method, @AllArguments @RuntimeType Object[] params) throws Throwable {

        // 加filter地方之二
        // mock == true, new Student("hubao");

        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(param.getServiceClass().getName());
        request.setMethod(method.getName());
        request.setParams(params);
        Filter[] filters = param.getFilters();

        if (null != filters) {
            for (Filter filter : filters) {
                if (!filter.filter(request)) {
                    return null;
                }
            }
        }

        RpcfxResponse response = HttpSnoopClient.post(request, param.getUrl());

//         RpcfxResponse response = post(request, param.getUrl());

        return JSON.parse(response.getResult().toString());
    }

    private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: " + reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, reqJson))
                .build();
        String respJson = client.newCall(request).execute().body().string();
        System.out.println("resp json: " + respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }
}
