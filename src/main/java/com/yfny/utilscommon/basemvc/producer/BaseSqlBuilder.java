package com.yfny.utilscommon.basemvc.producer;
/**
 * Created by jisongZhou on 2019/10/29.
 **/

import com.alibaba.nacos.api.config.filter.IFilterConfig;
import com.yfny.utilscommon.basemvc.common.*;
import com.yfny.utilscommon.util.ReflectUtils;

import java.util.List;

/**
 * 微服务通用SqlBuilder
 * Author jisongZhou
 * Date  2019/10/29
 */
public class BaseSqlBuilder {

    protected String queryWay(int type) {
        String orSql = "";
        if (type == 1) {
            orSql = " ||";
        }
        return orSql + " '%'";
    }

    protected <T extends BaseEntity> String queryBy(T entity, String entityName, String column, String property) {
        String queryType = " = ";
        String logical = "";
        String sqlResult = column + queryType + "#{" + entityName + "." + property + "}" + logical;
        if (entity.getQueries() != null && entity.getQueries().size() > 0) {
            List<BaseQuery> queries = entity.getQueries();
            for (BaseQuery query : queries) {
                if (property.equals(query.getQueryBy())) {
                    if (BaseQuery.UNEQUAL.equals(query.getQueryType())) {
                        queryType = " != ";
                    } else if (BaseQuery.LIKE.equals(query.getQueryType())) {
                        queryType = " LIKE ";
                        logical = " '%'";
                    }
                    if (BaseQuery.OR.equals(query.getLogical())) {
                        logical = " ||";
                    }
                    sqlResult = column + queryType + "#{" + entityName + "." + property + "}" + logical;
                }
            }
        }
        return sqlResult;
    }

    protected <T extends BaseEntity> String numScope(T entity, BaseNumScope scope) {
        String name = scope.getName();
        Number start = scope.getStart();
        Number end = scope.getEnd();
        String column = ReflectUtils.getColumnName(entity.getClass(), name);
        String conditions = "";
        if (start != null && end == null) {
            conditions = column + " >= " + start;
        } else if (start == null && end != null) {
            conditions = column + " <= " + end;
        } else if (start != null && end != null) {
            conditions = column + " >= " + start + " and " + column + " <= " + end;
        }
        return conditions;
    }

    protected <T extends BaseEntity> String timeScope(T entity, BaseTimeScope scope) {
        String name = scope.getName();
        String start = scope.getStart();
        String end = scope.getEnd();
        String column = ReflectUtils.getColumnName(entity.getClass(), name);
        String conditions = "";
        if (start != null && end == null) {
            conditions = column + " >= " + "'" + start + "'";
        } else if (start == null && end != null) {
            conditions = column + " <= " + "'" + end + "'";
        } else if (start != null && end != null) {
            conditions = column + " >= " + "'" + start + "'" + " and " + column + " <= " + "'" + end + "'";
        }
        return conditions;
    }

    protected <T extends BaseEntity> String orderBy(T entity, String sqlResult) {
        if (entity.getOrders() != null && entity.getOrders().size() > 0) {
            List<BaseOrder> orders = entity.getOrders();
            int count = 0;
            sqlResult = sqlResult + " ORDER BY ";
            String orderBy = "";
            for (BaseOrder order : orders) {
                String orderColumn = ReflectUtils.getColumnName(entity.getClass(), order.getOrderBy());
                if (count != 0) {
                    orderBy = ", " + orderColumn;
                } else {
                    orderBy = orderColumn;
                }
                if (BaseOrder.DESC.equals(order.getOrderSort())) {
                    sqlResult = sqlResult + orderBy + " DESC";
                } else {
                    sqlResult = sqlResult + orderBy + " ASC";
                }
                count++;
            }
        }
        return sqlResult;
    }

}
