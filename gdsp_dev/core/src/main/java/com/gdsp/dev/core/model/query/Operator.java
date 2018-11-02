/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.core.model.query;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作符枚举
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public enum Operator {
    /*操作符常量*/
    /**
     * =操作符
     */
    EQUAL("=", "等于"),
    /**
     * <>操作符
     */
    NOTEQUAL("<>", "不等于"),
    /**
     * >操作符
     */
    GREATER(">", "大于"),
    /**
     * <操作符
     */
    LESS("<", "小于"),
    /**
     * >=操作符
     */
    GREATEREQUAL(">=", "大于等于"),
    /**
     * <=操作符
     */
    LESSEQAUL("<=", "小于等于"),
    /**
     * like操作符，全模糊匹配
     */
    LIKEANY("like", "包含"),
    /**
     * like操作符，开始匹配，结尾模糊
     */
    LIKESTART("likes", "左包含"),
    /**
     * like操作符，结尾匹配，开始模糊
     */
    LIKEEND("likee", "右包含"),
    /**
     * is null操作符
     */
    ISNULL("is null", "为空值"),
    /**
     * is not null操作符
     */
    ISNOTNULL("is not null", "不为空值"),
    /**
     * in操作符号
     */
    IN("in", "列举"),
    /**
     * not in操作符号
     */
    NOTIN("not in", "反列举");

    /**
     * 操作符编码
     */
    private String code;
    /**
     * 操作符名称
     */
    private String name;

    /**
     * 构造方法
     * @param code 编码
     * @param name 名称
     */
    Operator(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 取得类型编码
     * @return 类型编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 取得类型名称
     * @return 类型名称
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return getCode();
    }

    /**
     * 根据编码获取操作符
     * @param code 操作符编码
     * @return 操作符
     */
    public static Operator parse(String code) {
        code = code.toLowerCase();
        if (!strValMap.containsKey(code)) {
            throw new IllegalArgumentException("Unknown String Value: " + code);
        }
        return strValMap.get(code);
    }

    private static final Map<String, Operator> strValMap;
    static {
        strValMap = new HashMap<>();
        for (final Operator en : Operator.values()) {
            strValMap.put(en.getCode(), en);
        }
    }
}
