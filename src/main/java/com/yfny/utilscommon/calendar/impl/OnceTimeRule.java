package com.yfny.utilscommon.calendar.impl;

import com.yfny.utilscommon.calendar.Event;
import com.yfny.utilscommon.calendar.utils.DateTimeUtils;

import java.util.Date;

public class OnceTimeRule extends AbstractRule {

    public OnceTimeRule(Event calendar) {
        super(calendar);
    }

    @Override
    public boolean includes(Date theDate) {
        if (theDate == null)
            return false;
        return (DateTimeUtils.isSameDay(getStartDate(), theDate));
    }

    @Override
    public Date nextOccurDate(Date offsetDate) {
        if (offsetDate == null)
            return null;
        if (DateTimeUtils.compareTo(offsetDate, getStartDateTime()) < 0) {
            return getStartDateTime();
        } else {
            return null;
        }
    }

    @Override
    public Date getRecurEndDate() {
        return getStartDateTime();
    }

}
