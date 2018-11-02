package com.gdsp.dev.persist.query.trans;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.core.model.query.IExpression;
import com.gdsp.dev.core.model.query.LogicExpression;
import com.gdsp.dev.persist.query.IBatisTranslator;
import com.gdsp.dev.persist.query.IBatisTranslatorService;
import com.gdsp.dev.persist.query.QueryBatisHelper;

/**
 * 逻辑条件表达式的转换器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class LogicExpressionTranslator implements IBatisTranslator {

    /**
     * 转换表达式为值类型表达式
     * @param expression 表达式
     * @return 值表达式
     */
    protected LogicExpression getExpression(IExpression expression) {
        if (!(expression instanceof LogicExpression))
            throw new IllegalArgumentException("非法的表达式转换");
        return (LogicExpression) expression;
    }

    @Override
    public Map<String, Object> toCondition(IExpression expression,
            Map<String, Object> parameters, String paramKey, String... fromes) {
        LogicExpression exp = getExpression(expression);
        IBatisTranslatorService service = QueryBatisHelper.getQueryTranslatorService();
        String leftCon = null;
        if (parameters == null)
            parameters = new HashMap<String, Object>();
        IExpression left = exp.getLeft();
        if (left != null) {
            parameters = service.toCondition(left, parameters, paramKey, fromes);
            leftCon = (String) parameters.get(QueryBatisHelper.MYBATIS_CON);
        }
        IExpression right = exp.getRight();
        String rightCon = null;
        if (right != null) {
            parameters = service.toCondition(right, parameters, paramKey, fromes);
            rightCon = (String) parameters.get(QueryBatisHelper.MYBATIS_CON);
        }
        if (StringUtils.isEmpty(leftCon))
            return parameters;
        if (StringUtils.isEmpty(rightCon)) {
            parameters.put(QueryBatisHelper.MYBATIS_CON, leftCon);
            return parameters;
        }
        String condition = null;
        if (exp.isOr()) {
            condition = "(" + leftCon + " or " + rightCon + ")";
        } else {
            condition = "(" + leftCon + " and " + rightCon + ")";
        }
        parameters.put(QueryBatisHelper.MYBATIS_CON, condition);
        return parameters;
    }
}
