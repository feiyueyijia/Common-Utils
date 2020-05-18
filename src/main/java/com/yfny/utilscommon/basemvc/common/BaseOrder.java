package com.yfny.utilscommon.basemvc.common;
/**
 * Created by jisongZhou on 2019/10/29.
 **/

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 排序字段及排序方式定义
 * Author jisongZhou
 * Date  2019/10/29
 */
public class BaseOrder implements Serializable {

    public final static String ASC = "ASC";//升序
    public final static String DESC = "DESC";//降序

    @Transient
    private String orderBy;

    @Transient
    private String orderSort;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }
}
