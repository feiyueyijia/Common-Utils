package com.yfny.utilscommon.basemvc.producer;

import com.yfny.utilscommon.annotation.relation.ForeignKey;
import com.yfny.utilscommon.annotation.relation.OneToMany;
import com.yfny.utilscommon.annotation.relation.OneToOne;
import com.yfny.utilscommon.basemvc.common.BaseEntity;
import com.yfny.utilscommon.util.ReflectUtils;
import com.yfny.utilscommon.util.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 微服务基类ServiceImpl调用之前设置
 * Created by jisongZhou on 2019/5/8.
 **/
public class BeforeBaseServiceImpl {

    @Before(value = "execution(public * com.yfny.utilscommon.basemvc.producer.BaseServiceImpl.insert(..))")
    public void doBeforeInsert(JoinPoint joinPoint) {
        doBefore(joinPoint);
    }

    @Before(value = "execution(public * com.yfny.utilscommon.basemvc.producer.BaseServiceImpl.insertSelective(..))")
    public void doBeforeInsertSelective(JoinPoint joinPoint) {
        doBefore(joinPoint);
    }

    @Before(value = "execution(public * com.yfny.utilscommon.basemvc.producer.BaseServiceImpl.update(..))")
    public void doBeforeUpdate(JoinPoint joinPoint) {
        doBefore(joinPoint);
    }

    @Before(value = "execution(public * com.yfny.utilscommon.basemvc.producer.BaseServiceImpl.updateSelective(..))")
    public void doBeforeUpdateSelective(JoinPoint joinPoint) {
        doBefore(joinPoint);
    }

    @Before(value = "execution(public * com.yfny.utilscommon.basemvc.producer.BaseServiceImpl.delete(..))")
    public void doBeforeDelete(JoinPoint joinPoint) {
        doBefore(joinPoint);
    }

    //执行方法前设置
    private void doBefore(JoinPoint joinPoint) {
        //获取参数，对象需为实体对象，规范传参
        Object[] args = joinPoint.getArgs();
        Object param = args[0];
        if (param instanceof BaseEntity) {
            int action = ((BaseEntity) param).getAction();
            if (action == BaseEntity.INSERT) {
                //设置主键值以防没有
                ReflectUtils.setPKValue(param);
            }
            //获取主体对象容器构件（由于主构件只用一次，所以不特别进行实例化）
            Object composite = getComposite(param);
            //进行容器清理，避免数据错误
            ((AbstractComponent) composite).clear();
            //设置要操作的主对象
            ((AbstractComponent) composite).setParam(param);
            //设置对应关系结构
            setComposite(param, composite);
        }
    }

    //设置关系结构下的容器构件
    private void setComposite(Object param, Object composite) {
        int action = ((BaseEntity) param).getAction();
        //获取主体对象主键值
        Object pkValue = ReflectUtils.getPKValue(param);
        //获取主体对象中有对应关系的字段
        List<String> filedNames = ReflectUtils.getAnnotationFieldName(param, OneToMany.class, OneToOne.class);
        for (String filedName : filedNames) {
            //获取子对象值
            Object fieldValue = ReflectUtils.getFieldValue(param, filedName);
            if (fieldValue != null) {
                if (fieldValue instanceof List) {//一对多情况下验证字段为列表类型
                    if (((List) fieldValue).size() > 0) {
                        //创建子对象容器构件参数
                        String subCompositeName = getCompositeName(((List) fieldValue).get(0));
                        Class subCompositeClass = getCompositeClass(subCompositeName);
                        String lowerSubCompositeName = StringUtils.toLowerCaseFirstOne(subCompositeName);
                        //设置列表中每一个子对象
                        for (Object object : (List) fieldValue) {
                            //创建设置子容器构件
                            Object subComposite = setSubComposite(object, pkValue, subCompositeClass, lowerSubCompositeName);
                            //递归设置子容器构件
                            setComposite(object, subComposite);
                            //将子对象容器构件加入抽象构件
                            ((AbstractComponent) composite).add((AbstractComponent) subComposite);
                        }
                    }
                } else if (fieldValue instanceof BaseEntity) {//一对一情况下验证字段为对象类型,多对一写入操作不考虑
                    //创建设置子容器构件
                    Object subComposite = setSubComposite(fieldValue, pkValue);
                    //递归设置子容器构件
                    setComposite(fieldValue, subComposite);
                    //将子对象容器构件加入抽象构件
                    ((AbstractComponent) composite).add((AbstractComponent) subComposite);
                }
            } else {
//                if (action == BaseEntity.DELETE) {
//                    //此情况下只获取到主体对象关键参数（一般为主键），无法递归取到所有层级的值
//                    Field field = ReflectUtils.getDeclaredField(param, filedName);
//                    String typeName = field.getGenericType().getTypeName();
//                    if ("List".equals(field.getType().getSimpleName())) {
//                        typeName = typeName.substring(typeName.indexOf("<") + 1, typeName.lastIndexOf(">"));
//                    }
//                    Object subEntity = ReflectUtils.createInstance(typeName);
//                    //创建设置子容器构件
//                    Object subComposite = setSubComposite(subEntity, pkValue);
//                    //将子对象容器构件加入抽象构件
//                    ((AbstractComponent) composite).add((AbstractComponent) subComposite);
//                }
            }
        }
    }

