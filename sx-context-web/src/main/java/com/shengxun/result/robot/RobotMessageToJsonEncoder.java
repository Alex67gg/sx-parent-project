package com.shengxun.result.robot;

import com.alibaba.fastjson.JSON;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by ldh on 2018/7/6.
 */
public class RobotMessageToJsonEncoder implements Encoder.Text<RobotMessage> {

    @Override
    public String encode(RobotMessage object) throws EncodeException {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}