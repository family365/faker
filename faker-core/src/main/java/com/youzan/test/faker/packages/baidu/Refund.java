package com.youzan.test.faker.packages.baidu;

import com.youzan.test.faker.api.dto.ExpectationDto;
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
    public String getResponse(ExpectationDto expectation) {
        final String CashBackAmount = "cashback_amount";
        final String SpNo = "sp_no";
        final String OrderNo = "order_no";
        final String BfbOrderNo = "bfb_order_no";
        final String SpRefundNo = "sp_refund_no";
        final String RetCode = "ret_code";
        final String RetDetail = "ret_detail";

        Map<String, String> expParam = expectation != null ? expectation.getExpectation() : null;

        String cashBackAmount;
        String spNo;
        String orderNo;
        String spRefundNo;
        String retCode;
        if (expParam == null) {
            cashBackAmount = (String) this.request2Map.get(CashBackAmount);
            spNo = (String) this.request2Map.get(SpNo);
            orderNo = (String) this.request2Map.get(OrderNo);
            spRefundNo = (String) this.request2Map.get(SpRefundNo);
            retCode = "1";
        } else {
            cashBackAmount = expParam.getOrDefault(CashBackAmount, (String) this.request2Map.get(CashBackAmount));
            spNo = expParam.getOrDefault(SpNo, (String) this.request2Map.get(SpNo));
            orderNo = expParam.getOrDefault(OrderNo, (String) this.request2Map.get(OrderNo));
            spRefundNo = expParam.getOrDefault(SpRefundNo, (String) this.request2Map.get(SpRefundNo));
            retCode = expParam.getOrDefault(RetCode, "1");
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
