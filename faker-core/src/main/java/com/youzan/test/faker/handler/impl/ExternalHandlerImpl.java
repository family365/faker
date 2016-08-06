package com.youzan.test.faker.handler.impl;

import com.youzan.test.faker.cache.ServiceConfigCache;
import com.youzan.test.faker.dto.ServiceConfigDto;
import com.youzan.test.faker.enums.MockTypeEnum;
import com.youzan.test.faker.handler.Handler;
import com.youzan.test.faker.processor.GeneralizedProcessor;
import com.youzan.test.faker.processor.HardCodeProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * Created by libaixian on 16/7/31.
 */
@Component
public class ExternalHandlerImpl implements Handler{
    @Resource
    private ServiceConfigCache serviceConfigCache;

    @Resource
    private HardCodeProcessor hardCodeProcessor;

    @Resource
    private GeneralizedProcessor generalizedProcessor;

    @Override
    public boolean supports(HttpServletRequest request) {
        String url = request.getRequestURI();
        Set<String> supportedAPI = serviceConfigCache.getKeys();
        if (!supportedAPI.contains(url)) {
            return false;
        }

        return true;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURI();
        ServiceConfigDto serviceConfig = serviceConfigCache.getValue(url);
        if (serviceConfig.getMockType() == MockTypeEnum.Coded) {
            hardCodeProcessor.process(serviceConfig.getClassHandler(), request, response);
        } else {
            generalizedProcessor.process(request, response);
        }
    }
}
