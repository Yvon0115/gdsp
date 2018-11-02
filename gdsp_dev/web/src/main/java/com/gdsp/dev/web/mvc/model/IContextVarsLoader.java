package com.gdsp.dev.web.mvc.model;

import java.util.Map;

/**
 * 上下变量加载器
 * @author paul.yang
 * @version 1.0 2014年11月5日
 * @since 1.6
 */
public interface IContextVarsLoader {

    /**
     * 取得变量集合
     * @return 变量级
     */
    Map<String, Object> getVariables();
}
