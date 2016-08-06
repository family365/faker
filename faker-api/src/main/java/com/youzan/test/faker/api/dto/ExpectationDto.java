package com.youzan.test.faker.api.dto;

import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by libaixian on 16/7/30.
 */
@Data
public class ExpectationDto {
    private Map expectation = new HashMap<String, String>();
    private String responseData = null;
    private int responseDelayTime = 0;
    private boolean throwException = false;
    private boolean handOver2RealMethod;
    private boolean saveFootprint;
    private String requestUrl = null;
    private int bgCallbackDelayTime = 0;
    private String bgCallbackURL = null;
    private String bgCallbackData = null;
}
