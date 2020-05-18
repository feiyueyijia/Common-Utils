package ${BasePackageName}.${SqlBuilderPackageName};

import ${BasePackageName}.${EntityPackageName}.${ClassName}Entity;
import com.yfny.utilscommon.basemvc.common.BaseNumScope;
import com.yfny.utilscommon.basemvc.common.BaseTimeScope;
import com.yfny.utilscommon.basemvc.producer.BaseSqlBuilder;
import com.yfny.utilscommon.util.ReflectUtils;
import com.yfny.utilscommon.util.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * ${Descriptions}SqlBuilder
 * Author ${Author}
 * Date  ${Date}
 */
public class ${ClassName}SqlBuilder extends BaseSqlBuilder {

    /**
     * 根据实体中的属性进行维度区分，查询分组
     *
     * @param   ${ClassName?uncap_first}    对象实体
     * @return  Sql语句
     */
    public String buildFind${ClassName}GroupBy(final ${ClassName}Entity ${ClassName?uncap_first}) {
        String sqlResult = "";
        String fieldName = ${ClassName?uncap_first}.getGroupBy();
        if (StringUtils.isNotBlank(fieldName)) {
            String groupBy = ReflectUtils.getColumnName(${ClassName}Entity.class, fieldName);
            if (StringUtils.isNotBlank(groupBy)) {
                sqlResult = new SQL() {{
                SELECT(
                        groupBy + " AS " + fieldName
                );
                FROM("${TableName}");
                GROUP_BY(groupBy);
                }}.toString();
            }
        }
        return StringUtils.isNotBlank(sqlResult) ? sqlResult : buildFind${ClassName}ByCondition(${ClassName?uncap_first});
    }

    public String buildFind${ClassName}ByCondition(final ${ClassName}Entity ${ClassName?uncap_first}) {
        String column = column();
        String sqlResult = new SQL() {{
            SELECT(
                    column
            );
            FROM("${TableName}");
        <#list ColumnInfoList as ColumnInfo>
            <#if ColumnInfo.typeName == "String">
            if (${ClassName?uncap_first}.get${ColumnInfo.propertyName?cap_first}() != null && !${ClassName?uncap_first}.get${ColumnInfo.propertyName?cap_first}().equals("")) {
                String conditions = queryBy(${ClassName?uncap_first}, "${ClassName?uncap_first}", "${ColumnInfo.columnName}", "${ColumnInfo.propertyName}");
                WHERE(conditions);
            }
            <#elseif ColumnInfo.typeName == "long" || ColumnInfo.typeName == "int" || ColumnInfo.typeName == "short" || ColumnInfo.typeName == "double">
            if (${ClassName?uncap_first}.getNumScopes() != null && ${ClassName?uncap_first}.getNumScopes().size() > 0) {
                for (BaseNumScope scope : ${ClassName?uncap_first}.getNumScopes()) {
                    if ("${ColumnInfo.propertyName}".equals(scope.getName())) {
                        String conditions = numScope(${ClassName?uncap_first}, scope);
                        if (StringUtils.isNotBlank(conditions)) {
                            WHERE(conditions);
                        }
                        break;
                    }
                }
            }
            <#elseif ColumnInfo.typeName == "Date">
            if (${ClassName?uncap_first}.getTimeScopes() != null && ${ClassName?uncap_first}.getTimeScopes().size() > 0) {
                for (BaseTimeScope scope : ${ClassName?uncap_first}.getTimeScopes()) {
                    if ("${ColumnInfo.propertyName}".equals(scope.getName())) {
                        String conditions = timeScope(${ClassName?uncap_first}, scope);
                        if (StringUtils.isNotBlank(conditions)) {
                            WHERE(conditions);
                        }
                    }
                }
            }
            </#if>
        </#list>
        }}.toString();
        sqlResult = orderBy(${ClassName?uncap_first}, sqlResult);
        return sqlResult;
    }

    private String column() {
        String column =
        <#assign ColumnInfoListSize = ColumnInfoList?size/>
        <#assign ColumnInfoListIndex = 0/>
        <#list ColumnInfoList as ColumnInfo>
            <#assign ColumnInfoListIndex = ColumnInfoListIndex + 1/>
            "${ColumnInfo.columnName}," +
            "${ColumnInfo.columnName} AS ${ColumnInfo.propertyName}<#if ColumnInfoListSize!=ColumnInfoListIndex>," +<#else>";</#if>
        </#list>
        return column;
    }

    private String columnJoin() {
        String column =
        <#assign ColumnInfoListSize = ColumnInfoList?size/>
        <#assign ColumnInfoListIndex = 0/>
        <#list ColumnInfoList as ColumnInfo>
            <#assign ColumnInfoListIndex = ColumnInfoListIndex + 1/>
            "main.${ColumnInfo.columnName}," +
            "main.${ColumnInfo.columnName} AS ${ColumnInfo.propertyName}<#if ColumnInfoListSize!=ColumnInfoListIndex>," +<#else>";</#if>
        </#list>
        return column;
    }

}
