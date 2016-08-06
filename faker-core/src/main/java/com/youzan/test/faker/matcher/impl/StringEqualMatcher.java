package com.youzan.test.faker.matcher.impl;

import com.youzan.test.faker.matcher.Matcher;

/**
 * Created by libaixian on 16/8/2.
 */
public class StringEqualMatcher extends Matcher {
    private String value = null;

    public StringEqualMatcher(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean match(String target) {
        // TODO: not implement

        return false;
    }
}
