package com.yfny.utilscommon.generator.task.scheme.constant;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器生产者（微服务）对象常量构造器
 * Created by jisongZhou on 2019/5/16.
 **/
public class ConstantTask extends AbstractTask {

    public ConstantTask(BCodeMaterials materials) {
        super(materials);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Constant填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        String filePath = FileUtil.getSourcePath(projectName, FileUtil.CONSUMER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path("constant");
        String fileName = materials.getClassName() + "Constant.java";
        // 生成Constant文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_CONSTANT, dataMap, filePath + fileName);
    }
}
