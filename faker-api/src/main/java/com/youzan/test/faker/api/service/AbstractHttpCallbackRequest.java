package com.youzan.test.faker.api.service;

/**
 * Created by libaixian on 16/7/30.
 */
public abstract class AbstractHttpCallbackRequest extends AbstractBaseHttpRequest {
    public abstract String getCallbackUrl();

    public abstract String getCallbackData();
}
