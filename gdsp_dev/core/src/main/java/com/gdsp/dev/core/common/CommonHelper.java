package com.gdsp.dev.core.common;

import java.sql.Types;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * 公共部分的助手类，主要是取的一些公共配置
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public final class CommonHelper {

    /**
     * 本地化语言配置键值
     */
    private final static String CONFIG_KEY_LOCALE_LANGUAGE = "common.locale.language";
    /**
     * 本地化国家配置键值
     */
    private final static String CONFIG_KEY_LOCALE_COUNTRY  = "common.locale.country";
    /**
     * 默认主题名称键值
     */
    private final static String CONFIG_KEY_THEME           = "common.theme";
    /**
     * 默认时间精度键值，如果为0时按类型匹配
     */
    private final static String CONFIG_KEY_TIMESCALE       = "common.timescale";
    /**
     * 默认自定义日期时间字段类型配置键值，默认为BIGINT
     */
    private final static String CONFIG_KEY_DDATEJDBCTYPE   = "common.ddate_jdbctype";
    /**
     * 默认的区域对象
     */
    private static Locale       defaultLocale              = null;
    /**
     * 默认的主题
     */
    private static String       defaultTheme               = null;
    /**
     * 默认时间精度
     */
    private static int          timeScale                  = -1;
    /**
     * 自定义日期类型对应的jdbc类型
     */
    private static int          ddateJdbcType              = -99999;

    /**
     * 取得默认的区域化对象
     * @return 区域化对象
     */
    public static Locale getDefaultLocale() {
        if (defaultLocale == null)
            defaultLocale = getLocale(AppConfig.getInstance().getString(CONFIG_KEY_LOCALE_LANGUAGE, "zh"), AppConfig.getInstance().getString(CONFIG_KEY_LOCALE_COUNTRY, "CN"));
        return defaultLocale;
    }

    /**
     * 取得区域对象
     * @param language 语言
     * @param country 国家
     * @return 区域对象
     */
    public static Locale getLocale(String language, String country) {
        return new Locale(language, country);
    }

    /**
     * 根据本地化标识如zh_cn
     * @param locale 本地化标识
     * @return 本地化对象
     */
    public static Locale getLocale(String locale) {
        if (StringUtils.isEmpty(locale)) {
            return null;
        }
        String[] r = locale.split("_");
        String lang = r[0];
        String country = null;
        if (r.length > 1) {
            country = r[1];
        }
        return getLocale(lang, country);
    }

    /**
     * 取得默认主题
     * @return 主题名称
     */
    public static String getDefaultTheme() {
        if (defaultTheme == null)
            defaultTheme = AppConfig.getInstance().getString(CONFIG_KEY_THEME, "default");
        return defaultTheme;
    }

    /**
     * 取得默认时间精度
     * @return 默认时间精度
     */
    public static int getTimeScale() {
        if (timeScale < 0)
            timeScale = AppConfig.getInstance().getInt(CONFIG_KEY_TIMESCALE, 1000);
        return timeScale;
    }

    /**
     * 取得默认的自定义日期类型对应的数据库字段类型
     * @return 数据库字段类型
     */
    public static int getDDateJdbcType() {
        if (ddateJdbcType == -99999)
            ddateJdbcType = AppConfig.getInstance().getInt(CONFIG_KEY_DDATEJDBCTYPE, Types.BIGINT);
        return ddateJdbcType;
    }
}
