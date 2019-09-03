package com.yfny.utilscommon.generator.invoker;

import com.yfny.utilscommon.generator.invoker.base.AbstractBuilder;
import com.yfny.utilscommon.generator.invoker.base.AbstractInvoker;
import com.yfny.utilscommon.generator.invoker.base.Invoker;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器关联对象关系调度器
 * Created by jisongZhou on 2019/3/5.
 **/
public class RelationInvoker extends AbstractInvoker {

    private String writeType;

    @Override
    protected void getTableInfos() throws SQLException {

    }

    @Override
    protected void initTasks() {
        taskQueue.initRelationTasks(className, foreignKey, relationClassNameMap, writeType);
    }

    public static class Builder extends AbstractBuilder {

        public final static String ENTITY_FILE = "ENTITY";
        public final static String CONSUMER_FILE = "CONSUMER";
        public final static String PRODUCER_FILE = "PRODUCER";

        public final static String MAIN_CLASS = "MAIN";
        public final static String VICE_CLASS = "VICE";

        public final static String ONE_TO_ONE = "ONE2ONE";
        public final static String ONE_TO_MANY = "ONE2MANY";

        private RelationInvoker invoker = new RelationInvoker();

        private Map<String, String> relationClassNameMap = new HashMap<>();

        public Builder setClassName(String className) {
            invoker.setClassName(className);
            return this;
        }

        public Builder setForeignKey(String foreignKey) {
            invoker.setForeignKey(foreignKey);
            return this;
        }

        public Builder setRelationClass(String relationClassName, String relationType) {
            relationClassNameMap.put(relationClassName, relationType);
            invoker.setRelationClassNameMap(relationClassNameMap);
            return this;
        }

        public Builder setWriteType(String writeType) {
            invoker.setWriteType(writeType);
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

    public String getWriteType() {
        return writeType;
    }

    public void setWriteType(String writeType) {
        this.writeType = writeType;
    }

}
