package com.yfny.utilscommon.calendar.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeUtils extends DateUtils {

    public static int periodDays(Date d1, Date d2){
        if(d1 == null || d2 == null){
            throw new IllegalArgumentException("d1, d2 should not be null!");
        }
        Duration duration = new Duration(d1.getTime(), d2.getTime());
        return (int)duration.getStandardDays();
    }

    public static int periodWeeks(Date d1, Date d2){
    	d1 = theWeekFirst(d1);
    	d2 = theWeekFirst(d2);
		int period = periodDays(d1, d2) / 7;
        return period;
    }

    public static int periodMonths(Date d1, Date d2){
        if(d1 == null || d2 == null){
            throw new IllegalArgumentException("d1, d2 should not be null!");
        }
        Period period = new Period(d1.getTime(), d2.getTime());
        return period.getYears() * 12 + period.getMonths();
    }

    public static int periodYears(Date d1, Date d2){
        if(d1 == null || d2 == null){
            throw new IllegalArgumentException("d1, d2 should not be null!");
        }
        Period period = new Period(d1.getTime(), d2.getTime());
        return period.getYears();
    }

    /**
     * 比较时间的大小. 比较的精确度是分, 为什么精确度是分呢? 因为日历事件关注的时间相关度就是分,到秒有意义不?
     *
     * @param d1
     * @param d2
     * @return if d1 &gt; d2, return 1, else if d1 == d2 ,return 0 , else , return -1
     */
	public static int compareTo(Date d1, Date d2){
        if(d1 == null || d2 == null){
            throw new IllegalArgumentException("d1, d2 should not be null!");
        }
        DateTime dt1 = new DateTime(d1).withSecondOfMinute(0).withMillisOfSecond(0);
        DateTime dt2 = new DateTime(d2).withSecondOfMinute(0).withMillisOfSecond(0);
        return dt1.compareTo(dt2);
    }

    public static Date plusDays(Date d, int days){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusDays(days).toDate();
    }

    public static Date plusMinutes(Date d, int minutes){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusMinutes(minutes).toDate();
    }

    public static Date plusWeeks(Date d, int weeks){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusWeeks(weeks).toDate();
    }

    public static Date plusMonths(Date d, int months){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusMonths(months).toDate();
    }

    public static Date plusYears(Date d, int years){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusYears(years).toDate();
    }

    public static Date clearTime(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).withMillisOfDay(0).toDate();
    }

    public static Date theWeekFirst(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).withDayOfWeek(1).toDate();
    }
    public static Date theNextWeekFirst(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusWeeks(1).withDayOfWeek(1).toDate();
    }

    public static Date theMonthFirst(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).withDayOfMonth(1).toDate();
    }

    public static Date theNextMonthFirst(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusMonths(1).withDayOfMonth(1).toDate();
    }

    public static Date theMonthEnd(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).withDayOfMonth(new DateTime(d).dayOfMonth().getMaximumValue()).toDate();
    }

    public static Date theNextYearFirst(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusYears(1).withDayOfYear(1).toDate();
    }

    public static Date theYearEnd(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).withDayOfYear(new DateTime(d).dayOfYear().getMaximumValue()).toDate();
    }

	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到昨天日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getYesterday() {
		return DateFormatUtils.format(DateTimeUtils.getYesterday(new Date()), "yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}

	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

	/**
	 * 获取两个日期之间的天数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}


	/**
	 * 简单匹配CST时间，EST时间
	 * @param time
	 * @return
	 */
	public static boolean matchCST(String time){
	    // CST时间验证规则(简易规则，只针对CST日期，不严谨的正则验证)
		String regEx = "^[MTWFS][ouehra][neduitn]\\s"
				+ "[JFMASOND][aepuco][nbrylgptvc]\\s"
				+ "[0-3]\\d\\s"
				+ "[0-2]\\d:[0-5]\\d:[0-5]\\d\\s"
				+ "[CE]ST\\s\\d{4}";
	    // 编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    // 忽略大小写的写法
	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(time);
	    // 字符串是否与正则表达式相匹配
	    boolean flag = matcher.matches();
		return flag;
	}

	/**
	 * 获取提前时间，以分钟推算
	 * @param date
	 * @param minute
	 * @return
	 */
	public static String remindTime(Date date,int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//date 换成已经已知的Date对象
        cal.add(Calendar.MINUTE, -minute);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(cal.getTime());
	}

    public static boolean isSameDay(Date d1, Date d2){
        if(d1 == null || d2 == null){
            throw new IllegalArgumentException("d1, d2 should not be null!");
        }
        DateTime dt1 = new DateTime(d1).withMillisOfDay(0);
        DateTime dt2 = new DateTime(d2).withMillisOfDay(0);
        return dt1.compareTo(dt2) == 0;
    }

    public static Date getYesterday(Date date){
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    public static Date getPeriodDay(Date date,int i){
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, i);
        date = calendar.getTime();
        return date;
    }

    public static String getBirthday(Date date,int i){
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, i);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(calendar.getTime());
    }

    public static String getMinute(Date date,int i){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, i);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return  format.format(cal.getTime());
    }

    public static String getSecond(Date date,int i){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, i);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return  format.format(cal.getTime());
    }

    /**
     * yyyy-MM-dd HH:mm:ss SSS 格式日期字符串
     *
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
        try {
            return DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(dateStr).toDate();
        } catch (IllegalArgumentException ex) {
            return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(dateStr).toDate();
        }
    }

	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate1(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		DateTimeUtils dateTimeUtils = new DateTimeUtils();
		String datestr = "2017-06-01 09:32:09";
		System.out.println(dateTimeUtils.parseDate(datestr));
	}
}
