package com.yfny.utilscommon.generator.task.scheme.test;

import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器API基础单元测试任务
 * Created by jisongZhou on 2019/3/5.
 **/
public class APIBaseTestTask extends AbstractTask {

    public APIBaseTestTask() {

    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成APIBaseTest填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        String filePath = FileUtil.getTestPath(projectName, FileUtil.PRODUCER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path("base");
        String fileName = "APIBaseTest.java";
        // 生成APIBaseTest文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_API_BASE_TEST, dataMap, filePath + fileName);
    }
}
