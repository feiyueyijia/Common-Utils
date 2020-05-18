package com.yfny.utilscommon.basemvc.common;

/**
 * 额外属性定义
 * Author jisongZhou
 * Date  2019/12/31
 */
public class ExtraProp {
    /**
     * 属性名称
     */
    private String propName;
    /**
     * 属性值
     */
    private Object propValue;

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public Object getPropValue() {
        return propValue;
    }

    public void setPropValue(Object propValue) {
        this.propValue = propValue;
    }
}
