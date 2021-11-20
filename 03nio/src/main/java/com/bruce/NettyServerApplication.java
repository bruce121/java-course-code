package com.bruce;

import com.bruce.inbound.HttpInboundServer;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @ClassName: NettyServerApplication
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/20/21 5:07 PM
 * @Version 1.0
 */
public class NettyServerApplication {

    public static void main(String[] args) {

        int serverPort = 8888;

        List<String> realServerUrls = Lists.newArrayList("http://localhost:8081");

        HttpInboundServer server = new HttpInboundServer(serverPort, realServerUrls);

        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
