package com.youzan.test.faker.handler.impl;

import com.youzan.test.faker.dto.ServiceConfigDto;
import com.youzan.test.faker.enums.MockTypeEnum;
import com.youzan.test.faker.handler.Handler;
import com.youzan.test.faker.processor.GeneralizedMockerProcessor;
import com.youzan.test.faker.processor.HardCodeMockerProcessor;
import com.youzan.test.faker.service.ServiceConfigService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Created by libaixian on 16/7/31.
 */
@Component
public class ExternalHandlerImpl implements Handler{
    @Resource
    private ServiceConfigService serviceConfigService;

    @Resource
    private HardCodeMockerProcessor hardCodeMockerProcessor;

    @Resource
    private GeneralizedMockerProcessor generalizedMockerProcessor;

    @Override
    public boolean supports(HttpServletRequest request) {
        String url = request.getServletPath();
        Set<String> supportedAPI = serviceConfigService.getKeySet();
        if (!supportedAPI.contains(url)) {
            return false;
        }

        return true;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getServletPath();
        ServiceConfigDto serviceConfig = serviceConfigService.get(url);
        if (serviceConfig.getMockType() == MockTypeEnum.Coded) {
            hardCodeMockerProcessor.process(serviceConfig.getClassHandler(), request, response);
        } else {
            generalizedMockerProcessor.process(request, response);
        }
    }
}
