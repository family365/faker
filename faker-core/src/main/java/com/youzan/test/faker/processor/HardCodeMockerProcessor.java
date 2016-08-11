package com.youzan.test.faker.processor;

import com.alibaba.fastjson.JSON;
import com.youzan.test.faker.api.cache.Cache;
import com.youzan.test.faker.api.dto.ExpectationDto;
import com.youzan.test.faker.api.exception.FakerOperationException;
import com.youzan.test.faker.api.service.AbstractBaseHttpRequest;
import com.youzan.test.faker.api.service.AbstractHttpCallbackRequest;
import com.youzan.test.faker.api.service.AbstractHttpRedirectRequest;
import com.youzan.test.faker.service.ExpectationCenterService;
import com.youzan.test.faker.service.FootprintService;
import com.youzan.test.faker.task.CallbackTask;
import com.youzan.test.faker.util.HttpRequestUtil;
import com.youzan.test.faker.util.ReflectiveUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Component
public class HardCodeMockerProcessor {
    @Resource
    private ExpectationCenterService expectationCenterService;

    @Resource
    private CallbackTask callbackTask;

    @Resource
    private FootprintService footprintService;

    @Resource
    private Cache autoExpiredCache;

    private static final Logger logger = LoggerFactory.getLogger(HardCodeMockerProcessor.class);
    private static final String HttpServletRequestSetter = "setHttpServletRequest";
    private static final String HttpServletResponseSetter = "setHttpServletResponse";
    private static final String CacheSetter = "setCache";
    private static final String Request2MapGetter = "getRequestMap";
    private static final String ExpectationKey = "getExpectationKey";
    private static final String GetResponse = "getResponse";
    private static final String CallRealMethod = "callRealMethod";
    private static final String GetCallbackUrl = "getCallbackUrl";
    private static final String GetCallbackData = "getCallbackData";
    private static final String GetRedirectURL = "getRedirectURL";
    private static final String GetRequestKey = "getRequestKey";

    public void process(String className, HttpServletRequest request, HttpServletResponse response) {
        try {
            Class classObj = ReflectiveUtil.classForName(className);
            if (!AbstractHttpRedirectRequest.class.isAssignableFrom(classObj)
                    && !AbstractHttpCallbackRequest.class.isAssignableFrom(classObj)
                    && !AbstractBaseHttpRequest.class.isAssignableFrom(classObj)) {
                throw new FakerOperationException("Not supported class, the class is expected to derived from AbstractBaseHttpRequest, AbstractHttpCallbackRequest or AbstractHttpRedirectRequest");
            }

            Constructor constructor = ReflectiveUtil.getDefaultContructor(classObj);
            Object instance = ReflectiveUtil.createInstance(constructor);
            injectFields(classObj, instance, request, response);

            Method requestMapGetterMethod = classObj.getMethod(Request2MapGetter);
            Map<String, Object> requestMap = (Map<String, Object>) requestMapGetterMethod.invoke(instance);
            Method expectationKeyGetterMethod = classObj.getMethod(ExpectationKey);
            String expectationKey = (String) expectationKeyGetterMethod.invoke(instance);
            ExpectationDto expectationDto = expectationCenterService.process(expectationKey, requestMap);

            if (expectationDto.isHandOver2RealMethod()) {
                Method realMethod = classObj.getMethod(CallRealMethod);
                realMethod.invoke(instance);
                return;
            }

            if (AbstractHttpRedirectRequest.class.isAssignableFrom(classObj)) {
                redirectRequestProcessor(classObj, instance, request, response, expectationDto);
                return;
            }

            if (AbstractHttpCallbackRequest.class.isAssignableFrom(classObj)) {
                callbackRequestProcessor(classObj, instance, request, response, expectationDto);
                return;
            }

            if (AbstractBaseHttpRequest.class.isAssignableFrom(classObj)) {
                baseRequestProcessor(classObj, instance, request, response, expectationDto);
                return;
            }
        } catch (NoSuchMethodException e) {
            throw new FakerOperationException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new FakerOperationException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new FakerOperationException(e.getMessage());
        } catch (IOException e) {
            throw new FakerOperationException(e.getMessage());
        }
    }

    private void injectFields(Class type, Object instance, HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        Method setRequestMethod = type.getMethod(HttpServletRequestSetter, HttpServletRequest.class);
        setRequestMethod.invoke(instance, request);

        Method setResponseMethod = type.getMethod(HttpServletResponseSetter, HttpServletResponse.class);
        setResponseMethod.invoke(instance, response);

        Method setCacheMethod = type.getMethod(CacheSetter, Cache.class);
        setCacheMethod.invoke(instance, autoExpiredCache);
    }

