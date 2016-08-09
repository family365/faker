package com.youzan.test.faker.packages.baidu;

import com.youzan.test.faker.api.service.AbstractHttpRedirectRequest;
import com.youzan.test.faker.packages.baidu.util.Helper;
import com.youzan.test.faker.util.DatetimeUtil;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by libaixian on 16/7/30.
 */
public class Pay extends AbstractHttpRedirectRequest{
    private static final String ReturnURL = "http://www.baidu.com"; //"http://trade.koudaitong.com/pay/baiduwap/return";

    @Override
    public String getRedirectURL() {
        return ReturnURL;
    }

    @Override
    public String getResponse(Map<String, Object> expectation) {
        TreeMap<String, String> parameterMap = creatPayParameter(expectation);
        String sign = Helper.createSign(parameterMap);
        return Helper.combineData(parameterMap) + "&sign=" + sign;
    }

    @Override
    public String callRealMethod() {
        return null;
    }

    private TreeMap<String, String> creatPayParameter(Map<String, Object> expectation) {
        TreeMap<String, String> parameterMap = new TreeMap<String, String>();
        Map<String, Object> params = request2Map;

        String payResult = "1";
        if (expectation != null && !expectation.isEmpty()) {
            payResult = (String) expectation.getOrDefault("pay_result", 1);
        }

        parameterMap.put("bank_no", params.get("bank_no").toString());
        parameterMap.put("bfb_order_create_time", DatetimeUtil.getStrNowTime());
        parameterMap.put("bfb_order_no", params.get("order_no") + "12345");
        parameterMap.put("buyer_sp_username", params.get("buyer_sp_username").toString());
        parameterMap.put("currency", params.get("currency").toString());
        parameterMap.put("extra", params.get("extra").toString());
        parameterMap.put("fee_amount", "0");
        parameterMap.put("input_charset", params.get("input_charset").toString());
        parameterMap.put("order_no", params.get("order_no").toString());
        parameterMap.put("pay_result", payResult);
        parameterMap.put("pay_time", DatetimeUtil.getStrNowTime());
        parameterMap.put("pay_type", params.get("pay_type").toString());
        parameterMap.put("sign_method", params.get("sign_method").toString());
        parameterMap.put("sp_no", params.get("sp_no").toString());
        parameterMap.put("total_amount", params.get("total_amount").toString());
        parameterMap.put("transport_amount", params.get("transport_amount").toString());
        parameterMap.put("unit_amount", params.get("unit_amount").toString());
        parameterMap.put("unit_count", params.get("unit_count").toString());
        parameterMap.put("version", params.get("version").toString());

        cache.put((String) params.get("order_no"), parameterMap.get("total_amount"));
        return parameterMap;
    }
}
