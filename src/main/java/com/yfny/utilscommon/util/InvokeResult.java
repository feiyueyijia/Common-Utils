package com.yfny.utilscommon.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;

/**
 * 统一调用响应格式
 * Created by jisongZhou on 2019/2/18.
 **/
@Component
public class InvokeResult<T> {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private static InvokeResult invokeResult;

    @PostConstruct
    public void init() {
        invokeResult = this;
    }

    private final static int SUCCESS = 0;
    private final static int FAILURE = 1;
    private final static int EXCEPTION = 2;

    //响应状态
    private String code;

    //响应消息
    private String message;

    //响应数据
    private T data;

    //配置读取器
    private static PropertiesLoader loader = new PropertiesLoader("props/business_code.properties", "props/system_code.properties");

    public static <T> InvokeResult success(T data) {
        return success("", data, null);
    }

    public static <T> InvokeResult success(String code, T data, String... params) {
        return getResultInit(code, "", data, SUCCESS, params);
    }

    public static InvokeResult failure() {
        return failure("sys.request.failed", new String[]{"未知"});
    }

    public static InvokeResult failure(String code, String... params) {
        return failure(code, null, params);
    }

    public static <T> InvokeResult failure(String code, T data, String... params) {
        if (params == null || params.length == 0) {
            params = new String[]{"未知"};
        }
        return getResultInit(code, "", data, FAILURE, params);
    }

    public static InvokeResult exception() {
        return exception("sys.request.exception", new String[]{"未知"});
    }

    public static InvokeResult exception(String code, String... params) {
        if (params == null || params.length == 0) {
            params = new String[]{"未知"};
        }
        return getResultInit(code, "", null, EXCEPTION, params);
    }

    public static InvokeResult fallback(String applicationName) {
        return exception("sys.request.fallback", applicationName, "网络请求超时或服务器崩溃");
    }

    /**
     * 执行写入时的返回结果
     *
     * @param result      数据库写入结果
     * @param successCode 成功编码
     * @param failureCode 正常失败编码
     * @param params
     * @return 统一调用响应格式
     */
    public static InvokeResult writeResult(int result, String successCode, String failureCode, String... params) {
        if (result == 1) {
            return InvokeResult.success(successCode, result);
        } else if (result == -1) {
            return InvokeResult.failure("sys.request.failed", "网络请求超时或服务器崩溃");
        }
        return InvokeResult.failure(failureCode, params);
    }

    /**
     * 执行写入时的返回结果
     *
     * @param result      数据库写入结果
     * @param result2     数据库写入对象
     * @param successCode 成功编码
     * @param failureCode 正常失败编码
     * @param params
     * @return 统一调用响应格式
     */
    public static InvokeResult writeResult(int result, Object result2, String successCode, String failureCode, String... params) {
        if (result == 1) {
            Object pkValue = ReflectUtils.getPKValue(result2);
            return InvokeResult.success(successCode, pkValue);
        } else if (result == -1) {
            return InvokeResult.failure("sys.request.failed", "网络请求超时或服务器崩溃");
        }
        return InvokeResult.failure(failureCode, params);
    }

    /**
     * 执行读取时的返回结果
     *
     * @param result      数据库读取结果
     * @param successCode 成功编码
     * @param failureCode 正常失败编码
     * @param params
     * @return 统一调用响应格式
     */
    public static InvokeResult readResult(Object result, String successCode, String failureCode, String... params) {
        if (result != null) {
            return InvokeResult.success(successCode, result);
        } else if (result == null) {
            return InvokeResult.failure("sys.request.failed", "数据不存在或网络请求超时或服务器崩溃");
        }
        return InvokeResult.failure(failureCode, params);
    }

    /**
     * 接口数据统一格式化
     *
     * @param code
     * @param message
     * @param data
     * @param params
     * @return
     */
    private static InvokeResult getResultInit(String code, String message, Object data, int type, String[] params) {
        InvokeResult result = new InvokeResult();
        code = StringUtils.isNotBlank(code) ? code : "sys.request.success";
        message = StringUtils.isNotBlank(message) ? message : getMsgFromCfg(code, params);
        data = data != null ? data : StringUtils.isUTF8(message);
        if (type == SUCCESS) {
            code = "sys.request.success";
        }
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 从配置文件中获取错误信息
     *
     * @param code
     * @param params
     * @return
     */
    public static String getMsgFromCfg(String code, String[] params) {
        String message = "";
        if (invokeResult != null && invokeResult.applicationContext != null
                && invokeResult.applicationContext.getEnvironment() != null) {
            message = invokeResult.applicationContext.getEnvironment().getProperty(code);
            if (StringUtils.isBlank(message)) {
                message = loader.getProperty(code, "");
                if (StringUtils.isBlank(message)) {
                    String[] codes = StringUtils.split(code, "//.", 2);
                    code = "business." + codes[1];
                    message = invokeResult.applicationContext.getEnvironment().getProperty(code);
                    if (StringUtils.isBlank(message)) {
                        message = loader.getProperty(code, "");
                        if (StringUtils.isBlank(message)) {
                            message = "提示信息缺失";
                        }
                    }
                }
            }
        } else {
            message = loader.getProperty(code, "");
            if (StringUtils.isBlank(message)) {
                String[] codes = StringUtils.split(code, "//.", 2);
                code = "business." + codes[1];
                message = loader.getProperty(code, "");
                if (StringUtils.isBlank(message)) {
                    message = "提示信息缺失";
                }
            }
        }
        return params == null ? message : MessageFormat.format(message,
                params);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
