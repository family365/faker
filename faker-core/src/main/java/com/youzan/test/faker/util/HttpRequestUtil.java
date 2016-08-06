package com.youzan.test.faker.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by libaixian on 16/8/5.
 */
public class HttpRequestUtil {
    public static Map<String, Object> convertToMap(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            resultMap.put(paramName, paramValue);
        }

        return resultMap;
    }
}
