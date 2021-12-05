package com.bruce121.autoconfigure;

import com.bruce121.homework.model.Klass;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @ClassName: SchoolProperties
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/5/21 7:36 PM
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "school")
public class SchoolProperties {

    private String name;

    private List<Klass> klasses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Klass> getKlasses() {
        return klasses;
    }

    public void setKlasses(List<Klass> klasses) {
        this.klasses = klasses;
    }
}
