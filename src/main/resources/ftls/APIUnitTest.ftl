package ${BasePackageName}${APIUnitTestPackageName};

import ${BasePackageName}${APIBaseTestPackageName}.APIBaseTest;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口页面模拟单元测试
 * Author ${Author}
 * Date  ${Date}
 */
public class APIUnitTest extends APIBaseTest {

    <#list RequestInfoList as RequestInfo>
    @Test
    public void ${RequestInfo.methodName}() throws Exception{

        /*--------------------开始业务组装--------------------*/

        <#assign url = RequestInfo.url/>
        <#assign param = "paramsMap"/>
        <#assign requestParamsSize = RequestInfo.requestParams?size/>
        <#if requestParamsSize gt 0>
        Map<String, String> paramsMap = new HashMap<>();

            <#list RequestInfo.requestParams as requestParam>
                <#if requestParam.parameter.type.simpleName == "String">
        ${requestParam.parameter.type.simpleName} ${requestParam.parameter.name} = "单元测试${requestParam.parameter.name}";
                    <#if url?contains("{${requestParam.parameter.name}}")>
                        <#assign url = url?replace('{${requestParam.parameter.name}}', '" + ${requestParam.parameter.name} + "')/>
                    </#if>
        paramsMap.put("${requestParam.parameter.name}", ${requestParam.parameter.name});
                <#elseif requestParam.parameter.type.simpleName == "long" || requestParam.parameter.type.simpleName == "Long"
                || requestParam.parameter.type.simpleName == "int" || requestParam.parameter.type.simpleName == "Integer"
                || requestParam.parameter.type.simpleName == "short" || requestParam.parameter.type.simpleName == "Short"
                || requestParam.parameter.type.simpleName == "double" || requestParam.parameter.type.simpleName == "Double">
        ${requestParam.parameter.type.simpleName?lower_case} ${requestParam.parameter.name} = 1;
                    <#if url?contains("{${requestParam.parameter.name}}")>
                        <#assign url = url?replace('{${requestParam.parameter.name}}', '" + ${requestParam.parameter.name} + "')/>
                    </#if>
        paramsMap.put("${requestParam.parameter.name}", String.valueOf(${requestParam.parameter.name}));
                <#elseif requestParam.parameter.type.simpleName == "boolean">
        ${requestParam.parameter.type.simpleName} ${requestParam.parameter.name} = true;
        paramsMap.put("${requestParam.parameter.name}", String.valueOf(${requestParam.parameter.name}));
                <#else>
        ${requestParam.parameter.type.name} ${requestParam.parameter.name} = new ${requestParam.parameter.type.name}();
                    <#assign param = "content"/>
                    <#list requestParam.parameter.type.declaredFields as field>
                        <#if field.name != "serialVersionUID" && field.name != "pageNum"
                        && field.name != "pageSize" && field.name != "pageCount"
                        && field.name != "total" && field.name != "orders"
                        && field.name != "ASC" && field.name != "DESC"
                        && field.name != "INSERT" && field.name != "UPDATE"
                        && field.name != "DELETE" && field.name != "SELECT">
        ${requestParam.parameter.name}.set${field.name?cap_first}(null);
                        </#if>
                    </#list>

        //转换成ajax请求的json数据
        String content = JSONObject.toJSONString(${requestParam.parameter.name});
                </#if>

            </#list>
        </#if>
        /*--------------------业务组装结束--------------------*/

        //指定要请求的接口路径
        String url = "${url}";

        <#assign requestMethod = RequestInfo.requestMethod/>
        //模拟页面请求
        <#if requestMethod == "GET">
            <#if requestParamsSize gt 0>
        getRequest(url, ${param});
            <#elseif requestParamsSize == 0>
        getRequest(url);
            </#if>
        <#else>
            <#if requestParamsSize gt 0>
        postRequest(url, ${param});
            <#elseif requestParamsSize == 0>
        postRequest(url);
            </#if>
        </#if>
    }

    </#list>
}
