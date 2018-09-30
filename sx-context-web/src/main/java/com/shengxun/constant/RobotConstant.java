package com.shengxun.constant;


import com.shengxun.robot.Robot;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ldh on 2018/7/4.
 */
public class RobotConstant {

    private static ConcurrentHashMap<String, Robot> ROBOTS ;

    public synchronized static ConcurrentHashMap<String, Robot> getTEXTTRYS(){
        if(ROBOTS == null){
            ROBOTS = new ConcurrentHashMap<String, Robot>();
        }
        return ROBOTS;
    }
}