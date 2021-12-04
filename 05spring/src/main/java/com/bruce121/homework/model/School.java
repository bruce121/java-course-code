package com.bruce121.homework.model;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: School
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 9:12 PM
 * @Version 1.0
 */
public class School implements Serializable {
    private static final long serialVersionUID = -1L;

    private String name;
    private List<Klass> klasses;

    public School(String name, List<Klass> klasses) {
        this.name = name;
        this.klasses = klasses;
    }

    public School() {
    }

    public List<Klass> getKlasses() {
        return klasses;
    }

    public void setKlasses(List<Klass> klasses) {
        this.klasses = klasses;
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", klasses=" + klasses +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
