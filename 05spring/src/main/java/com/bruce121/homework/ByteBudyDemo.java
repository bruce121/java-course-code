package com.bruce121.homework;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import static net.bytebuddy.matcher.ElementMatchers.is;

/**
 * @ClassName: ByteBudyDemo
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 11:18 PM
 * @Version 1.0
 */
public class ByteBudyDemo {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(ByteBudyDemo.class.getClassLoader())
                .getLoaded();

        System.out.println(dynamicType.newInstance().toString());
    }
}
