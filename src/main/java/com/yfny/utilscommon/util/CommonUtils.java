package com.yfny.utilscommon.util;

import java.util.*;

/**
 * 常用工具方法类
 * Created by jisongZhou on 2019/2/28.
 **/
public class CommonUtils {

    /**
     * 根据条件列表去重
     *
     * @param list   待去重列表
     * @param fields 去重依据字段
     * @param <T>    泛型对象
     * @return 去重列表
     */
    public static <T> List<T> removeDuplicate(List<T> list, String... fields) {
        List<T> resultList = new ArrayList<>();
        Map<String, T> commonMap = new HashMap<>();
        Object object;
        for (T entity : list) {
            String key = "";
            for (String filed : fields) {
                object = ReflectUtils.getFieldValueByName(filed, entity);
                String value = "";
                if (object != null) {
                    value = object.toString();
                }
                key = String.format("%s,%s", value, key);
            }

            if (StringUtils.isNotBlank(key)) {
                if (!commonMap.containsKey(key)) {
                    commonMap.put(key, entity);
                }
            }
        }
        for (String k : commonMap.keySet()) {
            resultList.add(commonMap.get(k));
        }
        return resultList;
    }

    /**
     * 列表去重
     *
     * @param list 待去重列表
     * @return 去重列表
     */
    public static List removeDuplicate(List list) {
        HashSet set = new HashSet();
        List newList = new ArrayList();
        for (Object cd : list) {
            String result = cd.toString();
            if (set.add(result)) {
                newList.add(cd);
            }
        }
        return newList;
    }

    /**
     * 根据int排序
     *
     * @param list  所需列表
     * @param order 升序/降序 asc/desc
     * @param field 字段名
     * @param <T>   泛型对象
     * @return 完成排序的列表数据
     */
    public static <T> List<T> sortingByListByInt(List<T> list, final String order, final String field) {

        list.sort((o1, o2) -> {
            //反射获取字段名
            String t1 = ReflectUtils.getFieldValueByName(field, o1) + "";
            String t2 = ReflectUtils.getFieldValueByName(field, o2) + "";
            boolean b1 = StringUtils.isNumeric(t1);
            boolean b2 = StringUtils.isNumeric(t2);
            if (b1 && b2) {
                int a1 = Integer.parseInt(t1);
                int a2 = Integer.parseInt(t2);
                //降序
                if (order.equals("desc")) {
                    return Integer.compare(a2, a1);
                }
                //升序
                else if (order.equals("asc")) {
                    return Integer.compare(a1, a2);
                }
            }
            return 0;
        });
        return list;
    }

    /**
     * 根据String排序
     *
     * @param list  所需列表
     * @param order 升序/降序 asc/desc
     * @param field 字段名
     * @param <T>   泛型
     * @return 完成排序的列表数据
     */
    public static <T> List<T> sortingByListByString(List<T> list, final String order, final String field) {

        list.sort((o1, o2) -> {
            //反射获取字段名
            String t1 = ReflectUtils.getFieldValueByName(field, o1) + "";
            String t2 = ReflectUtils.getFieldValueByName(field, o2) + "";
            //降序
            if (order.equals("desc")) {
                return Integer.compare(t2.length(), t1.length());
            }
            //升序
            else if (order.equals("asc")) {
                return Integer.compare(t1.length(), t2.length());
            }
            return 0;
        });
        return list;
    }

    /**
     * 插入排序法
     *
     * @param arr 排序数组
     * @return 完成排序数组
     */
    public static int[] insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (temp < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
                if (j == -1) {
                    break;
                }
            }
            arr[j + 1] = temp;
        }
        return arr;
    }


    /**
     * 快速排序法
     *
     * @param arr   排序数组
     * @param left  排序最小边界
     * @param right 排序最大边界
     * @return 完成排序数组
     */
    public static int[] fastSort(int[] arr, int left, int right) {
        if (left < right) {
            int s = arr[left];
            int i = left;
            int j = right + 1;
            while (true) {
                //向右找大于s的元素的索引
                while (i + 1 < arr.length && arr[++i] < s) ;
                //向左找小于s的元素的索引
                while (j - 1 > -1 && arr[--j] > s) ;
                //如果i >= j 推出循环
                if (i >= j) {
                    break;
                } else {
                    //教化i和j位置的元素
                    int t = arr[i];
                    arr[i] = arr[j];
                    arr[j] = t;
                }
            }
            arr[left] = arr[j];
            arr[j] = s;
            //对左面进行递归
            fastSort(arr, left, j - 1);
            //对右面进行递归
            fastSort(arr, j + 1, right);
        }
        return arr;
    }
}
