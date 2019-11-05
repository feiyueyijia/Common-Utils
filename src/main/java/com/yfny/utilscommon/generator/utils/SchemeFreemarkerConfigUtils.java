package com.yfny.utilscommon.generator.utils;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * 代码生成器配置获取工具
 * Created by jisongZhou on 2019/3/5.
 **/
public class SchemeFreemarkerConfigUtils {
    private static String path = new File(SchemeFreemarkerConfigUtils.class.getClassLoader().getResource("ftls/scheme").getFile()).getPath();
    public final static int TYPE_ENTITY = 10000;//实体对象模型
    public final static int TYPE_CONSTANT = 10001;//实体对象常量
    public final static int TYPE_SQL_BUILDER = 10002;//SQL构造器
    public final static int TYPE_MAPPER = 10003;//数据交互层
    public final static int TYPE_SERVICE = 10004;//服务接口
    public final static int TYPE_SERVICE_IMPL = 10005;//服务实现层
    public final static int TYPE_COMPOSITE = 10006;//组合业务
    public final static int TYPE_BEFORE_SERVICE = 10007;//切面处理
    public final static int TYPE_VALID = 10008;//实体验证
    public final static int TYPE_CONTROLLER = 10009;//控制层
    public final static int TYPE_CLIENT = 10010;//服务调用层
    public final static int TYPE_HYSTRIX = 10011;//消费者-断路器
    public final static int TYPE_EXCEPTION_HANDLER = 10012;//统一异常处理机制
    public final static int TYPE_ADD_ENTITY = 10013;//实体对象模型追加
    public final static int TYPE_ADD_MAPPER = 10014;//数据交互层追加
    public final static int TYPE_API_BASE_TEST = 10015;//接口模拟测试基类
    public final static int TYPE_API_UNIT_TEST = 10016;//接口单元测试

    private static Configuration configuration;

    public static synchronized Configuration getInstance() {
        if (null == configuration) {
            configuration = new Configuration(Configuration.VERSION_2_3_28);
            try {
                if (path.contains("jar")) {
                    configuration.setClassForTemplateLoading(SchemeFreemarkerConfigUtils.class, "/ftls/scheme");
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
