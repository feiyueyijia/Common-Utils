package com.yfny.utilscommon.calendar.impl;

import com.yfny.utilscommon.calendar.Event;
import com.yfny.utilscommon.calendar.utils.Lunar;
import com.yfny.utilscommon.calendar.utils.LunarMap;
import org.joda.time.DateTime;

import java.util.Date;

public class LunarYearlyRule extends AbstractYearlyRule {

    public LunarYearlyRule(Event calendar) {
        super(calendar);
    }


    @Override
    protected Date getNextYearFirst(Date theDay) {
        Lunar theDayL = new Lunar(theDay);
        return new DateTime(LunarMap.getDate(new Lunar(theDayL.getYear() + 1, 1, 1, false))).toDate();
    }

    @Override
    protected Date getNextYearMonthFirst(Date theDay) {
        Lunar theDayL = new Lunar(theDay);
        int year = theDayL.getYear() + 1;
        int month = getByMonthSet().iterator().next();
        Lunar theNextYearMonthFirstL = new Lunar(year, month, 1, false);
        return new DateTime(LunarMap.getDate(theNextYearMonthFirstL)).withMillisOfDay(getStartTime() * 60 * 1000).toDate();

    }

    @Override
    public Date computeTheLastCountOccurDate() {
        return null;
    }


}
