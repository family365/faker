package com.youzan.test.faker.messageConvert;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by libaixian on 16/8/5.
 */
@Component
public class MessageConverter {
    private Map<Class, Object> converterList = null;

    @PostConstruct
    public void init() {
        //TODO: 初始化所有converter对象, 对象必须实现了Converter接口
        converterList = new HashMap<>();
    }

    public String convert(String src, Map<String, Object> data) {
        String varReplacement = variableReplace(src, data);
        //TODO: 检查字符串中是否匹配 $\s+{ , 匹配则代表需要代用函数来生成字符串
        for (Map.Entry<Class, Object> entry : converterList.entrySet()) {
            try {
                Method convertMethod = entry.getKey().getMethod("convert", String.class);
                varReplacement = (String) convertMethod.invoke(entry.getValue(), varReplacement);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return varReplacement;
    }

    public Map<String, String> convert(Map<String, String> srcMap, Map<String, Object> data) {
        if (srcMap == null || srcMap.isEmpty()
                || data == null || data.isEmpty()) {
            return null;
        }

        Map<String, String> newMap = new HashMap<>();
        for (Map.Entry<String, String> entry : newMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String afterConvert = convert(value, data);
            newMap.put(key, afterConvert);
        }

        return newMap;
    }

    private String variableReplace(String src, Map<String, Object> data) {
        //TODO: 把字符串中包含${var}模式的字符串替换成data中相对应的值
        return null;
    }
}
