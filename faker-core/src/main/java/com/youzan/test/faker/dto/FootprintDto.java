package com.youzan.test.faker.dto;

import lombok.Data;

/**
 * Created by libaixian on 16/8/11.
 */
@Data
public class FootprintDto {
    private String requestURL;
    private String requestKey;
    private String requestData;
    private String responseData;
    private Integer createDatetime;
}
