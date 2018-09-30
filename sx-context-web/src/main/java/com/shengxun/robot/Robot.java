package com.shengxun.robot;

import org.apache.commons.exec.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by ldh on 2018/7/2.
 */
@Scope("prototype")
@Component
public class Robot {

    private static final Logger log = LoggerFactory.getLogger(Robot.class);

    @Value("${bin.path}")
    private   String SHELLPATH ;

    private BufferedReader bufferedReader;

    private BufferedReader bufferedReaderErr;

    private BufferedOutputStream processInput;

    private PumpStreamHandler streamHandler;

    private  final ExecuteWatchdog watchdog = new ExecuteWatchdog(Integer.MAX_VALUE);

/*    public Robot start(){

    }*/

    public BufferedOutputStream getProcessInput() {
        return processInput;
    }

    public boolean isAlive(){
        return this.bufferedReader != null && this.bufferedReaderErr != null && this.processInput != null && this.watchdog.isWatching();
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public BufferedReader getBufferedReaderErr() {
        return bufferedReaderErr;
    }

    public void  destroy(){
        try {
            watchdog.destroyProcess();
            if (this.bufferedReader != null){
                this.bufferedReader.close();
            }if (this.getProcessInput() != null){
                this.getProcessInput().close();
            }if (this.bufferedReaderErr != null){
                this.bufferedReaderErr.close();
            }
            this.streamHandler.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Robot() {
        DefaultExecutor executor = new DefaultExecutor();
        DefaultExecuteResultHandler resultHandler =  new DefaultExecuteResultHandler();
        final CommandLine cmdLine = CommandLine.parse("/root/app/shell/sa.bin" + "  -t  " + "/root/app/shell/");

        PipedOutputStream stdout = new PipedOutputStream();
        PipedOutputStream stderr = new PipedOutputStream();
        PipedInputStream stdin = new PipedInputStream();
        System.setIn(stdin);
        PumpStreamHandler streamHandler = new PumpStreamHandler(stdout,stderr,stdin);

        try {
            executor.setStreamHandler(streamHandler);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new PipedInputStream(stdout)));
            BufferedReader bufferedReaderErr = new BufferedReader(new InputStreamReader(new PipedInputStream(stderr)));
            this.bufferedReader = bufferedReader;
            this.bufferedReaderErr = bufferedReaderErr;
            this.processInput = new BufferedOutputStream(new PipedOutputStream(stdin));
            this.streamHandler = streamHandler;
            executor.setWatchdog(watchdog);
            executor.execute(cmdLine,resultHandler);
            streamHandler.start();
        }catch (Exception e){
            e.printStackTrace();
        }
        log.debug("[Robot][" + this +"]");
    }
}