package com.gdsp.dev.base.utils.exp;

import java.util.Map;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.MapContext;

/**
 * Jexl方式实现的表达式对象
 * @author paul.yang
 * @version 1.0 2010-1-15
 * @since 1.7
 */
public class JexlExpression implements Expression {

    /**jexl表达式对象*/
    private org.apache.commons.jexl2.Expression expression = null;

    /**
     * 构造方法
     * @param expression 表达对象
     */
    public JexlExpression(org.apache.commons.jexl2.Expression expression) {
        this.expression = expression;
    }

    @Override
    public Object evaluate(Map<String, Object> map) {
        JexlContext ctx = createContext(map);
        return expression.evaluate(ctx);
    }

    /**
     * 使用应用上下文创建表达式上下文
     * @param map 应用上下文
     * @return 表达式上下文
     */
    public static JexlContext createContext(Map<String, Object> map) {
        JexlContext ctx = new MapContext(map);
        return ctx;
    }
}
