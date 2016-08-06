package com.youzan.test.faker.processor;

import com.alibaba.fastjson.JSON;
import com.youzan.test.faker.api.dto.ExpectationDto;
import com.youzan.test.faker.api.service.AbstractBaseHttpRequest;
import com.youzan.test.faker.api.service.AbstractHttpCallbackRequest;
import com.youzan.test.faker.api.service.AbstractHttpRedirectRequest;
import com.youzan.test.faker.service.ExpectationCenterService;
import com.youzan.test.faker.service.FootprintService;
import com.youzan.test.faker.task.CallbackTask;
import com.youzan.test.faker.util.HttpRequestUtil;
import com.youzan.test.faker.util.ReflectiveUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by libaixian on 16/8/1.
 */
@Slf4j
@Component
public class HardCodeProcessor {
    @Resource
    private ExpectationCenterService expectationCenterService;

    @Resource
    private CallbackTask callbackTask;

    @Resource
    private FootprintService footprintService;

    private static final String HttpServletRequestSetter = "setHttpServletRequest";
    private static final String HttpServletResponseSetter = "setHttpServletResponse";
    private static final String Request2MapGetter = "getRequestMap";
    private static final String ExpectationKey = "getExpectationKey";
    private static final String GetResponse = "getResponse";
    private static final String CallRealMethod = "callRealMethod";
    private static final String GetCallbackUrl = "getCallbackUrl";
    private static final String GetCallbackData = "getCallbackData";
    private static final String GetRedirectURL = "getRedirectURL";

    public void process(String className, HttpServletRequest request, HttpServletResponse response) {
        try {
            Class classObj = ReflectiveUtil.classForName(className);
            Constructor constructor = ReflectiveUtil.getDefaultContructor(classObj);
            Object instance = ReflectiveUtil.createInstance(constructor);

            if (!AbstractHttpRedirectRequest.class.isAssignableFrom(classObj)
                    && !AbstractHttpCallbackRequest.class.isAssignableFrom(classObj)
                    && !AbstractBaseHttpRequest.class.isAssignableFrom(classObj)) {
                throw new RuntimeException("Not supported class, the class is expected to derived from AbstractBaseHttpRequest, AbstractHttpCallbackRequest or AbstractHttpRedirectRequest");
            }

            populateFields(classObj, instance, request, response);
            //TODO: load Expectation,
            // 1. 包括调用接口设置的期望值, 使用过一次后即丢弃
            // 2. 还有在管理平台上设置的期望管理平台上的期望值, 需要进行根据请求信息进行过滤
            Method requestMapGetterMethod = classObj.getMethod(Request2MapGetter);
            Map<String, Object> requestMap = (Map<String, Object>) requestMapGetterMethod.invoke(instance);
            Method expectationKeyGetterMethod = classObj.getMethod(ExpectationKey);
            String expectationKey = (String) expectationKeyGetterMethod.invoke(instance);

            ExpectationDto expectationDto = expectationCenterService.process(expectationKey, requestMap);

            if (AbstractHttpRedirectRequest.class.isAssignableFrom(classObj)) {
                baseRequestProcessor(classObj, instance, request, response, expectationDto);
                return;
            }

            if (AbstractHttpCallbackRequest.class.isAssignableFrom(classObj)) {
                callbackRequestProcessor(classObj, instance, request, response, expectationDto);
                return;
            }

            if (AbstractHttpRedirectRequest.class.isAssignableFrom(classObj)) {
                redirectRequestProcessor(classObj, instance, response, expectationDto);
                return;
            }

        } catch (Exception e) {

        }
    }

    private void populateFields(Class type, Object instance, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Method setRequestMethod = type.getMethod(HttpServletRequestSetter, HttpServletRequest.class);
        setRequestMethod.invoke(instance, request);

        Method setResponseMethod = type.getMethod(HttpServletResponseSetter, HttpServletResponse.class);
        setResponseMethod.invoke(instance, response);
    }

