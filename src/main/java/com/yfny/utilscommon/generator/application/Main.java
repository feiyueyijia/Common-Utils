package com.yfny.utilscommon.generator.application;

import com.yfny.utilscommon.generator.invoker.*;
import com.yfny.utilscommon.generator.invoker.base.Invoker;

/**
 * 代码生成器测试主类
 * Created by jisongZhou on 2019/3/5.
 **/
public class Main {

    public static void main(String[] args) {
        singleInvokerTest();
    }

    public static void singleInvokerTest() {
        Invoker invoker = new SingleInvoker.Builder()
                .setTableName("user")
                .setClassName("User")
                .setDescription("用户")
                .build();
        invoker.execute();
    }

    public static void producerInvokerTest() {
        Invoker invoker = new ProducerInvoker.Builder()
                .setTableName("user")
                .setClassName("User")
                .setDescription("用户")
                .setFirst(true)
                .build();
        invoker.execute();
    }

    public void consumerInvokerTest() {
        Invoker invoker = new ConsumerInvoker.Builder()
                .setClassName("User")
                .setDescription("用户")
                .setApplicationName("service-user")
                .build();
        invoker.execute();
    }

    public void relationInvoker() {
        Invoker invoker = new RelationInvoker.Builder()
                .setClassName("User")
                .setForeignKey("userId")
                .setRelationClass("Car", RelationInvoker.Builder.ONE_TO_MANY)
                .setWriteType(RelationInvoker.Builder.ENTITY_FILE)
                .build();
        invoker.execute();
    }

    public static void apiTestInvokerTest() {
        Invoker invoker = new TestInvoker.Builder()
                .build();
        invoker.execute();
    }

}
