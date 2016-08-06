package com.youzan.test.faker.cache;

import com.youzan.test.faker.dto.ServiceConfigDto;

import java.util.List;
import java.util.Set;

/**
 * Created by libaixian on 16/8/3.
 */
public class ServiceConfigCache {
    private LocalCache<ServiceConfigDto> cache = new LocalCache<>();

    public ServiceConfigDto getValue(String key) {
        return null;
    }

    public Set<String> getKeys() {
        return cache.getKeySet();
    }

    public void setValue(String key, ServiceConfigDto expectationDto) {

    }

    public boolean setExpired(String key) {
        return true;
    }
}
