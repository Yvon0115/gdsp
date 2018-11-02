package com.gdsp.dev.core.model.param;

/**
 * 查询限制对象,包括查询的起始记录和最大记录数
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class Limit {

    /**
     * 起始位置
     */
    private int offset = 0;
    /**
     * 最大记录数
     */
    private int limit  = 20;

    /**
     * 构造方法
     */
    public Limit() {}

    /**
     * 构造方法
     * @param limit 限制记录数
     */
    public Limit(int limit) {
        this.limit = limit;
    }

    /**
     * 构造方法
     * @param offset 记录起始位置
     * @param limit 限制记录数
     */
    public Limit(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    /**
     * 取得记录起始位置
     * @return 记录起始位置
     */
    public int getOffset() {
        return offset;
    }

    /**
     * 设置记录起始位置
     * @param offset 记录起始位置
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * 取得最大记录数
     * @return 最大记录数
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 设置最大记录数
     * @param limit 最大记录数
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
