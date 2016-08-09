package com.youzan.test.faker.packages.baidu;

import com.youzan.test.faker.api.service.AbstractBaseHttpRequest;
import com.youzan.test.faker.packages.baidu.util.Helper;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by libaixian on 16/7/30.
 */
public class PayQuery extends AbstractBaseHttpRequest {
    @Override
    public String getResponse(Map<String, Object> expectation) {
        TreeMap<String, String> parameterMap = createQueryParameter(expectation);
        return Helper.generateXml(parameterMap);
    }

    @Override
    public String callRealMethod() {
        return null;
    }


    private TreeMap<String, String> createQueryParameter(Map<String, Object> expectation) {
        TreeMap<String, String> parameterMap = new TreeMap<String, String>();
        String payAmount = (String) cache.get("order_no");
        if (StringUtils.isEmpty(payAmount)) {
            //TODO: 如果这里拿不到数据, 改如何响应
            payAmount = "-1";
        }

        String payResult = "1";
        if (expectation != null && !expectation.isEmpty()) {
            payResult = (String) expectation.getOrDefault("pay_result", "1");
        }

        parameterMap.put("query_status", "0");
        parameterMap.put("bfb_order_no", request2Map.get("order_no") + "12345");
        parameterMap.put("order_no", request2Map.get("order_no").toString());
        parameterMap.put("pay_result", payResult);
        parameterMap.put("total_amount", payAmount);

        return parameterMap;
    }
}
