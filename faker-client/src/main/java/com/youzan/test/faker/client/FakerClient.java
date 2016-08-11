package com.youzan.test.faker.client;

import com.youzan.test.faker.api.codec.ExpectationCodec;
import com.youzan.test.faker.api.dto.ExpectationDto;

import java.util.Map;

/**
 * Created by libaixian on 16/8/11.
 */
public class FakerClient {
    private static final String FakerURL = "http://10.9.68.12:8080/faker/setExpectation";
    public void call(ExpectationDto expectationDto) {
        Map<String, Object> requestData = ExpectationCodec.serialize(expectationDto);

    }
}
