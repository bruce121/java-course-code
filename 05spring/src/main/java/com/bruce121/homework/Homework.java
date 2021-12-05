package com.bruce121.homework;

import com.bruce121.homework.javaproxy.CglibProxyFactory;
import com.bruce121.homework.javaproxy.JDKProxyFactory;
import com.bruce121.homework.model.School;
import com.bruce121.homework.service.SchoolService;
import com.bruce121.homework.service.SchoolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.Resource;

/**
 * @ClassName: Homework02
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 9:08 PM
 * @Version 1.0
 */
@ImportResource(value = {"classpath:bean-config.xml"})
@SpringBootApplication
public class Homework implements CommandLineRunner {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private School school;

    @Resource(name = "huada")
    private School huada;

    @Resource(name = "qingbei2")
    private School qingbei2;

    public static void main(String[] args) {
        SpringApplication.run(Homework.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // bean方式装配，方法1：通过注解annotation
        System.out.println(school);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");

        // bean方式装配，方法2：通过xml配置
        System.out.println(huada);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");

        // AOP 方式1：通过注解
        schoolService.haveSportsMeeting();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");

        // AOP 方式2：通过xml配置
        schoolService.requiredMoney();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");

        // AOP 方式3：通过Jdk动态代理
        SchoolService schoolServiceJdkProxy = (SchoolService) new JDKProxyFactory(new SchoolServiceImpl()).getProxyObject();
        schoolServiceJdkProxy.raiseUpRedFlag();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");

        // AOP 方式4：通过Cglib动态代理
        SchoolServiceImpl schoolServiceImpl = new SchoolServiceImpl();
        SchoolServiceImpl schoolServiceCglibProxy = (SchoolServiceImpl) new CglibProxyFactory(schoolServiceImpl).getProxyObject();
        schoolServiceCglibProxy.schoolCelebration();

        // 使用SpringbootStarter 实现自动装配的bean
        System.out.println(qingbei2);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
    }

}
