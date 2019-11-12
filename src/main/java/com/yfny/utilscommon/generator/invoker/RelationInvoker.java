package com.yfny.utilscommon.generator.invoker;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.invoker.base.AbstractBuilder;
import com.yfny.utilscommon.generator.invoker.base.AbstractInvoker;
import com.yfny.utilscommon.generator.invoker.base.Invoker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器关联对象关系调度器
 * Created by jisongZhou on 2019/3/5.
 **/
public class RelationInvoker extends AbstractInvoker {

    @Override
    protected void getTableInfos() throws SQLException {

    }

    @Override
    protected void initTasks() {
        taskQueue.initRelationTasks(materialList);
    }

    private List<BCodeMaterials> materialList = new ArrayList<>();

    public static class Builder extends AbstractBuilder {
        
        public final static String ONE_TO_ONE = "ONE2ONE";
        public final static String ONE_TO_MANY = "ONE2MANY";
        public final static String MANY_TO_ONE = "MANY2ONE";

        private RelationInvoker invoker = new RelationInvoker();

        public Builder setMaterials(BCodeMaterials materials) {
            invoker.setMaterials(materials);
            return this;
        }

        public Builder setMaterialList(List<BCodeMaterials> materialList) {
            invoker.setMaterialList(materialList);
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

    public List<BCodeMaterials> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<BCodeMaterials> materialList) {
        this.materialList = materialList;
    }
}
