package com.shengxun.robot;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by ldh on 2018/7/6.
 */
public class RobotResponseFilter {

    /**
     * 过滤文字
     * @param message
     * @return
     */
    public static String wordFilter(String message){
        if (StringUtils.isNoneBlank(message)){
            if (message.startsWith("                                  - ")){
                String[] strings = message.split("]: ");
                if (strings.length > 1){
                    String result ="";
                    for (int i = 0;i<strings.length;i++){
                        if (i != 0){
                            result +=strings[i];
                        }
                    }
                    return result;
                }
                return "";
            }
            return "";
        }
        return "";
    }
}