    //设置关系结构下的子容器构件
    private Object setSubComposite(Object subParam, Object pkValue) {
        //创建子对象容器构件参数
        String subCompositeName = getCompositeName(subParam);
        Class subCompositeClass = getCompositeClass(subCompositeName);
        String lowerSubCompositeName = StringUtils.toLowerCaseFirstOne(subCompositeName);
        //设置子对象
        Object subComposite = setSubComposite(subParam, pkValue, subCompositeClass, lowerSubCompositeName);
        return subComposite;
    }

    //设置关系结构下的子容器构件
    private Object setSubComposite(Object subParam, Object pkValue, Class subCompositeClass, String lowerSubCompositeName) {
        int action = ((BaseEntity) subParam).getAction();
        if (action == BaseEntity.INSERT) {
            //设置主键值以防没有
            ReflectUtils.setPKValue(subParam);
        }
        //获取子对象中标识的外键字段(一般为一个，暂不考虑吧多外键情况，个人认为多外键是糟糕的设计)
        List<String> foreignKeys = ReflectUtils.getAnnotationFieldName(subParam, ForeignKey.class);
        for (String foreignKey : foreignKeys) {
            //设置子对象外键值为主对象主键值
            ReflectUtils.setFieldValue(subParam, foreignKey, pkValue);
        }
        //获取子对象容器构件注入内容
        Object staticValue = ReflectUtils.getStaticFieldValue(subCompositeClass, lowerSubCompositeName);
        //实例化子对象容器构件（因为静态变量只能赋值一次，所以要实例化多个构件才能满足需要，否则值会被覆盖）
        Object subComposite = ReflectUtils.createInstance(subCompositeClass);
        //进行容器清理，避免数据错误
        ((AbstractComponent) subComposite).clear();
        //设置子对象构件注入内容
        ((AbstractComponent) subComposite).setBaseMapper(((AbstractComponent) staticValue).getBaseMapper());
        //设置要操作的子对象
        ((AbstractComponent) subComposite).setParam(subParam);
        return subComposite;
    }

    //获取容器构件
    private Object getComposite(Object param) {
        String compositeName = getCompositeName(param);
        Class compositeClass = getCompositeClass(compositeName);
        String lowerCompositeName = StringUtils.toLowerCaseFirstOne(compositeName);
        Object composite = ReflectUtils.getStaticFieldValue(compositeClass, lowerCompositeName);
        return composite;
    }

    //获取容器构件类别
    private Class getCompositeClass(String compositeName) {
        /** 获取当前类的路径 com.yfny.servicedemandform*/
        String parentPackage = getPackageName();
        String compositePackage = parentPackage + "composite" + "." + compositeName;
        Class compositeClass = ReflectUtils.getClazz(compositePackage);
        return compositeClass;
    }

    //获取容器构件名称
    private String getCompositeName(Object param) {
        String compositeName = param.getClass().getSimpleName();
        compositeName = compositeName.substring(0, compositeName.lastIndexOf("Entity"));
        compositeName = compositeName + "Composite";
        return compositeName;
    }

    public String getPackageName() {
        return "com.yfny.utilscommon.";
    }

}
