package com.shengxun;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.shengxun.bean.InitListener;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

//解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@Import(FdfsClientConfig.class)//只需要一行注解 @Import(FdfsClientConfig.class)就可以拥有带有连接池的FastDFS Java客户端了
@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class ShengxunApplication {
    public static void main(String[] args) {
        InitListener listener = new InitListener();
        SpringApplication application = new SpringApplication(ShengxunApplication.class);
        application.addListeners(listener);
        application.run(args);
    }

    //Tomcat large file upload connection reset
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });
        return tomcat;
    }

    /**
     * 使用 websockt注解的时候，使用@EnableScheduling注解
     * 启动的时候一直报错，增加这个bean 则报错解决。
     * 报错信息：  Unexpected use of scheduler.
     *https://stackoverflow.com/questions/49343692/websocketconfigurer-and-scheduled-are-not-work-well-in-an-application
     *
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler(){

        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        return taskScheduler;
    }
}