package com.gdsp.dev.core.data.query;

import java.util.List;

import com.gdsp.dev.core.model.query.IExpression;

/**
 * QL翻译器工厂方法接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface IExpressionTranslatorService extends IExpressionTranslator {

    /**
     * 取得QL(JPQL或HQL)条件转换器
     * @param expression 表达式
     * @return QL条件转换器
     */
    IExpressionTranslator getTranslator(IExpression expression);

    /**
     * 取得一组表达式的预编译条件
     * @param expression 表达式
     * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
     * @return 预编译条件
     */
    public PrepareCondition getPreCondition(List<IExpression> expressions, String... fromes);

    /**
     * 取得一组表达式的查询条件
     * @param expression 表达式
     * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
     * @return 查询条件
     */
    public String getCondition(List<IExpression> expressions, String... fromes);
}
