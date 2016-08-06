package com.youzan.test.faker.packages.baidu;

import com.youzan.test.faker.api.dto.ExpectationDto;
import com.youzan.test.faker.api.service.AbstractBaseHttpRequest;
import com.youzan.test.faker.packages.baidu.util.Helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by libaixian on 16/7/30.
 */
public class PayQuery extends AbstractBaseHttpRequest {
    @Override
    public String getResponse(ExpectationDto expectation) {
        TreeMap<String, String> parameterMap = creatQueryParameter(request2Map);
        return Helper.generateXml(parameterMap);
    }

    @Override
    public String callRealMethod() {
        return null;
    }


    private TreeMap<String, String> creatQueryParameter(Map<String, Object> params) {
        TreeMap<String, String> parameterMap = new TreeMap<String, String>();
        String payAmount = "10.00"; ////TODO: 使用redis来作为缓存 localCacheStore.get(params.get("order_no"))).get("total_amount")
        parameterMap.put("query_status", "0");
        parameterMap.put("bfb_order_no", params.get("order_no") + "12345");
        parameterMap.put("order_no", params.get("order_no").toString());
        parameterMap.put("pay_result", "1");
        parameterMap.put("total_amount", payAmount);

        return parameterMap;
    }
}
