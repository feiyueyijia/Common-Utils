package com.yfny.utilscommon.generator.task.scheme.composite;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器生产者（微服务）组合构件任务
 * Created by jisongZhou on 2019/5/16.
 **/
public class CompositeTask extends AbstractTask {

    public CompositeTask(BCodeMaterials materials) {
        super(materials);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Composite填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        String filePath = FileUtil.getSourcePath(projectName, FileUtil.PRODUCER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path("composite");
        String fileName = materials.getClassName() + "Composite.java";
        // 生成Composite文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_COMPOSITE, dataMap, filePath + fileName);
    }
}
