package com.gdsp.dev.core.data.query.trans;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.gdsp.dev.core.data.query.IExpressionTranslator;
import com.gdsp.dev.core.data.query.ExpressionTranslatorFactory;
import com.gdsp.dev.core.data.query.IExpressionTranslatorService;
import com.gdsp.dev.core.data.query.PrepareCondition;
import com.gdsp.dev.core.model.query.IExpression;

/**
 * 基于hibernate的条件转换器助手类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
@Service("JDBCExpressionTranslatorService")
public class ExpressionTranslatorService implements IExpressionTranslatorService {

    @Override
    public IExpressionTranslator getTranslator(IExpression expression) {
        return ExpressionTranslatorFactory.getExpressionTranslatorServise(expression.getType());
    }

    @Override
    public PrepareCondition getPreCondition(List<IExpression> expressions, String... fromes) {
        if (expressions == null || expressions.size() == 0)
            return null;
        String where = "";
        Object[] vals = null;
        for (IExpression expression : expressions) {
            PrepareCondition con = getTranslator(expression).getPreCondition(expression, fromes);
            if (where.length() > 0) {
                where += " and ";
                vals = ArrayUtils.addAll(vals, con.getParameters());
            } else {
                vals = con.getParameters();
            }
            where += con.getQuery();
        }
        return new PrepareCondition(where, vals);
    }

    @Override
    public String getCondition(List<IExpression> expressions, String... fromes) {
        if (expressions == null || expressions.size() == 0)
            return null;
        String where = "";
        for (IExpression expression : expressions) {
            if (where.length() > 0)
                where += " and ";
            where += getTranslator(expression).getPreCondition(expression, fromes);
        }
        return where;
    }

    @Override
    public PrepareCondition getPreCondition(IExpression expression, String... froms) {
        return getTranslator(expression).getPreCondition(expression, froms);
    }

    @Override
    public String getCondition(IExpression expression, String... froms) {
        return getTranslator(expression).getCondition(expression);
    }
}
