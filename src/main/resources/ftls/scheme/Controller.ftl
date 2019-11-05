package ${BasePackageName}.${ControllerPackageName};

import ${BasePackageName}.${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}.${ServicePackageName}.${ClassName}Service;
import ${BasePackageName}.${ValidPackageName}.${ClassName}Valid;
import com.yfny.utilscommon.util.InvokeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ${Descriptions}Controller
 * Author ${Author}
 * Date  ${Date}
 */
@RestController
@RequestMapping(value = "/${ClassName?uncap_first}")
public class ${ClassName}Controller {

    @Autowired
    private ${ClassName}Service ${ClassName?uncap_first}Service;

    @Autowired
    private ${ClassName}Valid ${ClassName?uncap_first}Valid;

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/insertSelective")
    @ResponseBody
    public InvokeResult insertSelective(@RequestBody ${ClassName}Entity entity) throws Exception {
        ${ClassName?uncap_first}Valid.validInsert(entity);
        int result = ${ClassName?uncap_first}Service.insertSelective(entity);
        return InvokeResult.writeResult(result, "${ClassName?uncap_first}.create.success", "${ClassName?uncap_first}.create.failed");
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/updateSelective")
    @ResponseBody
    public InvokeResult updateSelective(@RequestBody ${ClassName}Entity entity) throws Exception {
        ${ClassName?uncap_first}Valid.validUpdate(entity);
        int result = ${ClassName?uncap_first}Service.updateSelective(entity);
        return InvokeResult.writeResult(result, "${ClassName?uncap_first}.update.success", "${ClassName?uncap_first}.update.failed");
    }

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回0为失败，返回1为成功
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public InvokeResult delete(@RequestBody ${ClassName}Entity entity) throws Exception {
        ${ClassName?uncap_first}Valid.validDelete(entity);
        int result = ${ClassName?uncap_first}Service.delete(entity);
        return InvokeResult.writeResult(result, "${ClassName?uncap_first}.delete.success", "${ClassName?uncap_first}.delete.failed");
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
        int result = ${ClassName?uncap_first}Service.deleteByPrimaryKey(key);
        return InvokeResult.writeResult(result, "${ClassName?uncap_first}.delete.success", "${ClassName?uncap_first}.delete.failed");
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回null为未查询到结果，返回对象为查询结果，返回多个结果则抛出异常
     */
    @PostMapping(value = "/selectOne")
    @ResponseBody
    public InvokeResult selectOne(@RequestBody ${ClassName}Entity entity) throws Exception {
        ${ClassName?uncap_first}Valid.validSelect(entity);
        ${ClassName}Entity result = ${ClassName?uncap_first}Service.selectOne(entity);
        return InvokeResult.readResult(result, "${ClassName?uncap_first}.loadOne.success", "${ClassName?uncap_first}.loadOne.failed");
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
        ${ClassName}Entity result = ${ClassName?uncap_first}Service.selectByPrimaryKey(key);
        return InvokeResult.readResult(result, "${ClassName?uncap_first}.loadOne.success", "${ClassName?uncap_first}.loadOne.failed");
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
        ${ClassName}Entity result = ${ClassName?uncap_first}Service.selectComplexById(id);
        return InvokeResult.readResult(result, "${ClassName?uncap_first}.loadOne.success", "${ClassName?uncap_first}.loadOne.failed");
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param entity 对象实体
     * @return 返回查询结果数量
     */
    @PostMapping(value = "/selectCount")
    @ResponseBody
    public InvokeResult selectCount(@RequestBody ${ClassName}Entity entity) throws Exception {
        int result = ${ClassName?uncap_first}Service.selectCount(entity);
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
    public InvokeResult findList(@RequestBody ${ClassName}Entity entity, @PathVariable(value = "pageNum", required = false) String pageNum, @PathVariable(value = "pageSize", required = false) String pageSize) throws Exception {
        List<${ClassName}Entity> result = ${ClassName?uncap_first}Service.findList(entity, pageNum, pageSize);
        return InvokeResult.readResult(result, "${ClassName?uncap_first}.loadList.success", "${ClassName?uncap_first}.loadList.failed");
    }

    /**
     * 定义实体中分组维度，并返回分组
     *
     * @param entity 对象实体
     * @return 返回对象分组为查询结果
     */
    @PostMapping(value = "/findGroupBy")
    @ResponseBody
    public InvokeResult findGroupBy(@RequestBody ${ClassName}Entity entity) throws Exception {
    List<String> result = ${ClassName?uncap_first}Service.findGroupBy(entity);
        return InvokeResult.readResult(result, "${ClassName?uncap_first}.loadGroup.success", "${ClassName?uncap_first}.loadGroup.failed");
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集，分组返回
     *
     * @param entity 对象实体
     * @return 返回分组对象列表为查询结果
     */
    @PostMapping(value = "/findMapGroupByCondition")
    @ResponseBody
    public InvokeResult findMapGroupByCondition(@RequestBody ${ClassName}Entity entity) throws Exception {
        Map<String, List<${ClassName}Entity>> result = ${ClassName?uncap_first}Service.findMapGroupByCondition(entity);
        return InvokeResult.readResult(result, "${ClassName?uncap_first}.loadList.success", "${ClassName?uncap_first}.loadList.failed");
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用LIKE，并列查询取交集，树形结构返回
     *
     * @param entity 对象实体
     * @return 返回树形结构对象列表为查询结果
     */
    @PostMapping(value = "/getTreeOf")
    @ResponseBody
    public InvokeResult getTreeOf(@RequestBody ${ClassName}Entity entity) throws Exception {
        Map<String, Object> result = ${ClassName?uncap_first}Service.getTreeOf(entity);
        return InvokeResult.readResult(result, "${ClassName?uncap_first}.loadTree.success", "${ClassName?uncap_first}.loadTree.failed");
    }

}
