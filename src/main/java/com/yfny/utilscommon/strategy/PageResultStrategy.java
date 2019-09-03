package com.yfny.utilscommon.strategy;

import java.util.List;

/**
 * 获取列表结果函数策略
 * Created by jisongZhou on 2019/4/29.
 */
@FunctionalInterface
public interface PageResultStrategy<T> {

    List<T> pageResult();

}
