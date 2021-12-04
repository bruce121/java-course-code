package com.bruce121.homework;

import com.bruce121.homework.model.School;
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
public class Homework02 implements CommandLineRunner {

    @Autowired
    private School school;

    @Resource(name = "huada")
    private School huada;

    public static void main(String[] args) {
        SpringApplication.run(Homework02.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // method1，annotation
        System.out.println(school);

        // method2，xml
        System.out.println(huada);

        System.exit(0);
    }

}
