package com.garyhu.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author: garyhu
 * @since: 2018/11/22 0022
 * @decription: 切面类
 */
//@Configuration
//@Aspect
public class AopConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopConfig.class);

    @Around("@within(org.springframework.stereotype.Controller)")
    public Object simpleAop(final ProceedingJoinPoint pjp)throws Throwable{
        try{
            Object[] args = pjp.getArgs();
            LOGGER.info("args : ", Arrays.asList(args));
            System.out.println("args: "+Arrays.asList(args));
            // 调用原来的方法
            Object proceed = pjp.proceed();
            LOGGER.info("proceed : ", proceed);
            System.out.println("proceed: "+proceed);
            // 返回原方法的执行结果
            return proceed;
        }catch (Throwable e){
            throw e;
        }
    }
}
