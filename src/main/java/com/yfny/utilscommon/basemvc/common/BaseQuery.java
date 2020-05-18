package com.yfny.utilscommon.basemvc.common;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 条件查询字段及查询方式定义
 * Author jisongZhou
 * Date  2019/12/24
 */
public class BaseQuery implements Serializable {

    public final static String EQUAL = "EQUAL";//等于
    public final static String UNEQUAL = "UNEQUAL";//不等于
    public final static String LIKE = "LIKE";//模糊

    public final static String AND = "AND";//逻辑与
    public final static String OR = "OR";//逻辑或

    @Transient
    private String queryBy;

    @Transient
    private String queryType = EQUAL;//查询方式设定，默认为等于

    @Transient
    private String logical = AND;//列表条件逻辑指定，默认为AND

    public String getQueryBy() {
        return queryBy;
    }

    public void setQueryBy(String queryBy) {
        this.queryBy = queryBy;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getLogical() {
        return logical;
    }

    public void setLogical(String logical) {
        this.logical = logical;
    }
}
