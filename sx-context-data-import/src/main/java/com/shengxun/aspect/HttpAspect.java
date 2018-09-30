package com.shengxun.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by chenwei
 * on 2018/1/10 下午3:47.
 */
@Aspect
@Component
public class HttpAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.shengxun.controller..*(..))")
    public void log() {

    }

    /*@Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            //url
            LOGGER.info("url={}", request.getRequestURL());

            //method
            LOGGER.info("method={}", request.getMethod());

            //ip
            LOGGER.info("ip={}", request.getRemoteAddr());

            //类方法
            LOGGER.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

            //参数
            LOGGER.info("args={}", joinPoint.getArgs());

            LOGGER.info("-----------Before Before Before Before---------");
        }catch (Exception e){
            LOGGER.error("do Before error");
        }
    }

    @After("log()")
    public void doAfter() {

    }*/


    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        LOGGER.info("response={}", object);
    }
}
