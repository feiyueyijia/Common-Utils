package com.yfny.utilscommon.generator.task.scheme.handler;

import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器实体任务
 * Created by jisongZhou on 2019/3/5.
 **/
public class ExceptionHandlerTask extends AbstractTask {

    public ExceptionHandlerTask() {

    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成GlobalExceptionHandler填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        String filePath = FileUtil.getSourcePath(projectName, FileUtil.PRODUCER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path("handler");
        String fileName = "GlobalExceptionHandler.java";
        // 生成GlobalExceptionHandler文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_EXCEPTION_HANDLER, dataMap, filePath + fileName);
    }
}
