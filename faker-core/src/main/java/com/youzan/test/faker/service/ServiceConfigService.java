package com.youzan.test.faker.service;

import com.youzan.test.faker.cache.ServiceConfigCache;
import com.youzan.test.faker.dao.ServiceConfigDao;
import com.youzan.test.faker.dto.ServiceConfigDto;
import com.youzan.test.faker.po.ServiceConfigPO;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by libaixian on 16/8/3.
 */
public class ServiceConfigService {
    @Resource
    private ServiceConfigDao serviceConfigDao;

    @Resource
    private ServiceConfigCache cache;

    @PostConstruct
    public void init() {
        List<ServiceConfigPO> serviceList = serviceConfigDao.getAll();
        for(ServiceConfigPO serviceConfig : serviceList) {
            ServiceConfigDto serviceConfigDto = convert(serviceConfig);
            String key =serviceConfigDto.getApiPath();
            cache.setValue(key, serviceConfigDto);
        }
    }

    public ServiceConfigDto get(String key) {
        return null;
    }

    public Set<String> getKeySet() {
        return cache.getKeys();
    }

    public void refresh(String key) {
        //TODO: 刷新
    }

    public void refresh(String key, ServiceConfigDto serviceConfigDto) {

    }

    public void delete(String key) {

    }

    private ServiceConfigDto convert(ServiceConfigPO serviceConfigPO) {
        //TODO: convert
        return null;
    }
}
