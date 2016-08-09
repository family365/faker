package com.youzan.test.faker.cache;

import com.google.common.cache.CacheBuilder;
import com.youzan.test.faker.api.cache.Cache;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by libaixian on 16/8/9.
 */
@Component
public class AutoExpiredCache implements Cache {
    private static final int Capbility = Integer.MAX_VALUE;
    private static final long AliveTime = 24; //24 hours
    private com.google.common.cache.Cache<String, Object> cache;

    @PostConstruct
    public void init() {
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(Capbility)
                .expireAfterWrite(AliveTime, TimeUnit.HOURS)
                .build();
    }

    @Override
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object get(String key) {
        return cache.getIfPresent(key);
    }
}
