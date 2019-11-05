package com.yfny.utilscommon.generator.task.frame;

import com.yfny.utilscommon.generator.entity.Configuration;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.ConfigUtil;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.FrameFreemarkerConfigUtils;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器项目结构Git忽略设置构造器
 * Created by jisongZhou on 2019/9/23.
 **/
public class GitIgnoreTask extends AbstractTask {

    public GitIgnoreTask(int type) {
        super(type);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成.gitignore填充数据
        Map<String, Object> dataMap = new HashMap<>();
        Configuration configuration = ConfigUtil.getConfiguration();
        String projectName = configuration.getProjectName();
        String filePath = FileUtil.getPath(projectName, type);
        String fileName = ".gitignore";
        // 生成.gitignore文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(FrameFreemarkerConfigUtils.TYPE_GIT_IGNORE, dataMap, filePath + fileName);
    }
}
