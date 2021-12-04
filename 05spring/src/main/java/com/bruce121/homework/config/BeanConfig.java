package com.bruce121.homework.config;

import com.bruce121.homework.model.Klass;
import com.bruce121.homework.model.School;
import com.bruce121.homework.model.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BeanConfig
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 9:30 PM
 * @Version 1.0
 */
@Configuration
public class BeanConfig {

    @Bean(name = "s1")
    public Student studentA1() {
        return new Student("小A1");
    }

    @Bean(name = "s2")
    public Student studentB2() {
        return new Student("小B2");
    }

    @Bean(name = "k1")
    public Klass classA1(Student s1, Student s2) {
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        return new Klass("A班", students);
    }

    @Bean
    public School school(Klass k1) {
        List<Klass> klasses = new ArrayList<>();
        klasses.add(k1);
        return new School("清北", klasses);
    }
}
