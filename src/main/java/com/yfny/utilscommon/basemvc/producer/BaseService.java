package com.yfny.utilscommon.basemvc.producer;

import com.yfny.utilscommon.basemvc.common.BaseEntity;
import com.yfny.utilscommon.basemvc.common.BusinessException;

import java.util.List;
import java.util.Map;

/**
 * 微服务通用Service
 * Author jisongZhou
 * Date  2019-04-03
 */
public interface BaseService<T extends BaseEntity> {

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    int insert(T entity) throws BusinessException;

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    int insertSelective(T entity) throws BusinessException;

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    int update(T entity) throws BusinessException;

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    int updateSelective(T entity) throws BusinessException;

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    int delete(T entity) throws BusinessException;

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return 返回0为失败，返回1为成功
     */
    int deleteByPrimaryKey(Object key) throws BusinessException;

    /**
     * 根据主键字段进行批量删除，方法参数必须包含完整的主键属性
     *
     * @param ids 主键，示例"1,2,3,4"
     * @return 返回0为失败，返回1为成功
     */
    int deleteByIds(String ids) throws BusinessException;

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return 返回false为不存在，返回true为存在
     */
    boolean existsWithPrimaryKey(Object key) throws BusinessException;

    /**
     * 根据主键字段进行查询复合嵌套的整体对象（如有），方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param id 主键
     * @return 返回null为未查询到结果，返回对象为查询结果
     */
    T selectComplexById(String id) throws BusinessException;

    /**
     * 根据实体中的属性查询，可选择查询条件
     *
     * @param entity 对象实体
     * @return 返回null为未查询到结果，返回对象为查询结果
     */
    T selectByCondition(T entity) throws BusinessException;

    /**
     * 根据实体中的属性查询总数，可选择查询条件
     *
     * @param entity 对象实体
     * @return 返回查询结果数量
     */
    int selectCount(T entity) throws BusinessException;

    /**
     * 根据实体中的属性值进行查询，可选择查询条件，分页返回
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    List<T> findList(T entity, String pageNum, String pageSize) throws BusinessException;

    /**
     * 定义实体中分组维度，并返回分组
     *
     * @param entity 对象实体
     * @return 返回对象分组为查询结果
     */
    public List<String> findGroupBy(T entity) throws BusinessException;

    /**
     * 根据实体中的属性值进行查询，可选择查询条件，分组返回
     *
     * @param entity 对象实体
     * @return 返回分组对象列表为查询结果
     */
    public Map<String, List<T>> findMapGroupByCondition(T entity) throws BusinessException;

    /**
     * 根据实体中的属性值进行查询，可选择查询条件，树形结构返回
     *
     * @param entity 对象实体
     * @return 返回树形结构对象列表为查询结果
     */
    Map<String, Object> getTreeOf(T entity) throws BusinessException;

}
