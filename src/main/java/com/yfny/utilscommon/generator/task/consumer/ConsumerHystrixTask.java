package com.yfny.utilscommon.generator.task.consumer;

import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.ConfigUtil;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.FreemarketConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器消费者熔断器任务
 * Created by jisongZhou on 2019/3/28.
 **/
public class ConsumerHystrixTask extends AbstractTask {

    public ConsumerHystrixTask(String className, String description) {
        super(className, description);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Hystric填充数据
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        dataMap.put("ServicePackageName", ConfigUtil.getConfiguration().getPath().getService());
        dataMap.put("BaseEntityPackageName", ConfigUtil.getConfiguration().getEntityPackageName());
        dataMap.put("EntityPackageName", ConfigUtil.getConfiguration().getPath().getEntity());
        dataMap.put("HystrixPackageName", ConfigUtil.getConfiguration().getPath().getHystrix());
        dataMap.put("Author", ConfigUtil.getConfiguration().getAuthor());
        dataMap.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        dataMap.put("ClassName", className);
        dataMap.put("Description", StringUtil.isBlank(description) ? className : description);
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getHystrix());
        String fileName = className + "Hystrix.java";
        // 生成Hystric文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_CONSUMER_HYSTRIX, dataMap, filePath + fileName);
    }
}
