package com.shengxun.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * Created by ldh on 2018/7/2.
 */
@Component
public class RobotThreadConf {

    private static final Logger log = LoggerFactory.getLogger(RobotThreadConf.class);

    private Executor executor;

    public Executor getTrialThreadExecutor() {
        if (executor == null){
            ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
            threadPool.setCorePoolSize(5);//当前线程数
            threadPool.setMaxPoolSize(50);// 最大线程数
            threadPool.setQueueCapacity(20);//线程池所使用的缓冲队列
            threadPool.setWaitForTasksToCompleteOnShutdown(true);//等待任务在关机时完成--表明等待所有线程执行完
            threadPool.setAwaitTerminationSeconds(60 * 15);// 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
            threadPool.setThreadNamePrefix("MyAsync-");//  线程名称前缀
            threadPool.initialize(); // 初始化
            log.info("[TextTryThreadConfig——>][线程池已开启]");
            this.executor = threadPool;
        }
        return executor;
    }

    @Bean
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }

    /**
     * 自定义异常处理类
     */
    public class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        //手动处理捕获的异常
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            log.info("[MyAsyncExceptionHandler——>][捕获线程池异常信息]");
            log.info("Exception message - " + throwable.getMessage());
            log.info("Method name - " + method.getName());
            for (Object param : obj) {
                log.info("Parameter value - " + param);
            }
        }
    }
}