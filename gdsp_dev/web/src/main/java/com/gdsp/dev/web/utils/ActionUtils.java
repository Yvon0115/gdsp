/*
 * Copyright(c) FastLane Software 2012. All Rights Reserved.
 */
package com.gdsp.dev.web.utils;

import com.gdsp.dev.base.utils.UUIDUtils;

/**
 * 框架工具类
 * @author yaboocn
 * @version 1.0 2013-3-21
 * @since 1.6
 */
public final class ActionUtils {

    /**
     * 单例对象
     */
    private static ActionUtils instance = new ActionUtils();

    /**
     * 私有化构造方法
     */
    private ActionUtils() {}

    /**
     * 取得单例对象
     * @return 单例对象
     */
    public static ActionUtils getInstance() {
        return instance;
    }

    /**
     * 通过标志截取第一个标志之前的字符串
     * @param originString 需要截取的字符串
     * @param flag 截取标志
     * @return 截取出的串
     */
    public String cutFirstString(String originString, String flag) {
        int i = originString.indexOf(flag);
        if (i <= 0)
            return originString;
        return originString.substring(0, i);
    }

    /**
     * 通过标志截取第一标志之前的字符串
     * @param originString 需要截取的字符串
     * @param flag 截取标志
     * @param defaultValue 截不到时默认值
     * @return 截取出的串
     */
    public String cutFirstString(String originString, String flag, String defaultValue) {
        int i = originString.indexOf(flag);
        if (i <= 0)
            return defaultValue;
        return originString.substring(0, i);
    }

    /**
     * 通过标志截取最后一个标志之后的字符串
     * @param originString 需要截取的字符串
     * @param flag 截取标志
     * @return 截取出的串
     */
    public String cutLastString(String originString, String flag) {
        int i = originString.lastIndexOf(flag);
        if (i < 0 && i == originString.length() - flag.length())
            return null;
        return originString.substring(i + flag.length(), originString.length());
    }

    /**
     * 通过标志截取最后一个标志之后的字符串
     * @param originString 需要截取的字符串
     * @param flag 截取标志
     * @param defaultValue 截不到时默认值
     * @return 截取出的串
     */
    public String cutLastString(String originString, String flag, String defaultValue) {
        int i = originString.lastIndexOf(flag);
        if (i < 0 && i == originString.length() - flag.length())
            return defaultValue;
        return originString.substring(i + flag.length(), originString.length());
    }

    /**
     * 取得uuid
     * @return uuid
     */
    public String getUUID() {
        return UUIDUtils.getRandomUUID();
    }
}
