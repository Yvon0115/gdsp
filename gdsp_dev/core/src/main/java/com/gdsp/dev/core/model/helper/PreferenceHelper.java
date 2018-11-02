package com.gdsp.dev.core.model.helper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;
import com.gdsp.dev.base.utils.data.TypeConvert;
import com.gdsp.dev.core.model.entity.PreferenceEntity;

/**
 * PreferenceEntity助手累
 *
 * @author paul.yang
 * @version 1.0 2015-9-10
 * @since 1.6
 */
public final class PreferenceHelper {

    /**
     * 取得所有配置参数
     * @param entitys    多个按顺序叠加的配置实体
     * @return 参数值
     */
    public static Map<String, Object> getAllPreferences(PreferenceEntity... entitys) {
        Map<String, Object> rs = new HashMap<>();
        if (entitys == null)
            return rs;
        for (PreferenceEntity entity : entitys) {
            rs.putAll(entity.getAllPreferences());
        }
        return rs;
    }

    /**
     * 取得配置指定配置参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 参数值
     */
    public static Object getPreference(String key, Object defaultValue, PreferenceEntity... entitys) {
        if (entitys == null)
            return defaultValue;
        Object rs;
        for (PreferenceEntity entity : entitys) {
            if (entity == null)
                continue;
            rs = entity.getPreference(key);
            if (rs != null)
                return rs;
        }
        return defaultValue;
    }

    /**
     * 取得配置指定配置参数
     *
     * @param key 参数名
     * @param entitys    多个按顺序叠加的配置实体
     * @return 参数值
     */
    public static Object getPreference(String key, PreferenceEntity... entitys) {
        return getPreference(key, null, entitys);
    }

    /**
     * 取得配置指定配置字符型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 字符型参数值
     */
    public static String getPrefString(String key, String defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return p.toString();
    }

    /**
     * 取得配置指定配置字符型参数
     *
     * @param key 参数名
     * @param entitys    多个按顺序叠加的配置实体
     * @return 字符型参数值
     */
    public static String getPrefString(String key, PreferenceEntity... entitys) {
        return getPrefString(key, null, entitys);
    }

    /**
     * 取得配置指定配置整型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 整型参数值
     */
    public static int getPrefInt(String key, int defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseInt(p);
    }

    /**
     * 取得配置指定配置整型参数
     *
     * @param key 参数名
     * @param entitys    多个按顺序叠加的配置实体
     * @return 整型参数值
     */
    public static int getPrefInt(String key, PreferenceEntity... entitys) {
        return getPrefInt(key, 0, entitys);
    }

    /**
     * 取得配置指定配置长整型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 长整型参数值
     */
    public static long getPrefLong(String key, long defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseLong(p);
    }

    /**
     * 取得配置指定配置长整型参数
     *
     * @param key 参数名
     * @param entitys    多个按顺序叠加的配置实体
     * @return 长整型参数值
     */
    public static long getPrefLong(String key, PreferenceEntity... entitys) {
        return getPrefLong(key, 0L, entitys);
    }

    /**
     * 取得配置指定配置浮点型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 浮点型参数值
     */
    public static float getPrefFloat(String key, float defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseFloat(p);
    }

    /**
     * 取得配置指定配置浮点型参数
     *
     * @param key 参数名
     * @param entitys    多个按顺序叠加的配置实体
     * @return 浮点型参数值
     */
    public static float getPrefFloat(String key, PreferenceEntity... entitys) {
        return getPrefFloat(key, 0f, entitys);
    }

    /**
     * 取得配置指定配置双浮点型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 双浮点型参数值
     */
    public static double getPrefDouble(String key, double defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDouble(p);
    }

    /**
     * 取得配置指定配置双浮点型参数
     *
     * @param key 参数名
     * @param entitys    多个按顺序叠加的配置实体
     * @return 双浮点型参数值
     */
    public static double getPrefDouble(String key, PreferenceEntity... entitys) {
        return getPrefDouble(key, 0d, entitys);
    }

    /**
     * 取得配置指定配置数值型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 数值型参数值
     */
    public static BigDecimal getPrefBigDecimal(String key, BigDecimal defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseBigDecimal(p);
    }

    /**
     * 取得配置指定配置数值型参数
     *
     * @param key 参数名
     * @param entitys    多个按顺序叠加的配置实体
     * @return 数值型参数值
     */
    public static BigDecimal getPrefBigDecimal(String key, PreferenceEntity... entitys) {
        return getPrefBigDecimal(key, null, entitys);
    }

    /**
     * 取得配置指定配置布尔参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 布尔参数值
     */
    public static boolean getPrefBoolean(String key, boolean defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseBoolean(p);
    }

    /**
     * 取得配置指定配置布尔参数
     *
     * @param key 参数名
     * @param entitys    多个按顺序叠加的配置实体
     * @return 布尔参数值
     */
    public static boolean getPrefBoolean(String key, PreferenceEntity... entitys) {
        return getPrefBoolean(key, false, entitys);
    }

    /**
     * 取得配置指定配置自定义布尔参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 自定义布尔参数值
     */
    public static DBoolean getPrefDBoolean(String key, DBoolean defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDBoolean(p);
    }

    /**
     * 取得配置指定配置自定义布尔参数
     *
     * @param key 参数名
     * @param entitys    多个按顺序叠加的配置实体
     * @return 自定义布尔参数值
     */
    public static DBoolean getPrefDBoolean(String key, PreferenceEntity... entitys) {
        return getPrefDBoolean(key, null, entitys);
    }

    /**
     * 取得配置指定配置日期参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 日期参数值
     */
    public static Date getPrefDate(String key, Date defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDate(p);
    }

    /**
     * 取得配置指定配置日期参数
     *
     * @param key          参数名
     * @param entitys      多个按顺序叠加的配置实体
     * @return 日期参数值
     */
    public static Date getPrefDate(String key, PreferenceEntity... entitys) {
        return getPrefDate(key, null, entitys);
    }

    /**
     * 取得配置指定配置自定义日期参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 自定义日期参数值
     */
    public static DDate getPrefDDate(String key, DDate defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDDate(p);
    }

    /**
     * 取得配置指定配置自定义日期参数
     *
     * @param key          参数名
     * @param entitys      多个按顺序叠加的配置实体
     * @return 自定义日期参数值
     */
    public static DDate getPrefDDate(String key, PreferenceEntity... entitys) {
        return getPrefDDate(key, null, entitys);
    }

    /**
     * 取得配置指定配置自定义日期时间参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 自定义日期时间参数值
     */
    public static DDateTime getPrefDDateTime(String key, DDateTime defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDDateTime(p);
    }

    /**
     * 取得配置指定配置自定义日期时间参数
     *
     * @param key          参数名
     * @param entitys      多个按顺序叠加的配置实体
     * @return 自定义日期时间参数值
     */
    public static DDateTime getPrefDDateTime(String key, PreferenceEntity... entitys) {
        return getPrefDDateTime(key, null, entitys);
    }

    /**
     * 取得配置指定配置自定义时间参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @param entitys      多个按顺序叠加的配置实体
     * @return 自定义时间参数值
     */
    public static DTime getPrefDTime(String key, DTime defaultValue, PreferenceEntity... entitys) {
        Object p = getPreference(key, entitys);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDTime(p);
    }

    /**
     * 取得配置指定配置自定义时间参数
     *
     * @param key          参数名
     * @param entitys      多个按顺序叠加的配置实体
     * @return 自定义时间参数值
     */
    public static DTime getPrefDTime(String key, PreferenceEntity... entitys) {
        return getPrefDTime(key, null, entitys);
    }
}
