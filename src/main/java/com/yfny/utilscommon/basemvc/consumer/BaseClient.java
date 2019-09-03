package com.yfny.utilscommon.basemvc.consumer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务消费者通用Client
 * Author jisongZhou
 * Date  2019-04-02
 */
public interface BaseClient<T> {

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/insert")
    int insert(@RequestBody T entity);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/insertSelective")
    int insertSelective(@RequestBody T entity);

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/update")
    int update(@RequestBody T entity);

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/updateSelective")
    int updateSelective(@RequestBody T entity);

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/delete")
    int delete(@RequestBody T entity);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/deleteByPrimaryKey")
    int deleteByPrimaryKey(@RequestParam(value = "key") Object key);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return 返回false为不存在，返回true为存在
     */
    @GetMapping(value = "/existsWithPrimaryKey")
    boolean existsWithPrimaryKey(@RequestParam(value = "key") Object key);

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回null为未查询到结果，返回对象为查询结果，返回多个结果则抛出异常
     */
    @PostMapping(value = "/selectOne")
    T selectOne(@RequestBody T entity);

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key 主键
     * @return 返回null为未查询到结果，返回对象为查询结果
     */
    @GetMapping(value = "/selectByPrimaryKey")
    T selectByPrimaryKey(@RequestParam(value = "key") Object key);

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回查询结果数量
     */
    @PostMapping(value = "/selectCount")
    int selectCount(@RequestBody T entity);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回null为未查询到结果，返回对象列表为查询结果
     */
    @PostMapping(value = "/findList")
    List<T> findList(@RequestBody T entity);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号，分页返回
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回null为未查询到结果，返回对象列表为查询结果
     */
    @PostMapping(value = "/findList/{pageNum}/{pageSize}")
    List<T> findList(@RequestBody T entity, @PathVariable("pageNum") String pageNum, @PathVariable("pageSize") String pageSize);

    /**
     * 查询全部结果
     *
     * @return 返回对象列表为查询结果
     */
    @GetMapping(value = "/findAllList")
    List<T> findAllList();

    /**
     * 查询全部结果分页返回
     *
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    @GetMapping(value = "/findAllList/{pageNum}/{pageSize}")
    List<T> findAllList(@PathVariable("pageNum") String pageNum, @PathVariable("pageSize") String pageSize);

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集
     *
     * @param entity 对象实体
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = "/findSimpleListByAndCondition")
    List<T> findSimpleListByAndCondition(@RequestBody T entity);

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = "/findSimpleListByAndCondition/{pageNum}/{pageSize}")
    List<T> findSimpleListByAndCondition(@RequestBody T entity, @PathVariable("pageNum") String pageNum, @PathVariable("pageSize") String pageSize);

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集
     *
     * @param entity 对象实体
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = "/findListByAndCondition")
    List<T> findListByAndCondition(@RequestBody T entity);

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = "/findListByAndCondition/{pageNum}/{pageSize}")
    List<T> findListByAndCondition(@RequestBody T entity, @PathVariable("pageNum") String pageNum, @PathVariable("pageSize") String pageSize);

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，亦或查询取并集
     *
     * @param entity 对象实体
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = "/findSimpleListByORCondition")
    List<T> findSimpleListByORCondition(@RequestBody T entity);

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，亦或查询取并集
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = "/findSimpleListByORCondition/{pageNum}/{pageSize}")
    List<T> findSimpleListByORCondition(@RequestBody T entity, @PathVariable("pageNum") String pageNum, @PathVariable("pageSize") String pageSize);

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，亦或查询取并集
     *
     * @param entity 对象实体
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = "/findListByORCondition")
    List<T> findListByORCondition(@RequestBody T entity);

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，亦或查询取并集
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = "/findListByORCondition/{pageNum}/{pageSize}")
    List<T> findListByORCondition(@RequestBody T entity, @PathVariable("pageNum") String pageNum, @PathVariable("pageSize") String pageSize);

}
