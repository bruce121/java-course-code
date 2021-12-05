package com.bruce121.homework.service;

/**
 * @ClassName: SchoolService
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 12/4/21 9:54 PM
 * @Version 1.0
 */
public interface SchoolService {

    /**
     * 开运动会
     */
    void haveSportsMeeting();

    /**
     * 收学费
     */
    void requiredMoney();

    /**
     * 升国旗
     */
    void raiseUpRedFlag();

    /**
     * 欢迎开学，有话对家长说
     *
     * @return 对家长说的话
     */
    String sayWelcome();
}
