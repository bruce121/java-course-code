package com.bruce121.homework.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @ClassName: SchoolAspectXml
 * @Description: xml配置顺序可以影响before around的先后顺序，bean配置则固定
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 10:00 PM
 * @Version 1.0
 */
public class SchoolAspectXml {

    public void doSomethingBefore() {
        System.out.println("Do something before ~");
    }

    public void doSomethingAfter() {
        System.out.println("Do something after ~");
    }

    public void doSomethingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Do something around before preceed ~");

        joinPoint.proceed();

        System.out.println("Do something around after preceed ~");
    }
}
