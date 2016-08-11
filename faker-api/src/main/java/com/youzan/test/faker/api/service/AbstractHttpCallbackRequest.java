package com.youzan.test.faker.api.service;

/**
 * Created by libaixian on 16/7/30.
 */
public abstract class AbstractHttpCallbackRequest extends AbstractBaseHttpRequest {
    /**
     * 获取回调地址URL,
     * 1. 优先从期望规则中指定的值获取
     * 2. 其次从服务配置中获取
     * 3. 最后从本接口中获取
     * @return
     */
    public abstract String getCallbackUrl();

    /**
     * 用于生成回调接口调用时的数据
     * @return
     */
    public abstract String getCallbackData();
}
