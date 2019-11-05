package com.yfny.utilscommon.generator.invoker;

import com.yfny.utilscommon.generator.invoker.base.AbstractBuilder;
import com.yfny.utilscommon.generator.invoker.base.AbstractInvoker;
import com.yfny.utilscommon.generator.invoker.base.Invoker;

import java.sql.SQLException;

/**
 * 代码生成器框架结构调度器
 * Created by jisongZhou on 2019/9/23.
 **/
public class FrameInvoker extends AbstractInvoker {

    @Override
    protected void getTableInfos() throws SQLException {

    }

    @Override
    protected void initTasks() {
        taskQueue.initFrameTasks(type);
    }

    public static class Builder extends AbstractBuilder {
        private FrameInvoker invoker = new FrameInvoker();

        public Builder setType(int type) {
            invoker.setType(type);
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

        }
    }

}
