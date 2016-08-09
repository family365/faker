package com.youzan.test.faker.util;

import com.youzan.test.faker.api.exception.FakerOperationException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by libaixian on 16/8/1.
 */
@Slf4j
public class ReflectiveUtil {
    public static Class<?> classForName(String className) {
        Class<?> classObj = null;
        try {
            classObj = Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException: {}", className);
            throw new FakerOperationException("ClassNotFoundException: " + className);
        }

        return classObj;
    }

    public static Constructor<?> getDefaultContructor(Class classObj) {
        Constructor[] constructorList = classObj.getConstructors();
        if (constructorList.length != 1) {
            throw new RuntimeException("Only constructor is expected");
        }

        int parameterCount = constructorList[0].getParameterCount();
        if (parameterCount != 0) {
            throw new FakerOperationException("Contructor should be default contructor");
        }

        return constructorList[0];
    }

    public static Object createInstance(Constructor constructor) {
        try {
            return constructor.newInstance(new Object[0]);
        } catch (Exception e) {
            throw new FakerOperationException(e.getMessage());
        }
    }
}
