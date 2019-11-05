package com.yfny.utilscommon.basemvc.common;

import com.yfny.utilscommon.basemvc.producer.BaseService;
import com.yfny.utilscommon.basemvc.producer.BaseValid;
import com.yfny.utilscommon.util.InvokeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 对象实体通用Controller
 * Author jisongZhou
 * Date  2019-04-03
 */
public class BaseController {

    @Autowired
    private BaseService baseService;

    @Autowired
    private BaseValid baseValid;

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/insertSelective")
    @ResponseBody
    public InvokeResult insertSelective(@RequestBody BaseEntity entity) throws Exception {
        baseValid.validInsert(entity);
        int result = baseService.insertSelective(entity);
        return InvokeResult.writeResult(result, "business.create.success", "business.create.failed");
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/updateSelective")
    @ResponseBody
    public InvokeResult updateSelective(@RequestBody BaseEntity entity) throws Exception {
        baseValid.validUpdate(entity);
        int result = baseService.updateSelective(entity);
        return InvokeResult.writeResult(result, "business.update.success", "business.update.failed");
    }

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public InvokeResult delete(@RequestBody BaseEntity entity) throws Exception {
        baseValid.validDelete(entity);
        int result = baseService.delete(entity);
        return InvokeResult.writeResult(result, "business.delete.success", "business.delete.failed");
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
        int result = baseService.deleteByPrimaryKey(key);
        return InvokeResult.writeResult(result, "business.delete.success", "business.delete.failed");
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回null为未查询到结果，返回对象为查询结果，返回多个结果则抛出异常
     */
    @PostMapping(value = "/selectOne")
    @ResponseBody
    public InvokeResult selectOne(@RequestBody BaseEntity entity) throws Exception {
        baseValid.validSelect(entity);
        BaseEntity result = baseService.selectOne(entity);
        return InvokeResult.readResult(result, "business.loadOne.success", "business.loadOne.failed");
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
        BaseEntity result = baseService.selectByPrimaryKey(key);
        return InvokeResult.readResult(result, "business.loadOne.success", "business.loadOne.failed");
    }

    /**
     * 根据主键字段进行查询复合嵌套的整体对象（如有），方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param id 主键
     * @return 返回null为未查询到结果，返回对象为查询结果
     */
    @PostMapping(value = "/selectComplexById")
    @ResponseBody
    public InvokeResult selectComplexById(@RequestParam(value = "id") String id) throws Exception {
        BaseEntity result = baseService.selectComplexById(id);
        return InvokeResult.readResult(result, "business.loadOne.success", "business.loadOne.failed");
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回查询结果数量
     */
    @PostMapping(value = "/selectCount")
    @ResponseBody
    public InvokeResult selectCount(@RequestBody BaseEntity entity) throws Exception {
        int result = baseService.selectCount(entity);
        if (result >= 0) {
            return InvokeResult.success(result);
        } else if (result == -1) {
            return InvokeResult.failure("sys.request.exception", "网络请求超时或服务器崩溃");
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
    public InvokeResult findList(@RequestBody BaseEntity entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
        List<BaseEntity> result = baseService.findList(entity, pageNum, pageSize);
        return InvokeResult.readResult(result, "business.loadList.success", "business.loadList.failed");
    }

    /**
     * 定义实体中分组维度，并返回分组
     *
     * @param entity 对象实体
     * @return 返回对象分组为查询结果
     */
    @PostMapping(value = "/findGroupBy")
    @ResponseBody
    public InvokeResult findGroupBy(@RequestBody BaseEntity entity) throws Exception {
        List<String> result = baseService.findGroupBy(entity);
        return InvokeResult.readResult(result, "business.loadGroup.success", "business.loadGroup.failed");
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集，分组返回
     *
     * @param entity 对象实体
     * @return 返回分组对象列表为查询结果
     */
    @PostMapping(value = "/findMapGroupByCondition")
    @ResponseBody
    public InvokeResult findMapGroupByCondition(@RequestBody BaseEntity entity) throws Exception {
        Map<String, List<BaseEntity>> result = baseService.findMapGroupByCondition(entity);
        return InvokeResult.readResult(result, "business.loadList.success", "business.loadList.failed");
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集，树形结构返回
     *
     * @param entity 对象实体
     * @return 返回树形结构对象列表为查询结果
     */
    @PostMapping(value = "/getTreeOf")
    @ResponseBody
    public InvokeResult getTreeOf(@RequestBody BaseEntity entity) throws Exception {
        Map<String, Object> result = baseService.getTreeOf(entity);
        return InvokeResult.readResult(result, "business.loadTree.success", "business.loadTree.failed");
    }

}
