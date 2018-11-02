package com.gdsp.platform.schedule.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.Trigger.TriggerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.platform.schedule.model.JobTriggerVO;

public class FormatUtils {
	private static final Logger logger = LoggerFactory.getLogger(FormatUtils.class);
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DAY_FORMAT_PATTERN  = "yyyy-MM-dd";
    public static final String TIME_FORMAT_PATTERN = "HH:mm:ss";

    public static SimpleDateFormat dateFormatter() {
        return new SimpleDateFormat(DATE_FORMAT_PATTERN);
    }

    public static SimpleDateFormat dayFormatter() {
        return new SimpleDateFormat(DAY_FORMAT_PATTERN);
    }

    public static SimpleDateFormat timeFormatter() {
        return new SimpleDateFormat(TIME_FORMAT_PATTERN);
    }

    /**
     *
     * @param date
     * @return
     */
    public static String getDateAsString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        return dateFormatter().format(date);
    }

    /**
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date parseStringToDate(String dateStr) throws ParseException {
        if (dateStr == null) {
            return null;
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        return dateFormatter().parse(dateStr);
    }

    public static Date parseStringToDate(String dateStr, String format) throws ParseException {
        if (dateStr == null) {
            return null;
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
        return dateFormatter.parse(dateStr);
    }

    /**
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date parseStringToTime(String dateStr) throws ParseException {
        if (dateStr == null) {
            return null;
        }
        return timeFormatter().parse(dateStr);
    }

    /**
     *
     * @param state
     * @return
     */
    public static String getTriggerState(JobTriggerVO triggerVO,TriggerState state) {
        if (TriggerState.NONE.equals(state)) {
            return "";
        }
        if (TriggerState.COMPLETE.equals(state)) {
            return "完成";
        }
        if (TriggerState.PAUSED.equals(state)) {
        	triggerVO.setJobState(ScheduleConst.TRIGGER_STATE_PAUSED);
            return "挂起";
        }
        if (TriggerState.ERROR.equals(state)) {
            return "错误";
        }
        if (TriggerState.BLOCKED.equals(state)) {
            return "等待";
        }
        triggerVO.setJobState(ScheduleConst.TRIGGER_STATE_EXECUTING);
        return "执行中";
    }

    /**
     *
     * @param date
     * @return
     */
    public static String getDayAsString(Date date) {
        if (date == null) {
            return "";
        }
        return dayFormatter().format(date);
    }

    /**
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date parseStringToDay(String dateStr) throws ParseException {
        if (dateStr == null) {
            return null;
        }
        return dayFormatter().parse(dateStr);
    }

    /**
     * 增加日期的天数。失败返回null。
     * @param date 日期字符串
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期字符串
     */
    public static String addDay(String date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     * @param date 日期
     * @param monthAmount 增加数量。可为负数
     * @return 增加月份后的日期字符串
     */
    public static String addMonth(String date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     * @param date 日期字符串
     * @param dateType 类型
     * @param amount 数值
     * @return 计算后日期字符串
     */
    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        Date myDate;
        try {
            myDate = parseStringToDay(date);
            myDate = addInteger(myDate, dateType, amount);
            dateString = getDayAsString(myDate);
        } catch (ParseException e) {
        	logger.error(e.getMessage(),e);
        }
        return dateString;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     * @param date 日期
     * @param dateType 类型
     * @param amount 数值
     * @return 计算后日期
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }
}