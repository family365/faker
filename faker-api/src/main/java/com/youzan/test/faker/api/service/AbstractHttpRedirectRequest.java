package com.youzan.test.faker.api.service;

/**
 * Created by libaixian on 16/7/30.
 */
public abstract class AbstractHttpRedirectRequest extends AbstractHttpCallbackRequest {
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