    private void redirectRequestProcessor(Class<?> type, Object instance, HttpServletRequest request, HttpServletResponse response,
                                         ExpectationDto expectationDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        callback(type, instance, expectationDto);

        String redirectURL = null;
        if (expectationDto != null && !StringUtils.isEmpty(expectationDto.getBgCallbackURL())) {
            redirectURL = expectationDto.getBgCallbackURL();
        } else {
            Method redirectURLGetter = type.getMethod(GetRedirectURL);
            redirectURL = (String) redirectURLGetter.invoke(instance);
            if (StringUtils.isEmpty(redirectURL)) {
                throw new FakerOperationException("redirect URL cannot be null or empty");
            }
        }

        String retMessage = getResponseData(type, instance, expectationDto);
        if (redirectURL.endsWith("/")) {
            redirectURL = redirectURL.substring(0, redirectURL.length() -1);
        }

        String redirectContent = null;
        if (!StringUtils.isEmpty(retMessage)) {
            redirectContent = redirectURL + "?" + retMessage;
        } else {
            redirectContent = redirectURL;
        }

        String contentType = request.getContentType();
        String charset = request.getCharacterEncoding();
        response.setContentType(contentType);
        response.setCharacterEncoding(charset);
        response.sendRedirect(redirectContent);
    }

    private void callbackRequestProcessor(Class<?> type, Object instance, HttpServletRequest request,
                                          HttpServletResponse response, ExpectationDto expectationDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        callback(type, instance, expectationDto);
        response(type, instance, request, response, expectationDto);
    }

    private void baseRequestProcessor(Class<?> type, Object instance, HttpServletRequest request,
                                      HttpServletResponse response, ExpectationDto expectationDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        response(type, instance, request, response, expectationDto);
    }

    private void callback(Class<?> type, Object instance, ExpectationDto expectationDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String callBackURL;
        if (expectationDto != null && !StringUtils.isEmpty(expectationDto.getBgCallbackURL())) {
            callBackURL = expectationDto.getBgCallbackURL();
        } else {
            Method callbackURLGetter = type.getMethod(GetCallbackUrl);
            callBackURL = (String) callbackURLGetter.invoke(instance);
        }

        if (StringUtils.isEmpty(callBackURL)) {
            logger.info("callback URL is empty, ignore the callback operation");
            return;
        }

        String callbackData;
        if (expectationDto != null && !StringUtils.isEmpty(expectationDto.getBgCallbackData())) {
            callbackData = expectationDto.getBgCallbackData();
        } else {
            Method callbackDataGetter = type.getMethod(GetCallbackData);
            callbackData = (String) callbackDataGetter.invoke(instance);
        }

        int callbackDelayTime = 0;
        if (expectationDto != null) {
            callbackDelayTime = expectationDto.getBgCallbackDelayTime();
        }

        callbackTask.addCallbackTask(callBackURL, callbackData, callbackDelayTime);
    }

    private void response(Class<?> type, Object instance, HttpServletRequest request,
                          HttpServletResponse response, ExpectationDto expectationDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        String retMessage = getResponseData(type, instance, expectationDto);
        if (expectationDto != null && expectationDto.isSaveFootprint()) {
            Map<String, Object> requestMap = HttpRequestUtil.convertToMap(request);
            String requestStr = JSON.toJSONString(requestMap);

            Method requestKeyGetterMethod = type.getMethod(GetRequestKey);
            String requestKey = (String) requestKeyGetterMethod.invoke(instance);
            footprintService.saveFootprint(request.getServletPath(), requestKey, requestStr, retMessage);
        }

        String contentType = request.getContentType();
        String charset = request.getCharacterEncoding();
        response.setContentType(contentType);
        response.setCharacterEncoding(charset);
        PrintWriter printer = response.getWriter();
        printer.write(retMessage);
    }

    private String getResponseData(Class<?> type, Object instance, ExpectationDto expectationDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String retMessage;
        if (expectationDto != null && !StringUtils.isEmpty(expectationDto.getResponseData())) {
            retMessage = expectationDto.getResponseData();
        } else {
            Map<String, Object> expectation = expectationDto == null ? null : expectationDto.getExpectation();
            Method getResponseMethod = type.getMethod(GetResponse, Map.class);
            retMessage = (String) getResponseMethod.invoke(instance, expectation);
        }

        return retMessage;
    }
}