    private void baseRequestProcessor(Class<?> type, Object instance, HttpServletRequest request,
                                      HttpServletResponse response, ExpectationDto expectationDto) throws Exception {
        if (expectationDto.isHandOver2RealMethod()) {
            Method realMethod = type.getMethod(CallRealMethod);
            realMethod.invoke(instance);
            return;
        }

        response(type, instance, request, response, expectationDto);
    }

    private void callbackRequestProcessor(Class<?> type, Object instance, HttpServletRequest request,
                                          HttpServletResponse response, ExpectationDto expectationDto) throws Exception {
        if (expectationDto.isHandOver2RealMethod()) {
            Method realMethod = type.getMethod(CallRealMethod);
            realMethod.invoke(instance);
            return;
        }

        callback(type, instance, expectationDto);
        response(type, instance, request, response, expectationDto);
    }


    private void redirectRequestProcessor(Class<?> type, Object instance, HttpServletResponse response,
                                         ExpectationDto expectationDto) throws Exception {
        if (expectationDto.isHandOver2RealMethod()) {
            Method realMethod = type.getMethod(CallRealMethod);
            realMethod.invoke(instance);
            return;
        }

        callback(type, instance, expectationDto);

        Method redirectURLGetter = type.getMethod(GetRedirectURL);
        String redirectURL = (String) redirectURLGetter.invoke(instance);

        String retMessage = getResponseData(type, instance, expectationDto);
        if (!redirectURL.endsWith("/")) {
            redirectURL.substring(0, redirectURL.length() -1);
        }

        response.sendRedirect(redirectURL + "?" + retMessage);
    }


    private void response(Class<?> type, Object instance, HttpServletRequest request,
                          HttpServletResponse response, ExpectationDto expectationDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        String retMessage = getResponseData(type, instance, expectationDto);
        if (expectationDto.isSaveFootprint()) {
            Map<String, Object> requestMap = HttpRequestUtil.convertToMap(request);
            String requestStr = JSON.toJSONString(requestMap);
            footprintService.saveFootprint(expectationDto.getRequestUrl(), requestStr, retMessage);
        }

        PrintWriter printer = response.getWriter();
        printer.write(retMessage);
    }

    private void callback(Class<?> type, Object instance, ExpectationDto expectationDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String callBackURL;
        if (!StringUtils.isEmpty(expectationDto.getBgCallbackURL())) {
            callBackURL = expectationDto.getBgCallbackURL();
        } else {
            Method callbackURLGetter = type.getMethod(GetCallbackUrl);
            callBackURL = (String) callbackURLGetter.invoke(instance);
        }

        String callbackData;
        if (!StringUtils.isEmpty(expectationDto.getBgCallbackData())) {
            callbackData = expectationDto.getBgCallbackData();
        } else {
            Method callbackDataGetter = type.getMethod(GetCallbackData);
            callbackData = (String) callbackDataGetter.invoke(instance);
        }

        callbackTask.addCallbackTask(callBackURL, callbackData, expectationDto.getBgCallbackDelayTime());
    }

    private String getResponseData(Class<?> type, Object instance, ExpectationDto expectationDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String retMessage;
        if (expectationDto != null && !StringUtils.isEmpty(expectationDto.getResponseData())) {
            retMessage = expectationDto.getResponseData();
        } else {
            Method getResponseMethod = type.getMethod(GetResponse, ExpectationDto.class);
            retMessage = (String) getResponseMethod.invoke(instance, expectationDto);
        }

        return retMessage;
    }

    public static void main(String[] args) {
        String a = "this is E${order_no}, today is $DATE(yyyy,d,-1)";
        int startIndex = a.indexOf("$");
        if (a.charAt(startIndex + 1) == '{') {
            int endIndex = a.indexOf("}");
            String varName = a.substring(startIndex + 2, endIndex);
        }

    }

}
