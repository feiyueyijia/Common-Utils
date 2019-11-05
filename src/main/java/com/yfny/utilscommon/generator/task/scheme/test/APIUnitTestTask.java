package com.yfny.utilscommon.generator.task.scheme.test;

import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import com.yfny.utilscommon.util.CodeInfoUtils;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器API单元测试任务
 * Created by jisongZhou on 2019/3/7.
 **/
public class APIUnitTestTask extends AbstractTask {

    public APIUnitTestTask() {

    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成APIUnitTest填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        dataMap.put("RequestInfoList", CodeInfoUtils.getRequestInfos());
        String filePath = FileUtil.getTestPath(projectName, FileUtil.PRODUCER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path("unit");
        String fileName = "APIUnitTest.java";
        // 生成APIUnitTest文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_API_UNIT_TEST, dataMap, filePath + fileName);
    }
}
