package com.gdsp.dev.base.utils.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import net.sf.cglib.beans.BeanMap;

/**
 * 对AppConfig的map包装
 * @author paul.yang
 * @version 1.0 2014年11月5日
 * @since 1.6
 */
public class ConfigMap extends HashMap<String, Object> {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 3500655517793930527L;
    private Object            defaultValue;

    /**
     * 构造方法
     * @param profix 前缀
     * @param defaultValue 默认值
     * @param config 配置内容
     */
    public ConfigMap(String profix, Object defaultValue, Map<Object, Object> config) {
        super();
        this.defaultValue = defaultValue;
        Set<Object> keys = config.keySet();
        int len = profix.length();
        for (Object o : keys) {
            String key = o.toString();
            if (!key.startsWith(profix))
                continue;
            Object v = config.get(o);
            if (StringUtils.isBlank(v.toString()))
                continue;
            put(key.substring(len), v);
        }
    }

    @SuppressWarnings("unchecked")
    public ConfigMap(String profix, Object defaultValue, Object config) {
        this(profix, defaultValue, BeanMap.create(config));
    }

    @Override
    public Object get(Object key) {
        Object o = super.get(key);
        if (o != null)
            return o;
        return this.defaultValue;
    }
}
