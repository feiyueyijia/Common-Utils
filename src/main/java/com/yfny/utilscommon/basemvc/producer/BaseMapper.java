package com.yfny.utilscommon.basemvc.producer;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * 微服务通用Mapper
 * Author jisongZhou
 * Date  2019-04-09
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T> {

    List<T> findListByCondition(T entity);

    List<T> findComplexListByCondition(T entity);

    List<String> findGroupBy(T entity);

    T selectByCondition(T entity);

    T selectComplexById(String id);

}
