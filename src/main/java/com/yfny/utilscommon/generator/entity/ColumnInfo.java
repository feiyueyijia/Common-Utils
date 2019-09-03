package com.yfny.utilscommon.generator.entity;

import com.yfny.utilscommon.generator.utils.StringUtil;
import com.yfny.utilscommon.generator.utils.TypeUtil;

import java.io.Serializable;

/**
 * 代码生成器数据表字段类
 * Created by jisongZhou on 2019/3/5.
 **/
public class ColumnInfo implements Serializable {

    private String columnName; // 列名
    private int type; // 类型代码
    private String typeName; //新增属性--类型名称
    private String propertyName; // 属性名
    private boolean isPrimaryKey; // 是否主键
    private int length; //字段长度
    private int nullable; //字段是否为NULL

    public ColumnInfo() {

    }

    public ColumnInfo(String columnName, int type, boolean isPrimaryKey, int length, int nullable) {
        this.columnName = columnName;
        this.type = type;
        this.typeName = TypeUtil.parseTypeFormSqlType(type);
        this.propertyName = StringUtil.columnName2PropertyName(columnName);
        this.isPrimaryKey = isPrimaryKey;
        this.length = length;
        this.nullable = nullable;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }
}
