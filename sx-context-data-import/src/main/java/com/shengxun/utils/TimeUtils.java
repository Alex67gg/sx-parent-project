package com.shengxun.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ChenWei
 * on 2018/7/5 15:02.
 */
public class TimeUtils {
    /**
     * 日期格式yyyy-MM-dd字符串常量
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT_NON_ = "yyyy-MM-dd-HH-mm";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATETIME_FORMAT_NON_);

    /**
     * Date 类型 转换 String 格式 2010-8-8
     *
     * @param date 时间
     * @return String 类型的时间
     */
    public static String date2String(Date date) {
        if (date == null) {
            return "";
        }
        return TimeUtils.dateFormat.format(date);
    }

    /**
     * Date 类型 转换 String 格式 2010-08-08-10-11
     *
     * @param date 时间
     * @return String 类型的时间
     */
    public static String dateTime2String(Date date) {
        if (date == null) {
            return "";
        }
        return TimeUtils.dateTimeFormat.format(date);
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    public static String getNowDate() {
        Date date = new Date();
        return date2String(date);
    }

}
