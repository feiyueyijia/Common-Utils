package com.yfny.utilscommon.generator.utils;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * 代码生成器配置获取工具
 * Created by jisongZhou on 2019/9/20.
 **/
public class FrameFreemarkerConfigUtils {
    private static String path = new File(FrameFreemarkerConfigUtils.class.getClassLoader().getResource("ftls/frame").getFile()).getPath();
    public final static int TYPE_MAIN_APPLICATION = 20000;//项目主类
    public final static int TYPE_MAIN_YAML = 20001;//项目主类配置
    public final static int TYPE_MAIN_BOOTSTRAP = 20002;//项目主类配置
    public final static int TYPE_TEST_APPLICATION = 20003;//项目测试主类
    public final static int TYPE_TEST_YAML = 20004;//项目测试主类配置
    public final static int TYPE_GIT_IGNORE = 20005;//项目Git提交忽略文件
    public final static int TYPE_PRODUCER_POM = 20006;//项目POM文件
    public final static int TYPE_CONSUMER_POM = 20007;//项目POM文件
    public final static int TYPE_README = 20008;//项目说明

    private static Configuration configuration;

    public static synchronized Configuration getInstance() {
        if (null == configuration) {
            configuration = new Configuration(Configuration.VERSION_2_3_28);
            try {
                if (path.contains("jar")) {
                    configuration.setClassForTemplateLoading(FrameFreemarkerConfigUtils.class, "/ftls/frame");
                } else {
                    configuration.setDirectoryForTemplateLoading(new File(path));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            configuration.setEncoding(Locale.CHINA, "utf-8");
        }
        return configuration;
    }
}
