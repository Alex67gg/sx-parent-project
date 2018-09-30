package com.shengxun.robot;

import com.shengxun.constant.RobotConstant;
import com.shengxun.result.robot.JsonToRobotMessageDecoder;
import com.shengxun.result.robot.RobotMessage;
import com.shengxun.result.robot.RobotMessageToJsonEncoder;
import com.shengxun.service.RobotService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ldh on 2018/7/5.
 */
@Component
@ServerEndpoint(value = "/robotServerSocket/{userId}",encoders = {RobotMessageToJsonEncoder.class},decoders = {JsonToRobotMessageDecoder.class})
public class RobotServerSocket {

    private static final Logger log = LoggerFactory.getLogger(RobotServerSocket.class);

    private Session session;
    private static Map<String,Session> sessionPool = new HashMap<String,Session>();
    private static Map<String,String> sessionIds = new HashMap<String,String>();

    private static RobotService robotService;

    private static Robot robot;
    @Autowired
    public void setRobotService(RobotService robotService) {
        RobotServerSocket.robotService = robotService;
    }

    @Autowired
    public  void setRobot(Robot robot) {
        RobotServerSocket.robot = robot;
    }

    /**
     * 用户连接时触发
     * @param session
     * @param userId
     */
    @OnOpen
    public void open(Session session,@PathParam(value="userId")String userId){
        this.session = session;
        sessionPool.put(userId, session);
        sessionIds.put(session.getId(), userId);
        ConcurrentHashMap<String, Robot> cmp = RobotConstant.getTEXTTRYS();
        log.info("[当前运行的试用程序有]——>["+cmp.size()+"个]");
        cmp.forEach((k,v)->{
            log.info("userId = " + k + ", Robot = " + v.toString());
        });
        try {
            String output = this.robotService.interacts(userId,null);
            if (!StringUtils.isEmpty(output)){
                sendMessage(RobotMessage.sendMessage(output),userId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 收到信息时触发
     * @param message
     */
    @OnMessage
    public void onMessage(RobotMessage message){

        String userId = sessionIds.get(session.getId());
        ConcurrentHashMap<String, Robot> cmp = RobotConstant.getTEXTTRYS();
        try {
            log.info("[onMessage][message]"+message );
            log.info("[onMessage][userId]"+userId );
            Robot robot = cmp.get(userId);
            String output = "";
            /**
             * 正常消息对话
             */
            if (message.getCode() == RobotMessage.MSGCODE){
                output= robotService.interacts(userId,message.getMessage());
                if (StringUtils.isNotBlank(output)){
                    sendMessage(RobotMessage.sendMessage(output),userId);
                }
                /**
                 * 请求关闭试用
                 */
            } else if (message.getCode() == RobotMessage.CLOSECODE) {
                if (robot != null){
                    robotService.close(robot);
                }
                sendMessage(RobotMessage.close("关闭成功"),userId);
                sessionPool.remove(sessionIds.get(session.getId()));
                sessionIds.remove(session.getId());
                /**
                 * 请求重新试用
                 */
            }else if (message.getCode() == RobotMessage.CONCODE) {
                if (robot == null ) {
                    robot = robotService.connect();
                    cmp.put(userId,robot);
                }else {
                    if (!robot.isAlive()){
                        robot = robotService.connect();
                        cmp.put(userId,robot);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭触发
     */
    @OnClose
    public void onClose(){
        ConcurrentHashMap<String, Robot> cmp = RobotConstant.getTEXTTRYS();
        try {
            Robot robot= cmp.get(sessionIds.get(session.getId()));
            if (robot != null){
                robotService.close(robot);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());
    }

    /**
     * 发生错误时触发
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        if (error instanceof DecodeException){
            if(session!=null){
                try {
                    session.getBasicRemote().sendObject(RobotMessage.error("编码错误"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else if (error instanceof EncodeException){
            if(session!=null){
                try {
                    session.getBasicRemote().sendObject(RobotMessage.error("解码错误"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            if(session!=null){
                try {
                    session.getBasicRemote().sendObject(RobotMessage.error("系统错误"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *信息发送的方法
     * @param message
     * @param userId
     */
    public static void sendMessage(RobotMessage message, String userId){
        Session s = sessionPool.get(userId);
        if(s!=null){
            try {
                s.getBasicRemote().sendObject(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Scheduled(cron = "0/1 * * * * ? ")
    public void sendMessage(){
        ConcurrentHashMap<String, Robot> map = RobotConstant.getTEXTTRYS();
        log.info("0/1 * * * * ? ");
        if(map.size() > 0){
            try {
                map.forEach((k,v) ->{
                    try {
                        String result ="";
                        if (!v.isAlive()){
                            map.remove(k);
                            sendMessage(RobotMessage.close("机器人已关闭"),k);
                        }else {
                            result = robotService.interacts(k,null);
                            if(StringUtils.isNotBlank(result)){
                                sendMessage(RobotMessage.sendMessage(result),k);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}