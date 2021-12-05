package com.bruce121.homework.service;

import com.bruce121.homework.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

/**
 * @ClassName: SchoolServiceImpl
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 9:56 PM
 * @Version 1.0
 */
@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private School school;

    @Override
    public void haveSportsMeeting() {

        String name = school.getName();
        System.out.println(format("%s 开运动会了！", name));
    }

    @Override
    public void requiredMoney() {
        String name = school.getName();
        System.out.println(format("%s 又要开学收学费了！", name));
    }

    @Override
    public void raiseUpRedFlag() {
        System.out.println("升国旗了!");
    }

    @Override
    public String sayWelcome() {
        return "学生们会健康成长的。";
    }

    public void schoolCelebration() {
        System.out.println("举办校庆!");
    }
}
