package com.bruce.router;

import java.util.List;
import java.util.Random;

/**
 * @ClassName: RandomHttpEndpointRouter
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/20/21 5:59 PM
 * @Version 1.0
 */
public class RandomHttpEndpointRouter implements  HttpEndpointRouter {

    @Override
    public String route(List<String> endpoints) {

        int size = endpoints.size();

        Random random = new Random(System.currentTimeMillis());

        return endpoints.get(random.nextInt(size));
    }
}
