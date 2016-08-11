package com.youzan.test.faker.service;

import com.google.common.collect.Lists;
import com.youzan.test.faker.dto.FootprintDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by libaixian on 16/8/6.
 */
@Component
public class FootprintService {
    public void saveFootprint(String requestUrl, String key, String request, String response) {

    }

    public List<FootprintDto> get(String requestUrl, String key) {
        List<FootprintDto> footprintList = Lists.newArrayList();

        return footprintList;
    }

}
