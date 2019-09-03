package com.yfny.utilscommon.generator.task.producer;

import com.yfny.utilscommon.generator.invoker.RelationInvoker;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.ConfigUtil;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.FreemarketConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器生产者（微服务）数据交互层任务
 * Created by jisongZhou on 2019/3/26.
 **/
public class ProducerMapperAddTask extends AbstractTask {

    public ProducerMapperAddTask(String className, String foreignKey, Map<String, String> relationClassNameMap) {
        super(className, foreignKey, relationClassNameMap);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成副类文件数据
        String foreignKeyPropertyName = StringUtil.columnName2PropertyName(foreignKey);
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getMapper());
        for (String relationClass : relationClassNameMap.keySet()) {
            // 生成Mapper填充数据
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("ClassName", relationClass);
            dataMap.put("RelationType", relationClassNameMap.get(relationClass));
            dataMap.put("ForeignKeyColumnName", foreignKey);
            dataMap.put("ForeignKeyPropertyName", foreignKeyPropertyName);
            dataMap.put("ClassType", RelationInvoker.Builder.VICE_CLASS);

            String fileName = relationClass + "Mapper.java";
            // 填充Mapper文件
            System.out.println("Generating Add " + fileName);
            FileUtil.addToJavaEnd(FreemarketConfigUtils.TYPE_ADD_PRODUCER_MAPPER, dataMap, filePath + fileName);
        }
        // 生成主类文件数据
        // 生成Mapper填充数据
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        dataMap.put("MapperPackageName", ConfigUtil.getConfiguration().getPath().getMapper());
        dataMap.put("ClassName", className);
        dataMap.put("PrimaryKey", "XQDH");
        dataMap.put("RelationClassNameMap", relationClassNameMap);
        dataMap.put("ForeignKeyPropertyName", foreignKeyPropertyName);
        dataMap.put("ClassType", RelationInvoker.Builder.MAIN_CLASS);

        String fileName = className + "Mapper.java";
        // 填充Mapper文件
        System.out.println("Generating Add " + fileName);
        FileUtil.addToJavaEnd(FreemarketConfigUtils.TYPE_ADD_PRODUCER_MAPPER, dataMap, filePath + fileName);
    }
}
