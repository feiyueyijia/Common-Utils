package com.yfny.utilscommon.generator.task.scheme.entity;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.entity.Configuration;
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
 * 代码生成器实体任务
 * Created by jisongZhou on 2019/4/3.
 **/
public class EntityAddTask extends AbstractTask {

    public EntityAddTask(List<BCodeMaterials> materialList) {
        super(materialList);
    }

    @Override
    public void run() throws IOException, TemplateException {
        for (BCodeMaterials materials : materialList) {
            // 生成EntityAdd填充数据
            if (materials.getRelationList() != null && materials.getRelationList().size() > 0) {
                Map<String, Object> dataMap = new HashMap<>();
                Configuration configuration = ConfigUtil.getConfiguration();
                String basePackageName = configuration.getPackageName();
                String entityPackageName = configuration.getPath().getEntity();
                String projectName = configuration.getProjectName();
                dataMap.put("RelationList", materials.getRelationList());
                String filePath = FileUtil.getSourcePath(projectName, FileUtil.CONSUMER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path(entityPackageName);
                String fileName = materials.getClassName() + "Entity.java";
                // 填充Entity文件
                System.out.println("Generating Add " + fileName);
                FileUtil.addToJavaEnd(SchemeFreemarkerConfigUtils.TYPE_ADD_ENTITY, dataMap, filePath + fileName);
            }
        }
    }
}
