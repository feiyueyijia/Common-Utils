package com.yfny.utilscommon.util;

import com.yfny.utilscommon.generator.entity.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 代码信息获取工具
 * Created by jisongZhou on 2019/3/8.
 **/
@Component
public class CodeInfoUtils {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    private static CodeInfoUtils codeInfoUtils;

    @PostConstruct
    public void init() {
        codeInfoUtils = this;
    }

    /**
     * 获取过滤的url地址
     *
     * @return
     */
    public static Map<String, String> getFilterUrls() {
        Map<String, String> filterUrlsMap = new HashMap<>();
        filterUrlsMap.put("{ /hystrix}", "{ /hystrix}");
        filterUrlsMap.put("{ /hystrix/{path}}", "{ /hystrix/{path}}");
        filterUrlsMap.put("{ /error}", "{ /error}");
        filterUrlsMap.put("{ /error, produces [text/html]}", "{ /error, produces [text/html]}");
        return filterUrlsMap;
    }

    /**
     * 获取请求信息列表
     *
     * @return
     */
    public static List<RequestInfo> getRequestInfos() {
        List<RequestInfo> requestInfoList = new ArrayList<>();
        Map map = codeInfoUtils.handlerMapping.getHandlerMethods();
        Iterator<?> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = entry.getKey().toString();
            if (!getFilterUrls().containsKey(key)) {
                HandlerMethod value = (HandlerMethod) entry.getValue();
                int index0 = key.indexOf("{") + 1;
                int index1 = key.indexOf("/");
                int index2 = key.lastIndexOf("}");
                String url = key.substring(index1, index2);
                String methodName = value.getBean().toString() + "_" + value.getMethod().getName();
                String requestMethod = StringUtils.isNotBlank(key.substring(index0, index1)) ? key.substring(index0, index1).replace(" ","") : "GET";
                MethodParameter[] requestParameters = value.getMethodParameters();
                RequestInfo requestInfo = new RequestInfo(url, methodName, requestMethod, requestParameters);
                requestInfoList.add(requestInfo);
            }
        }
        return requestInfoList;
    }

}
