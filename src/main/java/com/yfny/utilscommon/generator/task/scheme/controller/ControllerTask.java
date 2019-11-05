package com.yfny.utilscommon.generator.task.scheme.controller;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器生产者（微服务）控制层任务
 * Created by jisongZhou on 2019/3/26.
 **/
public class ControllerTask extends AbstractTask {

    public ControllerTask(BCodeMaterials materials) {
        super(materials);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Controller填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        String controllerPackageName = (String) dataMap.get("ControllerPackageName");
        String filePath = FileUtil.getSourcePath(projectName, FileUtil.PRODUCER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path(controllerPackageName);
        String fileName = materials.getClassName() + "Controller.java";
        // 生成Controller文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_CONTROLLER, dataMap, filePath + fileName);
    }
}
