package com.shengxun.result.robot;

import java.io.Serializable;

/**
 * Created by ldh on 2018/7/5.
 */
public class RobotMessage implements Serializable{

    /**
     * 关闭机器人
     */
    public final static int CLOSECODE = 1 << 8 | 220;
    /**
     * 发送消息
     */
    public final static int MSGCODE = 2 << 8 | 220;
    /**
     * 重连
     */
    public final static int CONCODE = 3 << 8 | 220;
    /**
     * 错误
     */
    public final static int ERRCODE = 5 << 8 | 220;

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RobotMessage() {
    }

    public RobotMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "RobotMessage{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * 关闭机器人
     * @param message
     * @return
     */
    public static RobotMessage close(String message){
        return new RobotMessage(CLOSECODE,message);
    }

    /**
     * 关闭机器人
     * @param message
     * @return
     */
    public static RobotMessage error(String message){
        return new RobotMessage(ERRCODE,message);
    }

    /**
     * 发送消息
     * @param message
     * @return
     */
    public static RobotMessage sendMessage(String message){
        return new RobotMessage(MSGCODE,message);
    }
}