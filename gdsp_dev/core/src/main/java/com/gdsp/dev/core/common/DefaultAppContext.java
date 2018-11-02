package com.gdsp.dev.core.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的上下文类实现
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class DefaultAppContext extends AppContext {

    /**
     * 序列id
     */
    private static final long                serialVersionUID = 1260173666135525539L;
    /**范围键值前缀*/
    private final static String              PROFIX           = "$";
    /**参数映射对象保存参数集合*/
    private Map<String, String[]>            parameters;
    /**属性映射对象保存属性集合*/
    private Map<String, Map<String, Object>> attributes;

    /**
     * 构造方法
     * @since 1.0
     */
    public DefaultAppContext() {
        parameters = new HashMap<String, String[]>();
        attributes = new HashMap<String, Map<String, Object>>();
    }

    /**
     * 根据范围取得对应的Map
     * @param scope 上下文范围
     * @param isCreate 如果不存在是否创建
     * @return 上下文Map
     */
    protected Map<String, Object> getScopeMap(int scope, boolean isCreate) {
        Map<String, Object> scopeMap = attributes.get(PROFIX + scope);
        if (isCreate && scopeMap == null) {
            scopeMap = new HashMap<String, Object>();
            attributes.put(PROFIX + scope, scopeMap);
        }
        return scopeMap;
    }

    public Object getApplication() {
        return getScopeMap(APPLICATION, true);
    }

    @Override
    public Object getSession() {
        return getScopeMap(SESSION, true);
    }

    @Override
    public Object getViewContext() {
        return getScopeMap(VIEW, true);
    }

    @Override
    public Object getRequest() {
        return getScopeMap(REQUEST, true);
    }

    public Object getAttribute(int scope, String name) {
        Map<String, Object> scopeMap = getScopeMap(scope, false);
        if (scopeMap == null)
            return null;
        return scopeMap.get(name);
    }

    public String getParameter(String name) {
        String value = null;
        Object parameter = parameters.get(name);
        if (parameter == null)
            return null;
        //根据是否是数组类型取得参数
        Class<?> cl = parameter.getClass();
        if (cl.isArray() && cl.getComponentType().equals(java.lang.String.class)) {
            String[] values = (String[]) parameter;
            if (values.length > 0) {
                value = values[0];
            }
        } else {
            value = String.valueOf(parameter);
        }
        return value;
    }

    public String[] getParameters(String name) {
        String value[] = null;
        Object parameter = parameters.get(name);
        if (parameter == null)
            return null;
        //根据参数值类型构造或转换参数数组
        Class<?> clazz = parameter.getClass();
        if (clazz.isArray() && clazz.getComponentType().equals(java.lang.String.class)) {
            value = (String[]) parameter;
        } else {
            value = new String[] { String.valueOf(parameter) };
        }
        return value;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameters;
    }

    public void removeAttribute(int scope, String name) {
        Map<String, Object> scopeMap = getScopeMap(scope, false);
        if (scopeMap == null)
            return;
        scopeMap.remove(name);
    }

    public void setAttribute(int scope, String name, Object value) {
        getScopeMap(scope, true).put(name, value);
    }

    public boolean isEmptyInAttributes(int scope) {
        Map<String, Object> scopeMap = getScopeMap(scope, false);
        if (scopeMap == null) {
            return true;
        }
        return scopeMap.isEmpty();
    }

    public boolean isEmpty() {
        for (Map<String, Object> scopeMap : attributes.values()) {
            if (!scopeMap.isEmpty())
                return false;
        }
        return true;
    }
}
