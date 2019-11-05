package com.yfny.utilscommon.generator.entity;

import com.yfny.utilscommon.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jisongZhou on 2019/10/8.
 **/
public class BCodeMaterials {

    private String id;

    private String tableName;//数据库表名称

    private String className;//Java对象类名称

    private String description;//描述

    private String primaryKey;//主键

    private String pkProperty;//主键属性

    private List<String> fkList;//外键列表

    private List<RelationMaterials> relationList;//相关对象类集合

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return StringUtils.isNotBlank(description) ? description : "AutoClass";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrimaryKey() {
        return primaryKey != null ? primaryKey : "";
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPkProperty() {
        return pkProperty;
    }

    public void setPkProperty(String pkProperty) {
        this.pkProperty = pkProperty;
    }

    public List<String> getFkList() {
        return fkList != null ? fkList : new ArrayList<>();
    }

    public void setFkList(List<String> fkList) {
        this.fkList = fkList;
    }

    public List<RelationMaterials> getRelationList() {
        return relationList != null ? relationList : new ArrayList<>();
    }

    public void setRelationList(List<RelationMaterials> relationList) {
        this.relationList = relationList;
    }
}
