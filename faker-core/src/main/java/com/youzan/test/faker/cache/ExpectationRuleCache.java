package com.youzan.test.faker.cache;

import com.youzan.test.faker.matcher.ExpectationMatcher;

import java.util.List;

/**
 * Created by libaixian on 16/8/2.
 */
public class ExpectationRuleCache {
    private LocalCache<List<ExpectationMatcher>> cache = new LocalCache<>();

    public void add(String key, List<ExpectationMatcher> matcherList) {
        cache.setValue(key, matcherList);
    }

    public List<ExpectationMatcher> get(String key) {
        return cache.getValue(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

}
