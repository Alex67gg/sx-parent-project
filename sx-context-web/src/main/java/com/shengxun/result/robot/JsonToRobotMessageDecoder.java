package com.shengxun.result.robot;

import com.alibaba.fastjson.JSON;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by ldh on 2018/7/6.
 */
public class JsonToRobotMessageDecoder implements   Decoder.Text<RobotMessage>{


    @Override
    public RobotMessage decode(String s) throws DecodeException {
        return JSON.parseObject(s,RobotMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}