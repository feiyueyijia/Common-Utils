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
 * 代码生成器项目结构pom文件构造器
 * Created by jisongZhou on 2019/9/23.
 **/
public class PomTask extends AbstractTask {

    public PomTask(int type) {
        super(type);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成pom填充数据
        Map<String, String> dataMap = new HashMap<>();
        Configuration configuration = ConfigUtil.getConfiguration();
        String basePackageName = configuration.getPackageName();
        String projectName = configuration.getProjectName();
        String[] packageNames = StringUtils.split(basePackageName, "//.");
        String groupId = "";
        for (int i = 0; i < packageNames.length - 1; i++) {
            groupId = groupId + packageNames[i] + ".";
        }
        groupId = groupId.substring(0, groupId.length() - 1);
        String artifactId = packageNames[packageNames.length - 1];

        dataMap.put("ProjectGroupId", groupId);
        dataMap.put("ProjectArtifactId", artifactId);
        dataMap.put("ProjectName", projectName);
        String filePath = FileUtil.getPath(projectName, type);
        String fileName = "pom.xml";
        // 生成pom文件
        System.out.println("Generating " + fileName);
        int template = FrameFreemarkerConfigUtils.TYPE_PRODUCER_POM;
        if (type == FileUtil.CONSUMER) {
            template = FrameFreemarkerConfigUtils.TYPE_CONSUMER_POM;
        }
        FileUtil.generateToJava(template, dataMap, filePath + fileName);
    }

}
