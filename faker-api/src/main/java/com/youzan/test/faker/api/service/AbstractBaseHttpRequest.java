package com.youzan.test.faker.api.service;

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
    protected HttpServletRequest httpServletRequest;
    protected HttpServletResponse httpServletResponse;
    protected Map<String, Object> request2Map;

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

    public Map<String, Object> getRequestMap() {
        return request2Map;
    }

    //对于请求参数不是一般的key, value值的, 需要重写这个方法, 以获取正确的request参数map
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

    public String getExpectationKey() {
        return httpServletRequest.getRequestURI();
    }

    public abstract String getResponse(ExpectationDto expectation);

    public abstract String callRealMethod();
}
