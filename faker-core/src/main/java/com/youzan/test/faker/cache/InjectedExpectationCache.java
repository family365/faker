package com.youzan.test.faker.cache;

import com.youzan.test.faker.api.dto.ExpectationDto;

/**
 * Created by libaixian on 16/8/2.
 */
public class InjectedExpectationCache {
    private LocalCache<ExpectationDto> cache = new LocalCache<>();

    public ExpectationDto getValue(String key) {
        return null;
    }

    public void setValue(String key, ExpectationDto expectationDto) {

    }

    public boolean setExpired(String key) {
        return true;
    }
}
