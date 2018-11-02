package com.gdsp.dev.base.lang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 自定日期时间类，用于转化表示日期时间串等
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class DDateTime extends DDate implements Comparable<DDate> {

    /**
     * 序列化id
     */
    private static final long  serialVersionUID = 7280791980952901691L;
    /**
     * 日期格式化对象
     */
    public static final String DEFAULT_FORMAT   = "yyyy-MM-dd HH:mm:ss";

    /**
     * 自定义日期时间无参构造方法。
     */
    public DDateTime() {
        super();
    }

    /**
     * 通过毫秒数构造自定义日期时间对象
     * (以从1970年1月1日0时0分0秒到现在的毫秒数来构造日期)
     *
     * @param millis 毫秒数
     */
    public DDateTime(long millis) {
        super(millis);
    }

    /**
     * 通过日期对象构造自定义日期时间对象
     *
     * @param date 日期对象
     */
    public DDateTime(Date date) {
        super(date);
    }

    /**
     * 通过自定义日期对象构造自定义日期时间对象
     *
     * @param date 日期对象
     */
    public DDateTime(DDate date) {
        super(date.getMillis());
    }

    /**
     * 通过自定义日期对象和时间对象构造自定义日期时间对象
     *
     * @param date 日期对象
     */
    public DDateTime(DDate date, DTime time) {
        super(date.getMillis() + time.getMillis());
    }

    /**
     * 通过默认形式日期串构造自定义日期对象
     *
     * @param date 默认格式日期串
     */
    public DDateTime(String date) {
        super(date, DEFAULT_FORMAT);
    }

    /**
     * 通过日期串和指定的格式化串构造自定义日期时间对象
     *
     * @param date   符合格式串的日期时间对象
     * @param format 日期格式化串
     */
    public DDateTime(String date, String format) {
        super(date, format);
    }

    /**
     * 通过日期串和指定的格式化对象构造自定义日期时间对象
     *
     * @param date       符合格式化对象的日期时间对象
     * @param dateFormat 格式化对象
     */
    public DDateTime(String date, DateFormat dateFormat) {
        super(date, dateFormat);
    }

    /**
     * 创建默认格式化对象
     * @return 格式化对象
     */
    public static SimpleDateFormat defaultFormat() {
        return new SimpleDateFormat(DEFAULT_FORMAT);
    }

    /**
     * 根据对象构建自定义日期时间对象
     *
     * @param obj 对象
     * @return 自定义日期时间对象
     */
    public static DDateTime parseDDateTime(Object obj) {
        if (obj == null)
            return null;
        if (obj instanceof DDate)
            return new DDateTime(((DDate) obj).getMillis());
        if (obj instanceof Date)
            return new DDateTime((Date) obj);
        if (obj instanceof Integer) {
            return new DDateTime(((Integer) obj).longValue() * 1000);
        }
        if (obj instanceof Number) {
            return new DDateTime(((Number) obj).longValue());
        }
        if (NumberUtils.isNumber(obj.toString())) {
            return new DDateTime(NumberUtils.toLong(obj.toString()));
        }
        return new DDateTime(obj.toString());
    }

    /**
     * 用日期对象初始化对象
     */
    protected void init() {}

    /**
     * 取得自定义日期时间对象中的自定义日期对象
     *
     * @return 自定义日期对象
     */
    public DDate getDDate() {
        return new DDate(getMillis());
    }

    /**
     * 取得自定义日期时间对象中的自定义时间对象
     *
     * @return 自定义时间对象
     */
    public DTime getDTime() {
        return new DTime(getMillis());
    }

    /* 返回指定天数之后的自定义日期时间对象
     * @see com.fastlane.base.lang.CDate#getDateAfter(int)
     */
    public DDateTime getDateAfter(int days) {
        return new DDateTime(getMillis() + MILLIS_PERDAY * days);
    }

    /* 返回指定天数之前的自定义日期时间对象
     * @see com.fastlane.base.lang.CTime#getDateBefore(int)
     */
    public DDateTime getDateBefore(int days) {
        return getDateAfter(-days);
    }

    /**
     * 取得两个日期时间对象相差的小时数
     *
     * @param begin 开始日期时间
     * @param end   结束日期时间
     * @return 小时数
     */
    public static int getHoursBetween(DDateTime begin, DDateTime end) {
        return (int) (end.getMillis() / DTime.MILLIS_PERHOUR - begin.getMillis() / DTime.MILLIS_PERHOUR);
    }

    /**
     * 取得两个日期时间对象相差的分钟数
     *
     * @param begin 开始日期时间
     * @param end   结束日期时间
     * @return 分钟数
     */
    public static int getMinutesBetween(DDateTime begin, DDateTime end) {
        return (int) (end.getMillis() / DTime.MILLIS_PERMINUTE - begin.getMillis() / DTime.MILLIS_PERMINUTE);
    }

    /**
     * 取得两个日期时间对象相差的秒数
     *
     * @param begin 开始日期时间
     * @param end   结束日期时间
     * @return 秒数
     */
    public static int getSecondsBetween(DDateTime begin, DDateTime end) {
        return (int) (end.getMillis() / DTime.MILLIS_PERSECOND - begin.getMillis() / DTime.MILLIS_PERSECOND);
    }

    /**
     * 取得两个日期时间对象相差的毫秒数
     *
     * @param begin 开始日期时间
     * @param end   结束日期时间
     * @return 毫秒数
     */
    private static long getMilisBetween(DDateTime begin, DDateTime end) {
        if (begin == null || end == null)
            return 0;
        return end.getMillis() - begin.getMillis();
    }

    /**
     * 取得当前日期时间对象在指定的日期时间对象之后多少毫秒
     *
     * @param dateTime 要比较的自定义日期时间
     * @return 当前时间在给定日期时间
     */
    public long getMillisAfter(DDateTime dateTime) {
        return getMilisBetween(dateTime, this);
    }

    /**
     * 取得小时数
     *
     * @return 小时数
     */
    public int getHour() {
        return get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 取得小时数字符串
     *
     * @return 小时数字符串
     */
    public String getHOUR() {
        return formatNumner(getHour());
    }

    /**
     * 取得分钟数
     *
     * @return 分钟数
     */
    public int getMinute() {
        return get(Calendar.MINUTE);
    }

    /**
     * 取得分钟数字符串
     *
     * @return 分钟数字符串
     */
    public String getMINUTE() {
        return formatNumner(getMinute());
    }

    /**
     * 取得秒数
     *
     * @return 秒数
     */
    public int getSecond() {
        return get(Calendar.SECOND);
    }

    /**
     * 取得秒数字符串
     *
     * @return 秒数字符串
     */
    public String getSECOND() {
        return formatNumner(getSecond());
    }

    /**
     * 取得毫秒数
     *
     * @return 毫秒数
     */
    public int getMilliSecond() {
        return get(Calendar.MILLISECOND);
    }

    /**
     * 取得毫秒数串
     *
     * @return 毫秒数串
     */
    public String getMILLISECOND() {
        int ms = getMilliSecond();
        return Integer.toString(ms + 1000).substring(1);
    }

    /* 实现比较接口方法
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(DDate cdate) {
        return calendar.compareTo(cdate.calendar);
    }

    /**
     * 比较日期是否相等
     *
     * @param o 比较的对象
     * @return 布尔值
     */
    public boolean equals(Object o) {
        if ((o != null) && (o instanceof DDate)) {
            return this.getMillis() == ((DDate) o).getMillis();
        }
        return false;
    }

    /* 用默认的格式化串转化日期时间
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return toString(DEFAULT_FORMAT);
    }

    public int hashCode() {
        return super.hashCode();
    }
}