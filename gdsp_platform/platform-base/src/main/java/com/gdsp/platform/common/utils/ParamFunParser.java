package com.gdsp.platform.common.utils;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.platform.config.global.model.DefDocVO;

/**
 * 
* @ClassName: ParamDefaultvalueParser
* (自定义函数解析工具类)
* @author hongwei.xu
* @date 2015年8月13日 下午3:46:19
*
 */
public class ParamFunParser {

    /**
     * 
    * @Title: TODAY
    * (取得当日（yyyy-MM-DD）)
    * @param @return    设定文件
    * @return String    返回类型
    *      */
    public String TODAY() {
        return DateUtils.getDate(new Date());
    }

    /**
     * 
    * @Title: BFDAY
    * (取得前一日函数（yyyy-MM-dd）)
    * @param @return    设定文件
    * @return String    返回类型
    *      */
    public String BFDAY() {
        return DateUtils.DateToString(DateUtils.addDay(new Date(), -1), DateStyle.YYYY_MM_DD);
    }

    /**
     * 
    * @Title: AFDAY
    * (取得后一日函数（yyyy-MM-dd）)
    * @param @return    设定文件
    * @return String    返回类型
    *      */
    public String AFDAY() {
        return DateUtils.DateToString(DateUtils.addDay(new Date(), 1), DateStyle.YYYY_MM_DD);
    }

    /**
     * 
    * @Title: MONTH
    * (当月（格式：yyyy-MM）)
    * @param @return    设定文件
    * @return String    返回类型
    *      */
    public String MONTH() {
        return DateUtils.DateToString(new Date(), DateStyle.MM);
    }

    /**
     * 
    * @Title: BFMONTH
    * (前一月（格式：yyyy-MM）)
    * @param @return    设定文件
    * @return String    返回类型
    *      */
    public String BFMONTH() {
        return DateUtils.DateToString(DateUtils.addMonth(new Date(), -1), DateStyle.MM);
    }

    /**
     * 
    * @Title: MONTH_FD
    * (当月第一天（格式：yyyy-MM-dd）)
    * @param @return    设定文件
    * @return String    返回类型
    *      */
    public String MONTH_FD() {
        return DateUtils.DateToString(new Date(), DateStyle.MM);
    }

    /**
     * 
    * @Title: YEAR
    * (当年（yyyy）)
    * @param @return    设定文件
    * @return String    返回类型
    *      */
    public String YEAR() {
        return DateUtils.DateToString(new Date(), DateStyle.YYYY);
    }

    /**
     * 
    * @Title: BFYEAR
    * (前一年（yyyy）)
    * @param @return    设定文件
    * @return String    返回类型
    *      */
    public String BFYEAR() {
        return DateUtils.DateToString(DateUtils.addYear(new Date(), -1), DateStyle.YYYY);
    }

    /**
     * 
    * @Title: parserFun_years
    * (去年份函数)
    * @param @param dataSource    设定文件
    * @return void    返回类型
    *      */
    public List<DefDocVO> parserFun_years(String params) {
        int counts = 10;
        if (!StringUtils.isEmpty(params)) {
            counts = Integer.parseInt(params);
        }
        return DateUtils.getBefYears(new Date(), counts);
    }

    /**
     * 
    * @Title: parserFun_month
    * (取月份函数)
    * @param @param dataSource
    * @param @return    设定文件
    * @return List<DefDocVO>    返回类型
    *      */
    public List<DefDocVO> parserFun_month() {
        return DateUtils.getMonths();
    }

    /**
     * 
    * @Title: FD_currentMonth
    * (当前月第一天)
    * @return    参数说明
    * @return String
    *      */
    public String FD_currentMonth() {
        return DateUtils.getFirstDayOfCurrentMonth();
    }

    /**
     * 
    * @Title: LD_currentMonth
    * (当月最后一天)
    * @return    参数说明
    * @return String
    *      */
    public String LD_currentMonth() {
        return DateUtils.getLastDayOfCurrentMonth();
    }

    /**
     * 
    * @Title: FD_preMonth
    * (上月第一天)
    * @return    参数说明
    * @return String
    *      */
    public String FD_preMonth() {
        return DateUtils.getFirstDayOfPreMonth();
    }

    /**
     * 
    * @Title: LD_preMonth
    * (上月最后一天)
    * @return    参数说明
    * @return String
    *      */
    public String LD_preMonth() {
        return DateUtils.getLastDayOfPreMonth();
    }

    /**
     * 
    * @Title: FD_currentYear
    * (当年第一天)
    * @return    参数说明
    * @return String
    *      */
    public String FD_currentYear() {
        return DateUtils.getFirstDayOfCurrentYear();
    }

    /**
     * 
    * @Title: LD_currentYear
    * (当年最后一天)
    * @return    参数说明
    * @return String
    *      */
    public String LD_currentYear() {
        return DateUtils.getLastDayOfCurrentYear();
    }

    /**
     * 
    * @Title: FD_preYear
    * (上一年第一天)
    * @return    参数说明
    * @return String
    *      */
    public String FD_preYear() {
        return DateUtils.getFirstDayOfPreYear();
    }

    /**
     * 
    * @Title: LD_preYear
    * (上一年最后一天)
    * @return    参数说明
    * @return String
    *      */
    public String LD_preYear() {
        return DateUtils.getLastDayOfPreYear();
    }

    /**
     * 
    * @Title: py_date
    * (偏移日期)
    * @param days
    * @return    参数说明
    * @return String
    *      */
    public String py_date(String days) {
        int daycnts = 0;
        if (!StringUtils.isEmpty(days)) {
            daycnts = Integer.parseInt(days);
        }
        return DateUtils.DateToString(DateUtils.addDay(new Date(), daycnts), DateStyle.YYYY_MM_DD);
    }

    public List<DefDocVO> future_YEAR(String params) {
        int counts = 10;
        if (!StringUtils.isEmpty(params)) {
            counts = Integer.parseInt(params);
        }
        return DateUtils.getFurYears(new Date(), counts);
    }

    /**
     * 
    * @Title: main
    * (这里用一句话描述这个方法的作用)
    * @param args    参数说明
    * @return void
    *      */
    public static void main(String[] args) {
        DateUtils.DateToString(new Date(), DateStyle.YYYY_MM_DD);
        System.out.println(DateUtils.addDay(new Date(), -1));
        System.out.println(DateUtils.addDay(new Date(), -1));
    }
}
