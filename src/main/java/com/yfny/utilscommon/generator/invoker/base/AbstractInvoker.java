package com.yfny.utilscommon.generator.invoker.base;

import com.yfny.utilscommon.generator.db.ConnectionUtil;
import com.yfny.utilscommon.generator.entity.ColumnInfo;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.ConfigUtil;
import com.yfny.utilscommon.generator.utils.StringUtil;
import com.yfny.utilscommon.generator.utils.TaskQueue;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 代码生成器抽象调度器
 * Created by jisongZhou on 2019/3/5.
 **/
public abstract class AbstractInvoker implements Invoker {
    protected String tableName;
    protected String className;
    protected String description;//新增属性--描述
    protected String foreignKey = "";//外键
    protected boolean first = false;
    protected Map<String, String> relationClassNameMap;
    protected List<ColumnInfo> tableInfos;
    protected ConnectionUtil connectionUtil = new ConnectionUtil();
    protected TaskQueue taskQueue = new TaskQueue();
    private ExecutorService executorPool = Executors.newFixedThreadPool(8);

    private void initDataSource() throws Exception {
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getDb().getUrl())) {
            if (!this.connectionUtil.initConnection()) {
                throw new Exception("Failed to connect to database at url:" + ConfigUtil.getConfiguration().getDb().getUrl());
            }
            getTableInfos();
            connectionUtil.close();
        }
    }

    protected abstract void getTableInfos() throws SQLException;

    protected abstract void initTasks();

    @Override
    public void execute() {
        try {
            initDataSource();
            initTasks();
            while (!taskQueue.isEmpty()) {
                AbstractTask task = taskQueue.poll();
                executorPool.execute(() -> {
                    try {
                        task.run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                });
            }
            executorPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void setRelationClassNameMap(Map<String, String> relationClassNameMap) {
        this.relationClassNameMap = relationClassNameMap;
    }

    public String getTableName() {
        return tableName;
    }

    public String getClassName() {
        return className;
    }

    public String getDescription() {
        return description;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public boolean isFirst() {
        return first;
    }

    public Map<String, String> getRelationClassNameMap() {
        return relationClassNameMap;
    }
}
