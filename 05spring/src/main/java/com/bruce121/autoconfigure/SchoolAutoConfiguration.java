package com.bruce121.autoconfigure;

import com.bruce121.homework.model.School;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: SchoolAutoConfiguration
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/5/21 7:32 PM
 * @Version 1.0
 */
@Configuration
@Import(SchoolConfiguration.class)
@ConditionalOnClass(value = {School.class})
@PropertySource("classpath:application-${spring.profiles.active:local}.properties")
@ConditionalOnProperty(prefix = "school.autoconfig", value = "enable", havingValue = "true", matchIfMissing = false)
@ComponentScan(value = "com.bruce121.autoconfigure")
public class SchoolAutoConfiguration {
}
