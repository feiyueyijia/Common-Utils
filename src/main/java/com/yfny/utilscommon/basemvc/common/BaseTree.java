package com.yfny.utilscommon.basemvc.common;
/**
 * Created by jisongZhou on 2019/10/29.
 **/

import com.yfny.utilscommon.util.StringUtils;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构基本定义
 * Author jisongZhou
 * Date  2019/10/29
 */
public class BaseTree implements Serializable {

    public final static String NODE_LEVEL = "LEVEL";//层级排序
    public final static String NODE_LABEL = "LABEL";//名称排序

    @Transient
    private String id;//标识字段定义

    @Transient
    private String name;//名称字段定义

    @Transient
    private String parentId;//父级标识字段定义

    @Transient
    private String level;//层级字段定义

    @Transient
    private String rootId;//根节点

    @Transient
    private String leaf;//是否叶子节点

    @Transient
    private String orderBy;//排序依据

    @Transient
    private String haveList;//是否返回列表

    @Transient
    private List<String> extraProps;//额外属性名列表

    public String getId() {
        return StringUtils.isNotBlank(id) ? id : "id";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return StringUtils.isNotBlank(name) ? name : "name";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return StringUtils.isNotBlank(parentId) ? parentId : "parentId";
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getLeaf() {
        return StringUtils.isNotBlank(leaf) ? leaf : "true";
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getHaveList() {
        return StringUtils.isNotBlank(haveList) ? haveList : "false";
    }

    public void setHaveList(String haveList) {
        this.haveList = haveList;
    }

    public List<String> getExtraProps() {
        return extraProps != null ? extraProps : new ArrayList<>();
    }

    public void setExtraProps(List<String> extraProps) {
        this.extraProps = extraProps;
    }
}
