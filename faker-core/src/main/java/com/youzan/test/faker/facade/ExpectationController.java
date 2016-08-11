package com.youzan.test.faker.facade;

import com.youzan.test.faker.api.codec.ExpectationCodec;
import com.youzan.test.faker.api.dto.ExpectationDto;
import com.youzan.test.faker.service.DynamicExpectationService;
import com.youzan.test.faker.util.HttpRequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by libaixian on 16/8/10.
 */
@Controller
public class ExpectationController {
    @Resource
    private DynamicExpectationService cache;

    @RequestMapping("/setExpectation")
    public void setExpectation(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> requestParam = HttpRequestUtil.convertToMap(request);
            ExpectationDto expectation = ExpectationCodec.deserialize(requestParam);
            cache.save(expectation.getExpectationKey(), expectation);
            printRsponse(response, "0000", "success");
        }catch (IllegalArgumentException ex) {
            printRsponse(response, "9999", ex.getMessage());
        } catch (Exception ex) {
            printRsponse(response, "9999", ex.getMessage());
        }

    }

    private void printRsponse(HttpServletResponse response, String errCode, String message) {
        PrintWriter printer = null;
        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("{");
            strBuilder.append("\"errCode\":\"").append(errCode!=null&&errCode.trim().length()!=0 ? errCode: "9999").append("\"");
            strBuilder.append("\"errMsg\":\"").append(message).append("\"");
            strBuilder.append("}");
            String retMsg = strBuilder.toString();

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            printer = response.getWriter();
            printer.append(retMsg);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (printer != null) {
                printer.close();
            }
        }

    }
}
