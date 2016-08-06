package com.youzan.test.faker.dao;

import com.youzan.test.faker.dto.ExpectationRuleDto;
import com.youzan.test.faker.po.ExpectationRulePO;

import java.util.List;

/**
 * Created by libaixian on 16/8/3.
 */
public interface ExpectationRuleDao {
    List<ExpectationRulePO> getByKey(String key);
}
