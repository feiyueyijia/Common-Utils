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
public class MainYamlTask extends AbstractTask {

    public MainYamlTask(int type) {
        super(type);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成application.yaml填充数据
        Map<String, Object> dataMap = new HashMap<>();
        Configuration configuration = ConfigUtil.getConfiguration();
        String url = configuration.getDb().getUrl();
        String userName = configuration.getDb().getUsername();
        String password = configuration.getDb().getPassword();
        String basePackageName = configuration.getPackageName();
        String projectName = configuration.getProjectName();
        String[] packageNames = StringUtils.split(basePackageName, "//.");
        String artifactId = packageNames[packageNames.length - 1];
        dataMap.put("DataBaseUrl", url);
        dataMap.put("DataBaseUserName", userName);
        dataMap.put("DataBasePassword", password);
        dataMap.put("ProjectArtifactId", artifactId);
        dataMap.put("IsLocal", false);
        String filePath = FileUtil.getResourcePath(projectName, type);
        String fileName = "application.yaml";
        // 生成application.yaml文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(FrameFreemarkerConfigUtils.TYPE_MAIN_YAML, dataMap, filePath + fileName);

        // 生成application.yaml文件
        dataMap.put("IsLocal", true);
        fileName = "application-local.yaml";
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(FrameFreemarkerConfigUtils.TYPE_MAIN_YAML, dataMap, filePath + fileName);
    }
}
