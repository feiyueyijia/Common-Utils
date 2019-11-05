package com.yfny.utilscommon.annotation.businesslog;

import com.yfny.utilscommon.util.PropertiesLoader;
import com.yfny.utilscommon.util.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;


/**
 * 自定义业务日志注解类AOP切面
 * Created by zileShi on 2019/2/27.
 **/
@Aspect
@Component
public class BusinessLogAOP implements Ordered {

    private final Logger logger = LoggerFactory.getLogger(BusinessLogAOP.class);

    private static PropertiesLoader logmanager = new PropertiesLoader("props/business_log.properties");

    private Instant beforeTime = null;

    private Instant afterTime = null;

    @Pointcut("@annotation(com.yfny.utilscommon.annotation.businesslog.BusinessLog)")
    public void annotationPointCut() {
    }


    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) {
        //System.out.println("before方法执行前");
        //开始时间
        beforeTime = Instant.now();
    }


    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        //System.out.println("after方法执行后");


        //1.获取该方法传入的参数，目前不做详细处理
//        Object[] args = joinPoint.getArgs();
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        //2.获取到方法的所有参数名称的字符串数组
//        String[] parameterNames = methodSignature.getParameterNames();
//        System.out.println("---------------参数列表开始-------------------------");
//        for (int i =0 ,len=parameterNames.length;i < len ;i++){
//            System.out.println("参数名："+ parameterNames[i] + " = " +args[i]);
//        }
//        System.out.println("---------------参数列表结束-------------------------");

        StringBuilder buf = new StringBuilder();
        //获取类名
        buf.append(joinPoint.getTarget().getClass().getSimpleName());
        //获取方法名
        buf.append("_").append(joinPoint.getSignature().getName());

        //根据类名_方法名获取对应的中文解释
        String message = logmanager.getProperty(buf.toString());

        //根据session获取用户名暂时用root代替
        //代码今后再处理...
        String username = "root";

        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = formatter.format(new Date());

        //结束时间
        afterTime = Instant.now();

        //时间差
        String timeDifference = String.valueOf(Duration.between(beforeTime, afterTime).toMillis());

        String[] params = new String[]{username, currentTime, timeDifference};

        //整合业务信息
        String businessMessage = StringUtils.replaceMessage(message, params);

        //输出到指定文件上，具体看service-feign的logback.xml配置
        logger.debug(businessMessage);

    }

    @Override
    public int getOrder() {
        return 1001;
    }
}
