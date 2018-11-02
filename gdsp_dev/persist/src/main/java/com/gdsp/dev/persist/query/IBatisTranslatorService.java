package com.gdsp.dev.persist.query;

import java.util.List;
import java.util.Map;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.model.query.IExpression;

/**
 * 表达式转换服务
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface IBatisTranslatorService extends IBatisTranslator {

    /**
     * 根据表达式对象去的相应的转换器
     * @param expression 表达式对象
     * @return 转换器
     */
    public IBatisTranslator getTranslator(IExpression expression);

    /**
     * 转换单表达式的查询条件
     * @param expression 表达式
     * @param parameterPath 查询条件在dao方法参数中的键值路径(多参数时使用)
     * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
     * @return mybatis条件map
     */
    public Map<String, Object> toCondition(IExpression expression, String parameterPath, String... fromes);

    /**
     * 转换一组表达式的查询条件
     * @param expressions 表达式列表
     * @param parameterPath 查询条件在dao方法参数中的键值路径(多参数时使用)
     * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
     * @return mybatis条件map
     */
    public Map<String, Object> toCondition(List<IExpression> expressions, String parameterPath, String... fromes);

    /**
     * 转换一组表达式的查询条件
     * @param parameterPath 查询条件在dao方法参数中的键值路径(多参数时使用)
     * @param condition 条件对象
     * @return mybatis条件map
     */
    public Map<String, Object> toCondition(String parameterPath, Condition condition);
}
