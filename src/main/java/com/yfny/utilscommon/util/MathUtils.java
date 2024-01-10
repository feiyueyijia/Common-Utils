package com.yfny.utilscommon.util;

import java.math.BigDecimal;
import java.util.*;

/**
 * 数学计算工具
 * Author jisongZhou
 * Date  2019/11/5
 */
public class MathUtils {

    public static double mathSum(double value1, String value2, String defaultValue) {
        double sum = 0;
        sum = value1 + Integer.parseInt(StringUtils.isNotBlank(value2) ? value2 : defaultValue);
        BigDecimal bigDecimal = new BigDecimal(sum);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static int mathSum(int value1, String value2, String defaultValue) {
        int sum = 0;
        sum = value1 + Integer.parseInt(StringUtils.isNotBlank(value2) ? value2 : defaultValue);
        return sum;
    }

    public static double mathSum(double value1, double value2) {
        double sum = 0;
        sum = value1 + value2;
        BigDecimal bigDecimal = new BigDecimal(sum);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double mathSub(double value1, String value2, String defaultValue) {
        double sub = 0;
        sub = value1 - Integer.parseInt(StringUtils.isNotBlank(value2) ? value2 : defaultValue);
        BigDecimal bigDecimal = new BigDecimal(sub);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static int mathSub(int value1, String value2, String defaultValue) {
        int sub = 0;
        sub = value1 - Integer.parseInt(StringUtils.isNotBlank(value2) ? value2 : defaultValue);
        return sub;
    }

    public static double mathSub(double value1, double value2) {
        double sub = 0;
        sub = value1 - value2;
        BigDecimal bigDecimal = new BigDecimal(sub);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double mathAve(Double value, int size) {
        return mathAve(value, size, 2);
    }

    public static double mathAve(Double value, int size, int scale) {
        double ave = 0;
        ave = value / size;
        BigDecimal bigDecimal = new BigDecimal(ave);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double mathOpAve(Double value, int size) {
        double ave = 0;
        ave = value / size;
        BigDecimal bigDecimal = new BigDecimal(1 - ave);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double powerSum(double value, int count) {
        double result = 0;
        for (int i = 1; i <= count; i++) {
            result = result + Math.pow(value, i);
        }
        return result;
    }

    public static double mathMul(int num, double price) {
        num = num > 0 ? num : 0;
        price = price > 0 ? price : 0;
        double total = price * num;
        BigDecimal bigDecimal = new BigDecimal(total);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //正数随机
    public static int getRandom(int min, int max) {
        int rd = 0;
        Random random = new Random();
        if (max - min > 0) {
            if (max == 1) {
                rd = random.nextInt(2);
            } else {
                rd = random.nextInt(max) % (max - min + 1) + min;
            }
        }
        return rd;
    }

    //正数随机
    public static int[] getRandom(int size, int min, int max) {
        //随机数量不能大于总数量
        if (max - min < size - 1) {
            size = max - min;
        }
        Random random = new Random();
        Set<Integer> intSet = new HashSet<Integer>();
        //Set内不允许存在相同值，利用此特性可以获取不同的随机数
        while (intSet.size() < size) {
            //在区间内随机数
            int rd = random.nextInt(max) % (max - min + 1) + min;
            intSet.add(rd);
        }
        //转成int数组返回
        int intRet[] = new int[size];
        Iterator<Integer> it = intSet.iterator();
        int i = 0;
        while (it.hasNext() & i < size) {
            intRet[i] = Integer.parseInt(it.next().toString());
            i++;
        }
        return CommonUtils.insertSort(intRet);
    }

    /**
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1
     *
     * @param n
     * @return
     */
    public static long factorial(int n) {
        return (n > 1) ? n * factorial(n - 1) : 1;
    }

    /**
     * 计算排列数，即A(n, m) = n!/(n-m)!
     *
     * @param n
     * @param m
     * @return
     */
    public static long arrangement(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) : 0;
    }

    /**
     * 计算组合数，即C(n, m) = n!/((n-m)! * m!)
     *
     * @param n
     * @param m
     * @return
     */
    public static long combination(int n, int m) {
        return (n >= m) ? factorial(n) / (factorial(n - m) * factorial(m)) : 0;
    }

    public static void main(String[] args) {
        System.out.println(MathUtils.combination(35, 5));
        //System.out.println(MathUtils.combination(16, 1));
        //System.out.println(MathUtils.combination(33, 6) * 16);
        //System.out.println(Arrays.toString(MathUtils.getRandom(2, 1, 2)));
    }
}
