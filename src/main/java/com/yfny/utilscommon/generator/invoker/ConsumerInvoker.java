package com.yfny.utilscommon.generator.invoker;

import com.yfny.utilscommon.generator.invoker.base.AbstractBuilder;
import com.yfny.utilscommon.generator.invoker.base.AbstractInvoker;
import com.yfny.utilscommon.generator.invoker.base.Invoker;
import com.yfny.utilscommon.generator.utils.StringUtil;

import java.sql.SQLException;

/**
 * 代码生成器服务消费者调度器
 * Created by jisongZhou on 2019/3/28.
 **/
public class ConsumerInvoker extends AbstractInvoker {

    protected String applicationName;

    @Override
    protected void getTableInfos() throws SQLException {

    }

    @Override
    protected void initTasks() {
        taskQueue.initConsumerTasks(className, description, applicationName);
    }

    public static class Builder extends AbstractBuilder {

        private ConsumerInvoker invoker = new ConsumerInvoker();

        public Builder setClassName(String className) {
            invoker.setClassName(className);
            return this;
        }

        public Builder setDescription(String description) {
            invoker.setDescription(description);
            return this;
        }

        public Builder setApplicationName(String applicationName) {
            invoker.setApplicationName(applicationName);
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
