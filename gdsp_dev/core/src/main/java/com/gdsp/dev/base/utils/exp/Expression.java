package com.gdsp.dev.base.utils.exp;

import java.util.Map;

/**
 * 表达式接口
 * @author paul.yang
 * @version 1.0 14-12-30
 * @since 1.6
 */
public interface Expression {

    /**
     * 装载参数
     * @param map
     * @return 评估结果值
     */
    public Object evaluate(Map<String, Object> map);
}
