package com.bruce.router;

import java.util.List;

/**
 * @ClassName: HttpEndpointRouter
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/20/21 5:58 PM
 * @Version 1.0
 */
public interface HttpEndpointRouter {

    String route(List<String> endpoints);

    // Load Balance
    // Random
    // RoundRibbon
    // Weight
    // - server01,20
    // - server02,30
    // - server03,50
}
