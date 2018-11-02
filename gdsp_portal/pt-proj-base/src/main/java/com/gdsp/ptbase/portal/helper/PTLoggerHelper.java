package com.gdsp.ptbase.portal.helper;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.gdsp.platform.common.utils.DateStyle;
import com.gdsp.platform.common.utils.DateUtils;

/**
 * 
* @ClassName: PTLoggerHelper
* (日志帮助类)
* @author hongwei.xu
* @date 2015年9月25日 下午4:47:53
*
 */
public class PTLoggerHelper {

    private final static Object START_TAG = "___start";
    private final static Object END_TAG   = "___end";
    private final static String HEAD      = "gdsp-pt-logger:";
    public Logger               logger    = null;

    public PTLoggerHelper(Class<?> __class) {
        logger = Logger.getLogger(__class);
    }

    /**
     * 
    * @Title: debugMethodStart
    * (进入方法日志)
    * @param @param msg    设定文件
    * @return void    返回类型
    *      */
    public void debugMethodStart(String msg) {
        StackTraceElement[] se = new Throwable().getStackTrace();
        Object _methodName = new Exception().getStackTrace()[1].getMethodName();
        Object className = se.getClass().getName();
        logger.debug(MessageFormat.format(HEAD + "[{0}][{1}]{2}.{3}---->{4}",
                DateUtils.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS),
                msg, className, _methodName, START_TAG));
    }

    /**
     * 
    * @Title: debugMethodEnd
    * (离开方法日志)
    * @param @param msg    设定文件
    * @return void    返回类型
    *      */
    public void debugMethodEnd(String msg) {
        StackTraceElement[] se = new Throwable().getStackTrace();
        Object _methodName = new Exception().getStackTrace()[1].getMethodName();
        Object className = se.getClass().getName();
        logger.debug(MessageFormat.format(HEAD + "[{0}][{1}]{2}.{3}---->{4}",
                DateUtils.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS),
                msg, className, _methodName, END_TAG));
    }

    /**
     * 
    * @Title: debugWithTimestamps
    * (时间戳日志)
    * @param @param msg    设定文件
    * @return void    返回类型
    *      */
    public void debugWithTimestamps(String msg) {
        logger.debug(MessageFormat.format(HEAD + "[{0}]---->{1}", DateUtils.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS), msg));
    }

    public void debug(String message) {
        this.logger.debug(message);
    }

    public void debug(Object message, Throwable t) {
        this.logger.debug(message, t);
    }

    public void error(Object message) {
        this.logger.error(message);
    }

    public void error(Object message, Throwable t) {
        this.logger.error(message, t);
    }
}
