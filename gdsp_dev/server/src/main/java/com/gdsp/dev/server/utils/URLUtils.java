/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.server.utils;

/**
 * URL工具方法
 * @author yaboocn
 * @version 1.0 2014年5月5日
 * @since 1.7
 */
public final class URLUtils {

    /**分隔符*/
    private static final char SEPARATOR = '/';

    /**
     * 构造方法
     */
    private URLUtils() {}

    /**
     * 格式化url，格式化为前后都带分隔符形式
     * @param url url串
     * @return 格式化后的url串
     */
    public static String formatURL(String url) {
        return formatURL(url, true, true);
    }

    /**
     * 格式化url,格式化为前后不带分隔符的形式
     * @param url url串
     * @return 格式化后的url串
     */
    public static String formatCleanURL(String url) {
        return formatURL(url, false, false);
    }

    /**
     * 格式化url，格式化为指定形式
     * @param url 原有url
     * @param hasStart 开始是否带分隔符
     * @param hasEnd 结束是否带分隔符
     * @return 格式化后的url串
     */
    public static String formatURL(String url, boolean hasStart, boolean hasEnd) {
        StringBuilder buffer = new StringBuilder(url);
        int i = 0;
        while (i < buffer.length()) {
            if (buffer.charAt(i) == SEPARATOR) {
                i++;
                continue;
            }
            buffer.delete(0, i);
            break;
        }
        i = buffer.length() - 1;
        while (i >= 0) {
            if (buffer.charAt(i) == SEPARATOR) {
                i--;
                continue;
            }
            buffer.delete(i + 1, buffer.length());
            break;
        }
        if (buffer.length() == 0) {
            if (hasStart || hasEnd)
                return String.valueOf(SEPARATOR);
            else
                return "";
        }
        if (hasStart)
            buffer.insert(0, SEPARATOR);
        if (hasEnd)
            buffer.append(SEPARATOR);
        return buffer.toString();
    }

    /**
     * 取得url路径的起始路径名
     * @param url ur串
     * @return 起始路径名
     */
    public static String getStartPath(String url) {
        if (url == null)
            return null;
        int pos;
        int start = 0;
        if (url.startsWith("/")) {
            pos = url.indexOf("/", 1);
            start = 1;
        } else {
            pos = url.indexOf("/");
        }
        if (pos < 0) {
            pos = url.length();
        }
        return url.substring(start, pos);
    }
}
