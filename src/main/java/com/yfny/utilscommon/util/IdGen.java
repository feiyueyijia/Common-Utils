/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yfny.utilscommon.util;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * <p>
 * Author jisongZhou
 * Date  2019/11/19
 */
@Service
@Lazy(false)
public class IdGen {

    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static String randomLongStr() {
        return randomLong() + "";
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     */
    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return Encodes.encodeBase62(randomBytes);
    }

    public static void main(String[] args) {
        System.out.println(IdGen.uuid());
        System.out.println(IdGen.uuid().length());
        for (int i = 0; i < 10; i++) {
            System.out.println(IdGen.randomLongStr() + "  " + IdGen.randomBase62(5));
        }
    }

}
