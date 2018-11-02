package com.gdsp.dev.core.data.query;

/**
 * 预编译查询sql
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class PrepareCondition {

    /**
     * 预编译条件
     */
    private String   query = null;
    /**
     * 预编译值
     */
    private Object[] parameters;

    /**
     * 无参构造方法
     */
    public PrepareCondition() {}

    /**
     * 带参构造方法
     * @param query 预编译条件
     * @param parameters 预编译参数
     */
    public PrepareCondition(String query, Object[] parameters) {
        this.query = query;
        this.parameters = parameters;
    }

    /**
     * 取得预编译条件
     * @return 预编译条件
     */
    public String getQuery() {
        return query;
    }

    /**
     * 设置预编译条件
     * @param query 预编译条件
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * 取得预编译参数
     * @return 预编译参数
     */
    public Object[] getParameters() {
        return parameters;
    }

    /**
     * 预编译参数
     * @param parameters 预编译参数
     */
    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
