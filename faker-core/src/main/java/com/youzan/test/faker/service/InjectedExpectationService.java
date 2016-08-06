package com.youzan.test.faker.service;

import com.youzan.test.faker.api.dto.ExpectationDto;
import com.youzan.test.faker.cache.InjectedExpectationCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by libaixian on 16/8/2.
 */
@Service
public class InjectedExpectationService {
    @Resource
    private InjectedExpectationCache cache;

    public void save(String key, String expectation) {
        //TODO: expectation是从request中获取到的字符串, 内容是ExpectationDto对象序列化后的字符串
        // 反序列化后, 保存到缓存中
    }

    public ExpectationDto get(String key) {
        ExpectationDto expectation = cache.getValue(key);
        cache.setExpired(key);
        return expectation;
    }

}
