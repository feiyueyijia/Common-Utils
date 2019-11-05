package com.yfny.utilscommon.generator.application;

import com.yfny.utilscommon.generator.db.ConnectionUtil;
import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.invoker.*;
import com.yfny.utilscommon.generator.invoker.base.Invoker;
import com.yfny.utilscommon.generator.utils.FileUtil;

import java.util.List;

/**
 * 代码生成器测试主类
 * Created by jisongZhou on 2019/3/5.
 **/
public class Main {

    public static void main(String[] args) throws Exception {
        ConnectionUtil connectionUtil = new ConnectionUtil();
        List<BCodeMaterials> materials = connectionUtil.getTablesData();
        producerFrameInvokerTest();
        consumerFrameInvokerTest();
        for (BCodeMaterials material : materials) {
            singleInvokerTest(material);
            producerInvokerTest(material);
            consumerInvokerTest(material);
            //apiTestInvokerTest();
        }
        Thread.sleep(5000);
        relationInvoker(materials);
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
