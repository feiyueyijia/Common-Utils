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
 * 代码生成器消费者服务层任务
 * Created by jisongZhou on 2019/3/27.
 **/
public class ConsumerClientTask extends AbstractTask {

    public ConsumerClientTask(String className, String description, String applicationName) {
        super(className, description, applicationName);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Client填充数据
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        dataMap.put("ServicePackageName", ConfigUtil.getConfiguration().getPath().getService());
        dataMap.put("BaseEntityPackageName", ConfigUtil.getConfiguration().getEntityPackageName());
        dataMap.put("EntityPackageName", ConfigUtil.getConfiguration().getPath().getEntity());
        dataMap.put("HystricPackageName", ConfigUtil.getConfiguration().getPath().getHystrix());
        dataMap.put("Author", ConfigUtil.getConfiguration().getAuthor());
        dataMap.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        dataMap.put("ClassName", className);
        dataMap.put("ApplicationName", applicationName);
        dataMap.put("Description", StringUtil.isBlank(description) ? className : description);
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getService());
        String fileName = className + "Client.java";
        // 生成Client文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_CONSUMER_CLIENT, dataMap, filePath + fileName);
    }
}
