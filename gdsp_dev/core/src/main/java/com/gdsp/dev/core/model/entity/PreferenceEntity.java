package com.gdsp.dev.core.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;
import com.gdsp.dev.base.utils.data.TypeConvert;
import com.gdsp.dev.core.data.json.Json2ObjectMapper;

/**
 * 带有基于json的单字段配置实体抽象累
 *
 * @author paul.yang
 * @version 1.0 2015-9-7
 * @since 1.6
 */
public abstract class PreferenceEntity extends AuditableEntity {

    /**
     * 
     */
    private static final long     serialVersionUID = 1L;
    /**
     * 日志变量
     */
    protected static final Logger                logger           = LoggerFactory.getLogger(PreferenceEntity.class);
    /**
     * 配置信息
     */
    protected Map<String, Object> _preferences;

    /**
     * 取得基于json的配置串
     *
     * @return json串
     */
    public abstract String getPreference();

    /**
     * 设置基于json的配置串
     *
     * @param preference json串
     */
    public abstract void setPreference(String preference);

    /**
     * 重置配置参数缓存
     */
    public void resetPreference() {
        _preferences = null;
    }

    /**
     * 取得所有配置参数
     * @return 参数值
     */
  
	@SuppressWarnings("unchecked")
	public Map<String, Object> getAllPreferences() {
        if (_preferences != null) {
            return _preferences;
        }
        String origin = getPreference();
        if (StringUtils.isNotEmpty(origin)) {
            try {
                _preferences = Json2ObjectMapper.getInstance().readValue(origin,Map.class);
            } catch (Exception e) {
                logger.error("parsing json to map occur error in preference", e);
                _preferences = null;
            }
        }
        if (_preferences == null)
            _preferences = new HashMap<>();
        return _preferences;
    }

    /**
     * 设置指定参数值
     *
     * @param key   参数名
     * @param value 参数值
     */
    public void setPreference(String key, Object value) {
        Map<String, Object> prefs = getAllPreferences();
        prefs.put(key, value);
        if (prefs.size() > 0) {
            try {
                String result = Json2ObjectMapper.getInstance().writeValueAsString(_preferences);
                setPreference(result);
            } catch (JsonProcessingException e) {
                logger.error("parsing json to map occur error in preference", e);
            }
        }
    }

    /**
     * 取得配置指定配置参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 参数值
     */
    public Object getPreference(String key, Object defaultValue) {
        Object p = getAllPreferences().get(key);
        if (p == null)
            return defaultValue;
        return p;
    }

    /**
     * 取得配置指定配置参数
     *
     * @param key 参数名
     * @return 参数值
     */
    public Object getPreference(String key) {
        return getPreference(key, null);
    }

    /**
     * 取得配置指定配置字符型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 字符型参数值
     */
    public String getPrefString(String key, String defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return p.toString();
    }

    /**
     * 取得配置指定配置字符型参数
     *
     * @param key 参数名
     * @return 字符型参数值
     */
    public String getPrefString(String key) {
        return getPrefString(key, null);
    }

    /**
     * 取得配置指定配置整型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 整型参数值
     */
    public int getPrefInt(String key, int defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseInt(p);
    }

    /**
     * 取得配置指定配置整型参数
     *
     * @param key 参数名
     * @return 整型参数值
     */
    public int getPrefInt(String key) {
        return getPrefInt(key, 0);
    }

    /**
     * 取得配置指定配置长整型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 长整型参数值
     */
    public long getPrefLong(String key, long defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseLong(p);
    }

    /**
     * 取得配置指定配置长整型参数
     *
     * @param key 参数名
     * @return 长整型参数值
     */
    public long getPrefLong(String key) {
        return getPrefLong(key, 0L);
    }

    /**
     * 取得配置指定配置浮点型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 浮点型参数值
     */
    public float getPrefFloat(String key, float defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseFloat(p);
    }

    /**
     * 取得配置指定配置浮点型参数
     *
     * @param key 参数名
     * @return 浮点型参数值
     */
    public float getPrefFloat(String key) {
        return getPrefFloat(key, 0f);
    }

    /**
     * 取得配置指定配置双浮点型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 双浮点型参数值
     */
    public double getPrefDouble(String key, double defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDouble(p);
    }

    /**
     * 取得配置指定配置双浮点型参数
     *
     * @param key 参数名
     * @return 双浮点型参数值
     */
    public double getPrefDouble(String key) {
        return getPrefDouble(key, 0d);
    }

    /**
     * 取得配置指定配置数值型参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 数值型参数值
     */
    public BigDecimal getPrefBigDecimal(String key, BigDecimal defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseBigDecimal(p);
    }

    /**
     * 取得配置指定配置数值型参数
     *
     * @param key 参数名
     * @return 数值型参数值
     */
    public BigDecimal getPrefBigDecimal(String key) {
        return getPrefBigDecimal(key, null);
    }

    /**
     * 取得配置指定配置布尔参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 布尔参数值
     */
    public boolean getPrefBoolean(String key, boolean defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseBoolean(p);
    }

    /**
     * 取得配置指定配置布尔参数
     *
     * @param key 参数名
     * @return 布尔参数值
     */
    public boolean getPrefBoolean(String key) {
        return getPrefBoolean(key, false);
    }

    /**
     * 取得配置指定配置自定义布尔参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 自定义布尔参数值
     */
    public DBoolean getPrefDBoolean(String key, DBoolean defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDBoolean(p);
    }

    /**
     * 取得配置指定配置自定义布尔参数
     *
     * @param key 参数名
     * @return 自定义布尔参数值
     */
    public DBoolean getPrefDBoolean(String key) {
        return getPrefDBoolean(key, null);
    }

    /**
     * 取得配置指定配置日期参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 日期参数值
     */
    public Date getPrefDate(String key, Date defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDate(p);
    }

    /**
     * 取得配置指定配置日期参数
     *
     * @param key          参数名
     * @return 日期参数值
     */
    public Date getPrefDate(String key) {
        return getPrefDate(key, null);
    }

    /**
     * 取得配置指定配置自定义日期参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 自定义日期参数值
     */
    public DDate getPrefDDate(String key, DDate defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDDate(p);
    }

    /**
     * 取得配置指定配置自定义日期参数
     *
     * @param key          参数名
     * @return 自定义日期参数值
     */
    public DDate getPrefDDate(String key) {
        return getPrefDDate(key, null);
    }

    /**
     * 取得配置指定配置自定义日期时间参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 自定义日期时间参数值
     */
    public DDateTime getPrefDDateTime(String key, DDateTime defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDDateTime(p);
    }

    /**
     * 取得配置指定配置自定义日期时间参数
     *
     * @param key          参数名
     * @return 自定义日期时间参数值
     */
    public DDateTime getPrefDDateTime(String key) {
        return getPrefDDateTime(key, null);
    }

    /**
     * 取得配置指定配置自定义时间参数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 自定义时间参数值
     */
    public DTime getPrefDTime(String key, DTime defaultValue) {
        Object p = getPreference(key);
        if (p == null)
            return defaultValue;
        return TypeConvert.parseDTime(p);
    }

    /**
     * 取得配置指定配置自定义时间参数
     *
     * @param key          参数名
     * @return 自定义时间参数值
     */
    public DTime getPrefDTime(String key) {
        return getPrefDTime(key, null);
    }
}
