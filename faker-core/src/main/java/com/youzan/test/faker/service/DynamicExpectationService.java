package com.youzan.test.faker.service;

import com.youzan.test.faker.api.dto.ExpectationDto;
import com.youzan.test.faker.cache.DynamicExpectationCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by libaixian on 16/8/2.
 */
@Service
public class DynamicExpectationService {
    @Resource
    private DynamicExpectationCache cache;

    public void save(String key, ExpectationDto expectationDto) {
        cache.setValue(key, expectationDto);
    }

    public ExpectationDto get(String key) {
        ExpectationDto expectation = cache.getValue(key);
        cache.remove(key);
        return expectation;
    }

}
