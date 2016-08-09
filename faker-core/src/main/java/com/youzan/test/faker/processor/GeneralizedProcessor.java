package com.youzan.test.faker.processor;

import com.alibaba.fastjson.JSON;
import com.youzan.test.faker.api.dto.ExpectationDto;
import com.youzan.test.faker.api.exception.FakerOperationException;
import com.youzan.test.faker.service.ExpectationCenterService;
import com.youzan.test.faker.service.FootprintService;
import com.youzan.test.faker.task.CallbackTask;
import com.youzan.test.faker.util.HttpRequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by libaixian on 16/8/1.
 */
@Component
public class GeneralizedProcessor {
    @Resource
    private ExpectationCenterService expectationCenterService;

    @Resource
    private FootprintService footprintService;

    @Resource
    private CallbackTask callbackTask;

    public void process(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getServletPath();
        Map<String, Object> request2Map = HttpRequestUtil.convertToMap(request);

        ExpectationDto expectationDto = expectationCenterService.process(url, request2Map);

        if (!StringUtils.isEmpty(expectationDto.getBgCallbackURL())) {
            String callbackData = expectationDto.getBgCallbackData();
            callbackTask.addCallbackTask(expectationDto.getBgCallbackURL(), callbackData, expectationDto.getBgCallbackDelayTime());
        }

        if (expectationDto.isSaveFootprint()) {
            Map<String, Object> requestMap = HttpRequestUtil.convertToMap(request);
            String requestStr = JSON.toJSONString(requestMap);
            footprintService.saveFootprint(expectationDto.getRequestUrl(), requestStr, expectationDto.getResponseData());
        }

        if (!StringUtils.isEmpty(expectationDto.getResponseData())) {
            PrintWriter printer = null;
            try {
                printer = response.getWriter();
            } catch (IOException e) {
                throw new FakerOperationException(e.getMessage());
            }
            printer.write(expectationDto.getResponseData());
        }

    }
}
