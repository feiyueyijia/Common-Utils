package com.yfny.utilscommon.calendar.impl;

import com.yfny.utilscommon.calendar.Event;
import com.yfny.utilscommon.calendar.utils.DateTimeUtils;
import com.yfny.utilscommon.calendar.utils.RuleConst;

import java.util.Date;

public abstract class AbstractYearlyRule extends AbstractRecurRule {

    private AbstractMutliCalendarRuleHelper ruleHelper = new GregorianCalenarRuleHelper();

    public AbstractYearlyRule(Event calendar) {
        super(calendar);
    }

    public AbstractYearlyRule(Event calendar, AbstractMutliCalendarRuleHelper ruleHelper) {
        super(calendar);
        this.ruleHelper = ruleHelper;
    }

    @Override
    public Date computeNextOccurDate(Date offsetDate) {
        return computeNextOccurDateHelper(offsetDate, 1);
    }

    private Date computeNextOccurDateHelper(Date offsetDate, int retry) {
        if (retry > RuleConst.MAX_RETRY) {
            return null;
        }

        Date nextOccurMonthFirst = getNextYearMonthFirst(offsetDate);
        Date nextOccurDate = ruleHelper.computeNextOccurMonthDay(this, nextOccurMonthFirst);

        boolean isRetry =
                (nextOccurDate == null) || (DateTimeUtils.compareTo(nextOccurDate, offsetDate) < 0)
                        || (DateTimeUtils.compareTo(nextOccurDate, offsetDate) == 0 && retry == 1);

        if (isRetry) {
            nextOccurDate =
                    computeNextOccurDateHelper(DateTimeUtils.clearTime(getNextYearFirst(offsetDate)), ++retry);
        }

        return nextOccurDate;

    }

    protected abstract Date getNextYearFirst(Date theDay);

    protected abstract Date getNextYearMonthFirst(Date theDay);

}
