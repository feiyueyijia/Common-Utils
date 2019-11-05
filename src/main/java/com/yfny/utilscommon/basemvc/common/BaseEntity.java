package com.yfny.utilscommon.basemvc.common;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 对象实体通用辅助Entity（含非数据库字段）
 * Author jisongZhou
 * Date  2019-04-03
 */
public class BaseEntity implements Serializable {

    public final static String ASC = "ASC";//升序
    public final static String DESC = "DESC";//降序

    public final static String AND = "AND";//逻辑与
    public final static String OR = "OR";//逻辑或

    public final static String NORMAL = "NORMAL";//正常
    public final static String COMPLEX = "COMPLEX";//复杂

    public final static int INSERT = 1;//插入
    public final static int UPDATE = 2;//更新
    public final static int DELETE = 3;//删除
    public final static int SELECT = 4;//查询

    @Transient
    private Integer action = SELECT;//执行动作

    @Transient
    private Integer pageNum;//当前页数

    @Transient
    private Integer pageSize;//每页数量

    @Transient
    private Integer pageCount;//总页数

    @Transient
    private Long total;//总数

    @Transient
    private String groupBy;//分组字段

    @Transient
    private String logical = AND;//列表条件逻辑指定，默认为AND

    @Transient
    private String complexRate = NORMAL;//列表条件复杂度指定，默认为NORMAL

    @Transient
    private BaseTree treeConfig;//树形结构指定

    @Transient
    private List<BaseOrder> orders;//排序字段及排序方式（升序，降序）

    @Transient
    private List<BaseNumScope> numScopes;//数字范围指定

    @Transient
    private List<BaseTimeScope> timeScopes;//时间范围指定

    @Transient
    private String param1;//备用参数1

    @Transient
    private String param2;//备用参数1

    @Transient
    private String param3;//备用参数1

    public BaseEntity() {

    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getLogical() {
        return logical;
    }

    public void setLogical(String logical) {
        this.logical = logical;
    }

    public String getComplexRate() {
        return complexRate;
    }

    public void setComplexRate(String complexRate) {
        this.complexRate = complexRate;
    }

    public BaseTree getTreeConfig() {
        return treeConfig != null ? treeConfig : new BaseTree();
    }

    public void setTreeConfig(BaseTree treeConfig) {
        this.treeConfig = treeConfig;
    }

    public List<BaseOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<BaseOrder> orders) {
        this.orders = orders;
    }

    public List<BaseNumScope> getNumScopes() {
        return numScopes;
    }

    public void setNumScopes(List<BaseNumScope> numScopes) {
        this.numScopes = numScopes;
    }

    public List<BaseTimeScope> getTimeScopes() {
        return timeScopes;
    }

    public void setTimeScopes(List<BaseTimeScope> timeScopes) {
        this.timeScopes = timeScopes;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }
}
