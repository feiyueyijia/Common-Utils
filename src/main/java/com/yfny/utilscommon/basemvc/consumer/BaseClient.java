package com.yfny.utilscommon.basemvc.consumer;

import com.yfny.utilscommon.util.InvokeResult;
import org.springframework.web.bind.annotation.*;

/**
 * 服务消费者通用Client
 * Author jisongZhou
 * Date  2019-04-02
 */
public interface BaseClient<T> {

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/insertSelective")
    InvokeResult insertSelective(@RequestBody T entity);
    
    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/updateSelective")
    InvokeResult updateSelective(@RequestBody T entity);

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/delete")
    InvokeResult delete(@RequestBody T entity);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/deleteByPrimaryKey")
    InvokeResult deleteByPrimaryKey(@RequestParam(value = "key") Object key);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return 返回false为不存在，返回true为存在
     */
    @GetMapping(value = "/existsWithPrimaryKey")
    InvokeResult existsWithPrimaryKey(@RequestParam(value = "key") Object key);

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回null为未查询到结果，返回对象为查询结果，返回多个结果则抛出异常
     */
    @PostMapping(value = "/selectOne")
    InvokeResult selectOne(@RequestBody T entity);

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key 主键
     * @return 返回null为未查询到结果，返回对象为查询结果
     */
    @GetMapping(value = "/selectByPrimaryKey")
    InvokeResult selectByPrimaryKey(@RequestParam(value = "key") Object key);


    /**
     * 根据主键字段进行查询复合嵌套的整体对象（如有），方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param id 主键
     * @return 返回null为未查询到结果，返回对象为查询结果
     */
    @PostMapping(value = "/selectComplexById")
    InvokeResult selectComplexById(@RequestParam(value = "id") String id);

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回查询结果数量
     */
    @PostMapping(value = "/selectCount")
    InvokeResult selectCount(@RequestBody T entity);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回null为未查询到结果，返回对象列表为查询结果
     */
    @PostMapping(value = "/findList")
    InvokeResult findList(@RequestBody T entity);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号，分页返回
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回null为未查询到结果，返回对象列表为查询结果
     */
    @PostMapping(value = "/findList/{pageNum}/{pageSize}")
    InvokeResult findList(@RequestBody T entity, @PathVariable("pageNum") String pageNum, @PathVariable("pageSize") String pageSize);

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集，分组返回
     *
     * @param entity 对象实体
     * @return 返回分组对象列表为查询结果
     */
    @PostMapping(value = "/findMapGroupByCondition")
    InvokeResult findMapGroupByCondition(@RequestBody T entity);

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集，树形结构返回
     *
     * @param entity 对象实体
     * @return 返回树形结构对象列表为查询结果
     */
    @PostMapping(value = "/getTreeOf")
    InvokeResult getTreeOf(@RequestBody T entity);

}
