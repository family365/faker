package com.youzan.test.faker.dao;

import com.youzan.test.faker.po.ServiceConfigPO;

import java.util.List;

/**
 * Created by libaixian on 16/8/3.
 */
public interface ServiceConfigDao {
    List<ServiceConfigPO> getAll();

    void add(ServiceConfigPO serviceConfigPO);

    void delete(String key);
}
