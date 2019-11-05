package com.yfny.utilscommon.generator.invoker;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
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
        tableInfos = connectionUtil.getMetaData(materials.getTableName());
    }

    @Override
    protected void initTasks() {
        taskQueue.initProducerTasks(materials, tableInfos);
    }

    public static class Builder extends AbstractBuilder {

        private ProducerInvoker invoker = new ProducerInvoker();

        public Builder setMaterials(BCodeMaterials materials) {
            invoker.setMaterials(materials);
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
            if (StringUtil.isBlank(invoker.getMaterials().getClassName())) {
                throw new Exception("ClassName can not be null, please set className.");
            }
        }
    }

}
