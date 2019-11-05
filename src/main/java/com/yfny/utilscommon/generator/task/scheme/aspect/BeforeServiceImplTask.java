package com.yfny.utilscommon.generator.task.scheme.aspect;

import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器生产者（微服务）AOP ServiceImpl前置通知设置
 * Created by jisongZhou on 2019/5/16.
 **/
public class BeforeServiceImplTask extends AbstractTask {

    public BeforeServiceImplTask() {

    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成BeforeServiceImpl填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        String filePath = FileUtil.getSourcePath(projectName, FileUtil.PRODUCER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path("aspect");
        String fileName = "BeforeServiceImpl.java";
        // 生成BeforeServiceImpl文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_BEFORE_SERVICE, dataMap, filePath + fileName);
    }
}
