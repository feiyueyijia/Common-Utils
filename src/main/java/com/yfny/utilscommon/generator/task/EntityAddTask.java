package com.yfny.utilscommon.generator.task;

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
 * 代码生成器实体任务
 * Created by jisongZhou on 2019/4/3.
 **/
public class EntityAddTask extends AbstractTask {

    public EntityAddTask(String className, Map<String, String> relationClassNameMap) {
        super(className, null, relationClassNameMap);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成EntityAdd填充数据
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("RelationClassNameMap", relationClassNameMap);

        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getEntity());
        String fileName = className + "Entity.java";
        // 填充Entity文件
        System.out.println("Generating Add " + fileName);
        FileUtil.addToJavaEnd(FreemarketConfigUtils.TYPE_ADD_ENTITY, dataMap, filePath + fileName);
    }
}
