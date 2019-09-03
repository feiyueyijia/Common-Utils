package com.yfny.utilscommon.basemvc.consumer;

import java.util.List;

/**
 * 服务消费者通用Hystrix
 * Author jisongZhou
 * Date  2019-04-02
 */
public abstract class BaseHystrix<T> implements BaseClient<T> {

    @Override
    public int insert(T entity) { return -1; }

    @Override
    public int insertSelective(T entity) { return -1; }

    @Override
    public int update(T entity) { return -1; }

    @Override
    public int updateSelective(T entity) { return -1; }

    @Override
    public int delete(T entity) { return -1; }

    @Override
    public int deleteByPrimaryKey(Object key) { return -1; }

    @Override
    public boolean existsWithPrimaryKey(Object key) { return false; }

    @Override
    public T selectOne(T entity) { return null; }

    @Override
    public T selectByPrimaryKey(Object key) { return null; }

    @Override
    public int selectCount(T entity) { return -1; }

    @Override
    public List<T> findList(T entity) { return null; }

    @Override
    public List<T> findList(T entity, String pageNum, String pageSize) { return null; }

    @Override
    public List<T> findAllList() { return null; }

    @Override
    public List<T> findAllList(String pageNum, String pageSize) { return null; }

    @Override
    public List<T> findSimpleListByAndCondition(T entity) {
        return null;
    }

    @Override
    public List<T> findSimpleListByAndCondition(T entity, String pageNum, String pageSize) {
        return null;
    }

    @Override
    public List<T> findListByAndCondition(T entity) {
        return null;
    }

    @Override
    public List<T> findListByAndCondition(T entity, String pageNum, String pageSize) {
        return null;
    }

    @Override
    public List<T> findSimpleListByORCondition(T entity) {
        return null;
    }

    @Override
    public List<T> findSimpleListByORCondition(T entity, String pageNum, String pageSize) {
        return null;
    }

    @Override
    public List<T> findListByORCondition(T entity) {
        return null;
    }

    @Override
    public List<T> findListByORCondition(T entity, String pageNum, String pageSize) {
        return null;
    }

}
