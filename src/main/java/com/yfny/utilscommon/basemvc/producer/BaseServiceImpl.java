package com.yfny.utilscommon.basemvc.producer;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yfny.utilscommon.annotation.redis.CacheEvict;
import com.yfny.utilscommon.annotation.redis.CacheKey;
import com.yfny.utilscommon.annotation.redis.Cacheable;
import com.yfny.utilscommon.annotation.redis.CommonCacheTime;
import com.yfny.utilscommon.basemvc.common.BaseEntity;
import com.yfny.utilscommon.basemvc.common.BaseTree;
import com.yfny.utilscommon.basemvc.common.BusinessException;
import com.yfny.utilscommon.basemvc.common.ExtraProp;
import com.yfny.utilscommon.strategy.PageResultStrategy;
import com.yfny.utilscommon.util.MultipleTreeUtils;
import com.yfny.utilscommon.util.ReflectUtils;
import com.yfny.utilscommon.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微服务通用ServiceImpl
 * Author jisongZhou
 * Date  2019-04-03
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    @Autowired
    private BaseComponent<T> baseComponent;

    public BaseComponent<T> getBaseComponent() {
        return this.baseComponent;
    }

    @Autowired
    private BaseMapper<T> baseMapper;

    public BaseMapper<T> getBaseMapper() {
        return this.baseMapper;
    }

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @LcnTransaction //分布式事务注解
    @Transactional  //本地事务注解
    @CacheEvict
    public int insert(T entity) throws BusinessException {
        entity.setAction(BaseEntity.INSERT);
        save(getBaseComponent());
        return getBaseMapper().insert(entity);
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @LcnTransaction //分布式事务注解
    @Transactional  //本地事务注解
    @CacheEvict
    public int insertSelective(T entity) throws BusinessException {
        entity.setAction(BaseEntity.INSERT);
        saveSelective(getBaseComponent());
        return getBaseMapper().insertSelective(entity);
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @LcnTransaction //分布式事务注解
    @Transactional  //本地事务注解
    @CacheEvict(keyMode = CacheEvict.KeyMode.OBJECT_UPDATE_BASIC)
    public int update(@CacheKey(field = "id") T entity) throws BusinessException {
        entity.setAction(BaseEntity.UPDATE);
        save(getBaseComponent());
        return getBaseMapper().updateByPrimaryKey(entity);
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @LcnTransaction //分布式事务注解
    @Transactional  //本地事务注解
    @CacheEvict(keyMode = CacheEvict.KeyMode.OBJECT_UPDATE_BASIC)
    public int updateSelective(@CacheKey(field = "id") T entity) throws BusinessException {
        entity.setAction(BaseEntity.UPDATE);
        saveSelective(getBaseComponent());
        return getBaseMapper().updateByPrimaryKeySelective(entity);
    }

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     */
    private void save(Object object) {
        BaseComponent composite = ((BaseComponent) object);
        if (composite.list != null && composite.list.size() > 0) {
            for (Object subComposite : composite.list) {
                int action = ((BaseEntity) ((AbstractComponent) subComposite).getParam()).getAction();
                switch (action) {
                    case BaseEntity.INSERT:
                        ((AbstractComponent) subComposite).insert();
                        break;
                    case BaseEntity.UPDATE:
                        ((AbstractComponent) subComposite).update();
                        break;
                    case BaseEntity.DELETE:
                        ((AbstractComponent) subComposite).delete();
                        break;
                    default:
                        break;
                }
                saveSelective(subComposite);
            }
        }
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     */
    private void saveSelective(Object object) {
        BaseComponent composite = ((BaseComponent) object);
        if (composite.list != null && composite.list.size() > 0) {
            for (Object subComposite : composite.list) {
                int action = ((BaseEntity) ((AbstractComponent) subComposite).getParam()).getAction();
                switch (action) {
                    case BaseEntity.INSERT:
                        ((AbstractComponent) subComposite).insertSelective();
                        break;
                    case BaseEntity.UPDATE:
                        ((AbstractComponent) subComposite).updateSelective();
                        break;
                    case BaseEntity.DELETE:
                        ((AbstractComponent) subComposite).delete();
                        break;
                    default:
                        break;
                }
                saveSelective(subComposite);
            }
        }
    }

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @LcnTransaction //分布式事务注解
    @Transactional  //本地事务注解
    @CacheEvict(keyMode = CacheEvict.KeyMode.OBJECT_DELETE)
    public int delete(@CacheKey(field = "id") T entity) throws BusinessException {
        entity.setAction(BaseEntity.DELETE);
        if (getBaseComponent().list != null && getBaseComponent().list.size() > 0) {
            for (Object composite : getBaseComponent().list) {
                ((AbstractComponent) composite).delete();
            }
        }
        return getBaseMapper().delete(entity);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return 返回0为失败，返回1为成功
     */
    @LcnTransaction //分布式事务注解
    @Transactional  //本地事务注解
    @CacheEvict(keyMode = CacheEvict.KeyMode.BASIC_DELETE)
    public int deleteByPrimaryKey(Object key) throws BusinessException {
        return getBaseMapper().deleteByPrimaryKey(key);
    }

    /**
     * 根据主键字段进行批量删除，方法参数必须包含完整的主键属性
     *
     * @param ids 主键，示例"1,2,3,4"
     * @return 返回0为失败，返回1为成功
     */
    @LcnTransaction //分布式事务注解
    @Transactional  //本地事务注解
    public int deleteByIds(String ids) throws BusinessException {
        return getBaseMapper().deleteByIds(ids);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return 返回false为不存在，返回true为存在
     */
    public boolean existsWithPrimaryKey(Object key) throws BusinessException {
        return getBaseMapper().existsWithPrimaryKey(key);
    }

    /**
     * 根据主键字段进行查询复合嵌套的整体对象（如有），方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param id 主键
     * @return 返回null为未查询到结果，返回对象为查询结果
     */
    @Cacheable(expire = CommonCacheTime.ONE_WEEK)
    public T selectComplexById(@CacheKey String id) throws BusinessException {
        try {
            return getBaseMapper().selectComplexById(id);
        } catch (Exception e) {
            throw new BusinessException("sys.custom.error", "未检测到该数据的复合实现，请更换接口！");
        }
    }

    /**
     * 根据实体中的属性查询，可选择查询条件
     *
     * @param entity 对象实体
     * @return 返回null为未查询到结果，返回对象为查询结果
     */
    public T selectByCondition(T entity) throws BusinessException {
        try {
            return getBaseMapper().selectByCondition(entity);
        } catch (Exception e) {
            throw new BusinessException("sys.custom.error", "查询结果不唯一，请确认查询条件的唯一性！");
        }
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回查询结果数量
     */
    public int selectCount(T entity) throws BusinessException {
        return getBaseMapper().selectCount(entity);
    }

    /**
     * 根据实体中的属性值进行查询，可选择查询条件，分页返回
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    public List<T> findList(T entity, String pageNum, String pageSize) throws BusinessException {
        try {
            PageResultStrategy pageResultStrategy = null;
            switch (entity.getComplexRate()) {
                case BaseEntity.NORMAL:
                    pageResultStrategy = () -> getBaseMapper().findListByCondition(entity);
                    break;
                case BaseEntity.COMPLEX:
                    pageResultStrategy = () -> getBaseMapper().findComplexListByCondition(entity);
                    break;
                default:
                    break;
            }
            return findPageResultList(pageResultStrategy, pageNum, pageSize);
        } catch (Exception e) {
            throw new BusinessException("sys.custom.error", "未检测到该数据的复合实现，请更换接口！");
        }
    }

    /**
     * 定义实体中分组维度，并返回分组
     *
     * @param entity 对象实体
     * @return 返回对象分组为查询结果
     */
    public List<String> findGroupBy(T entity) throws BusinessException {
        return getBaseMapper().findGroupBy(entity);
    }

    /**
     * 根据实体中的属性值进行查询，可选择查询条件，分组返回
     *
     * @param entity 对象实体
     * @return 返回分组对象列表为查询结果
     */
    public Map<String, List<T>> findMapGroupByCondition(T entity) throws BusinessException {
        Map<String, List<T>> resultMap = new HashMap<>();
        List<T> list = getBaseMapper().findListByCondition(entity);
        String groupBy = entity.getGroupBy();
        if (StringUtils.isNotBlank(groupBy)) {
            for (T object : list) {
                Object field = ReflectUtils.getFieldValue(object, groupBy);
                String key = field != null ? field.toString() : "others";
                if (resultMap.containsKey(key)) {
                    resultMap.get(key).add(object);
                } else {
                    List<T> groupList = new ArrayList<>();
                    groupList.add(object);
                    resultMap.put(key, groupList);
                }
            }
        } else {
            throw new BusinessException("sys.custom.error", "未检测到分组依据，请添加分组依据或者使用其他接口！");
        }
        return resultMap;
    }

    /**
     * 根据实体中的属性值进行查询，可选择查询条件，树形结构返回
     *
     * @param entity 对象实体
     * @return 返回树形结构对象列表为查询结果
     */
    public Map<String, Object> getTreeOf(T entity) throws BusinessException {
        Map<String, Object> resultMap = new HashMap<>();
        List<T> list = findList(entity, null, null);
        List<Map<String, Object>> mapList = new ArrayList<>();
        try {
            BaseTree treeConfig = entity.getTreeConfig();
            String idFiled = treeConfig.getId();
            String nameField = treeConfig.getName();
            String parentIdField = treeConfig.getParentId();
            String rootId = treeConfig.getRootId();
            String levelFiled = treeConfig.getLevel();
            String leaf = treeConfig.getLeaf();
            String orderBy = treeConfig.getOrderBy();
            String haveList = treeConfig.getHaveList();
            List<String> extraPropsFiled = treeConfig.getExtraProps();
            for (T object : list) {
                Map<String, Object> map = new HashMap<>();
                Object id = ReflectUtils.getFieldValue(object, idFiled);
                Object name = ReflectUtils.getFieldValue(object, nameField);
                Object parentId = ReflectUtils.getFieldValue(object, parentIdField);
                Object level = null;
                if (StringUtils.isNotBlank(levelFiled)) {
                    level = ReflectUtils.getFieldValue(object, levelFiled);
                }
                if (id.equals(rootId)) {
                    parentId = null;
                }
                map.put("id", id);
                map.put("name", name);
                map.put("parentId", parentId);
                map.put("level", level);
                List<ExtraProp> extraProps = null;
                if (extraPropsFiled != null && extraPropsFiled.size() > 0) {
                    extraProps = new ArrayList<>();
                    for (String propName : extraPropsFiled) {
                        ExtraProp prop = new ExtraProp();
                        Object propValue = ReflectUtils.getFieldValue(object, propName);
                        prop.setPropName(propName);
                        prop.setPropValue(propValue);
                        extraProps.add(prop);
                    }
                }
                map.put("extraProps", extraProps);
                mapList.add(map);
            }
            boolean isLeaf = true;
            String order = BaseTree.NODE_LABEL;
            if ("false".equals(leaf)) {
                isLeaf = false;
            }
            if (BaseTree.NODE_LEVEL.equals(orderBy)) {
                order = orderBy;
            }
            String treeStr = MultipleTreeUtils.getTreeList(mapList, isLeaf, order);
            List<T> treeList = new ArrayList<>();
            if ("true".equals(haveList)) {
                treeList = list;
            }
            resultMap.put("tree", treeStr);
            resultMap.put("list", treeList);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("sys.custom.error", "树形结构依据错误，请检查数据是否符合树形结构格式！");
        }
        return resultMap;
    }

    /**
     * 获取分页相关数据，分页参数不合法时获取全部列表数据
     *
     * @param pageResultStrategy 对象列表结果
     * @param pageNum            页数
     * @param pageSize           每页数量
     * @return 返回对象列表为查询结果
     */
    public List<T> findPageResultList(PageResultStrategy pageResultStrategy, String pageNum, String pageSize) {
        List<T> resultList = new ArrayList<>();
        if (StringUtils.isNumeric(pageNum) && StringUtils.isNumeric(pageSize)) {
            int pageNumInteger = Integer.parseInt(pageNum);
            int pageSizeInteger = Integer.parseInt(pageSize);
            if (pageNumInteger > 0 && pageSizeInteger > 0) {
                Page<T> resultPage = PageHelper.startPage(pageNumInteger, pageSizeInteger);
                resultList = pageResultStrategy.pageResult();
                if (resultList != null) {
                    for (T result : resultList) {
                        result.setPageNum(resultPage.getPageNum());
                        result.setPageSize(resultPage.getPageSize());
                        result.setPageCount(resultPage.getPages());
                        result.setTotal(resultPage.getTotal());
                    }
                }
            }
        } else {
            resultList = pageResultStrategy.pageResult();
        }
        return resultList != null ? resultList : new ArrayList<>();
    }

}
