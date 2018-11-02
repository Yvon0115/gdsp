package com.gdsp.dev.core.model.entity;

/**
 * 实体状态
 * @author yangbo
 * @version 1.0 2014-6-13
 * @since 1.6
 */
public enum EntityStatus {
    /**
     * 文本
     */
    UNKNOWN("未知"),
    /**
     * 持久化
     */
    PERSISTENCE("持久化"),
    /**
     * 新增
     */
    ADD("新增"),
    /**
     * 修改
     */
    UPDATE("修改"),
    /**
     * 删除
     */
    DELETE("删除");

    /**
     * 枚举类的显示文本
     */
    private String text;

    /**
     * 构造方法
     * @param text 显示文本
     */
    private EntityStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public String getValue() {
        return this.toString();
    }
}
