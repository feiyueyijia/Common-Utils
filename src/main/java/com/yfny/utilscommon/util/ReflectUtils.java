package com.yfny.utilscommon.util;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import javax.persistence.Column;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 反射机制工具类
 * Created by jiSongZhou on 2017/12/4.
 **/
public class ReflectUtils {

    /**
     * 使用反射根据属性名称获取属性值
     *
     * @param fieldName 属性名称
     * @param object    操作对象
     * @return value 属性值
     */
    public static Object getFieldValueByName(String fieldName, Object object) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = object.getClass().getMethod(getter);
            return method.invoke(object);
        } catch (Exception e) {
            //System.out.println("属性不存在");
            return null;
        }
    }

    /**
     * 使用反射根据属性名称设置属性值
     *
     * @param clazz 类别
     * @param map   属性集合
     * @return 实体对象
     */
    public static Object setFieldValueByName(Class<?> clazz, Map<String, Object> map) {
        Object object = null;
        try {
            object = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setFieldValueByName(object, clazz, map);
    }

    /**
     * 使用反射根据属性名称设置属性值
     *
     * @param object 实体对象
     * @param clazz  类别
     * @param map    属性集合
     * @return 实体对象
     */
    public static Object setFieldValueByName(Object object, Class<?> clazz, Map<String, Object> map) {
        try {
            for (String fieldName : map.keySet()) {
                Object fieldValue = map.get(fieldName);
                Field field = getDeclaredField(object, fieldName);
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String setter = "set" + firstLetter + fieldName.substring(1);
                Method method = clazz.getMethod(setter, field.getType());
                method.invoke(object, fieldValue);
            }
            return object;
        } catch (Exception e) {
            //System.out.println("属性不存在");
            return null;
        }
    }

    /**
     * 获取对象的属性名称数组
     *
     * @param object 实体对象
     * @return 属性名称数组
     */
    public static String[] getFieldName(Object object) {
        Field[] localFields = object.getClass().getDeclaredFields();
        Field[] superFields = object.getClass().getSuperclass().getDeclaredFields();
        int size = localFields.length + superFields.length;
        String[] fieldNames = new String[size];
        for (int i = 0; i < size; i++) {
            if (i < localFields.length) {
                //System.out.println(fields[i].getType());
                fieldNames[i] = localFields[i].getName();
            } else {
                int j = i - localFields.length;
                fieldNames[i] = superFields[j].getName();
            }
        }
        return fieldNames;
    }

    /**
     * 复制非null属性
     *
     * @param source 复制来源
     * @param result 复制结果
     */
    public static void copyProperties(Object source, Object result) {
        copySuperProperties(source, result);
        copyLocalProperties(source, result);
    }

    /**
     * 复制非null属性
     *
     * @param source 复制来源
     * @param result 复制结果
     */
    public static void copyLocalProperties(Object source, Object result) {
        String[] fields = getFieldName(source);
        Map<String, Object> map = new HashMap<>();
        for (String field : fields) {
            Object value = getFieldValueByName(field, source);
            if (value != null) {
                map.put(field, value);
            } else {
                setFieldValue(result, field, null);
            }
        }
        setFieldValueByName(result, source.getClass(), map);
    }

    /**
     * 复制超类非null属性（超类动态获取太麻烦，写死好了）
     *
     * @param source 复制来源
     * @param result 复制结果
     */
    public static void copySuperProperties(Object source, Object result) {
//        setFieldValue(result, "delFlag", getFieldValue(source, "delFlag"));
//        setFieldValue(result, "updateBy", getFieldValue(source, "updateBy"));
//        setFieldValue(result, "updateDate", getFieldValue(source, "updateDate"));
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value     : 将要设置的值
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */
    public static Object getFieldValue(Object object, String fieldName) {
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //获取 object 中 field 所代表的属性值
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取对象中指定注解的字段名称
     *
     * @param object    对象
     * @param clazzList 注解类型
     * @return 字段名列表
     */
    @SafeVarargs
    public static List<String> getAnnotationFieldName(Object object, Class<? extends Annotation>... clazzList) {
        Field[] localFields = object.getClass().getDeclaredFields();
        List<String> annotationFieldNameList = new ArrayList<>();
        for (Field annotationField : localFields) {
            for (Class<? extends Annotation> clazz : clazzList) {
                if (annotationField.isAnnotationPresent(clazz)) {
                    annotationFieldNameList.add(annotationField.getName());
                }
            }
        }
        return annotationFieldNameList;
    }

    /**
     * 获取指定类中字段的数据库对应值
     *
     * @param clazz     类别
     * @param fieldName 字段名称
     * @return 字段值
     */
    public static String getColumnName(Class<?> clazz, String fieldName) {
        try {
            String columnName = "";
            Field field = clazz.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(Column.class)) {
                Column annotation = field.getAnnotation(Column.class);
                columnName = annotation.name();
            }
            return columnName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定类中静态变量的值
     *
     * @param clazz     类别
     * @param fieldName 字段名
     * @return 字段值
     */
    public static Object getStaticFieldValue(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getField(fieldName);
            return field.get(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据类别创建实体对象
     *
     * @param clazz 类别
     * @return 实体对象
     */
    public static Object createInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据类别名称创建实体对象
     *
     * @param className 类别全路径
     * @return 实体对象
     */
    public static Object createInstance(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据类别名称创建实体对象
     *
     * @param className 类别全路径
     * @return 类别
     */
    public static Class<?> getClazz(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取主键取值
     *
     * @param entity 对象实体
     * @return 主键
     */
    public static Object getPKValue(Object entity) {
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entity.getClass());
        EntityColumn column = columnList.iterator().next();
        Object pkValue = null;
        try {
            pkValue = column.getEntityField().getValue(entity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return pkValue;
    }

    /**
     * 设置主键取值（有则不重设，没有则设置uuid）
     *
     * @param entity 对象实体
     * @return 是否存在主键，true为原来已存在，false为原来不存在
     */
    public static boolean setPKValue(Object entity) {
        boolean isExistPkValue = false;
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entity.getClass());
        EntityColumn column = columnList.iterator().next();
        String pkName = column.getEntityField().getName();
        try {
            Object pkValue = column.getEntityField().getValue(entity);
            if (pkValue != null) {
                isExistPkValue = true;
                if (pkValue instanceof String) {
                    if (StringUtils.isBlank((String) pkValue)) {
                        pkValue = IdGen.randomLongStr();
                        ReflectUtils.setFieldValue(entity, pkName, pkValue);
                        isExistPkValue = false;
                    }
                }
            } else {
                pkValue = IdGen.randomLongStr();
                ReflectUtils.setFieldValue(entity, pkName, pkValue);
            }
            return isExistPkValue;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

}
