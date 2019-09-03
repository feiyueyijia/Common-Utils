//package com.yfny.utilscommon.basemvc.producer;
//
//import com.yfny.utilscommon.basemvc.common.BaseEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
///**
// * 微服务通用Controller
// * Author jisongZhou
// * Date  2019-04-02
// */
//public abstract class BaseController<T extends BaseEntity> {
//
//    @Autowired
//    private BaseFuture<T> baseFuture;
//
//    public BaseFuture<T> getBaseFuture() {
//        return this.baseFuture;
//    }
//
//    /**
//     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
//     *
//     * @param entity 对象实体
//     * @return 返回0为失败，返回1为成功
//     */
//    @PostMapping(value = "/insert")
//    @ResponseBody
//    public CompletableFuture<Integer> insert(@RequestBody T entity) throws Exception {
//        return getBaseFuture().insert(entity);
//    }
//
//    /**
//     * 保存一个实体，null的属性不会保存，会使用数据库默认值
//     *
//     * @param entity 对象实体
//     * @return 返回0为失败，返回1为成功
//     */
//    @PostMapping(value = "/insertSelective")
//    @ResponseBody
//    public CompletableFuture<Integer> insertSelective(@RequestBody T entity) throws Exception {
//        return getBaseFuture().insertSelective(entity);
//    }
//
//    /**
//     * 根据主键更新实体全部字段，null值会被更新
//     *
//     * @param entity 对象实体
//     * @return 返回0为失败，返回1为成功
//     */
//    @PostMapping(value = "/update")
//    @ResponseBody
//    public CompletableFuture<Integer> update(@RequestBody T entity) throws Exception {
//        return getBaseFuture().update(entity);
//    }
//
//    /**
//     * 根据主键更新属性不为null的值
//     *
//     * @param entity 对象实体
//     * @return 返回0为失败，返回1为成功
//     */
//    @PostMapping(value = "/updateSelective")
//    @ResponseBody
//    public CompletableFuture<Integer> updateSelective(@RequestBody T entity) throws Exception {
//        return getBaseFuture().updateSelective(entity);
//    }
//
//    /**
//     * 根据实体属性作为条件进行删除，查询条件使用等号
//     *
//     * @param entity 对象实体
//     * @return 返回0为失败，返回1为成功
//     */
//    @PostMapping(value = "/delete")
//    @ResponseBody
//    public CompletableFuture<Integer> delete(@RequestBody T entity) throws Exception {
//        return getBaseFuture().delete(entity);
//    }
//
//    /**
//     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
//     *
//     * @param key 主键
//     * @return 返回0为失败，返回1为成功
//     */
//    @PostMapping(value = "/deleteByPrimaryKey")
//    @ResponseBody
//    public CompletableFuture<Integer> deleteByPrimaryKey(@RequestParam(value = "key") Object key) throws Exception {
//        return getBaseFuture().deleteByPrimaryKey(key);
//    }
//
//    /**
//     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
//     *
//     * @param key 主键
//     * @return 返回false为不存在，返回true为存在
//     */
//    @GetMapping(value = "/existsWithPrimaryKey")
//    @ResponseBody
//    public CompletableFuture<Boolean> existsWithPrimaryKey(@RequestParam(value = "key") Object key) throws Exception {
//        return getBaseFuture().existsWithPrimaryKey(key);
//    }
//
//    /**
//     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
//     *
//     * @param entity 对象实体
//     * @return 返回null为未查询到结果，返回对象为查询结果，返回多个结果则抛出异常
//     */
//    @PostMapping(value = "/selectOne")
//    @ResponseBody
//    public CompletableFuture<T> selectOne(@RequestBody T entity) throws Exception {
//        return getBaseFuture().selectOne(entity);
//    }
//
//    /**
//     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
//     *
//     * @param key 主键
//     * @return 返回null为未查询到结果，返回对象为查询结果
//     */
//    @GetMapping(value = "/selectByPrimaryKey")
//    @ResponseBody
//    public CompletableFuture<T> selectByPrimaryKey(@RequestParam(value = "key") Object key) throws Exception {
//        return getBaseFuture().selectByPrimaryKey(key);
//    }
//
//    /**
//     * 根据实体中的属性查询总数，查询条件使用等号
//     *
//     * @param entity 对象实体
//     * @return 返回查询结果数量
//     */
//    @PostMapping(value = "/selectCount")
//    @ResponseBody
//    public CompletableFuture<Integer> selectCount(@RequestBody T entity) throws Exception {
//        return getBaseFuture().selectCount(entity);
//    }
//
//    /**
//     * 根据实体中的属性值进行查询，查询条件使用等号，分页返回
//     *
//     * @param entity   对象实体
//     * @param pageNum  页数
//     * @param pageSize 每页数量
//     * @return 返回null为未查询到结果，返回对象列表为查询结果
//     */
//    @PostMapping(value = {"/findList", "/findList/{pageNum}/{pageSize}"})
//    @ResponseBody
//    public CompletableFuture<List<T>> findList(@RequestBody T entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
//        return getBaseFuture().findList(entity, pageNum, pageSize);
//    }
//
//    /**
//     * 查询全部结果分页返回
//     *
//     * @param pageNum  页数
//     * @param pageSize 每页数量
//     * @return 返回对象列表为查询结果
//     */
//    @GetMapping(value = {"/findAllList", "/findAllList/{pageNum}/{pageSize}"})
//    @ResponseBody
//    public CompletableFuture<List<T>> findAllList(@PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
//        return getBaseFuture().findAllList(pageNum, pageSize);
//    }
//
//    /**
//     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集
//     *
//     * @param entity   对象实体
//     * @param pageNum  页数
//     * @param pageSize 每页数量
//     * @return 返回对象列表为查询结果
//     */
//    @PostMapping(value = {"/findSimpleListByAndCondition", "/findSimpleListByAndCondition/{pageNum}/{pageSize}"})
//    @ResponseBody
//    public CompletableFuture<List<T>> findSimpleListByAndCondition(@RequestBody T entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
//        return getBaseFuture().findSimpleListByAndCondition(entity, pageNum, pageSize);
//    }
//
//    /**
//     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集
//     *
//     * @param entity   对象实体
//     * @param pageNum  页数
//     * @param pageSize 每页数量
//     * @return 返回对象列表为查询结果
//     */
//    @PostMapping(value = {"/findListByAndCondition", "/findListByAndCondition/{pageNum}/{pageSize}"})
//    @ResponseBody
//    public CompletableFuture<List<T>> findListByAndCondition(@RequestBody T entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
//        return getBaseFuture().findListByAndCondition(entity, pageNum, pageSize);
//    }
//
//    /**
//     * 根据实体中的属性值进行查询，查询条件使用LIKE，亦或查询取并集
//     *
//     * @param entity   对象实体
//     * @param pageNum  页数
//     * @param pageSize 每页数量
//     * @return 返回对象列表为查询结果
//     */
//    @PostMapping(value = {"/findSimpleListByORCondition", "/findSimpleListByORCondition/{pageNum}/{pageSize}"})
//    @ResponseBody
//    public CompletableFuture<List<T>> findSimpleListByORCondition(@RequestBody T entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
//        return getBaseFuture().findSimpleListByORCondition(entity, pageNum, pageSize);
//    }
//
//    /**
//     * 根据实体中的属性值进行查询，查询条件使用LIKE，亦或查询取并集
//     *
//     * @param entity   对象实体
//     * @param pageNum  页数
//     * @param pageSize 每页数量
//     * @return 返回对象列表为查询结果
//     */
//    @PostMapping(value = {"/findListByORCondition", "/findListByORCondition/{pageNum}/{pageSize}"})
//    @ResponseBody
//    public CompletableFuture<List<T>> findListByORCondition(@RequestBody T entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
//        return getBaseFuture().findListByORCondition(entity, pageNum, pageSize);
//    }
//
//}
