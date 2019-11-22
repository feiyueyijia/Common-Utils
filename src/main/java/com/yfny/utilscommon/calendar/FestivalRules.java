package com.yfny.utilscommon.calendar;

public class FestivalRules {

	//农历
	public static final int GONGLI = 0;
	//公历
	public static final int NONGLI = 1;

	//元旦
	public static final String YUANDAN = "YEARLY;BYMONTH=1;BYMONTHDAY=1";
	public static final String YUANDANLABLE = "元旦";
	//春节（农历）
	public static final String CHUNJIE = "YEARLY;BYMONTH=1;BYMONTHDAY=1";
	public static final String CHUNJIELABLE = "春节";
	//元宵（农历）
	public static final String YUANXIAO = "YEARLY;BYMONTH=1;BYMONTHDAY=15";
	public static final String YUANXIAOLABLE = "元宵";
	//龙抬头（农历）
	public static final String LONGTAITOU = "YEARLY;BYMONTH=2;BYMONTHDAY=2";
	public static final String LONGTAITOULABLE = "龙抬头";
	//情人节
	public static final String QINGREN = "YEARLY;BYMONTH=2;BYMONTHDAY=14";
	public static final String QINGRENLABLE = "情人节";
	//妇女节
	public static final String FUNV = "YEARLY;BYMONTH=3;BYMONTHDAY=8";
	public static final String FUNVLABLE = "妇女节";
	//清明节
	public static final String QINGMING = "YEARLY;BYMONTH=4;BYMONTHDAY=4";
	public static final String QINGMINGLABLE = "清明节";
	//清明节
	public static final String QINGMING1 = "YEARLY;BYMONTH=4;BYMONTHDAY=5";
	public static final String QINGMING1LABLE = "清明节";
	//劳动节
	public static final String LAODONG = "YEARLY;BYMONTH=5;BYMONTHDAY=1";
	public static final String LAODONGLABLE = "劳动节";
	//母亲节
	public static final String MUQING = "YEARLY;BYMONTH=5;BYDAY=2SU";
	public static final String MUQINGLABLE = "母亲节";
	//端午（农历）
	public static final String DUANWU = "YEARLY;BYMONTH=5;BYMONTHDAY=5";
	public static final String DUANWULABLE = "端午节";
	//儿童节
	public static final String ERTONG = "YEARLY;BYMONTH=6;BYMONTHDAY=1";
	public static final String ERTONGLABLE  = "儿童节";
	//父亲节
	public static final String FUQING = "YEARLY;BYMONTH=6;BYDAY=3SU";
	public static final String FUQINGLABLE = "父亲节";
	//建党节
	public static final String JIANDANG = "YEARLY;BYMONTH=7;BYMONTHDAY=1";
	public static final String JIANDANGLABLE = "建党节";
	//七夕（农历）
	public static final String QIXI = "YEARLY;BYMONTH=7;BYMONTHDAY=7";
	public static final String QIXILABLE = "七夕";
	//建军节
	public static final String JIANJUN = "YEARLY;BYMONTH=8;BYMONTHDAY=1";
	public static final String JIANJUNLABLE = "建军节";
	//中秋
	public static final String ZHONGQIU = "YEARLY;BYMONTH=8;BYMONTHDAY=15";
	public static final String ZHONGQIULABLE = "中秋节";
	//教师节
	public static final String JIAOSHI = "YEARLY;BYMONTH=9;BYMONTHDAY=10";
	public static final String JIAOSHILABLE  = "教师节";
	//重阳（农历）
	public static final String CHONGYANG = "YEARLY;BYMONTH=9;BYMONTHDAY=9";
	public static final String CHONGYANGLABLE = "重阳节";
	//国庆节
	public static final String GUOQING = "YEARLY;BYMONTH=10;BYMONTHDAY=1";
	public static final String GUOQINGLABLE = "国庆节";
	//万圣节
	public static final String WANSHENG = "YEARLY;BYMONTH=11;BYMONTHDAY=1";
	public static final String WANSHENGLABLE = "万圣节";
	//光棍节
	public static final String GUANGGUN = "YEARLY;BYMONTH=11;BYMONTHDAY=11";
	public static final String GUANGGUNLABLE = "光棍节";
	//感恩节
	public static final String GANEN = "YEARLY;BYMONTH=11;BYDAY=4TH";
	public static final String GANENLABLE = "感恩节";
	//圣诞节
	public static final String SHENGDAN = "YEARLY;BYMONTH=12;BYMONTHDAY=25";
	public static final String SHENGDANLABLE = "圣诞节";

