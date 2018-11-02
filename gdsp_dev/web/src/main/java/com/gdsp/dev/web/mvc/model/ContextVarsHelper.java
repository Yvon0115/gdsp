package com.gdsp.dev.web.mvc.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.gdsp.dev.core.common.AppContext;

/**
 * 上下文变量助手类
 * @author paul.yang
 * @version 1.0 2014年11月5日
 * @since 1.6
 */
public class ContextVarsHelper {

    /**
     * 变量映射
     */
    private volatile Map<String, Object>      vars;
    /**
     * 是否已经初始化
     */
    private volatile boolean                  isInit = false;
    /**
     * 单例
     */
    private static volatile ContextVarsHelper helper = null;

    public ContextVarsHelper() {
        vars = new HashMap<>();
    }

    /**
     * 获取单例
     * @return 单例对象
     */
    public static ContextVarsHelper getInstance() {
        if (helper == null) {
            synchronized (ContextVarsHelper.class) {
                helper = AppContext.getContext().lookup(ContextVarsHelper.class);
                if (helper == null) {
                    helper = new ContextVarsHelper();
                }
            }
        }
        return helper;
    }

    /**
     * 只能系统初始化是调用
     * @param varName 变量名
     * @param var 变量对象
     */
    public void put(String varName, Object var) {
        if (isInit)
            return;
        synchronized (this) {
            vars.put(varName, var);
        }
    }

    /**
     * 取得上下文变量
     * @return 变量映射
     */
    public Map<String, Object> getVars() {
        if (isInit)
            return vars;
        synchronized (this) {
            if (isInit)
                return vars;
            vars = Collections.synchronizedMap(new HashMap<String, Object>());
            Map<String, IContextVarsLoader> loaderMap = AppContext.getContext().getBeansOfType(IContextVarsLoader.class);
            Collection<IContextVarsLoader> loaders = loaderMap.values();
            for (IContextVarsLoader loader : loaders) {
                Map<String, Object> v = loader.getVariables();
                if (v != null) {
                    vars.putAll(v);
                }
            }
            isInit = true;
        }
        return vars;
    }
}
