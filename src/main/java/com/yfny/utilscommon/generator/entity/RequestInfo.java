package com.yfny.utilscommon.generator.entity;

import org.springframework.core.MethodParameter;

import java.io.Serializable;

/**
 * 代码生成器请求信息
 * Created by jisongZhou on 2019/3/11.
 **/
public class RequestInfo implements Serializable {
    private String url;//请求url
    private String methodName;//方法名
    private String requestMethod;//请求方式
    private MethodParameter[] requestParams;//请求参数

    public RequestInfo() {
    }

    public RequestInfo(String url, String methodName, String requestMethod, MethodParameter[] requestParams) {
        this.url = url;
        this.methodName = methodName;
        this.requestMethod = requestMethod;
        this.requestParams = requestParams;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public MethodParameter[] getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(MethodParameter[] requestParams) {
        this.requestParams = requestParams;
    }
}
