package com.gdsp.dev.persist.query.trans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdsp.dev.core.model.query.ChainLogicExpression;
import com.gdsp.dev.core.model.query.IExpression;
import com.gdsp.dev.persist.query.IBatisTranslator;
import com.gdsp.dev.persist.query.IBatisTranslatorService;
import com.gdsp.dev.persist.query.QueryBatisHelper;

/**
 * 链式逻辑条件表达式的转换器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class ChainLogicExpressionTranslator implements IBatisTranslator {

    /**
     * 转换表达式为值类型表达式
     * @param expression 表达式
     * @return 值表达式
     */
    protected ChainLogicExpression getExpression(IExpression expression) {
        if (!(expression instanceof ChainLogicExpression))
            throw new IllegalArgumentException("非法的表达式转换");
        return (ChainLogicExpression) expression;
    }

    @Override
    public Map<String, Object> toCondition(IExpression expression, Map<String, Object> parameters, String paramKey,
            String... fromes) {
        ChainLogicExpression exp = getExpression(expression);
        IBatisTranslatorService service = QueryBatisHelper.getQueryTranslatorService();
        if (parameters == null)
            parameters = new HashMap<String, Object>();
        List<IExpression> children = exp.getExpressions();
        if (children == null || children.size() == 0)
            return parameters;
        if (children.size() == 1)
            return service.toCondition(children.get(0), parameters, paramKey, fromes);
        String condition = null;
        String op = " and ";
        if (exp.isOr())
            op = " or ";
        for (IExpression child : children) {
            parameters = service.toCondition(child, parameters, paramKey, fromes);
            if (condition == null) {
                condition = (String) parameters.get(QueryBatisHelper.MYBATIS_CON);
            } else {
                condition += op + (String) parameters.get(QueryBatisHelper.MYBATIS_CON);
            }
        }
        if (exp.isOr())
            condition = "(" + condition + ")";
        parameters.put(QueryBatisHelper.MYBATIS_CON, condition);
        return parameters;
    }
}
