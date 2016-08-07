package com.youzan.test.faker.facade;

import com.youzan.test.faker.confLoader.ServiceRouterLoader;
import com.youzan.test.faker.handler.Handler;
import com.youzan.test.faker.handler.HandlerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by libaixian on 16/7/31.
 */
@Controller
public class FakerRequestController {
    @Resource
    private ServiceRouterLoader serviceRouterLoader;

    @Resource
    private HandlerFactory handlerFactory;

    @RequestMapping("/{path1}")
    public void path1(HttpServletRequest request, HttpServletResponse response) {
        doDispatch(request, response);
    }

    @RequestMapping("/{path1}/{path2}")
    public void path2(HttpServletRequest request, HttpServletResponse response) {
        doDispatch(request, response);
    }

    @RequestMapping("/{path1}/{path2}/{path3}")
    public void path3(HttpServletRequest request, HttpServletResponse response) {
        doDispatch(request, response);
    }

    @RequestMapping("/{path1}/{path2}/{path3}/{path4}")
    public void path4(HttpServletRequest request, HttpServletResponse response) {
        doDispatch(request, response);
    }

    @RequestMapping("/{path1}/{path2}/{path3}/{path4}/{path5}")
    public void path5(HttpServletRequest request, HttpServletResponse response) {
        doDispatch(request, response);
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        Handler handler = handlerFactory.getHandler(request);
        handler.handle(request, response);
    }

}
