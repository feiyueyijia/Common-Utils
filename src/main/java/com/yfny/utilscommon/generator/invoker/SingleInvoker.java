package com.yfny.utilscommon.generator.invoker;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.invoker.base.AbstractBuilder;
import com.yfny.utilscommon.generator.invoker.base.AbstractInvoker;
import com.yfny.utilscommon.generator.invoker.base.Invoker;
import com.yfny.utilscommon.generator.utils.GeneratorUtil;
import com.yfny.utilscommon.generator.utils.StringUtil;

import java.sql.SQLException;

/**
 * 代码生成器单体调度器
 * Created by jisongZhou on 2019/3/5.
 **/
public class SingleInvoker extends AbstractInvoker {

    @Override
    protected void getTableInfos() throws SQLException {
        tableInfos = connectionUtil.getMetaData(materials.getTableName());
    }

    @Override
    protected void initTasks() {
        taskQueue.initSingleTasks(materials, tableInfos);
    }

    public static class Builder extends AbstractBuilder {
        private SingleInvoker invoker = new SingleInvoker();

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
            if (StringUtil.isBlank(invoker.getMaterials().getTableName())) {
                throw new Exception("Expect table's name, but get a blank String.");
            }
            if (StringUtil.isBlank(invoker.getMaterials().getClassName())) {
                invoker.getMaterials().setClassName(GeneratorUtil.generateClassName(invoker.getMaterials().getTableName()));
            }
        }
    }

}
