package ${BasePackageName}${SqlBuilderPackageName};

import ${BaseEntityPackageName}${EntityPackageName}.${ClassName}Entity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * ${Description}SqlBuilder
 * Author ${Author}
 * Date  ${Date}
 */
public class ${ClassName}SqlBuilder {

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，多条件并列查询取交集
     *
     * @param   ${ClassName?uncap_first}    对象实体
     * @return  Sql语句
     */
    public String buildFind${ClassName}ByAndCondition(final ${ClassName}Entity ${ClassName?uncap_first}) {
        return buildFind${ClassName}ByCondition(${ClassName?uncap_first}, 0);
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，多条件亦或查询取并集
     *
     * @param   ${ClassName?uncap_first}    对象实体
     * @return  Sql语句
     */
    public String buildFind${ClassName}ByORCondition(final ${ClassName}Entity ${ClassName?uncap_first}) {
        return buildFind${ClassName}ByCondition(${ClassName?uncap_first}, 1);
    }

    private String buildFind${ClassName}ByCondition(final ${ClassName}Entity ${ClassName?uncap_first}, final int type) {
        String orSql = "";
        if (type == 1) {
            orSql = " || '%'";
        } else {
            orSql = " '%'";
        }
        String finalOrSql = orSql;
        String sqlResult = new SQL() {{
            SELECT(
        <#assign ColumnInfoListSize = ColumnInfoList?size/>
        <#assign ColumnInfoListIndex = 0/>
        <#list ColumnInfoList as ColumnInfo>
            <#assign ColumnInfoListIndex = ColumnInfoListIndex + 1/>
                    "${ColumnInfo.columnName} AS ${ColumnInfo.propertyName}<#if ColumnInfoListSize!=ColumnInfoListIndex>," +<#else>"</#if>
        </#list>
                   );
            FROM("${TableName}");
        <#list ColumnInfoList as ColumnInfo>
            <#if ColumnInfo.typeName == "String">
            if (${ClassName?uncap_first}.get${ColumnInfo.propertyName?cap_first}() != null && !${ClassName?uncap_first}.get${ColumnInfo.propertyName?cap_first}().equals("")) {
                WHERE("${ColumnInfo.columnName} like <#noparse>#{</#noparse>${ClassName?uncap_first}.${ColumnInfo.propertyName}<#noparse>}</#noparse>" + finalOrSql);
            }
            <#else>
            if (${ClassName?uncap_first}.get${ColumnInfo.propertyName?cap_first}() != null) {
                WHERE("${ColumnInfo.columnName} like <#noparse>#{</#noparse>${ClassName?uncap_first}.${ColumnInfo.propertyName}<#noparse>}</#noparse>" + finalOrSql);
            }
            </#if>
        </#list>
        }}.toString();

        if (${ClassName?uncap_first}.getOrders() != null && ${ClassName?uncap_first}.getOrders().size() > 0) {
            Map<String, String> orderMap = ${ClassName?uncap_first}.getOrders();
            int count = 0;
            sqlResult = sqlResult + " ORDER BY ";
            for (String order : orderMap.keySet()) {
                if (count != 0) {
                    order = ", " + order;
                }
                if (${ClassName}Entity.DESC.equals(orderMap.get(order))) {
                    sqlResult = sqlResult + order + " DESC";
                } else {
                    sqlResult = sqlResult + order + " ASC";
                }
                count++;
            }
        }
        return sqlResult;
    }

}
