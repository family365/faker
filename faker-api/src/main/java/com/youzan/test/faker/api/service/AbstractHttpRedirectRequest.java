package com.youzan.test.faker.api.service;

/**
 * Created by libaixian on 16/7/30.
 */
public abstract class AbstractHttpRedirectRequest extends AbstractHttpCallbackRequest {

    /**
     * 用于获取重定向redirect的URL, 重定向中的数据报文将会从getResponse接口中获取
     * @return
     */
    public abstract String getRedirectURL();

    @Override
    public String getCallbackUrl(){
        return null;
    }

    @Override
    public String getCallbackData() {
        return null;
    }
}
