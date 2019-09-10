package com.yfny.utilscommon.basemvc.producer;

import com.yfny.utilscommon.basemvc.common.BaseEntity;
import com.yfny.utilscommon.basemvc.common.BusinessException;

/**
 * 微服务通用属性验证
 * Author jisongZhou
 * Date  2019-09-10
 */
public class BaseValid<T extends BaseEntity> {

    public void validInsert(T entity) throws BusinessException {

    }

    public void validUpdate(T entity) throws BusinessException {

    }

    public void validDelete(T entity) throws BusinessException {

    }

    public void validSelect(T entity) throws BusinessException {

    }

}
