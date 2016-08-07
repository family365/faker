package com.youzan.test.faker.service;

import com.youzan.test.faker.api.dto.ExpectationDto;
import com.youzan.test.faker.cache.ExpectationRuleCache;
import com.youzan.test.faker.dao.ExpectationRuleDao;
import com.youzan.test.faker.dto.ExpectationRuleDto;
import com.youzan.test.faker.matcher.ExpectationMatcher;
import com.youzan.test.faker.po.ExpectationRulePO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by libaixian on 16/8/3.
 */
@Service
public class RegularExpectationService {
    @Resource
    private ExpectationRuleCache cache;

    @Resource
    private ExpectationRuleDao expectationRuleDao;

    public ExpectationDto get(String key, Map<String, Object> request) {
        //TODO: 从缓存中获取ExpectationMatcher list
        //如果未找到, 加载数据刷新缓存
        List<ExpectationMatcher> matcherList = cache.get(key);
        if (matcherList == null) {
            refresh(key);
            matcherList = cache.get(key);
        }

        if (matcherList == null) {
            return null;
        }

        for(ExpectationMatcher matcher : matcherList) {
            if (matcher.matched(request)) {
                return matcher.getExpectation();
            }
        }

        return null;
    }

    public void refresh(String key) {
        //TODO: 从数据库中抓取rule, 生成 ExpectationMatcher, 放入缓存
        List<ExpectationRulePO> ruleList = expectationRuleDao.getByKey(key);
        if (ruleList == null || ruleList.isEmpty()) {
            return;
        }

        List<ExpectationMatcher> newMatcherList = new ArrayList<>();
        for(ExpectationRulePO expectationRulePO : ruleList) {
            ExpectationMatcher matcher = ExpectationMatcher.createInstance(key, expectationRulePO);
            newMatcherList.add(matcher);
        }

        cache.add(key, newMatcherList);
    }

    public void delete(Integer id) {

    }

    public void add(ExpectationRuleDto expectationRuleDto) {

    }
}
