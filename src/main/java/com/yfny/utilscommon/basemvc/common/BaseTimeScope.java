package com.yfny.utilscommon.basemvc.common;
/**
 * Created by jisongZhou on 2019/10/29.
 **/

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 时间范围定义
 * Author jisongZhou
 * Date  2019/10/29
 */
public class BaseTimeScope implements Serializable {

    @Transient
    private String name;

    @Transient
    private String start;

    @Transient
    private String end;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
