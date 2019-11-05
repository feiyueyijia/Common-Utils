package com.yfny.utilscommon.generator.task.scheme.mapper;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.entity.Configuration;
import com.yfny.utilscommon.generator.entity.RelationMaterials;
import com.yfny.utilscommon.generator.invoker.RelationInvoker;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.ConfigUtil;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器生产者（微服务）数据交互层任务
 * Created by jisongZhou on 2019/3/26.
 **/
public class MapperAddTask extends AbstractTask {

    public MapperAddTask(List<BCodeMaterials> materialList) {
        super(materialList);
    }

    @Override
    public void run() throws IOException, TemplateException {
        Map<String, String> foreignMap = new HashMap<>();
        for (BCodeMaterials materials : materialList) {
            if (materials.getRelationList() != null && materials.getRelationList().size() > 0) {
                // 生成副类文件数据
                Configuration configuration = ConfigUtil.getConfiguration();
                String basePackageName = configuration.getPackageName();
                String mapperPackageName = configuration.getPath().getMapper();
                String projectName = configuration.getProjectName();
                String filePath = FileUtil.getSourcePath(projectName, FileUtil.PRODUCER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path(mapperPackageName);
                for (RelationMaterials relation : materials.getRelationList()) {
                    // 生成Mapper填充数据
                    Map<String, String> fDataMap = new HashMap<>();
                    fDataMap.put("TableName", relation.getFkTableName());
                    fDataMap.put("ClassName", relation.getFkClassName());
                    fDataMap.put("RelationType", relation.getRelation());
                    fDataMap.put("ForeignKey", relation.getForeignKey());
                    fDataMap.put("FkProperty", relation.getFkProperty());
                    fDataMap.put("ClassType", RelationInvoker.Builder.FOREIGN_CLASS);

                    String fileName = relation.getFkClassName() + "Mapper.java";
                    // 填充Mapper文件
                    String key = filePath + fileName;
                    String addContent = "";
                    if (foreignMap.containsKey(key)) {
                        String content = foreignMap.get(key);
                        addContent = FileUtil.constructAddContent(SchemeFreemarkerConfigUtils.TYPE_ADD_MAPPER, fDataMap, content);
                        foreignMap.put(key, addContent);
                    } else {
                        addContent = FileUtil.constructAddContent(SchemeFreemarkerConfigUtils.TYPE_ADD_MAPPER, fDataMap, "");
                        foreignMap.put(key, addContent);
                    }
                }
                // 生成主类文件数据
                // 生成Mapper填充数据
                Map<String, Object> pDataMap = new HashMap<>();
                pDataMap.put("BasePackageName", basePackageName);
                pDataMap.put("MapperPackageName", mapperPackageName);
                pDataMap.put("TableName", materials.getTableName());
                pDataMap.put("ClassName", materials.getClassName());
                pDataMap.put("PrimaryKey", materials.getPrimaryKey());
                pDataMap.put("PkProperty", materials.getPkProperty());
                pDataMap.put("RelationList", materials.getRelationList());
                pDataMap.put("ClassType", RelationInvoker.Builder.PRIMARY_CLASS);

                String fileName = materials.getClassName() + "Mapper.java";
                // 填充Mapper文件
                System.out.println("Generating Add " + fileName);
                FileUtil.addToJavaEnd(SchemeFreemarkerConfigUtils.TYPE_ADD_MAPPER, pDataMap, filePath + fileName);
            }
        }
        for (String key : foreignMap.keySet()) {
            System.out.println("Generating Add " + key);
            FileUtil.addToJavaEnd(foreignMap.get(key), key);
        }
    }
}
