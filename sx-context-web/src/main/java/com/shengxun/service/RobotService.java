package com.shengxun.service;

import com.shengxun.robot.Robot;

/**
 * Created by ldh on 2018/7/2.
 */
public interface RobotService {

    void input(String sayWord,Robot robot)throws Exception;

    Robot connect()throws Exception;

    String interacts(String textTryId, String sayWord)throws Exception;

    String output(Robot robot)throws Exception;

    void close(Robot robot) throws Exception;
}