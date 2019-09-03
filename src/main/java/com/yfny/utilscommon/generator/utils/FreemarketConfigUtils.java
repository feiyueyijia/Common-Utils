package com.yfny.utilscommon.generator.utils;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * 代码生成器配置获取工具
 * Created by jisongZhou on 2019/3/5.
 **/
public class FreemarketConfigUtils {
    private static String path = new File(FreemarketConfigUtils.class.getClassLoader().getResource("ftls").getFile()).getPath();
    public final static int TYPE_ENTITY = 1;//实体对象模型层
    public final static int TYPE_PRODUCER_SQL_BUILDER = 2;//SQL构造器层
    public final static int TYPE_PRODUCER_MAPPER = 3;//生产者-数据交互层
    public final static int TYPE_PRODUCER_SERVICE = 4;//生产者-服务层
    public final static int TYPE_PRODUCER_COMPOSITE = 5;//生产者-业务者层
    public final static int TYPE_PRODUCER_BEFORE_SERVICE = 6;//生产者-切面处理
    public final static int TYPE_PRODUCER_FUTURE = 7;//生产者-异步调用处理
    public final static int TYPE_PRODUCER_CONTROLLER = 8;//生产者-控制层
    public final static int TYPE_CONSUMER_CLIENT = 9;//消费者-服务调用层
    public final static int TYPE_CONSUMER_HYSTRIX = 10;//消费者-断路器
    public final static int TYPE_CONSUMER_FUTURE = 11;//消费者-异步调用处理
    public final static int TYPE_CONSUMER_CONTROLLER = 12;//消费者-控制层
    public final static int TYPE_EXCEPTION_HANDLER = 13;//统一异常处理机制
    public final static int TYPE_ADD_ENTITY = 14;//实体对象模型追加
    public final static int TYPE_ADD_PRODUCER_MAPPER = 15;//数据交互层追加
    public final static int TYPE_API_BASE_TEST = 16;//接口模拟测试基类
    public final static int TYPE_API_UNIT_TEST = 17;//接口单元测试

    private static Configuration configuration;

    public static synchronized Configuration getInstance() {
        if (null == configuration) {
            configuration = new Configuration(Configuration.VERSION_2_3_28);
            try {
                if (path.contains("jar")) {
                    configuration.setClassForTemplateLoading(FreemarketConfigUtils.class, "/ftls");
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
