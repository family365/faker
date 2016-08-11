package com.youzan.test.faker.service;

import com.youzan.test.faker.api.dto.ExpectationDto;
import com.youzan.test.faker.api.exception.FakerResponseException;
import com.youzan.test.faker.dto.ServiceConfigDto;
import com.youzan.test.faker.messageConvert.MessageConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by libaixian on 16/8/5.
 */
@Service
public class ExpectationCenterService {
    @Resource
    private DynamicExpectationService dynamicExpectationService;

    @Resource
    private RegularExpectationService regularExpectationService;

    @Resource
    private MessageConverter messageConverter;

    public ExpectationDto process(String expectationKey, Map<String, Object> requestMap) {
        ExpectationDto expectationDto = dynamicExpectationService.get(expectationKey);
        if (expectationDto == null) {
            expectationDto = regularExpectationService.get(expectationKey, requestMap);
        }

        if(expectationDto == null) {
            //TODO: load serviceConfig,
            // 当期望值为null时, 按照mock接口的默认设置来响应请求
            ServiceConfigDto serviceConfigDto = null;
            expectationDto = convertServiceConfigToExpe(serviceConfigDto);
        }

        if (expectationDto != null) {
            if (expectationDto.isThrowException()) {
                throw new FakerResponseException();
            }

            if (expectationDto.getResponseDelayTime() > 0) {
                try {
                    Thread.sleep(expectationDto.getResponseDelayTime() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (!StringUtils.isEmpty(expectationDto.getResponseData())) {
                String afterConvert = messageConverter.convert(expectationDto.getResponseData(), requestMap);
                expectationDto.setResponseData(afterConvert);

            } else if (expectationDto.getExpectation() != null && !expectationDto.getExpectation().isEmpty()) {
                Map<String, String> afterConvert = messageConverter.convert(expectationDto.getExpectation(), requestMap);
                expectationDto.setExpectation(afterConvert);
            }

            if (!StringUtils.isEmpty(expectationDto.getBgCallbackData())) {
                String afterConvert = messageConverter.convert(expectationDto.getBgCallbackData(), requestMap);
                expectationDto.setBgCallbackData(afterConvert);
            }
        }

        return expectationDto;
    }


    private ExpectationDto convertServiceConfigToExpe(ServiceConfigDto serviceConfigDto) {
        //TODO: 把接口的默认配置, 转换为期望类型
        return null;
    }
}
