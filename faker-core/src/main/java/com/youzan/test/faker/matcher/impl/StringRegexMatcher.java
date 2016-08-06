package com.youzan.test.faker.matcher.impl;

import com.youzan.test.faker.matcher.Matcher;

/**
 * Created by libaixian on 16/8/2.
 */
public class StringRegexMatcher extends Matcher {
    private String key = null;
    private String pattern = null;

    public StringRegexMatcher(String key, String pattern) {
        this.key = key;
        this.pattern = pattern;
    }

    @Override
    public boolean match(String target) {
        // TODO: not implement

        return false;
    }
}
