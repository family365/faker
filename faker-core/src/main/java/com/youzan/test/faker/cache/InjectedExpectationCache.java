package com.youzan.test.faker.cache;

import com.youzan.test.faker.api.dto.ExpectationDto;
import org.springframework.stereotype.Component;

/**
 * Created by libaixian on 16/8/2.
 */
@Component
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
