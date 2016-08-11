package com.youzan.test.faker.api.service;

import com.youzan.test.faker.api.cache.Cache;
import com.youzan.test.faker.api.dto.ExpectationDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by libaixian on 16/7/30.
 */
public abstract class AbstractBaseHttpRequest {
    /**
     * 全局变量, 初始化时注入
     */
    protected HttpServletRequest httpServletRequest;

    /**
     * 全局变量, 初始化时注入
     */
    protected HttpServletResponse httpServletResponse;

    /**
     * 全局变量, 初始化时注入HttpServletResponse对象时, 调用convertRequest2Map方法生成, 必要时重写convertRequest2Map来生成此数据
     */
    protected Map<String, Object> request2Map;

    /**
     * 如果需要在缓存中保存数据, 并在其他接口中使用, 把数据扔在cache中即可
     */
    protected Cache cache;

    public void setHttpServletRequest(HttpServletRequest request) {
        if (request == null) {
            throw new RuntimeException("request is not allowed to be null");
        }

        httpServletRequest = request;
        request2Map = convertRequest2Map();
    }

    public void setHttpServletResponse(HttpServletResponse response) {
        if (response == null) {
            throw new RuntimeException("response is not allowed to be null");
        }

        httpServletResponse = response;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Map<String, Object> getRequestMap() {
        return request2Map;
    }

    /**
     * 对于请求参数不是一般的key, value值的, 而是比如说是xml, 需要重写这个方法, 以获取正确的request中的参数
     * @return
     */
    protected Map<String, Object> convertRequest2Map() {
        Map<String, Object> request2Map = new HashMap();
        Enumeration paramNames = this.httpServletRequest.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String paramValue = this.httpServletRequest.getParameter(paramName);
            request2Map.put(paramName, paramValue);
        }

        return request2Map;
    }

    /**
     * 针对本次的请求, 保存请求记录时默认的是保存请求格式为 URL + request + response
     * 如果你想方便以后的查询,可以给本次请求一个标签,来标识本次的请求信息, 请求记录将会保存为 URL + requestKey + request + response
     * @return
     */
    public String getRequestKey() {
        return null;
    }

    /**
     * 指定获取期望值的key值, 将根据这个key从缓存中获取对应的期望值, 默认为请求的URL
     * @return
     */
    public String getExpectationKey() {
        return httpServletRequest.getServletPath();
    }

    /**
     * 根据requst 和 expectation来生成响应的报文
     * @param expectation
     * @return
     */
    public abstract String getResponse(Map<String, Object> expectation);

    /**
     * 向真实的第三方发起请求, 并返回第三方返回的报文
     * @return
     */
    public abstract String callRealMethod();
}
