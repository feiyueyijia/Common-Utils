package com.yfny.utilscommon.calendar.field;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ByDay implements Comparable<ByDay> {

    private Integer qualifier;

    private WeekDayEnum weekDay;

    public ByDay(Integer qualifier, WeekDayEnum weekDay) {
        this.qualifier = qualifier;
        this.weekDay = weekDay;
    }

    public Integer getQualifier() {
        return qualifier;
    }

    public WeekDayEnum getWeekDay() {
        return weekDay;
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public int compareTo(ByDay other) {
        return 0;
    }
}
