package com.yfny.utilscommon.calendar;

import java.util.Date;

public class CommonRules {
	public static final String DAILY = "DAILY";
	public static final String WORKDAYS = "WEEKLY;INTERVAL=1;BYDAY=MO,TU,WE,TH,FR";
	public static final String WEEKLY = "DAILY;INTERVAL=7";
	public static final String MONTHLY = "MONTHLY;BYMONTHDAY=%s";
	public static final String YEARLY = "YEARLY;BYMONTH=%s;BYMONTHDAY=%s";

	public static final String returnWeekDay(Date date) {
		String weekDay = "";
		switch (date.getDay()) {
		case 1:
			weekDay = "MO";
			break;
		case 2:
			weekDay = "TU";
			break;
		case 3:
			weekDay = "WE";
			break;
		case 4:
			weekDay = "TH";
			break;
		case 5:
			weekDay = "FR";
			break;
		case 6:
			weekDay = "SA";
			break;
		case 7:
			weekDay = "SU";
			break;
		default:
			break;
		}
		return weekDay;
	}
}
