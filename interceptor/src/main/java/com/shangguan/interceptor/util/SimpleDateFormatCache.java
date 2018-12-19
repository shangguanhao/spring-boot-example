package com.shangguan.interceptor.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class SimpleDateFormatCache {
    // ThreadLocal为每个使用该变量的线程提供独立的变量副本
    private static final ThreadLocal<Map<String, SimpleDateFormat>> cache = new ThreadLocal<Map<String, SimpleDateFormat>>();
    private static final String YMD = "yyyy-MM-dd";
    private static final String YMDZC = "yyyy/MM/dd";
    private static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    private static final String PIC_NAME = "yyyy-MM-dd HH-mm-ss";
    private static final String PUSHDATE = "yyyyMMddHHmmss";
    private static final String YMD_PURE = "yyyyMMdd";

    public static SimpleDateFormat getYmd() {
        return getFormat(YMD);
    }

    public static SimpleDateFormat getYmdZC() {
        return getFormat(YMDZC);
    }

    public static SimpleDateFormat getYmdhms() {
        return getFormat(YMDHMS);
    }

    public static SimpleDateFormat getPicFormat() {
        return getFormat(PIC_NAME);
    }

    public static SimpleDateFormat getPushDate() {
        return getFormat(PUSHDATE);
    }

    public static SimpleDateFormat getYmd_Pure() {
        return getFormat(YMD_PURE);
    }

    // 也可以使用apache的commons-lang包的DateUtils和DateFormatUtils类，这两个类的方法是线程安全的
    public static SimpleDateFormat getFormat(String ymd2) {
        // 得到当前线程的副本变量
        Map<String, SimpleDateFormat> map = cache.get();
        SimpleDateFormat sdf;
        if (map != null) {// 此线程里已经有了
            sdf = map.get(ymd2);// 判断这种格式的的有没有
            if (sdf == null) {
                sdf = new SimpleDateFormat(ymd2);
                map.put(ymd2, sdf);
            }
        } else {// 首次创建
            map = new HashMap<String, SimpleDateFormat>();
            sdf = new SimpleDateFormat(ymd2);
            map.put(ymd2, sdf);
            cache.set(map);
        }
        return sdf;
    }

}
