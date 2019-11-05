package com.yfny.utilscommon.generator.entity;
/**
 * Created by jisongZhou on 2019/10/24.
 **/

/**
 * 关系材料
 * Author jisongZhou
 * Date  2019/10/24
 */
public class RelationMaterials {

    private String id;

    private String primaryKey;

    private String pkProperty;

    private String pkTableName;

    private String pkClassName;

    private String foreignKey;

    private String fkProperty;

    private String fkTableName;

    private String fkClassName;

    private String relation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrimaryKey() {
        return primaryKey;
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

    public String getPkTableName() {
        return pkTableName;
    }

    public void setPkTableName(String pkTableName) {
        this.pkTableName = pkTableName;
    }

    public String getPkClassName() {
        return pkClassName;
    }

    public void setPkClassName(String pkClassName) {
        this.pkClassName = pkClassName;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getFkProperty() {
        return fkProperty;
    }

    public void setFkProperty(String fkProperty) {
        this.fkProperty = fkProperty;
    }

    public String getFkTableName() {
        return fkTableName;
    }

    public void setFkTableName(String fkTableName) {
        this.fkTableName = fkTableName;
    }

    public String getFkClassName() {
        return fkClassName;
    }

    public void setFkClassName(String fkClassName) {
        this.fkClassName = fkClassName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
