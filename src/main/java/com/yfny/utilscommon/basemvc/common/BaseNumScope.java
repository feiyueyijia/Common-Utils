package com.yfny.utilscommon.basemvc.common;
/**
 * Created by jisongZhou on 2019/10/29.
 **/

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 数字范围定义
 * Author jisongZhou
 * Date  2019/10/29
 */
public class BaseNumScope implements Serializable {

    @Transient
    private String name;

    @Transient
    private Number start;

    @Transient
    private Number end;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getStart() {
        return start;
    }

    public void setStart(Number start) {
        this.start = start;
    }

    public Number getEnd() {
        return end;
    }

    public void setEnd(Number end) {
        this.end = end;
    }
}
