package com.yfny.utilscommon.generator.task.frame;

import com.yfny.utilscommon.generator.entity.Configuration;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.ConfigUtil;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.FrameFreemarkerConfigUtils;
import com.yfny.utilscommon.util.StringUtils;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器项目结构主要配置构造器
 * Created by jisongZhou on 2019/9/23.
 **/
public class MainBootstrapTask extends AbstractTask {

    public MainBootstrapTask(int type) {
        super(type);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成bootstrap.properties填充数据
        Map<String, String> dataMap = new HashMap<>();
        Configuration configuration = ConfigUtil.getConfiguration();
        String basePackageName = configuration.getPackageName();
        String projectName = configuration.getProjectName();
        String[] packageNames = StringUtils.split(basePackageName, "//.");
        String artifactId = packageNames[packageNames.length - 1];
        dataMap.put("ProjectArtifactId", artifactId);
        String filePath = FileUtil.getResourcePath(projectName, type);
        String fileName = "bootstrap.properties";
        // 生成bootstrap.properties文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(FrameFreemarkerConfigUtils.TYPE_MAIN_BOOTSTRAP, dataMap, filePath + fileName);
    }
}
