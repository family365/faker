package com.youzan.test.faker.packages.baidu;

import com.youzan.test.faker.api.service.AbstractBaseHttpRequest;
import com.youzan.test.faker.packages.baidu.util.Helper;
import com.youzan.test.faker.util.DatetimeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libaixian on 16/7/30.
 */
public class Refund extends AbstractBaseHttpRequest{
    @Override
    public String getResponse(Map<String, Object> expectation) {
        final String CashBackAmount = "cashback_amount";
        final String SpNo = "sp_no";
        final String OrderNo = "order_no";
        final String BfbOrderNo = "bfb_order_no";
        final String SpRefundNo = "sp_refund_no";
        final String RetCode = "ret_code";
        final String RetDetail = "ret_detail";

        String cashBackAmount;
        String spNo;
        String orderNo;
        String spRefundNo;
        String retCode;
        if (expectation == null || expectation.isEmpty()) {
            cashBackAmount = (String) this.request2Map.get(CashBackAmount);
            spNo = (String) this.request2Map.get(SpNo);
            orderNo = (String) this.request2Map.get(OrderNo);
            spRefundNo = (String) this.request2Map.get(SpRefundNo);
            retCode = "1";
        } else {
            cashBackAmount = (String) expectation.getOrDefault(CashBackAmount, this.request2Map.get(CashBackAmount));
            spNo = (String) expectation.getOrDefault(SpNo, this.request2Map.get(SpNo));
            orderNo = (String) expectation.getOrDefault(OrderNo, this.request2Map.get(OrderNo));
            spRefundNo = (String) expectation.getOrDefault(SpRefundNo, this.request2Map.get(SpRefundNo));
            retCode = (String) expectation.getOrDefault(RetCode, "1");
        }

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(CashBackAmount, cashBackAmount);
        responseMap.put(SpNo, spNo);
        responseMap.put(OrderNo, orderNo);
        responseMap.put(BfbOrderNo, DatetimeUtil.getStrTimeStamp() + "999999999999999");
        responseMap.put(SpRefundNo, spRefundNo);
        responseMap.put(RetCode, retCode);
        responseMap.put(RetDetail, "");

        String result = Helper.generateXml(responseMap);
        return result;
    }

    @Override
    public String callRealMethod() {
        return null;
    }
}
