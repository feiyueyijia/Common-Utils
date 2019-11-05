package com.yfny.utilscommon.basemvc.consumer;

import com.yfny.utilscommon.util.InvokeResult;
import com.yfny.utilscommon.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * 服务消费者通用Hystrix
 * Author jisongZhou
 * Date  2019-04-02
 */
public abstract class BaseHystrix<T> implements BaseClient<T> {

    @Value("${spring.application.name}")
    private String applicationName;

    private String getApplicationName() {
        if (StringUtils.isBlank(applicationName)) {
            applicationName = "未知服务";
        }
        return applicationName;
    }

    private InvokeResult fallback() {
        applicationName = getApplicationName();
        return InvokeResult.fallback(applicationName);
    }

    @Override
    public InvokeResult insertSelective(T entity) {
        return fallback();
    }
    
    @Override
    public InvokeResult updateSelective(T entity) {
        return fallback();
    }

    @Override
    public InvokeResult delete(T entity) {
        return fallback();
    }

    @Override
    public InvokeResult deleteByPrimaryKey(Object key) {
        return fallback();
    }

    @Override
    public InvokeResult existsWithPrimaryKey(Object key) {
        return fallback();
    }

    @Override
    public InvokeResult selectOne(T entity) {
        return fallback();
    }

    @Override
    public InvokeResult selectByPrimaryKey(Object key) {
        return fallback();
    }

    @Override
    public InvokeResult selectComplexById(String id) {
        return fallback();
    }

    @Override
    public InvokeResult selectCount(T entity) {
        return fallback();
    }

    @Override
    public InvokeResult findList(T entity) {
        return fallback();
    }

    @Override
    public InvokeResult findList(T entity, String pageNum, String pageSize) {
        return fallback();
    }

    @Override
    public InvokeResult findMapGroupByCondition(T entity) {
        return fallback();
    }

    @Override
    public InvokeResult getTreeOf(T entity) {
        return fallback();
    }
}
