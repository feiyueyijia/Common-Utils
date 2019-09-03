package com.yfny.utilscommon.generator.task.base;

import com.yfny.utilscommon.generator.entity.ColumnInfo;
import com.yfny.utilscommon.generator.utils.ConfigUtil;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器抽象任务
 * Created by jisongZhou on 2019/3/5.
 **/
public abstract class AbstractTask implements Serializable {
    protected String tableName;//数据库表名称
    protected String className;//Java对象类名称
    protected String description;//新增属性--描述
    protected String applicationName;//新增属性--微服务名称
    protected String foreignKey;//外键字段名
    protected Map<String, String> relationClassNameMap;//相关对象类集合
    protected List<ColumnInfo> tableInfos;//数据库表字段属性

    /**
     * Controller、Service、Dao
     *
     * @param className
     */
    public AbstractTask(String className) {
        this.className = className;
    }

    /**
     * Controller、Service、Dao
     *
     * @param className
     * @param description
     */
    public AbstractTask(String className, String description) {
        this.className = className;
        this.description = description;
    }

    /**
     * Controller、Service、Dao
     *
     * @param className
     * @param description
     * @param applicationName
     */
    public AbstractTask(String className, String description, String applicationName) {
        this.className = className;
        this.description = description;
        this.applicationName = applicationName;
    }

    /**
     * SqlBuilder
     *
     * @param className
     * @param tableName
     * @param description
     * @param tableInfos
     */
    public AbstractTask(String className, String tableName, String description, List<ColumnInfo> tableInfos) {
        this.className = className;
        this.tableName = tableName;
        this.description = description;
        this.tableInfos = tableInfos;
    }

    /**
     * Entity
     *
     * @param className
     * @param tableName
     * @param description
     * @param tableInfos
     * @param foreignKey
     */
    public AbstractTask(String className, String tableName, String description, List<ColumnInfo> tableInfos, String foreignKey) {
        this.className = className;
        this.tableName = tableName;
        this.description = description;
        this.tableInfos = tableInfos;
        this.foreignKey = foreignKey;
    }

    /**
     * Add
     *
     * @param className
     * @param foreignKey
     * @param relationClassNameMap
     */
    public AbstractTask(String className, String foreignKey, Map<String, String> relationClassNameMap) {
        this.className = className;
        this.foreignKey = foreignKey;
        this.relationClassNameMap = relationClassNameMap;
    }

    public abstract void run() throws IOException, TemplateException;

    @Deprecated
    protected void createFilePathIfNotExists(String filePath) {
        if (!StringUtil.isBlank(ConfigUtil.getConfiguration().getPackageName())) { // 用户配置了包名，不进行检测
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) { // 检测文件路径是否存在，不存在则创建
            file.mkdir();
        }
    }

}
