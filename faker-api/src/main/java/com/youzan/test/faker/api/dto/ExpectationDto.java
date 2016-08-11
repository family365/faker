package com.youzan.test.faker.api.dto;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by libaixian on 16/7/30.
 */
@Data
public class ExpectationDto {
    /**
     * 本次期望值设定的标识, mock接口也会使用这个key来获取期望设定值
     * 通常expectationKey的值为一个URL
     */
    private String expectationKey = null;

    /**
     * 指定返回信息中某个字段的值
     */
    private Map expectation = new HashMap<String, String>();

    /**
     * 指定返回的报文, 如果通过这个字段来指定报文的话, 将优先使用此字段中的内容来作为报文内容返回, 字段 expectation 中的值将失效
     */
    private String responseData = null;

    /**
     * 响应延迟时间
     */
    private int responseDelayTime = 0;

    /**
     * 是否抛出异常
     */
    private boolean throwException = false;

    /**
     * 是否调用真实的第三方接口, 而不是使用mock接口
     */
    private boolean handOver2RealMethod = false;

    /**
     * 是否保存请求响应记录
     */
    private boolean saveFootprint =false;

    /**
     * 回调的延迟时间, 延迟时间是指最低延迟时间, 真实的延迟时间可能会超过这个时间
     */
    private int bgCallbackDelayTime = 0;

    /**
     * 回调地址
     */
    private String bgCallbackURL = null;

    /**
     * 接口回调中的数据
     */
    private String bgCallbackData = null;
}


