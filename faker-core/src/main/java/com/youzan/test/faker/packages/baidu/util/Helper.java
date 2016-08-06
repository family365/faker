package com.youzan.test.faker.packages.baidu.util;

import com.youzan.test.faker.util.MD5;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Map;

/**
 * Created by wanwei on 16/3/15.
 */
public class Helper {

    public static final String SP_NO = "1500000042";
    public static final String SP_KEY = "4XpQHaSzEPyuqFPugT2Lt87L8KUHgDUi";
    public static final String SP_PASSWORD = "8ki2E9AwKc";

    public static String createSign(Map<String, String> params) {
        String parameterValue = combineData(params);
        String data = parameterValue + "&key" + "=" + SP_KEY;
        return new MD5().md5Digest(data);
    }

    public static String combineData(Map<String, String> params) {
        StringBuilder data = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (null == entry.getValue()) {
                data.append(entry.getKey() + "=" + "&");
            } else {
                data.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }

        return data.substring(0, data.length() - 1);
    }

    public static String generateXml(Map<String, String> params) {
        Element root = DocumentHelper.createElement("response");
        Document document = DocumentHelper.createDocument(root);
        document.setXMLEncoding("GBK");

        //给根节点添加孩子节点
        for (Map.Entry<String, String> entry : params.entrySet()) {
            root.addElement(entry.getKey()).addText(entry.getValue());
        }

        return document.asXML();
    }

}
