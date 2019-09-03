package com.yfny.utilscommon.annotation.businesslog;


import java.lang.annotation.*;

/**
 * 自定义业务日志注解类
 * Created by zileShi on 2019/2/27.
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessLog {

    String value() default "";
}
