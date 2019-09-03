package com.yfny.utilscommon.generator.invoker;

import com.yfny.utilscommon.generator.invoker.base.AbstractBuilder;
import com.yfny.utilscommon.generator.invoker.base.AbstractInvoker;
import com.yfny.utilscommon.generator.invoker.base.Invoker;
import com.yfny.utilscommon.generator.utils.StringUtil;

import java.sql.SQLException;

/**
 * 代码生成器服务生产者调度器
 * Created by jisongZhou on 2019/3/27.
 **/
public class ProducerInvoker extends AbstractInvoker {

    @Override
    protected void getTableInfos() throws SQLException {
        tableInfos = connectionUtil.getMetaData(tableName);
    }

    @Override
    protected void initTasks() {
        taskQueue.initProducerTasks(className, tableName, description, tableInfos, first);
    }

    public static class Builder extends AbstractBuilder {

        private ProducerInvoker invoker = new ProducerInvoker();

        public Builder setTableName(String tableName) {
            invoker.setTableName(tableName);
            return this;
        }

        public Builder setClassName(String className) {
            invoker.setClassName(className);
            return this;
        }

        public Builder setDescription(String description) {
            invoker.setDescription(description);
            return this;
        }

        public Builder setFirst(boolean isFirst) {
            invoker.setFirst(isFirst);
            return this;
        }

        @Override
        public Invoker build() {
            if (!isParamtersValid()) {
                return null;
            }
            return invoker;
        }

        @Override
        public void checkBeforeBuild() throws Exception {
            if (StringUtil.isBlank(invoker.getClassName())) {
                throw new Exception("ClassName can not be null, please set className.");
            }
        }
    }

}
