package com.bruce121.homework.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SchoolAspect
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 10:00 PM
 * @Version 1.0
 */
@Aspect
@Component
public class SchoolAspectAnno {

    @Pointcut(value = "execution(* com.bruce121.homework.service.SchoolService.have*(..))")
    public void pointcut() {
    }

    @Before(value = "pointcut()")
    public void doSomethingBefore() {
        System.out.println("Do something before ~");
    }

    @Around(value = "pointcut()")
    public void doSomethingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Do something around before preceed ~");

        joinPoint.proceed();

        System.out.println("Do something around after preceed ~");
    }

    @AfterReturning(value = "pointcut()")
    public void doSomethingAfter() {
        System.out.println("Do something after ~");
    }
}
