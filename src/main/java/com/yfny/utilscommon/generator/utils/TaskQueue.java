package com.yfny.utilscommon.generator.utils;

import com.yfny.utilscommon.generator.entity.ColumnInfo;
import com.yfny.utilscommon.generator.invoker.RelationInvoker;
import com.yfny.utilscommon.generator.task.APIBaseTestTask;
import com.yfny.utilscommon.generator.task.APIUnitTestTask;
import com.yfny.utilscommon.generator.task.EntityAddTask;
import com.yfny.utilscommon.generator.task.EntityTask;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.task.consumer.ConsumerClientTask;
import com.yfny.utilscommon.generator.task.consumer.ConsumerControllerTask;
import com.yfny.utilscommon.generator.task.consumer.ConsumerFutureTask;
import com.yfny.utilscommon.generator.task.consumer.ConsumerHystrixTask;
import com.yfny.utilscommon.generator.task.producer.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器任务执行
 * Created by jisongZhou on 2019/3/5.
 **/
public class TaskQueue {

    private LinkedList<AbstractTask> taskQueue = new LinkedList<>();

    /******************************************************此下方法为改造新增开始*****************************************************************/

    public void initSingleTasks(String className, String tableName, String description, List<ColumnInfo> tableInfos, String foreignKey) {
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getEntity())) {
            taskQueue.add(new EntityTask(className, tableName, description, tableInfos, foreignKey));
        }
    }

    public void initProducerTasks(String className, String tableName, String description, List<ColumnInfo> tableInfos, boolean isFirst) {
        if (isFirst) {
            taskQueue.add(new ProducerBeforeServiceImplTask(className, description));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getSqlbuilder())) {
            taskQueue.add(new ProducerSqlBuilderTask(className, tableName, description, tableInfos));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getMapper())) {
            taskQueue.add(new ProducerMapperTask(className, description));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getService())) {
            taskQueue.add(new ProducerServiceImplTask(className, description));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getComposite())) {
            taskQueue.add(new ProducerCompositeTask(className, description));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getFuture())) {
            taskQueue.add(new ProducerFutureTask(className, description));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getController())) {
            taskQueue.add(new ProducerControllerTask(className, description));
        }
    }

    public void initConsumerTasks(String className, String description, String applicationName) {
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getService())) {
            taskQueue.add(new ConsumerClientTask(className, description, applicationName));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getHystrix())) {
            taskQueue.add(new ConsumerHystrixTask(className, description));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getFuture())) {
            taskQueue.add(new ConsumerFutureTask(className, description));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getController())) {
            taskQueue.add(new ConsumerControllerTask(className, description));
        }
    }

    public void initRelationTasks(String className, String foreignKey, Map<String, String> relationClassNameMap, String writeType) {
        if (RelationInvoker.Builder.ENTITY_FILE.equals(writeType)) {
            if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getEntity())) {
                taskQueue.add(new EntityAddTask(className, relationClassNameMap));
            }
        } else if (RelationInvoker.Builder.PRODUCER_FILE.equals(writeType)) {
            if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getMapper())) {
                taskQueue.add(new ProducerMapperAddTask(className, foreignKey, relationClassNameMap));
            }
        } else if (RelationInvoker.Builder.CONSUMER_FILE.equals(writeType)) {

        }
    }

    public void initTestTasks(String className) {
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getBasetest())) {
            taskQueue.add(new APIBaseTestTask(className));
        }
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getUnittest())) {
            taskQueue.add(new APIUnitTestTask(className));
        }
    }

    /******************************************************此下方法为改造新增结束*****************************************************************/

    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    public AbstractTask poll() {
        return taskQueue.poll();
    }

}
