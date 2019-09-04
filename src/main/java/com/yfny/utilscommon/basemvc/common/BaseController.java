package com.yfny.utilscommon.basemvc.common;

import com.yfny.utilscommon.basemvc.producer.BaseService;
import com.yfny.utilscommon.util.InvokeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 消费者通用Controller
 * Author jisongZhou
 * Date  2019-04-02
 */
public abstract class BaseController<T extends BaseEntity> {

    @Autowired
    private BaseService<T> baseService;

    public BaseService<T> getBaseService() {
        return this.baseService;
    }

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity        对象实体
     * @param bindingResult 验证结果
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/insert")
    @ResponseBody
    public InvokeResult insert(@Valid @RequestBody T entity, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) { // 如果验证信息错误
            String message = bindingResult.getFieldError().getDefaultMessage(); // 返回一个错误信息
            return InvokeResult.failure("10002", message);
        }
        int result = getBaseService().insert(entity);
        return InvokeResult.writeResult(result, "20001", "10003", "20002");
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity        对象实体
     * @param bindingResult 验证结果
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/insertSelective")
    @ResponseBody
    public InvokeResult insertSelective(@Valid @RequestBody T entity, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) { // 如果验证信息错误
            String message = bindingResult.getFieldError().getDefaultMessage(); // 返回一个错误信息
            return InvokeResult.failure("10002", message);
        }
        int result = getBaseService().insertSelective(entity);
        return InvokeResult.writeResult(result, "20001", "10003", "20002");
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity        对象实体
     * @param bindingResult 验证结果
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/update")
    @ResponseBody
    public InvokeResult update(@Valid @RequestBody T entity, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) { // 如果验证信息错误
            String message = bindingResult.getFieldError().getDefaultMessage(); // 返回一个错误信息
            return InvokeResult.failure("10002", message);
        }
        int result = getBaseService().update(entity);
        return InvokeResult.writeResult(result, "20001", "10003", "20002");
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/updateSelective")
    @ResponseBody
    public InvokeResult updateSelective(@Valid @RequestBody T entity, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) { // 如果验证信息错误
            String message = bindingResult.getFieldError().getDefaultMessage(); // 返回一个错误信息
            return InvokeResult.failure("10002", message);
        }
        int result = getBaseService().updateSelective(entity);
        return InvokeResult.writeResult(result, "20001", "10003", "20002");
    }

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public InvokeResult delete(@RequestBody T entity) throws Exception {
        int result = getBaseService().delete(entity);
        return InvokeResult.writeResult(result, "20003", "10003", "20004");
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/deleteByPrimaryKey")
    @ResponseBody
    public InvokeResult deleteByPrimaryKey(@RequestParam(value = "key") Object key) throws Exception {
        int result = getBaseService().deleteByPrimaryKey(key);
        return InvokeResult.writeResult(result, "20003", "10003", "20004");
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回null为未查询到结果，返回对象为查询结果，返回多个结果则抛出异常
     */
    @PostMapping(value = "/selectOne")
    @ResponseBody
    public InvokeResult selectOne(@RequestBody T entity) throws Exception {
        T result = getBaseService().selectOne(entity);
        return InvokeResult.readResult(result, "10001", "10003");
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key 主键
     * @return 返回null为未查询到结果，返回对象为查询结果
     */
    @GetMapping(value = "/selectByPrimaryKey")
    @ResponseBody
    public InvokeResult selectByPrimaryKey(@RequestParam(value = "key") Object key) throws Exception {
        T result = getBaseService().selectByPrimaryKey(key);
        return InvokeResult.readResult(result, "10001", "10003");
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回查询结果数量
     */
    @PostMapping(value = "/selectCount")
    @ResponseBody
    public InvokeResult selectCount(@RequestBody T entity) throws Exception {
        int result = getBaseService().selectCount(entity);
        if (result >= 0) {
            return InvokeResult.success(result);
        } else if (result == -1) {
            return InvokeResult.failure("10003", "网络请求超时或服务器崩溃");
        }
        return InvokeResult.failure();
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号，分页返回
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = {"/findList", "/findList/{pageNum}/{pageSize}"})
    @ResponseBody
    public InvokeResult findList(@RequestBody T entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
        List<T> result = getBaseService().findList(entity, pageNum, pageSize);
        return InvokeResult.readResult(result, "10001", "10003");
    }

    /**
     * 查询全部结果分页返回
     *
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    @GetMapping(value = {"/findAllList", "/findAllList/{pageNum}/{pageSize}"})
    @ResponseBody
    public InvokeResult findAllList(@PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
        List<T> result = getBaseService().findAllList(pageNum, pageSize);
        return InvokeResult.readResult(result, "10001", "10003");
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = {"/findSimpleListByAndCondition", "/findSimpleListByAndCondition/{pageNum}/{pageSize}"})
    @ResponseBody
    public InvokeResult findSimpleListByAndCondition(@RequestBody T entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
        List<T> result = getBaseService().findSimpleListByAndCondition(entity, pageNum, pageSize);
        return InvokeResult.readResult(result, "10001", "10003");
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = {"/findListByAndCondition", "/findListByAndCondition/{pageNum}/{pageSize}"})
    @ResponseBody
    public InvokeResult findListByAndCondition(@RequestBody T entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
        List<T> result = getBaseService().findListByAndCondition(entity, pageNum, pageSize);
        return InvokeResult.readResult(result, "10001", "10003");
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，亦或查询取并集
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = {"/findSimpleListByORCondition", "/findSimpleListByORCondition/{pageNum}/{pageSize}"})
    @ResponseBody
    public InvokeResult findSimpleListByORCondition(@RequestBody T entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
        List<T> result = getBaseService().findSimpleListByORCondition(entity, pageNum, pageSize);
        return InvokeResult.readResult(result, "10001", "10003");
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，亦或查询取并集
     *
     * @param entity   对象实体
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @return 返回对象列表为查询结果
     */
    @PostMapping(value = {"/findListByORCondition", "/findListByORCondition/{pageNum}/{pageSize}"})
    @ResponseBody
    public InvokeResult findListByORCondition(@RequestBody T entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
        List<T> result = getBaseService().findListByORCondition(entity, pageNum, pageSize);
        return InvokeResult.readResult(result, "10001", "10003");
    }

}
