package com.yfny.utilscommon.basemvc.common;
/**
 * Created by jisongZhou on 2019/10/29.
 **/

import com.yfny.utilscommon.util.StringUtils;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 树形结构基本定义
 * Author jisongZhou
 * Date  2019/10/29
 */
public class BaseTree implements Serializable {

    public final static String NODE_NUMBER = "NUMBER";//数字
    public final static String NODE_STRING = "STRING";//字符

    @Transient
    private String id;//标识字段定义

    @Transient
    private String name;//名称字段定义

    @Transient
    private String parentId;//父级标识字段定义

    @Transient
    private String level;//层级字段定义

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
}
