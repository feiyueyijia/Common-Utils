package com.yfny.utilscommon.generator.application;

import com.yfny.utilscommon.generator.db.ConnectionUtil;
import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.invoker.*;
import com.yfny.utilscommon.generator.invoker.base.Invoker;
import com.yfny.utilscommon.generator.utils.FileUtil;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 代码生成器测试主类
 * Created by jisongZhou on 2019/3/5.
 **/
public class Main {

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        ConnectionUtil connectionUtil = new ConnectionUtil();
        List<BCodeMaterials> materials = connectionUtil.getTablesData();

        Thread frameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producerFrameInvokerTest();
                    consumerFrameInvokerTest();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException("CountDownLatch等待失败...", e);
                }
                countDownLatch.countDown();
            }
        }, "Frame-Thread");

        Thread singleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (BCodeMaterials material : materials) {
                        singleInvokerTest(material);
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException("CountDownLatch等待失败...", e);
                }
                countDownLatch.countDown();
            }
        }, "Single-Thread");

        Thread codeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (BCodeMaterials material : materials) {
                        producerInvokerTest(material);
                        consumerInvokerTest(material);
                        //apiTestInvokerTest();
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException("CountDownLatch等待失败...", e);
                }
                countDownLatch.countDown();
            }
        }, "Code-Thread");

        Thread relationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    relationInvoker(materials);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException("CountDownLatch等待失败...", e);
                }
            }
        }, "Relation-Thread");

        frameThread.start();
        singleThread.start();
        codeThread.start();
        relationThread.start();
    }

    public static void producerFrameInvokerTest() {
        Invoker invoker = new FrameInvoker.Builder()
                .setType(FileUtil.PRODUCER)
                .build();
        invoker.execute();
    }

    public static void consumerFrameInvokerTest() {
        Invoker invoker = new FrameInvoker.Builder()
                .setType(FileUtil.CONSUMER)
                .build();
        invoker.execute();
    }

    public static void singleInvokerTest(BCodeMaterials material) {
        Invoker invoker = new SingleInvoker.Builder()
                .setMaterials(material)
                .build();
        invoker.execute();
    }

    public static void producerInvokerTest(BCodeMaterials material) {
        Invoker invoker = new ProducerInvoker.Builder()
                .setMaterials(material)
                .build();
        invoker.execute();
    }

    public static void consumerInvokerTest(BCodeMaterials material) {
        Invoker invoker = new ConsumerInvoker.Builder()
                .setMaterials(material)
                .build();
        invoker.execute();
    }

    public static void relationInvoker(List<BCodeMaterials> materials) {
        Invoker invoker = new RelationInvoker.Builder()
                .setMaterialList(materials)
                .build();
        invoker.execute();
    }

    public static void apiTestInvokerTest() {
        Invoker invoker = new TestInvoker.Builder()
                .build();
        invoker.execute();
    }

}
