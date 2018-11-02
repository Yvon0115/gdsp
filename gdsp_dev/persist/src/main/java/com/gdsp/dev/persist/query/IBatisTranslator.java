package com.gdsp.dev.persist.query;

import java.util.Map;

import com.gdsp.dev.core.model.query.IExpression;

/**
 * 条件转换结果
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface IBatisTranslator {

    /**
     * 取得mybatis表达式对应的
     * @param expression 表达式对象
     * @param parameters 参数map
     * @param fromes 表名数组
     * @param parameterPath 查询条件在dao方法参数中的键值路径(多参数时使用)
     * @return 参数map
     */
    public Map<String, Object> toCondition(IExpression expression, Map<String, Object> parameters, String parameterPath,
            String... fromes);
}
