package ${BasePackageName}${EntityPackageName};

import com.yfny.utilscommon.annotation.relation.*;
import com.yfny.utilscommon.basemvc.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * ${Description}Entity
 * Author ${Author}
 * Date  ${Date}
 */
@Table(name = "${TableName}")
public class ${ClassName}Entity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    <#list ColumnInfoList as ColumnInfo>
        <#if ColumnInfo.primaryKey>
    @Id
        </#if>
        <#if ColumnInfo.propertyName == ForeignKey>
    @ForeignKey
        </#if>
        <#if ColumnInfo.nullable == 0>
    @NotEmpty(message = "存在不能为空的字段未填写")
        </#if>
    @Column(name = "${ColumnInfo.columnName}", length = ${ColumnInfo.length?c})
    private ${ColumnInfo.typeName?cap_first} ${ColumnInfo.propertyName};

    </#list>

    public ${ClassName}Entity(){
    }

    <#list ColumnInfoList as ColumnInfo>
    public ${ColumnInfo.typeName?cap_first} get${ColumnInfo.propertyName?cap_first}() {
        return ${ColumnInfo.propertyName};
    }

    public void set${ColumnInfo.propertyName?cap_first}(${ColumnInfo.typeName?cap_first} ${ColumnInfo.propertyName}) {
        this.${ColumnInfo.propertyName} = ${ColumnInfo.propertyName};
    }

    </#list>
    /**************************************此下为非数据库字段属性**************************************/
}
