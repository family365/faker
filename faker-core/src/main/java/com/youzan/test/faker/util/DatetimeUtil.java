package com.youzan.test.faker.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by libaixian on 16/8/1.
 */
public class DatetimeUtil {


    public static String getStrNowTime() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        return formatter.format(now);
    }

    public static String getStrTimeStamp() {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        return formatter.format(now);
    }

    public static String getStrTimeStamp(String format) {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        return formatter.format(now);
    }

    public static String getStrNowDate() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        return formatter.format(now);
    }
}
