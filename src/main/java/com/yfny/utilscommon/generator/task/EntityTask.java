package com.yfny.utilscommon.generator.task;

import com.yfny.utilscommon.generator.entity.ColumnInfo;
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
import java.util.List;
import java.util.Map;

/**
 * 代码生成器实体任务
 * Created by jisongZhou on 2019/3/5.
 **/
public class EntityTask extends AbstractTask {

    public EntityTask(String className, String tableName, String description, List<ColumnInfo> tableInfos, String foreignKey) {
        super(className, tableName, description, tableInfos, foreignKey);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Entity填充数据
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        dataMap.put("EntityPackageName", ConfigUtil.getConfiguration().getPath().getEntity());
        dataMap.put("Author", ConfigUtil.getConfiguration().getAuthor());
        dataMap.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        dataMap.put("ColumnInfoList", tableInfos);
        dataMap.put("ClassName", className);
        dataMap.put("TableName", tableName);
        dataMap.put("ForeignKey", foreignKey);
        dataMap.put("Description", StringUtil.isBlank(description) ? className : description);

        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getEntity());
        String fileName = className + "Entity.java";
        // 生成Entity文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_ENTITY, dataMap, filePath + fileName);
    }
}
