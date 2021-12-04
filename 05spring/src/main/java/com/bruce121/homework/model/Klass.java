package com.bruce121.homework.model;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Klass
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 9:13 PM
 * @Version 1.0
 */
public class Klass implements Serializable {
    private static final long serialVersionUID = -1L;

    private String name;

    private List<Student> students;

    public Klass() {
    }

    public Klass(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Klass{" +
                "name='" + name + '\'' +
                ", students=" + students +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
