package com.shengxun.bean;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public class InitListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("容器初始化或者刷新时触发该事件,执行一次");
    }
}
