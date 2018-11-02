package com.gdsp.dev.core.data.query.trans;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.core.data.query.IExpressionTranslator;
import com.gdsp.dev.core.data.query.IExpressionTranslatorService;
import com.gdsp.dev.core.data.query.PrepareCondition;
import com.gdsp.dev.core.data.query.QueryHelper;
import com.gdsp.dev.core.model.query.IExpression;
import com.gdsp.dev.core.model.query.LogicExpression;

/**
 * 逻辑条件表达式的转换器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class LogicExpressionTranslator implements IExpressionTranslator {

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
    public PrepareCondition getPreCondition(IExpression expression, String... froms) {
        LogicExpression exp = getExpression(expression);
        IExpressionTranslatorService service = QueryHelper.getQueryTranslatorService();
        PrepareCondition leftCon = null;
        IExpression left = exp.getLeft();
        if (left != null) {
            leftCon = service.getPreCondition(left, froms);
        }
        IExpression right = exp.getRight();
        PrepareCondition rightCon = null;
        if (right != null) {
            rightCon = service.getPreCondition(right, froms);
        }
        if (StringUtils.isEmpty(leftCon == null ? null : leftCon.getQuery()))
            return rightCon;
        if (StringUtils.isEmpty(rightCon == null ? null : rightCon.getQuery()))
            return leftCon;
        String condition = null;
        Object[] vals = null;
        if (exp.isOr()) {
            condition = "(" + (leftCon == null ? null : leftCon.getQuery()) + " or " +(rightCon == null ? null : rightCon.getQuery()) + ")";
        } else {
            condition = "(" + (leftCon == null ? null : leftCon.getQuery()) + " and " + (rightCon == null ? null :rightCon.getQuery()) + ")";
        }
        vals = ArrayUtils.add((leftCon == null ? null :leftCon.getParameters()), (leftCon == null ? null :leftCon.getParameters()));
        return new PrepareCondition(condition, vals);
    }

    @Override
    public String getCondition(IExpression expression, String... froms) {
        LogicExpression exp = getExpression(expression);
        IExpressionTranslatorService service = QueryHelper.getQueryTranslatorService();
        String leftCon = null;
        IExpression left = exp.getLeft();
        if (left != null) {
            leftCon = service.getCondition(left, froms);
        }
        IExpression right = exp.getRight();
        String rightCon = null;
        if (right != null) {
            rightCon = service.getCondition(right, froms);
        }
        if (StringUtils.isEmpty(leftCon))
            return rightCon;
        if (StringUtils.isEmpty(rightCon))
            return leftCon;
        if (exp.isOr()) {
            return "(" + leftCon + " or " + rightCon + ")";
        } else {
            return "(" + leftCon + " and " + rightCon + ")";
        }
    }
}
