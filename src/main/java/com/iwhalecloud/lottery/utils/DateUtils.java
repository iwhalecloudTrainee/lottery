package com.iwhalecloud.lottery.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author W4i
 * @date 2020/6/10 14:56
 */
public class DateUtils {
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTimeStr() {
        String time = df.format(new Date());
        return time;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTimeStr(Date date) {
        String time = df.format(date);
        return time;
    }

    /**
     * 传入某个时间的String格式，用于和当前时间比较
     * 若小于当前时间，则返回false，反之true
     *
     * @param time
     * @return
     */
    public static boolean timeCompareWithNow(String time) {
        Date date = timeToDate(time);
        String t = getTimeStr();
        Date now = timeToDate(t);
        if (date.before(now)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 传入某个时间的String格式以及增加的时间（分钟为单位），用于和当前时间比较
     * 若传入的时间加上增加的时间小于当前时间，则返回false，反之true
     *
     * @param time
     * @param moreTime
     * @return
     */
    public static boolean timeCompareWithNow(String time, long moreTime) {
        moreTime = moreTime * 60 * 1000;
        Date date = timeToDate(time);
        String t = getTimeStr();
        Date now = timeToDate(t);
        date = new Date(date.getTime() + moreTime);
        if (date.before(now)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将String类型的时间转为Date
     *
     * @param time
     * @return
     */
    public static Date timeToDate(String time) {
        Date data1 = null;
        try {
            data1 = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        data1 = new Date(data1.getTime());
        return data1;
    }

    /**
     * 获取当前时间戳
     * 20201110
     *
     * @return
     */
    public static String getNowDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    /**
     * 秒->分钟
     *
     * @param sec
     * @return
     */
    public static Long secToMin(Long sec) {
        return sec / 60;
    }

    /**
     * 分钟->小时
     *
     * @param min
     * @return
     */
    public static Long minToHour(Long min) {
        return min / 60;
    }

    /**
     * 小时->天
     *
     * @param sec
     * @return
     */
    public static Long hourToDay(Long sec) {
        return sec / 24;
    }

    /**
     * 分钟->秒
     *
     * @param min
     * @return
     */
    public static Long minToSec(Long min) {
        return min * 60;
    }

    /**
     * 小时->分钟
     *
     * @param hour
     * @return
     */
    public static Long hourToMin(Long hour) {
        return hour * 60;
    }

    /**
     * 天->小时
     *
     * @param day
     * @return
     */
    public static Long dayToHour(Long day) {
        return day * 24;
    }

    /**
     * 日期减法，返回毫秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Long dateSubtraction(Date date1, Date date2) {
        Long diff = date1.getTime() - date2.getTime();
        return diff;
    }

    /**
     * 日期减法，返回秒
     * @param date1
     * @param date2
     * @return
     */
    public static Long dateSubtractionSec(Date date1, Date date2) {
        Long diffSeconds = dateSubtraction(date1,date2) / 1000 ;
        return diffSeconds;
    }
    /**
     * 日期减法，返回分钟
     * @param date1
     * @param date2
     * @return
     */
    public static Long dateSubtractionMin(Date date1, Date date2) {
        Long diffMinutes = dateSubtraction(date1,date2) / (60 * 1000);
        return diffMinutes;
    }
    /**
     * 日期减法，返回小时
     * @param date1
     * @param date2
     * @return
     */
    public static Long dateSubtractionHour(Date date1, Date date2) {
        Long diffHours = dateSubtraction(date1,date2) / (60 * 60 * 1000) ;
        return diffHours;
    }
    /**
     * 日期减法，返回天
     * @param date1
     * @param date2
     * @return
     */
    public static Long dateSubtractionDays(Date date1, Date date2) {
        Long diffDays = dateSubtraction(date1,date2) / (24 * 60 * 60 * 1000);
        return diffDays;
    }
}




