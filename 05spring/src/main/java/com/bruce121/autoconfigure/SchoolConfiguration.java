package com.bruce121.autoconfigure;

import com.bruce121.homework.model.Klass;
import com.bruce121.homework.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ClassName: SchoolConfiguration
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/5/21 7:32 PM
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(value = {SchoolProperties.class})
public class SchoolConfiguration {

    @Autowired
    private SchoolProperties schoolProperties;

    @Bean(name = "qingbei2")
    public School school() {

        String schoolName = schoolProperties.getName();

        List<Klass> klasses = schoolProperties.getKlasses();

        return new School(schoolName, klasses);
    }
}
