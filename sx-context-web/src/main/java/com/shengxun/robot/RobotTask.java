package com.shengxun.robot;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.util.concurrent.Callable;

/**
 * Created by ldh on 2018/7/2.
 */
public class RobotTask implements Callable<String> {

    private static final Logger log = LoggerFactory.getLogger(RobotTask.class);

    private Robot robot;

    private String sayWord;

    public RobotTask(Robot robot, String sayWord) {
        this.robot = robot;
        this.sayWord = sayWord;
    }

    @Override
    public String call() throws Exception {
        String output = "";
        String outputErr ="";
        BufferedOutputStream bw = robot.getProcessInput();
        BufferedReader br = robot.getBufferedReader();
        BufferedReader brError = robot.getBufferedReaderErr();
        try {
            if (StringUtils.isNoneBlank(sayWord) ){
                bw.write((sayWord + "\n").getBytes());
                bw.flush();
            }
            String line  ;
            while (br.ready() && (line = br.readLine())!= null){
                output += RobotResponseFilter.wordFilter(line);
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
}