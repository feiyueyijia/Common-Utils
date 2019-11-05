package com.yfny.utilscommon.generator.task.scheme.fallback;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器消费者熔断器任务
 * Created by jisongZhou on 2019/3/28.
 **/
public class HystrixTask extends AbstractTask {

    public HystrixTask(BCodeMaterials materials) {
        super(materials);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Hystrix填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        String filePath = FileUtil.getSourcePath(projectName, FileUtil.CONSUMER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path("fallback");
        String fileName = materials.getClassName() + "Hystrix.java";
        // 生成Hystrix文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_HYSTRIX, dataMap, filePath + fileName);
    }
}
