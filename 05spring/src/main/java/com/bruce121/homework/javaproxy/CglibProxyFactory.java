package com.bruce121.homework.javaproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName: CglibProxyFactory
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 10:27 PM
 * @Version 1.0
 */
@SuppressWarnings("ALL")
public class CglibProxyFactory {

    // 声明一个代理对象引用
    private Object target;

    // 通过构造函数注入代理对象
    public CglibProxyFactory(Object target) {
        this.target = target;
    }

    // 获取代理对象
    public Object getProxyObject() {
        // 1.创建代理对象增强器
        Enhancer enhancer = new Enhancer();
        // 2.在增强器上设置两个属性
        // 设置要生成代理对象的目标对象:生成目标对象类型的子类型
        enhancer.setSuperclass(target.getClass());
        // 3.设置回调方法
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

                Object object;
                if (method.getName().equals("schoolCelebration")) {

                    // 调用目标对象原来的方法,并将返回值返回. target对象,使用args参数,执行method方法.
                    System.out.println("准备校庆了，开始集合~");

                    // 执行目标对象原来的方法
                    object = method.invoke(target, args);

                    System.out.println("校庆结束，开始发作业~");
                } else {
                    // 执行目标对象原来的方法
                    object = method.invoke(target, args);
                }
                return object;
            }
        });
        // 4.创建获取对象
        return enhancer.create();
    }
}