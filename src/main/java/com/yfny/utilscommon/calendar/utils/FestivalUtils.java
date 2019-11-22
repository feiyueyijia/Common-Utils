package com.yfny.utilscommon.calendar.utils;

import com.yfny.utilscommon.calendar.*;
import com.yfny.utilscommon.util.StringUtils;

import java.util.Date;

public class FestivalUtils {

    public static Festival getFestivalInfo(Date date) {
        Festival festival = new Festival();
        boolean flag = false;
        switch (date.getMonth() + 1) {
            case 1:
                String[] JAN = FestivalRules.JAN;
                int[] JANTYPE = FestivalRules.JANTYPE;
                for (int i = 0; i < JAN.length; i++) {
                    if (JANTYPE[i] == 0) {
                        flag = isFestival(date, JAN[i], DateTimeUtils.getYesterday(date), JANTYPE[i]);
                    } else {
                        flag = isFestival(date, JAN[i], DateTimeUtils.parseDate(getVaildDay(date)), JANTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.JANLABLE[i]);
                        break;
                    }
                }
                break;
            case 2:
                String[] FEB = FestivalRules.FEB;
                int[] FEBTYPE = FestivalRules.FEBTYPE;
                for (int i = 0; i < FEB.length; i++) {
                    if (FEBTYPE[i] == 0) {
                        flag = isFestival(date, FEB[i], DateTimeUtils.getYesterday(date), FEBTYPE[i]);
                    } else {
                        flag = isFestival(date, FEB[i], DateTimeUtils.parseDate(getVaildDay(date)), FEBTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.FEBLABLE[i]);
                        break;
                    }
                }
                break;
            case 3:
                String[] MAR = FestivalRules.MAR;
                int[] MARTYPE = FestivalRules.MARTYPE;
                for (int i = 0; i < MAR.length; i++) {
                    if (MARTYPE[i] == 0) {
                        flag = isFestival(date, MAR[i], DateTimeUtils.getYesterday(date), MARTYPE[i]);
                    } else {
                        flag = isFestival(date, MAR[i], DateTimeUtils.parseDate(getVaildDay(date)), MARTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.MARLABLE[i]);
                        break;
                    }
                }
                break;
            case 4:
                String[] APR = FestivalRules.APR;
                int[] APRTYPE = FestivalRules.APRTYPE;
                for (int i = 0; i < APR.length; i++) {
                    if (APRTYPE[i] == 0) {
                        flag = isFestival(date, APR[i], DateTimeUtils.getYesterday(date), APRTYPE[i]);
                    } else {
                        flag = isFestival(date, APR[i], DateTimeUtils.parseDate(getVaildDay(date)), APRTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.APRLABLE[i]);
                        break;
                    }
                }
                break;
            case 5:
                String[] MAY = FestivalRules.MAY;
                int[] MAYTYPE = FestivalRules.MAYTYPE;
                for (int i = 0; i < MAY.length; i++) {
                    if (MAYTYPE[i] == 0) {
                        flag = isFestival(date, MAY[i], DateTimeUtils.getYesterday(date), MAYTYPE[i]);
                    } else {
                        flag = isFestival(date, MAY[i], DateTimeUtils.parseDate(getVaildDay(date)), MAYTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.MAYLABLE[i]);
                        break;
                    }
                }
                break;
            case 6:
                String[] JUN = FestivalRules.JUN;
                int[] JUNTYPE = FestivalRules.JUNTYPE;
                for (int i = 0; i < JUN.length; i++) {
                    if (JUNTYPE[i] == 0) {
                        flag = isFestival(date, JUN[i], DateTimeUtils.getYesterday(date), JUNTYPE[i]);
                    } else {
                        flag = isFestival(date, JUN[i], DateTimeUtils.parseDate(getVaildDay(date)), JUNTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.JUNLABLE[i]);
                        break;
                    }
                }
                break;
            case 7:
                String[] JUL = FestivalRules.JUL;
                int[] JULTYPE = FestivalRules.JULTYPE;
                for (int i = 0; i < JUL.length; i++) {
                    if (JULTYPE[i] == 0) {
                        flag = isFestival(date, JUL[i], DateTimeUtils.getYesterday(date), JULTYPE[i]);
                    } else {
                        flag = isFestival(date, JUL[i], DateTimeUtils.parseDate(getVaildDay(date)), JULTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.JULLABLE[i]);
                        break;
                    }
                }
                break;
            case 8:
                String[] AUG = FestivalRules.AUG;
                int[] AUGTYPE = FestivalRules.AUGTYPE;
                for (int i = 0; i < AUG.length; i++) {
                    if (AUGTYPE[i] == 0) {
                        flag = isFestival(date, AUG[i], DateTimeUtils.getYesterday(date), AUGTYPE[i]);
                    } else {
                        flag = isFestival(date, AUG[i], DateTimeUtils.parseDate(getVaildDay(date)), AUGTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.AUGLABLE[i]);
                        break;
                    }
                }
                break;
            case 9:
                String[] SEP = FestivalRules.SEP;
                int[] SEPTYPE = FestivalRules.SEPTYPE;
                for (int i = 0; i < SEP.length; i++) {
                    if (SEPTYPE[i] == 0) {
                        flag = isFestival(date, SEP[i], DateTimeUtils.getYesterday(date), SEPTYPE[i]);
                    } else {
                        flag = isFestival(date, SEP[i], DateTimeUtils.parseDate(getVaildDay(date)), SEPTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.SEPLABLE[i]);
                        break;
                    }
                }
                break;
            case 10:
                String[] OCT = FestivalRules.OCT;
                int[] OCTTYPE = FestivalRules.OCTTYPE;
                for (int i = 0; i < OCT.length; i++) {
                    if (OCTTYPE[i] == 0) {
                        flag = isFestival(date, OCT[i], DateTimeUtils.getYesterday(date), OCTTYPE[i]);
                    } else {
                        flag = isFestival(date, OCT[i], DateTimeUtils.parseDate(getVaildDay(date)), OCTTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.OCTLABLE[i]);
                        break;
                    }
                }
                break;
            case 11:
                String[] NOV = FestivalRules.NOV;
                int[] NOVTYPE = FestivalRules.NOVTYPE;
                for (int i = 0; i < NOV.length; i++) {
                    if (NOVTYPE[i] == 0) {
                        flag = isFestival(date, NOV[i], DateTimeUtils.getYesterday(date), NOVTYPE[i]);
                    } else {
                        flag = isFestival(date, NOV[i], DateTimeUtils.parseDate(getVaildDay(date)), NOVTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.NOVLABLE[i]);
                        break;
                    }
                }
                break;
            case 12:
                String[] DEC = FestivalRules.DEC;
                int[] DECTYPE = FestivalRules.DECTYPE;
                for (int i = 0; i < DEC.length; i++) {
                    if (DECTYPE[i] == 0) {
                        flag = isFestival(date, DEC[i], DateTimeUtils.getYesterday(date), DECTYPE[i]);
                    } else {
                        flag = isFestival(date, DEC[i], DateTimeUtils.parseDate(getVaildDay(date)), DECTYPE[i]);
                    }
                    if (flag) {
                        festival.setName(FestivalRules.DECLABLE[i]);
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return festival;
    }

    private static String getVaildDay(Date date) {
        String year = (date.getYear() - 1 + 1900) + "";
        String month = (date.getMonth() + 3) + "";
        String day = "01";
        String vaildDate = year + "-" + month + "-" + day;
        return vaildDate;
    }

    public static boolean isFestival(Date date, String festivalRules, Date sdate, int type) {
        Event calendar = new Event();
        String rules = null;

        rules = String.format(festivalRules);
        calendar.setCalendarType(type);
        calendar.setStartDate(DateTimeUtils.parseDate("2010-01-01"));
        calendar.setRule(rules);

        Rule rule = RuleFactory.createRule(calendar);
        boolean flag = DateTimeUtils.isSameDay(rule.nextOccurDate(sdate), date);
        return flag;
    }

    public static void main(String[] args) {
        FestivalUtils festivalUtils = new FestivalUtils();
        String daString = "2017-05-30";
        Date date = DateTimeUtils.parseDate(daString);
        Festival i = festivalUtils.getFestivalInfo(date);
        String name = StringUtils.isNotBlank(i.getName()) ? i.getName() : "系统未定义";
        System.out.println("节日名称:  " + name);
    }
}
