package com.yfny.utilscommon.annotation.redis;

import com.yfny.utilscommon.basemvc.common.BaseEntity;
import com.yfny.utilscommon.util.ReflectUtils;
import com.yfny.utilscommon.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.data.redis.connection.DataType;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Redis缓存自定义注解实现
 * Created by jisongZhou on 2017/12/21.
 **/
@Aspect
@Component
public class CacheableAop implements Ordered {

    @Autowired
    private RedisUtils redisUtil;

    private List<String> keySuffixList = new ArrayList<>();

    @Around("@annotation(cache)")
    public Object cached(final ProceedingJoinPoint pjp, Cacheable cache) throws Throwable {
        Object value = null;
        List<Object> valueList = null;
        int i = 0;
        try {
            String key = getCacheKey(pjp, cache);
            DataType dataType = redisUtil.getType(key);
            if (dataType == DataType.STRING) {
                value = redisUtil.get(key); // 从缓存获取数据
            } else if (dataType == DataType.LIST) {
                valueList = redisUtil.lGet(key, 0, -1);
            }
            // 如果有数据,则直接返回
            if (value != null) {
                return value;
            } else if (valueList != null) {
                return valueList;
            }

            value = pjp.proceed(); // 跳过缓存,到后端查询数据
            if (value instanceof List) {
                valueList = (List<Object>) value;
                if (cache.expire() <= 0) { // 如果没有设置过期时间,则无限期缓存
                    redisUtil.lSet(key, valueList);
                } else { // 否则设置缓存时间
                    redisUtil.lSet(key, valueList, cache.expire());
                }
            } else {
                if (cache.expire() <= 0) { // 如果没有设置过期时间,则无限期缓存
                    redisUtil.set(key, value);
                } else { // 否则设置缓存时间
                    redisUtil.set(key, value, cache.expire());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            i = 1;
        } finally {
            if (i > 0) {
                value = pjp.proceed(); // 跳过缓存,到后端查询数据
            }
        }
        return value;
    }

    @Around("@annotation(evict)")
    public Object evictCache(ProceedingJoinPoint pjp, CacheEvict evict) throws Throwable {
        Object result = null;
        try {
            keySuffixList = new ArrayList<>();
            String keySuffix = getCacheKey(pjp, evict);
            String[] objectKeyPrefix = evict.objectKeyPrefix();
            String[] listKeyPrefix = evict.listKeyPrefix();
            Object[] args = pjp.getArgs();
            Object value = null;
            if (args.length == 1) {
                value = args[0];
            }
            result = pjp.proceed();
            if (result != null && result instanceof BaseEntity) {
                value = result;
            }
            CacheEvict.KeyMode keyMode = evict.keyMode();
            resetCacheBasic(objectKeyPrefix, keySuffix, value, keyMode);
            updateCacheList(listKeyPrefix, keySuffix, value, keyMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取缓存的key值
     *
     * @param pjp   切点
     * @param cache 缓存
     * @return 键值
     */
    private String getCacheKey(ProceedingJoinPoint pjp, Cacheable cache) {

        StringBuilder buf = new StringBuilder();
        // buf.append(pjp.getSignature().getDeclaringTypeName()).append(".").append(pjp.getSignature().getName());
        // System.out.println(pjp.getSignature().getDeclaringTypeName());
        // System.out.println(pjp.getSignature().getName());
        // if(cache.key().length()>0) {
        // buf.append(".").append(cache.key());
        // System.out.println(cache.key());
        // }

        // if(cache.type().length()>0) {
        // buf.append(pjp.getSignature().getName());
        // buf.append("_").append(cache.type());
        // System.out.println(cache.type());
        // }
        //buf.append(pjp.getTarget().getClass().getName());
        buf.append(pjp.getTarget().getClass().getSimpleName());
        buf.append("_").append(pjp.getSignature().getName());
        Object[] args = pjp.getArgs();
        Cacheable.KeyMode keyMode = cache.keyMode();
        if (keyMode == Cacheable.KeyMode.DEFAULT) {
            Annotation[][] pas = ((MethodSignature) pjp.getSignature())
                    .getMethod().getParameterAnnotations();
            for (int i = 0; i < pas.length; i++) {
                for (Annotation an : pas[i]) {
                    if (an instanceof CacheKey) {
                        buf.append("_").append(args[i].toString());
                        //System.out.println(args[i].toString());
                        break;
                    }
                }
            }
        } else if (keyMode == Cacheable.KeyMode.BASIC) {
            for (Object arg : args) {
                if (arg instanceof String) {
                    buf.append(".").append(arg);
                } else if (arg instanceof Integer || arg instanceof Long
                        || arg instanceof Short) {
                    buf.append(".").append(arg.toString());
                } else if (arg instanceof Boolean) {
                    buf.append(".").append(arg.toString());
                }
            }
        } else if (keyMode == Cacheable.KeyMode.ALL) {
            for (Object arg : args) {
                buf.append(".").append(arg.toString());
            }
        }

        return buf.toString();
    }

    /**
     * 获取缓存的key后缀
     *
     * @param pjp   切点
     * @param evict 缓存
     * @return 键值后缀
     */
    private String getCacheKey(ProceedingJoinPoint pjp, CacheEvict evict) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        String keySuffix = "";
        Object[] args = pjp.getArgs();
        CacheEvict.KeyMode keyMode = evict.keyMode();
        if (keyMode == CacheEvict.KeyMode.BASIC_INSERT
                || keyMode == CacheEvict.KeyMode.BASIC_UPDATE
                || keyMode == CacheEvict.KeyMode.BASIC_DELETE) {
            Annotation[][] pas = method.getParameterAnnotations();
            for (int i = 0; i < pas.length; i++) {
                for (Annotation an : pas[i]) {
                    if (an instanceof CacheKey) {
                        keySuffix = "_" + args[i].toString();
                        break;
                    }
                }
            }
        } else if (keyMode == CacheEvict.KeyMode.OBJECT_INSERT_BASIC || keyMode == CacheEvict.KeyMode.OBJECT_INSERT_OBJECT
                || keyMode == CacheEvict.KeyMode.OBJECT_UPDATE_BASIC || keyMode == CacheEvict.KeyMode.OBJECT_UPDATE_OBJECT
                || keyMode == CacheEvict.KeyMode.OBJECT_DELETE) {
            Annotation[][] pas = method.getParameterAnnotations();
            for (int i = 0; i < pas.length; i++) {
                for (Annotation an : pas[i]) {
                    if (an instanceof CacheKey) {
                        String field = ((CacheKey) an).field();
                        String[] fields = ((CacheKey) an).fields();
                        if (StringUtils.isNotBlank(field) && fields.length == 0) {
                            Object object = ReflectUtils.getFieldValue(args[i], field);
                            if (object != null) {
                                keySuffix = "_" + object.toString();
                            }
                            break;
                        } else if (StringUtils.isBlank(field) && fields.length > 0) {
                            for (String f : fields) {
                                Object object = ReflectUtils.getFieldValue(args[i], f);
                                String ks = "";
                                if (object != null) {
                                    ks = "_" + object.toString();
                                    keySuffixList.add(ks);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        return keySuffix;
    }

    /**
     * 重置基础缓存
     *
     * @param objectKeyPrefix 基础类型缓存
     * @param keySuffix       键值后缀
     * @param value           更新后的值
     * @param keyMode         类型
     */
    private void resetCacheBasic(String[] objectKeyPrefix, String keySuffix, Object value, CacheEvict.KeyMode keyMode) {
        // 清除对应缓存
        if (StringUtils.isNotBlank(keySuffix)) {
            redisUtil.deleteBySuffix(keySuffix);
        }
        if (keySuffixList != null && keySuffixList.size() > 0) {
            for (String ks : keySuffixList) {
                redisUtil.deleteBySuffix(ks);
            }
        }
        for (String objectKey : objectKeyPrefix) {
            String key = objectKey + keySuffix;
            if (value != null) {
                if (keyMode == CacheEvict.KeyMode.BASIC_INSERT || keyMode == CacheEvict.KeyMode.BASIC_UPDATE) {
                    redisUtil.set(key, value, CommonCacheTime.HALF_HOUR);
                } else if (keyMode == CacheEvict.KeyMode.OBJECT_INSERT_OBJECT || keyMode == CacheEvict.KeyMode.OBJECT_UPDATE_OBJECT) {
                    if (!(value instanceof Integer || value instanceof Long || value instanceof Boolean)) {
                        // 重新设置缓存
                        redisUtil.set(key, value, CommonCacheTime.HALF_HOUR);
                    }
                }
            }
        }
    }

    /**
     * 更新列表缓存
     *
     * @param listKeyPrefix 键值
     * @param keySuffix     键值后缀
     * @param value         更新的值
     * @param keyMode       类型
     */
    private void updateCacheList(String[] listKeyPrefix, String keySuffix, Object value, CacheEvict.KeyMode keyMode) {
        for (String listKey : listKeyPrefix) {
            List<Object> objectList1 = redisUtil.lGet(listKey, 0, -1);
            subUpdateCacheList(objectList1, listKey, keySuffix, value, keyMode);
            if (StringUtils.isNotBlank(keySuffix)) {
                List<Object> objectList2 = redisUtil.lGet(listKey + keySuffix, 0, -1);
                subUpdateCacheList(objectList2, listKey + keySuffix, keySuffix, value, keyMode);
            }
        }
    }

    /**
     * 更新列表缓存
     *
     * @param objectList 缓存列表
     * @param listKey    缓存键值
     * @param keySuffix  键值后缀
     * @param value      更新的值
     * @param keyMode    类型
     */
    private void subUpdateCacheList(List<Object> objectList, String listKey, String keySuffix, Object value, CacheEvict.KeyMode keyMode) {
        if (objectList != null && objectList.size() > 0) {
            if (keyMode == CacheEvict.KeyMode.BASIC_UPDATE
                    || keyMode == CacheEvict.KeyMode.OBJECT_INSERT_BASIC
                    || keyMode == CacheEvict.KeyMode.OBJECT_UPDATE_BASIC
                    || keyMode == CacheEvict.KeyMode.DEFAULT) {
                redisUtil.del(listKey);
            } else if (keyMode == CacheEvict.KeyMode.BASIC_DELETE
                    || keyMode == CacheEvict.KeyMode.OBJECT_DELETE) {
                int listIndex = getListIndex(keySuffix, objectList);
                if (listIndex == -1) {
                    redisUtil.del(listKey);
                } else {
                    objectList.remove(listIndex);
                    // 重新设置缓存
                    redisUtil.lSet(listKey, objectList, CommonCacheTime.HALF_HOUR);
                }
            } else if (keyMode == CacheEvict.KeyMode.BASIC_INSERT
                    || keyMode == CacheEvict.KeyMode.OBJECT_INSERT_OBJECT) {
                if (value != null) {
                    if (!(value instanceof Integer || value instanceof Long || value instanceof Boolean)) {
                        objectList.add(value);
                    }
                    // 重新设置缓存
                    redisUtil.lSet(listKey, objectList, CommonCacheTime.HALF_HOUR);
                }
            } else if (keyMode == CacheEvict.KeyMode.OBJECT_UPDATE_OBJECT) {
                int listIndex = getListIndex(keySuffix, objectList);
                if (listIndex == -1) {
                    redisUtil.del(listKey);
                } else {
                    if (value != null) {
                        objectList.remove(listIndex);
                        if (!(value instanceof Integer || value instanceof Long || value instanceof Boolean)) {
                            objectList.add(listIndex, value);
                        }
                        // 重新设置缓存
                        redisUtil.lSet(listKey, objectList, CommonCacheTime.HALF_HOUR);
                    } else {
                        redisUtil.del(listKey);
                    }
                }
            }
        }
    }

    /**
     * 获取列表中指定对象的索引
     *
     * @param keySuffix  键值后缀
     * @param objectList 缓存列表
     * @return 对象索引
     */
    private int getListIndex(String keySuffix, List<Object> objectList) {
        int listIndex = -1;
        if (StringUtils.isNotBlank(keySuffix)) {
            keySuffix = keySuffix.substring(1);
            Object object0 = objectList.get(0);
            boolean flag = object0 instanceof String || object0 instanceof Integer
                    || object0 instanceof Long || object0 instanceof Short
                    || object0 instanceof Boolean;
            for (int i = 0; i < objectList.size(); i++) {
                String key = "";
                if (objectList.get(i) != null) {
                    if (flag) {
                        key = objectList.get(i).toString();
                    } else {
                        Object object = ReflectUtils.getFieldValue(objectList.get(i), keySuffix);
                        if (object != null) {
                            key = object.toString();
                        }
                    }
                }
                if (keySuffix.equals(key)) {
                    listIndex = i;
                    break;
                }
            }
        }
        return listIndex;
    }

    /**
     * 重写定义注解执行顺序
     *
     * @return 返回顺序
     */
    @Override
    public int getOrder() {
        return 1002;
    }
}
