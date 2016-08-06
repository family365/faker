package com.youzan.test.faker.handler;

import com.youzan.test.faker.handler.impl.ExternalHandlerImpl;
import com.youzan.test.faker.handler.impl.InternalHandlerImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by libaixian on 16/8/1.
 */
@Component
public class HandlerFactory {
    @Resource
    private InternalHandlerImpl internalHandler;

    @Resource
    private ExternalHandlerImpl externalHandler;

    public Handler getHandler(HttpServletRequest reqest) {
        if (internalHandler.supports(reqest)) {
            return internalHandler;
        } else if (externalHandler.supports(reqest)) {
            return externalHandler;
        } else {
            throw new RuntimeException("cannot find the handler to process your request");
        }
    }
}
