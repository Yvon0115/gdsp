package com.gdsp.dev.base.lang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 自定时间类，用于转化表示时间串等
 *
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class DTime extends DDate {

    /**
     * 序列化id
     */
    private static final long  serialVersionUID = 733417362967334525L;
    /**
     * 时间格式化对象
     */
    public static final String DEFAULT_FORMAT   = "HH:mm:ss";
    /*时间常量**/
    /**
     * 一小时毫秒数
     */
    public static final int    MILLIS_PERHOUR   = 60 * 60 * 1000;
    /**
     * 一分钟毫秒数
     */
    public static final int    MILLIS_PERMINUTE = 60 * 1000;
    /**
     * 一秒毫秒数
     */
    public static final int    MILLIS_PERSECOND = 1000;

    /**
     * 自定义时间无参构造方法。
     */
    public DTime() {
        super();
    }

    /**
     * 通过毫秒数构造自定义时间对象
     * (以从1970年1月1日0时0分0秒到现在的毫秒数来构造日期)
     *
     * @param millis 毫秒数
     */
    public DTime(long millis) {
        super(millis);
    }

    /**
     * 通过日期对象构造自定义时间对象
     *
     * @param date 日期对象
     */
    public DTime(Date date) {
        super(date);
    }

    /**
     * 通过默认形式时间串构造自定义时间对象
     *
     * @param time 默认格式日期串
     */
    public DTime(String time) {
        super(time, DEFAULT_FORMAT);
    }

    /**
     * 通过时间串和指定的格式化串构造自定义时间对象
     *
     * @param time   符合格式串的时间对象
     * @param format 时间格式化串
     */
    public DTime(String time, String format) {
        super(time, format);
    }

    /**
     * 通过时间串和指定的格式化对象构造自定义时间对象
     *
     * @param time       符合格式化对象的时间对象
     * @param timeFormat 格式化对象
     */
    public DTime(String time, DateFormat timeFormat) {
        super(time, timeFormat);
    }

    /**
     * 取得默认的格式化对象
     *
     * @return 格式化对象
     */
    public static SimpleDateFormat defaultFormat() {
        return new SimpleDateFormat(DEFAULT_FORMAT);
    }

    /**
     * 根据对象构建自定义时间对象
     *
     * @param obj 对象
     * @return 自定义时间对象
     */
    public static DTime parseDTime(Object obj) {
        if (obj == null)
            return null;
        if (obj instanceof DDate)
            return new DTime(((DDate) obj).getMillis());
        if (obj instanceof Date)
            return new DTime((Date) obj);
        if (obj instanceof Integer) {
            return new DTime(((Integer) obj).longValue() * 1000);
        }
        if (obj instanceof Number) {
            return new DTime(((Number) obj).longValue());
        }
        if (NumberUtils.isNumber(obj.toString())) {
            return new DTime(NumberUtils.toLong(obj.toString()));
        }
        return new DTime(obj.toString());
    }

    /**
     * 用时间对象初始化对象
     *
     */
    protected void init() {
        set(Calendar.YEAR, 1970);
        set(Calendar.MONTH, 1);
        set(Calendar.DATE, 1);
    }

    /**
     * 比较时间先后，对象时间在参数时间之后为true
     *
     * @param when 比较的时间对象
     * @return 布尔值
     */
    public boolean after(DTime when) {
        return compareTo(when) > 0;
    }

    /**
     * 比较时间先后，对象时间在参数时间之前为true
     *
     * @param when 比较的时间对象
     * @return 布尔值
     */
    public boolean before(DTime when) {
        return compareTo(when) < 0;
    }

    /**
     * 比较日期先后，对象日期在参数日期之后为true
     *
     * @param when 比较的日期的对象
     * @return 布尔值
     * @deprecated 继承自CDate，CTime中不再使用
     */
    public boolean after(DDate when) {
        return getMillis() > 0;
    }

    /**
     * 比较日期先后，对象日期在参数日期之前为true
     *
     * @param when 比较的日期的对象
     * @return 布尔值
     * @deprecated 继承自CDate，CTime中不再使用
     */
    public boolean before(DDate when) {
        return getMillis() < 0;
    }

    /**
     * 返回指定天数之后的自定义日期对象
     *
     * @param days 指定的天数
     * @return 自定义日期对象
     * @deprecated 继承自CDate，CTime中不再使用
     */
    public DDate getDateAfter(int days) {
        return null;
    }

    /**
     * 返回指定天数之前的自定义日期对象
     *
     * @param days 指定的天数
     * @return 自定义日期对象
     * @deprecated 继承自CDate，CTime中不再使用
     */
    public DDate getDateBefore(int days) {
        return getDateAfter(-days);
    }

    /**
     * 取得当前日期对象在指定的日期对象之后多少天
     *
     * @param when 要比较的自定义对象
     * @return 在比较的自定义对象之后多少天
     * @deprecated 继承自CDate，CTime中不再使用
     */
    public int getDaysAfter(DDate when) {
        return 0;
    }

    /**
     * 取得两个时间对象相差的小时数
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 相差的小时整数
     */
    public static int getHoursBetween(DTime begin, DTime end) {
        return (int) (getMilisBetween(begin, end) / MILLIS_PERHOUR);
    }

    /**
     * 取得两个时间对象相差的分钟数
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 相差的分钟整数
     */
    public static int getMinutesBetween(DTime begin, DTime end) {
        return (int) (getMilisBetween(begin, end) / MILLIS_PERMINUTE);
    }

    /**
     * 取得两个时间对象相差的秒数
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 相差的秒整数
     */
    public static int getSecondsBetween(DTime begin, DTime end) {
        return (int) (getMilisBetween(begin, end) / MILLIS_PERSECOND);
    }

    /**
     * 取得两个时间对象相差的毫秒数
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 相差的毫秒整数
     */
    public static long getMilisBetween(DTime begin, DTime end) {
        if (begin == null || end == null)
            return 0;
        return end.getMillis() - begin.getMillis();
    }

    /**
     * 取得当前时间对象在指定的时间对象之后多少毫秒
     *
     * @param time 要比较的自定义时间
     * @return 当前时间在给定时间
     */
    public long getMillisAfter(DTime time) {
        return getMilisBetween(time, this);
    }

    /* 用默认的格式化串转化日期
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return toString(defaultFormat());
    }
}
