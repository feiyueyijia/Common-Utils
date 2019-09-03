package com.yfny.utilscommon.generator.task;

import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.ConfigUtil;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.FreemarketConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import com.yfny.utilscommon.util.CodeInfoUtils;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器API单元测试任务
 * Created by jisongZhou on 2019/3/7.
 **/
public class APIUnitTestTask extends AbstractTask {

    public APIUnitTestTask(String className) {
        super(className);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成APIUnitTest填充数据
        Map<String, Object> testData = new HashMap<>();
        testData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        testData.put("APIUnitTestPackageName", ConfigUtil.getConfiguration().getPath().getUnittest());
        testData.put("APIBaseTestPackageName", ConfigUtil.getConfiguration().getPath().getBasetest());
        testData.put("Author", ConfigUtil.getConfiguration().getAuthor());
        testData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        testData.put("RequestInfoList", CodeInfoUtils.getRequestInfos());
        String filePath = FileUtil.getTestPath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getUnittest());
        String fileName = "APIUnitTest.java";
        // 生成APIUnitTest文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_API_UNIT_TEST, testData, filePath + fileName);
    }
}
