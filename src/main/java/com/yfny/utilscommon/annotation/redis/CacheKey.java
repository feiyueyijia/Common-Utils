package com.yfny.utilscommon.annotation.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Redis缓存自定义键值
 * Created by jisongZhou on 2017/12/21.
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface CacheKey {

    public String field() default "";    //键值包含字段名
}
