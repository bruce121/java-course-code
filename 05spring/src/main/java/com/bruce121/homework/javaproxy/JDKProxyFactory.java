package com.bruce121.homework.javaproxy;

import java.lang.reflect.Proxy;

/**
 * @ClassName: JDKProxyFactory
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 10:27 PM
 * @Version 1.0
 */
@SuppressWarnings("ALL")
public class JDKProxyFactory {

    // 代理对象
    private Object target;

    public JDKProxyFactory(Object target) {
        super();
        this.target = target;
    }

    // 获得jdk动态代理对象
    public Object getProxyObject() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (proxy, method, args) -> {

            Object object;

            // 如果是保存的方法,执行记录日志的操作
            if (method.getName().equals("raiseUpRedFlag")) {

                // 调用目标对象原来的方法,并将返回值返回. target对象,使用args参数,执行method方法.
                System.out.println("准备升国旗了，开始集合~");

                object = method.invoke(target, args);

                System.out.println("升国旗结束，准备上课~");
            } else {
                object = method.invoke(target, args);
            }
            return object;
        });
    }// 结束获取代理对象的方法
}