package com.yfny.utilscommon.calendar.impl;

import com.yfny.utilscommon.calendar.Event;
import com.yfny.utilscommon.calendar.field.ByDay;
import com.yfny.utilscommon.calendar.utils.DateTimeUtils;
import com.yfny.utilscommon.calendar.utils.RuleConst;
import org.joda.time.DateTime;

import java.util.Date;

public class WeeklyRule extends AbstractRecurRule {

    public WeeklyRule(Event calendar) {
        super(calendar);
    }

    @Override
    public Date computeNextOccurDate(Date offsetDate) {
        return computeNextOccurDateHelper(offsetDate, 1);
    }

    private Date computeNextOccurDateHelper(Date offsetDate, int retry) {
        if (retry > RuleConst.MAX_RETRY) {
            return null;
        }
        Date startDateTime = getStartDateTime();
        Date theWeekFirst = DateTimeUtils.theWeekFirst(startDateTime);
        int periodWeeks =
                DateTimeUtils.periodWeeks(DateTimeUtils.clearTime(theWeekFirst), offsetDate);
        int nextWeekIndex =
                periodWeeks % getInterval() == 0 ? periodWeeks : periodWeeks + getInterval()
                        - periodWeeks % getInterval();
        Date nextWeekFirst = DateTimeUtils.plusWeeks(theWeekFirst, nextWeekIndex);
        Date nextOccurDate = null;
        for (ByDay byDay : getByDaySet()) {
            Date curDay =
                    new DateTime(nextWeekFirst).withDayOfWeek(byDay.getWeekDay().getIndex())
                            .toDate();
            if (curDay.getDay() == 1) {
                curDay = DateTimeUtils.theNextWeekFirst(curDay);
            }
            if (DateTimeUtils.compareTo(curDay, offsetDate) <= 0) {// 过期
                continue;
            }
            if (nextOccurDate == null || DateTimeUtils.compareTo(curDay, nextOccurDate) < 0) {
                nextOccurDate = curDay;
            }
        }

        // 如果不巧的事发生了: 虽然日期在该周期内,但是时间(HHMM)已经过期
        if (nextOccurDate == null) {
            nextOccurDate =
                    computeNextOccurDateHelper(DateTimeUtils.clearTime(DateTimeUtils
                            .theNextWeekFirst(offsetDate)), ++retry);
        }

        return nextOccurDate;
    }

    @Override
    public Date computeTheLastCountOccurDate() {
        int weeks = (getCount() - 1) * getInterval();
        return new DateTime(getStartDateTime()).plusWeeks(weeks).withDayOfWeek(7).toDate();
    }


}
