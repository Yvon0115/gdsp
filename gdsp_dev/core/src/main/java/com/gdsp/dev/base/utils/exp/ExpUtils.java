package com.gdsp.dev.base.utils.exp;

import java.util.Map;

/**
 * 表达式工具类
 * @author paul.yang
 * @version 1.0 2009-9-23
 * @since 1.7
 */
public final class ExpUtils {

    /**表达式解释器*/
    private static ExpEvaluator evaluator = null;

    /**
     * 单例构造
     */
    private ExpUtils() {}

    /**
     * 取得表达式解释器
     * @return 表达式解释器
     */
    public static ExpEvaluator getEvaluator() {
        if (evaluator == null) {
            evaluator = new JexlEvaluator();
        }
        return evaluator;
    }

    /**
     * 翻译执行指定的表达式，或包含表达式的字符串
     * @param expression 含有表达式串
     * @param map 上下文Map
     * @return 翻译后对象
     */
    public static Object evaluate(String expression, Map<String, Object> map){
        ExpEvaluator e = getEvaluator();
        if (e == null)
            return expression;
        return e.evaluate(expression, map);
    }
}
