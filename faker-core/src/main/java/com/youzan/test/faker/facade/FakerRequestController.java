package com.youzan.test.faker.facade;

import com.youzan.test.faker.api.exception.FakerOperationException;
import com.youzan.test.faker.confLoader.ServiceRouterLoader;
import com.youzan.test.faker.handler.Handler;
import com.youzan.test.faker.handler.HandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by libaixian on 16/7/31.
 */
@Slf4j
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
        try {
            Handler handler = handlerFactory.getHandler(request);
            handler.handle(request, response);
        } catch (FakerOperationException ex) {
            try {
                PrintWriter writer = response.getWriter();
                writer.write(ex.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
