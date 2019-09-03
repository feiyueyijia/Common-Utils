package com.yfny.utilscommon.generator.task.producer;

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
 * 代码生成器生产者（微服务）AOP ServiceImpl前置通知设置
 * Created by jisongZhou on 2019/5/16.
 **/
public class ProducerBeforeServiceImplTask extends AbstractTask {

    public ProducerBeforeServiceImplTask(String className, String description) {
        super(className, description);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成BeforeServiceImpl填充数据
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        dataMap.put("Author", ConfigUtil.getConfiguration().getAuthor());
        dataMap.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        dataMap.put("Description", StringUtil.isBlank(description) ? className : description);
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path("aspect");
        String fileName = "BeforeServiceImpl.java";
        // 生成BeforeServiceImpl文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_PRODUCER_BEFORE_SERVICE, dataMap, filePath + fileName);
    }
}
