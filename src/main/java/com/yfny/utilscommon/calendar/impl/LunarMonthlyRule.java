package com.yfny.utilscommon.calendar.impl;

import com.yfny.utilscommon.calendar.Event;
import com.yfny.utilscommon.calendar.utils.Lunar;
import com.yfny.utilscommon.calendar.utils.LunarMap;
import org.joda.time.DateTime;

import java.util.Date;

public class LunarMonthlyRule extends AbstractMonthlyRule {

    public LunarMonthlyRule(Event calendar) {
        super(calendar);
        this.ruleHelper = new LunarCalendarRuleHelper();
    }

    @Override
    protected Date getNextMonthFirst(Date theDay) {
        Lunar theDayL = new Lunar(theDay).nextMonthFirst();
        return new DateTime(LunarMap.getDate(theDayL)).toDate();
    }

    @Override
    protected Date getNextOccurMonthFirst(Date theDay) {
        Lunar theDayL = new Lunar(theDay);
        Lunar nextOccurMonthFirstL =
                new Lunar(theDayL.getYear(), theDayL.getMonth(), 1, theDayL.isLeap());
        return new DateTime(LunarMap.getDate(nextOccurMonthFirstL)).withMillisOfDay(getStartTime() * 60 * 1000).toDate();
    }

    @Override
    public Date computeTheLastCountOccurDate() {
        return null;
    }
}
