package com.shengxun.service.impl;

import com.shengxun.constant.RobotConstant;
import com.shengxun.robot.Robot;
import com.shengxun.robot.RobotTask;
import com.shengxun.robot.RobotThreadConf;
import com.shengxun.service.RobotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

/**
 * Created by ldh on 2018/7/2.
 */
@Service
public class RobotServiceImpl implements RobotService {

    private static final Logger log = LoggerFactory.getLogger(RobotServiceImpl.class);

    @Autowired
    private RobotThreadConf robotThreadConf;

    @Override
    public String interacts(String sessionId, String sayWord) throws Exception{
        try {
            log.info("[sayWord]——>" + sayWord + "[sessionId]——>" + sessionId);
            ConcurrentHashMap<String, Robot> cmp = RobotConstant.getTEXTTRYS();
            Robot rbt = cmp.get(sessionId);
            log.info("[rbt]——>" + rbt);
            if (rbt == null){
               rbt = new Robot();
                cmp.put(sessionId,rbt);
            }else if (!rbt.isAlive()){
                rbt = new Robot();
            }
            FutureTask<String> futureTask = new FutureTask<String>(new RobotTask(rbt,sayWord));
            robotThreadConf.getTrialThreadExecutor().execute(futureTask);
            String response = futureTask.get();
            return response;
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 向机器人输入
     * @param sayWord
     */
    @Override
    public void input( String sayWord,Robot robot) throws Exception{
        BufferedOutputStream bufferedOutputStream = robot.getProcessInput();
        bufferedOutputStream.write((sayWord + "\n").getBytes());
        bufferedOutputStream.flush();
    }

    /**
     * 输出
     */
    @Override
    public String output(Robot robot) throws Exception {
        String output = "";
        String outputErr ="";
        BufferedReader br = robot.getBufferedReader();
        BufferedReader brError = robot.getBufferedReaderErr();
        try {
            String line  ;
            while (br.ready() && (line = br.readLine())!= null){
                output += line;
            }
            String lineErr  ;
            while (brError.ready() && (lineErr = brError.readLine())!= null){
                outputErr += lineErr;
            }
            log.info("[outputErr]——>[" + outputErr+ "]");
            log.info("[output]——>[" + output+ "]");
        }catch (Exception e){
            throw e;
        }
        return output;
    }

    /**
     * 建立连接
     * @throws Exception
     */
    @Override
    public Robot connect()throws Exception {
        return  new Robot();
    }

    /**
     * 关闭机器人
     * @param robot
     * @throws Exception
     */
    @Override
    public void close(Robot robot) throws Exception{
        robot.destroy();
    }
}