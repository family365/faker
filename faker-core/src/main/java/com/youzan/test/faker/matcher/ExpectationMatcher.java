package com.youzan.test.faker.matcher;

import com.youzan.test.faker.api.dto.ExpectationDto;
import com.youzan.test.faker.po.ExpectationRulePO;

import java.util.List;
import java.util.Map;

/**
 * Created by libaixian on 16/8/2.
 */
public class ExpectationMatcher {
    private static ExpectationMatcher instance = null;
    private List<Matcher> matcherList = null;
    private ExpectationDto expectationDto;

    private ExpectationMatcher() {}

    public static ExpectationMatcher createInstance(String key, ExpectationRulePO expectationRulePO) {
        //TODO: 根据设置的规则的标识, 生成一系列matcher
        //TODO: convert PO to DTO

        return null;
    }

    public boolean matched(Map<String, Object> request) {

        return false;
    }

    public ExpectationDto getExpectation() {
        //TODO:
        return null;
    }

    private ExpectationDto conver2ExpectationDTO(ExpectationRulePO expectationRulePO) {
        return null;
    }
}
