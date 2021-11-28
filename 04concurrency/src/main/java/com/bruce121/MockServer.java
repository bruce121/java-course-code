package com.bruce121;


/**
 * @ClassName: MockServer
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/28/21 2:25 PM
 * @Version 1.0
 */
public class MockServer {

    public static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }
}
