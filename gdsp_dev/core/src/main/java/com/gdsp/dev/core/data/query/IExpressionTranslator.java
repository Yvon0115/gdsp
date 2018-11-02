package com.gdsp.dev.core.data.query;

import com.gdsp.dev.core.model.query.IExpression;

/**
 * 条件转换结果
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface IExpressionTranslator {

    /**
     * 取得表达式的预编译条件
     * @param expression 表达式
     * @param froms 实体列表支持别名
     * @return 预编译条件
     */
    public PrepareCondition getPreCondition(IExpression expression, String... froms);

    /**
     * 取得表达式对应的条件串
     * @param expression 表达式
     * @return 条件串
     */
    public String getCondition(IExpression expression, String... froms);
}
