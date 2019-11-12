package com.yfny.utilscommon.generator.task.scheme.mapper;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.entity.ColumnInfo;
import com.yfny.utilscommon.generator.entity.RelationMaterials;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器生产者（微服务）数据交互层任务
 * Created by jisongZhou on 2019/3/26.
 **/
public class MapperTask extends AbstractTask {

    public MapperTask(BCodeMaterials materials, List<ColumnInfo> tableInfos) {
        super(materials, tableInfos);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Mapper填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        String mapperPackageName = (String) dataMap.get("MapperPackageName");
        List<RelationMaterials> relationList = new ArrayList<>();
        for (String foreignKey : materials.getFkList()) {
            RelationMaterials relation = new RelationMaterials();
            relation.setForeignKey(foreignKey);
            relation.setFkProperty(StringUtil.columnName2PropertyName(foreignKey));
            relationList.add(relation);
        }
        dataMap.put("ForeignKeyList", relationList);
        String filePath = FileUtil.getSourcePath(projectName, FileUtil.PRODUCER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path(mapperPackageName);
        String fileName = materials.getClassName() + "Mapper.java";
        // 生成Mapper文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_MAPPER, dataMap, filePath + fileName);
    }
}
