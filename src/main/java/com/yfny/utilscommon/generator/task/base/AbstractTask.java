package com.yfny.utilscommon.generator.task.base;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.entity.ColumnInfo;
import com.yfny.utilscommon.generator.entity.Configuration;
import com.yfny.utilscommon.generator.utils.ConfigUtil;
import com.yfny.utilscommon.generator.utils.StringUtil;
import com.yfny.utilscommon.util.StringUtils;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码生成器抽象任务
 * Created by jisongZhou on 2019/3/5.
 **/
public abstract class AbstractTask implements Serializable {
    protected int type;//新增属性--服务类型（生产者或者消费者）
    protected BCodeMaterials materials = new BCodeMaterials();//数据库表结构信息
    protected List<ColumnInfo> tableInfos;//数据库表字段属性
    protected List<BCodeMaterials> materialList = new ArrayList<>();//数据库表结构信息

    public AbstractTask() {

    }

    public AbstractTask(int type) {
        this.type = type;
    }

    public AbstractTask(BCodeMaterials materials) {
        this.materials = materials;
    }

    public AbstractTask(List<BCodeMaterials> materialList) {
        this.materialList = materialList;
    }

    public AbstractTask(BCodeMaterials materials, List<ColumnInfo> tableInfos) {
        this.materials = materials;
        this.tableInfos = tableInfos;
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

    protected Map<String, Object> constructData() {
        Map<String, Object> dataMap = new HashMap<>();

        Configuration configuration = ConfigUtil.getConfiguration();
        String author = configuration.getAuthor();
        String basePackageName = configuration.getPackageName();
        String entityPackageName = configuration.getPath().getEntity();
        String mapperPackageName = configuration.getPath().getMapper();
        String servicePackageName = configuration.getPath().getService();
        String controllerPackageName = configuration.getPath().getController();
        String projectName = configuration.getProjectName();
        String[] packageNames = StringUtils.split(basePackageName, "//.");
        String applicationName = packageNames[packageNames.length - 1];

        dataMap.put("BasePackageName", basePackageName);
        dataMap.put("EntityPackageName", entityPackageName);
        dataMap.put("ConstantPackageName", "constant");
        dataMap.put("SqlBuilderPackageName", "builder");
        dataMap.put("MapperPackageName", mapperPackageName);
        dataMap.put("ServicePackageName", servicePackageName);
        dataMap.put("AspectPackageName", "aspect");
        dataMap.put("CompositePackageName", "composite");
        dataMap.put("ValidPackageName", "valid");
        dataMap.put("ControllerPackageName", controllerPackageName);
        dataMap.put("APIUnitTestPackageName", "unit");
        dataMap.put("APIBaseTestPackageName", "base");
        dataMap.put("HystrixPackageName", "fallback");
        dataMap.put("ClientPackageName", "client");
        dataMap.put("Author", author);
        dataMap.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        dataMap.put("ColumnInfoList", tableInfos);
        dataMap.put("ClassName", materials.getClassName());
        dataMap.put("TableName", materials.getTableName());
        dataMap.put("ApplicationName", applicationName);
        dataMap.put("ProjectName", projectName);
        dataMap.put("Descriptions", materials.getDescription());
        dataMap.put("PrimaryKey", materials.getPrimaryKey());
        dataMap.put("PkProperty", materials.getPkProperty());

        return dataMap;
    }

}
