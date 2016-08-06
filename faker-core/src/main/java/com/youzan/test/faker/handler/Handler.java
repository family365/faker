package com.youzan.test.faker.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by libaixian on 16/8/1.
 */
public interface Handler {
    boolean supports(HttpServletRequest request);

    void handle(HttpServletRequest request, HttpServletResponse response);
}
