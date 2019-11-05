package com.yfny.utilscommon.generator.task.frame;

import com.yfny.utilscommon.generator.entity.Configuration;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.ConfigUtil;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.FrameFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器项目结构主类构造器
 * Created by jisongZhou on 2019/9/20.
 **/
public class MainApplicationTask extends AbstractTask {

    public MainApplicationTask(int type) {
        super(type);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Application填充数据
        Map<String, Object> dataMap = new HashMap<>();
        Configuration configuration = ConfigUtil.getConfiguration();
        String basePackageName = configuration.getPackageName();
        String projectName = configuration.getProjectName();
        dataMap.put("BasePackageName", basePackageName);
        dataMap.put("ProjectName", projectName);
        String filePath = FileUtil.getSourcePath(projectName, type) + StringUtil.package2Path(basePackageName);
        String fileName = projectName + "Application.java";
        // 生成Application文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(FrameFreemarkerConfigUtils.TYPE_MAIN_APPLICATION, dataMap, filePath + fileName);
    }
}
