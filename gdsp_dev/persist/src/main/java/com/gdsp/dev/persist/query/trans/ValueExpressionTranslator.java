package com.gdsp.dev.persist.query.trans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.utils.data.DataType;
import com.gdsp.dev.base.utils.data.TypeConvert;
import com.gdsp.dev.core.data.jdbc.DialectHelper;
import com.gdsp.dev.core.data.query.QueryHelper;
import com.gdsp.dev.core.model.query.IExpression;
import com.gdsp.dev.core.model.query.Operator;
import com.gdsp.dev.core.model.query.ValueExpression;
import com.gdsp.dev.persist.query.IBatisTranslator;
import com.gdsp.dev.persist.query.QueryBatisHelper;

/**
 * 基于简单值条件的转换器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class ValueExpressionTranslator implements IBatisTranslator {

    /**
     * 转换表达式为值类型表达式
     * @param expression 表达式
     * @return 值表达式
     */
    protected ValueExpression getExpression(IExpression expression) {
        if (!(expression instanceof ValueExpression))
            throw new IllegalArgumentException("非法的表达式转换");
        return (ValueExpression) expression;
    }

    /**
     * 取得like表达式的mybatis预编译条件信息
     * @param property 表达式字段名
     * @param exp 表达式对象
     * @param paramExp
     * @return 条件+参数组合
     */
    protected String[] getLikeCondition(String property, ValueExpression exp, String paramExp) {
        Object value = exp.getValue();
        if (DataType.isNumberType(exp.getDataType())) {
            property = "CAST(" + property + " AS string)";
        } else if (DataType.DATE == exp.getDataType()) {
            SimpleDateFormat format = DDate.defaultFormat();
            property = DialectHelper.getDialect().getDateColumnCondition(property, format.toPattern());
            if (value instanceof Date) {
                value = format.format(value);
            }
        }
        switch (exp.getOperator()) {
            case LIKESTART:
                value = value.toString() + "%";
                break;
            case LIKEEND:
                value = "%" + value.toString();
                break;
            default:
                value = "%" + value.toString() + "%";
                break;
        }
        return new String[] { property + " like " + paramExp, value.toString() };
    }

    @Override
    public Map<String, Object> toCondition(IExpression expression, Map<String, Object> parameters, String parameterPath,
            String... fromes) {
        ValueExpression exp = getExpression(expression);
        String property = QueryHelper.getFullPropertyName(exp.getPropertyName(), fromes);
        Object value = exp.getValue();
        String propName = exp.getPropertyName();
        if (parameters == null)
            parameters = new HashMap<String, Object>();
        propName = propName.replaceAll("\\.", "__");
        Object oldValue = parameters.get(propName);
        int c = 1;
        while (oldValue != null) {
            if ((value == oldValue) || (value != null && value.equals(oldValue)))
                break;
            propName += c;
            oldValue = parameters.get(propName);
            c++;
        }
        if (parameterPath == null) {
            parameterPath = "";
        } else {
            parameterPath += ".";
        }
        String dt = JdbcType.forCode(exp.getJdbcType()).toString();
        String paramExp;
        if (StringUtils.isNotEmpty(dt)) {
            paramExp = "#{" + parameterPath + propName + ":" + dt + "}";
        } else {
            paramExp = "#{" + parameterPath + propName + "}";
        }
        String con = null;
        Operator op = exp.getOperator();
        switch (op) {
            case NOTEQUAL:
                con = property + "<>" + paramExp;
                break;
            case GREATER:
                con = property + ">" + paramExp;
                break;
            case LESS:
                con = property + "<" + paramExp;
                break;
            case GREATEREQUAL:
                con = property + ">=" + paramExp;
                break;
            case LESSEQAUL:
                con = property + "<=" + paramExp;
                break;
            case LIKEANY:
            case LIKESTART:
            case LIKEEND:
                String[] vals = getLikeCondition(property, exp, paramExp);
                con = vals[0];
                value = vals[1];
                break;
            case ISNULL:
                con = property + " is null";
                value = null;
                break;
            case ISNOTNULL:
                con = property + " is not null";
                value = null;
                break;
            case IN:
            case NOTIN:
                if (value.getClass().isArray()) {
                    String pitem = parameterPath + propName;
                    Object[] vs = (Object[]) value;
                    StringBuilder sb = new StringBuilder(property);
                    sb.append(" ").append(op).append("(");
                    for (int i = 0; i < vs.length; i++) {
                        parameters.put(propName + "[" + i + "]", TypeConvert.translate(exp.getDataType(), vs[i]));
                        if (i > 0)
                            sb.append(",");
                        sb.append("#{").append(pitem).append("[").append(i).append("]}");
                    }
                    sb.append(")");
                    con = sb.toString();
                } else if (value instanceof List) {
                    String pitem = parameterPath + propName;
                    List<?> vs = (List<?>) value;
                    StringBuilder sb = new StringBuilder(property);
                    sb.append(" ").append(op).append("(");
                    for (int i = 0; i < vs.size(); i++) {
                        parameters.put(propName + "[" + i + "]", TypeConvert.translate(exp.getDataType(), vs.get(i)));
                        if (i > 0)
                            sb.append(",");
                        sb.append("#{").append(pitem).append("[").append(i).append("]}");
                    }
                    sb.append(")");
                    con = sb.toString();
                } else {
                    con = property + " " + op + "(" + value + ")";
                    value = null;
                }
                break;
            default:
                con = property + "=" + paramExp;
        }
        if (value != null)
            parameters.put(propName, value);
        parameters.put(QueryBatisHelper.MYBATIS_CON, con);
        return parameters;
    }
}
