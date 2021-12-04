package com.bruce121.homework.model;

import java.io.Serializable;

/**
 * @ClassName: Student
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 9:14 PM
 * @Version 1.0
 */
public class Student implements Serializable {
    private static final long serialVersionUID = -1L;

    private String name;

    public Student(String name) {
        this.name = name;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
