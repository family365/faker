package com.youzan.test.faker.dto;

import com.youzan.test.faker.enums.MockTypeEnum;
import lombok.Data;

/**
 * Created by libaixian on 16/7/30.
 */
@Data
public class ServiceConfigDto {
    private String group;
    private String apiPath;
    private String apiDesc;
    private MockTypeEnum mockType;
    private int delayTime;
    private String classHandler;
    private boolean footprintSaved;
    private boolean handOver2RealMethod;
    private String defaultResponse;
    private int callbackDelayTime;
    private String bgCallbackURL;
    private String bgCallbackData;
}
