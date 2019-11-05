package com.yfny.utilscommon.generator.task.scheme.service;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器消费者服务层任务
 * Created by jisongZhou on 2019/3/27.
 **/
public class ClientTask extends AbstractTask {

    public ClientTask(BCodeMaterials materials) {
        super(materials);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Client填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        String filePath = FileUtil.getSourcePath(projectName, FileUtil.CONSUMER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path("client");
        String fileName = materials.getClassName() + "Client.java";
        // 生成Client文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_CLIENT, dataMap, filePath + fileName);
    }
}
