package com.yfny.utilscommon.generator.task.scheme.builder;

import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.entity.ColumnInfo;
import com.yfny.utilscommon.generator.task.base.AbstractTask;
import com.yfny.utilscommon.generator.utils.FileUtil;
import com.yfny.utilscommon.generator.utils.SchemeFreemarkerConfigUtils;
import com.yfny.utilscommon.generator.utils.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器生产者（微服务）Sql语句构造器任务
 * Created by jisongZhou on 2019/4/1.
 **/
public class SqlBuilderTask extends AbstractTask {

    public SqlBuilderTask(BCodeMaterials materials, List<ColumnInfo> tableInfos) {
        super(materials, tableInfos);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成SqlBuilder填充数据
        Map<String, Object> dataMap = constructData();
        String projectName = (String) dataMap.get("ProjectName");
        String basePackageName = (String) dataMap.get("BasePackageName");
        String filePath = FileUtil.getSourcePath(projectName, FileUtil.PRODUCER) + StringUtil.package2Path(basePackageName) + StringUtil.package2Path("builder");
        String fileName = materials.getClassName() + "SqlBuilder.java";
        // 生成SqlBuilder文件
        System.out.println("Generating " + fileName);
        FileUtil.generateToJava(SchemeFreemarkerConfigUtils.TYPE_SQL_BUILDER, dataMap, filePath + fileName);
    }
}
