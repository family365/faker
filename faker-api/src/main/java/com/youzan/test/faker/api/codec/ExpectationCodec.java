package com.youzan.test.faker.api.codec;

import com.alibaba.fastjson.JSON;
import com.youzan.test.faker.api.dto.ExpectationDto;
import com.youzan.test.faker.api.exception.FakerOperationException;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libaixian on 16/8/2.
 */
public class ExpectationCodec {
    private static final String FieldSeparator = "&";
    private static final String Key_ExpectationKey = "expectationKey";
    private static final String Key_Expectation = "expectation";
    private static final String Key_ResponseData = "responseData";
    private static final String Key_ThrowException = "throwException";
    private static final String Key_HandOver2RealMethod = "handOver2RealMethod";
    private static final String Key_SaveFootprint = "saveFootprint";
    private static final String Key_BgCallbackDelayTime = "bgCallbackDelayTime";
    private static final String Key_BgCallbackURL = "bgCallbackURL";
    private static final String Key_BgCallbackData = "bgCallbackData";

    public static Map<String, Object> serialize(ExpectationDto expectationDto) {
        if (expectationDto == null) {
            throw new FakerOperationException("ExpectationDto不能为null");
        }

        if (StringUtils.isEmpty(expectationDto.getExpectationKey())) {
            throw new FakerOperationException("expectationKey未指定");
        }

        if(StringUtils.isEmpty(expectationDto.getResponseData()) && (expectationDto.getExpectation() == null || expectationDto.getExpectation().isEmpty())){
            throw new FakerOperationException("必须指定设定期望的响应信息");
        }

        Map<String, Object> result = new HashMap<>();
        result.put(Key_ExpectationKey, expectationDto.getExpectationKey());
        if (expectationDto.getExpectation() != null && !expectationDto.getExpectation().isEmpty()) {
            result.put(Key_Expectation, JSON.toJSONString(expectationDto.getExpectation()));
        }

        result.put(Key_ResponseData, expectationDto.getResponseData());
        result.put(Key_ThrowException, expectationDto.isThrowException());
        result.put(Key_HandOver2RealMethod, expectationDto.isHandOver2RealMethod());
        result.put(Key_SaveFootprint, expectationDto.isSaveFootprint());
        result.put(Key_BgCallbackDelayTime, expectationDto.getBgCallbackDelayTime());
        result.put(Key_BgCallbackURL, expectationDto.getBgCallbackURL());
        result.put(Key_BgCallbackData, expectationDto.getBgCallbackData());

        return result;
    }

    public static ExpectationDto deserialize(Map<String, Object> requestMap) {
        if (requestMap == null || requestMap.isEmpty()) {
            return null;
        }

        if (StringUtils.isEmpty((String) requestMap.get(Key_ExpectationKey))) {
            throw new FakerOperationException("expectationKey未指定");
        }

        if (StringUtils.isEmpty((String) requestMap.get(Key_Expectation)) && StringUtils.isEmpty((String) requestMap.get(Key_ResponseData))) {
            throw new FakerOperationException("期望值未指定");
        }

        if (!StringUtils.isEmpty((String) requestMap.get(Key_Expectation))) {

        }
        ExpectationDto expectationDto = new ExpectationDto();
        expectationDto.setExpectationKey(requestMap.get(Key_ExpectationKey).toString());
        if (!StringUtils.isEmpty((String) requestMap.get(Key_Expectation))) {
            expectationDto.setExpectation(JSON.parseObject((String) requestMap.get(Key_Expectation), Map.class));
        }
        expectationDto.setResponseData((String) requestMap.get(Key_ResponseData));
        expectationDto.setThrowException((boolean)requestMap.getOrDefault(Key_ThrowException, false));
        expectationDto.setHandOver2RealMethod((boolean) requestMap.getOrDefault(Key_HandOver2RealMethod , false));
        expectationDto.setSaveFootprint((boolean) requestMap.getOrDefault(Key_SaveFootprint, false));
        expectationDto.setBgCallbackDelayTime((int) requestMap.getOrDefault(Key_BgCallbackDelayTime, 0));
        expectationDto.setBgCallbackURL((String) requestMap.get(Key_BgCallbackURL));
        expectationDto.setBgCallbackData((String) requestMap.get(Key_BgCallbackData));
        return expectationDto;
    }
}
