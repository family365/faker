package com.youzan.test.faker.matcher.impl;

import com.youzan.test.faker.matcher.Matcher;

import java.util.List;

/**
 * Created by libaixian on 16/8/2.
 */
public class ContainMatcher extends Matcher{
    private List<String> valueList = null;

    public ContainMatcher(String key, List<String> valueList) {
        this.key = key;
        this.valueList = valueList;
    }

    @Override
    public boolean match(String target) {
        // TODO: not implement
        return false;
    }
}
