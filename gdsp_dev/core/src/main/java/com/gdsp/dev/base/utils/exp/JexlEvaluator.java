package com.gdsp.dev.base.utils.exp;

import java.util.Map;

import org.apache.commons.jexl2.JexlEngine;

/**
 * 使用jexl实现的表达式解释器
 * @author yaboocn
 * @version 1.0 2009-9-23
 * @since 1.7
 */
public class JexlEvaluator implements ExpEvaluator {

    /**
     * jexl表达式引擎
     */
    private JexlEngine engine = null;

    /**
     * 构造方法
     */
    public JexlEvaluator() {
        engine = new JexlEngine();
    }

    @Override
    public ExpCompiler getExpCompiler(String text) {
        //增加缓存tes
        return ExpCompiler.compile(text);
    }

    @Override
    public Expression createExpression(String expText) {
        org.apache.commons.jexl2.Expression exp = engine.createExpression(expText);
        return new JexlExpression(exp);
    }

    @Override
    public String evaluate(String text, Map<String, Object> map) {
        ExpCompiler ct = getExpCompiler(text);
        if (ct != null) {
            return ct.translate(map);
        } else {
            return text;
        }
    }
}