	//一月
	public static final String []  JAN = {YUANDAN,CHUNJIE,YUANXIAO};
	public static final String []  JANLABLE = {YUANDANLABLE,CHUNJIELABLE,YUANXIAOLABLE};
	public static final int []  JANTYPE = {GONGLI,NONGLI,NONGLI};
	//二月
	public static final String []  FEB = {CHUNJIE,YUANXIAO,LONGTAITOU,QINGREN};
	public static final String []  FEBLABLE = {CHUNJIELABLE,YUANXIAOLABLE,LONGTAITOULABLE,QINGRENLABLE};
	public static final int []  FEBTYPE = {NONGLI,NONGLI,NONGLI,GONGLI};
	//三月
	public static final String []  MAR = {FUNV};
	public static final String []  MARLABLE = {FUNVLABLE};
	public static final int []  MARTYPE = {GONGLI};
	//四月
	public static final String []  APR = {QINGMING,QINGMING1};
	public static final String []  APRLABLE = {QINGMINGLABLE,QINGMING1LABLE};
	public static final int []  APRTYPE = {GONGLI,GONGLI};
	//五月
	public static final String []  MAY = {LAODONG,MUQING,DUANWU};
	public static final String []  MAYLABLE = {LAODONGLABLE,MUQINGLABLE,DUANWULABLE};
	public static final int []  MAYTYPE = {GONGLI,GONGLI,NONGLI};
	//六月
	public static final String []  JUN = {ERTONG,FUQING,DUANWU};
	public static final String []  JUNLABLE = {ERTONGLABLE,FUQINGLABLE,DUANWULABLE};
	public static final int []  JUNTYPE = {GONGLI,GONGLI,NONGLI};
	//七月
	public static final String []  JUL = {JIANDANG,QIXI};
	public static final String []  JULLABLE  = {JIANDANGLABLE ,QIXILABLE };
	public static final int []  JULTYPE = {GONGLI,NONGLI};
	//八月
	public static final String []  AUG = {JIANJUN,QIXI};
	public static final String []  AUGLABLE = {JIANJUNLABLE,QIXILABLE};
	public static final int []  AUGTYPE = {GONGLI,NONGLI};
	//九月
	public static final String []  SEP = {JIAOSHI,ZHONGQIU,CHONGYANG};
	public static final String []  SEPLABLE = {JIAOSHILABLE,ZHONGQIULABLE,CHONGYANGLABLE};
	public static final int []  SEPTYPE = {GONGLI,NONGLI,NONGLI};
	//十月
	public static final String []  OCT = {GUOQING,ZHONGQIU,CHONGYANG};
	public static final String []  OCTLABLE = {GUOQINGLABLE,ZHONGQIULABLE,CHONGYANGLABLE};
	public static final int []  OCTTYPE = {GONGLI,NONGLI,NONGLI};
	//十一月
	public static final String []  NOV = {WANSHENG,GUANGGUN,GANEN};
	public static final String []  NOVLABLE = {WANSHENGLABLE,GUANGGUNLABLE,GANENLABLE};
	public static final int []  NOVTYPE = {GONGLI,GONGLI,GONGLI};
	//十二月
	public static final String []  DEC = {SHENGDAN};
	public static final String []  DECLABLE = {SHENGDANLABLE};
	public static final int []  DECTYPE = {GONGLI};
}
