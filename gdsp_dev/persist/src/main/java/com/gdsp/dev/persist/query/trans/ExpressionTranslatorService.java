package com.gdsp.dev.persist.query.trans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.model.query.IExpression;
import com.gdsp.dev.persist.query.BatisTranslatorFactory;
import com.gdsp.dev.persist.query.IBatisTranslator;
import com.gdsp.dev.persist.query.IBatisTranslatorService;
import com.gdsp.dev.persist.query.QueryBatisHelper;

/**
 * 基于hibernate的条件转换器助手类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
@Service
public class ExpressionTranslatorService implements IBatisTranslatorService {
    @Override
    public IBatisTranslator getTranslator(IExpression expression) {
        return BatisTranslatorFactory.getTranslator(expression.getType());
    }

    @Override
    public Map<String, Object> toCondition(List<IExpression> expressions, String parameterPath, String... fromes) {
        if (expressions == null || expressions.size() == 0)
            return null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        String where = "";
        for (IExpression expression : expressions) {
            parameters = getTranslator(expression).toCondition(expression, parameters, parameterPath, fromes);
            String con = (String) parameters.remove(QueryBatisHelper.MYBATIS_CON);
            if (StringUtils.isEmpty(con))
                continue;
            if (where.length() > 0) {
                where += " and ";
            }
            where += con;
        }
        parameters.put(QueryBatisHelper.MYBATIS_CON, where);
        return parameters;
    }

    @Override
    public Map<String, Object> toCondition(IExpression expression, Map<String, Object> parameters, String parameterPath, String... fromes) {
        return getTranslator(expression).toCondition(expression, parameters, parameterPath, fromes);
    }

    @Override
    public Map<String, Object> toCondition(IExpression expression,
            String parameterPath, String... fromes) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters = getTranslator(expression).toCondition(expression, parameters, parameterPath, fromes);
        if (parameters.size() == 0)
            return null;
        return parameters;
    }

    @Override
    public Map<String, Object> toCondition(String parameterPath,
            Condition condition) {
        return toCondition(condition.toExpressions(), parameterPath, condition.getFromAlias());
    }
}
