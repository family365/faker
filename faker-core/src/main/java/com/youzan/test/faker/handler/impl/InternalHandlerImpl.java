package com.youzan.test.faker.handler.impl;

import com.youzan.test.faker.confLoader.ServiceRouterLoader;
import com.youzan.test.faker.handler.Handler;
import com.youzan.test.faker.processor.HardCodeMockerProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by libaixian on 16/7/31.
 */
@Component
public class InternalHandlerImpl implements Handler {
    @Resource
    public ServiceRouterLoader serviceRouterLoader;

    @Resource
    private HardCodeMockerProcessor hardCodeMockerProcessor;

    @Override
    public boolean supports(HttpServletRequest request) {
        String requestUrl = request.getServletPath();
        Map<String, String> serviceRouter = serviceRouterLoader.load();
        if (serviceRouter.containsKey(requestUrl)) {
            return true;
        }

        return false;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        String requestUrl = request.getServletPath();
        Map<String, String> serviceRouter = serviceRouterLoader.load();
        String className = serviceRouter.get(requestUrl);
        hardCodeMockerProcessor.process(className, request, response);
    }
}
