package com.gdsp.dev.base.utils.exp;

import java.util.Map;

/**
 * 表达式计算接口
 * @author paul.yang
 * @version 1.0 14-12-30
 * @since 1.6
 */
public interface ExpEvaluator {

    /**
     * 取得指定文本的编译器
     * @param text 需要转换的文本
     * @return 对应的表达式转换器
     */
    public ExpCompiler getExpCompiler(String text);

    /**
     * 根据表达式串创建表达式
     * @param expText 表达式文本
     * @return 表达式串
     */
    public Expression createExpression(String expText);

    /**
     * 根据上下文对象计算表达式值
     * @param text 带有表达式的字符串
     * @param map 上下文对象
     * @return 结果对象
     */
    public String evaluate(String text, Map<String, Object> map);
}
