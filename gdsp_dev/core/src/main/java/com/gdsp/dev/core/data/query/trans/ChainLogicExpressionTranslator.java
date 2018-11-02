package com.gdsp.dev.core.data.query.trans;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.gdsp.dev.core.data.query.IExpressionTranslator;
import com.gdsp.dev.core.data.query.IExpressionTranslatorService;
import com.gdsp.dev.core.data.query.PrepareCondition;
import com.gdsp.dev.core.data.query.QueryHelper;
import com.gdsp.dev.core.model.query.ChainLogicExpression;
import com.gdsp.dev.core.model.query.IExpression;

/**
 * 链式逻辑条件表达式的转换器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class ChainLogicExpressionTranslator implements
        IExpressionTranslator {

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
    public PrepareCondition getPreCondition(IExpression expression, String... froms) {
        ChainLogicExpression exp = getExpression(expression);
        IExpressionTranslatorService service = QueryHelper.getQueryTranslatorService();
        List<IExpression> children = exp.getExpressions();
        if (children == null || children.size() == 0)
            return null;
        if (children.size() == 1)
            return service.getPreCondition(children.get(0), froms);
        String condition = null;
        String op = " and ";
        if (exp.isOr())
            op = " or ";
        Object[] vals = null;
        for (IExpression child : children) {
            PrepareCondition current = service.getPreCondition(child, froms);
            if (condition == null) {
                condition = current.getQuery();
                vals = current.getParameters();
            } else {
                condition += op + current.getQuery();
                vals = ArrayUtils.addAll(vals, current.getParameters());
            }
        }
        if (exp.isOr())
            condition = "(" + condition + ")";
        return new PrepareCondition(condition, vals);
    }

    @Override
    public String getCondition(IExpression expression, String... froms) {
        ChainLogicExpression exp = getExpression(expression);
        IExpressionTranslatorService service = QueryHelper.getQueryTranslatorService();
        List<IExpression> children = exp.getExpressions();
        if (children == null || children.size() == 0)
            return null;
        if (children.size() == 1)
            return service.getCondition(children.get(0), froms);
        String result = null;
        String op = " and ";
        if (exp.isOr())
            op = " or ";
        for (IExpression child : children) {
            String current = service.getCondition(child, froms);
            if (result == null)
                result = current;
            else
                result += op + current;
        }
        if (exp.isOr())
            result = "(" + result + ")";
        return result;
    }
}
