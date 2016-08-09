package com.youzan.test.faker.api.cache;

/**
 * Created by libaixian on 16/8/9.
 */
public interface Cache {
    void put(String key, Object value);

    Object get(String key);
}
