package com.yfny.utilscommon.generator.task.scheme.entity;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.entity.ColumnInfo;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器实体任务
 * Created by jisongZhou on 2019/3/5.
 **/
public class EntityTask extends AbstractTask {

    public EntityTask(BCodeMaterials materials, List<ColumnInfo> tableInfos) {
        super(materials, tableInfos);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Entity填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        String entityPackageName = (String) dataMap.get("EntityPackageName");
        dataMap.put("ForeignKeyList", materials.getFkList());
        String filePath = FileUtil.getSourcePath(projectName, FileUtil.CONSUMER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path(entityPackageName);
        String fileName = materials.getClassName() + "Entity.java";
        // 生成Entity文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_ENTITY, dataMap, filePath + fileName);
    }
}
