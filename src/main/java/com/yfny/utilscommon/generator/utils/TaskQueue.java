package com.yfny.utilscommon.generator.utils;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.entity.ColumnInfo;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.task.frame.*;
import com.yfny.utilscommon.generator.task.scheme.aspect.BeforeServiceImplTask;
import com.yfny.utilscommon.generator.task.scheme.builder.SqlBuilderTask;
import com.yfny.utilscommon.generator.task.scheme.composite.CompositeTask;
import com.yfny.utilscommon.generator.task.scheme.constant.ConstantTask;
import com.yfny.utilscommon.generator.task.scheme.controller.ControllerTask;
import com.yfny.utilscommon.generator.task.scheme.entity.EntityAddTask;
import com.yfny.utilscommon.generator.task.scheme.entity.EntityTask;
import com.yfny.utilscommon.generator.task.scheme.fallback.HystrixTask;
import com.yfny.utilscommon.generator.task.scheme.handler.ExceptionHandlerTask;
import com.yfny.utilscommon.generator.task.scheme.mapper.MapperAddTask;
import com.yfny.utilscommon.generator.task.scheme.mapper.MapperTask;
import com.yfny.utilscommon.generator.task.scheme.service.ClientTask;
import com.yfny.utilscommon.generator.task.scheme.service.ServiceImplTask;
import com.yfny.utilscommon.generator.task.scheme.service.ServiceTask;
import com.yfny.utilscommon.generator.task.scheme.test.APIBaseTestTask;
import com.yfny.utilscommon.generator.task.scheme.valid.ValidTask;

import java.util.LinkedList;
import java.util.List;

/**
 * 代码生成器任务执行
 * Created by jisongZhou on 2019/3/5.
 **/
public class TaskQueue {

    private LinkedList<AbstractTask> taskQueue = new LinkedList<>();

    /******************************************************此下方法为改造新增开始*****************************************************************/

    public void initFrameTasks(int type) {
        taskQueue.add(new GitIgnoreTask(type));
        taskQueue.add(new ReadMeTask(type));
        taskQueue.add(new PomTask(type));
        if (type == FileUtil.PRODUCER) {
            taskQueue.add(new MainApplicationTask(type));
            taskQueue.add(new MainYamlTask(type));
            taskQueue.add(new MainBootstrapTask(type));
            taskQueue.add(new TestApplicationTask(type));
            taskQueue.add(new TestYamlTask(type));
            taskQueue.add(new BeforeServiceImplTask());
            taskQueue.add(new ExceptionHandlerTask());
            taskQueue.add(new APIBaseTestTask());
        }
    }

    public void initSingleTasks(BCodeMaterials materials, List<ColumnInfo> tableInfos) {
        taskQueue.add(new EntityTask(materials, tableInfos));
        taskQueue.add(new ConstantTask(materials));
    }

    public void initProducerTasks(BCodeMaterials materials, List<ColumnInfo> tableInfos) {
        taskQueue.add(new SqlBuilderTask(materials, tableInfos));
        taskQueue.add(new MapperTask(materials, tableInfos));
        taskQueue.add(new ServiceTask(materials));
        taskQueue.add(new ServiceImplTask(materials));
        taskQueue.add(new CompositeTask(materials));
        taskQueue.add(new ValidTask(materials));
        taskQueue.add(new ControllerTask(materials));
    }

    public void initConsumerTasks(BCodeMaterials materials) {
        taskQueue.add(new ClientTask(materials));
        taskQueue.add(new HystrixTask(materials));
    }

    public void initRelationTasks(List<BCodeMaterials> materialList) {
        taskQueue.add(new EntityAddTask(materialList));
        taskQueue.add(new MapperAddTask(materialList));
    }

    public void initTestTasks() {
        //taskQueue.add(new APIUnitTestTask());
    }

    /******************************************************此下方法为改造新增结束*****************************************************************/

    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    public AbstractTask poll() {
        return taskQueue.poll();
    }

}
