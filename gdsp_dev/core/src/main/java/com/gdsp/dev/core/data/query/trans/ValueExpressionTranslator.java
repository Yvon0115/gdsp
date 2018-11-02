package com.gdsp.dev.core.data.query.trans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.utils.data.DataType;
import com.gdsp.dev.core.data.jdbc.DialectHelper;
import com.gdsp.dev.core.data.query.IExpressionTranslator;
import com.gdsp.dev.core.data.query.PrepareCondition;
import com.gdsp.dev.core.data.query.QueryHelper;
import com.gdsp.dev.core.model.query.IExpression;
import com.gdsp.dev.core.model.query.Operator;
import com.gdsp.dev.core.model.query.ValueExpression;

/**
 * 基于简单值条件的转换器
 * 
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class ValueExpressionTranslator implements IExpressionTranslator {

    @Override
    public PrepareCondition getPreCondition(IExpression expression, String... froms) {
        ValueExpression exp = getExpression(expression);
        String property = QueryHelper.getFullPropertyName(exp.getPropertyName(), froms);
        Object value = exp.getValue();
        String con = null;
        switch (exp.getOperator()) {
            case NOTEQUAL:
                con = property + "!=?";
                break;
            case GREATER:
                con = property + ">?";
                break;
            case LESS:
                con = property + "<?";
                break;
            case GREATEREQUAL:
                con = property + ">=?";
                break;
            case LESSEQAUL:
                con = property + "<=?";
                break;
            case LIKEANY:
            case LIKESTART:
            case LIKEEND:
                return getLikePreCondition(property, exp);
            case IN:
            case NOTIN:
                return getInNotInPreCondition(property, exp);
            case ISNULL:
                con = property + " is null";
                value = null;
                break;
            case ISNOTNULL:
                con = property + " is not null";
                value = null;
                break;
            default:
                con = property + "=?";
        }
        return new PrepareCondition(con, value == null ? null : new Object[] { value });
    }

    private PrepareCondition getInNotInPreCondition(String property, ValueExpression exp) {
        Object value = exp.getValue();
        String query = "";
        if (exp.getOperator() == Operator.IN) {
            query = property + " in ";
        } else {
            query = property + " not in ";
        }
        Object[] parameters = null;
        query += "(";
        if (value instanceof Object[]) {
            for (int i = 0; i < ((Object[]) value).length; i++) {
                query += i == 0 ? "?" : ",?";
            }
            parameters = (Object[]) value;
        } else if (value instanceof List) {
            for (int i = 0; i < ((List<?>) value).size(); i++) {
                query += i == 0 ? "?" : ",?";
            }
            parameters = ((List<?>) value).toArray();
        } else {
            query += " ? ";
            parameters = new Object[] { value };
        }
        query += ")";
        return new PrepareCondition(query, parameters);
    }

    /**
     * 取得like表达式的预编译条件信息
     * 
     * @param exp 表达式对象
     * @return 预编译条件
     */
    protected PrepareCondition getLikePreCondition(String property, ValueExpression exp) {
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
        return new PrepareCondition(property + " like ?", new Object[] { value });
    }

    /**
     * 转换表达式为值类型表达式
     * 
     * @param expression 表达式
     * @return 值表达式
     */
    protected ValueExpression getExpression(IExpression expression) {
        if (!(expression instanceof ValueExpression))
            throw new IllegalArgumentException("非法的表达式转换");
        return (ValueExpression) expression;
    }

    @Override
    public String getCondition(IExpression expression, String... froms) {
        ValueExpression exp = getExpression(expression);
        String property = QueryHelper.getFullPropertyName(exp.getPropertyName(), froms);
        String origion = property;
        Object value = exp.getValue();
        if (DataType.DATE == exp.getDataType()) {
            SimpleDateFormat format = DDate.defaultFormat();
            property = DialectHelper.getDialect().getDateColumnCondition(property, format.toPattern());
            if (value instanceof Date) {
                value = format.format(value);
            }
        } else if (DataType.DATETIME == exp.getDataType()) {
            SimpleDateFormat format = DDateTime.defaultFormat();
            property = DialectHelper.getDialect().getDateColumnCondition(property, format.toPattern());
            if (value instanceof Date) {
                value = format.format(value);
            }
        }
        Operator op = exp.getOperator();
        if (op == Operator.LIKEANY) {
            value = "%" + value.toString() + "%";
        } else if (op == Operator.LIKESTART) {
            value = value.toString() + "%";
        } else if (op == Operator.LIKEEND) {
            value = "%" + value.toString();
        }
        if (!DataType.isNumberType(exp.getDataType())) {
            value = "'" + value + "'";
        }
        switch (op) {
            case NOTEQUAL:
                return property + "!=" + value;
            case GREATER:
                return property + ">" + value;
            case LESS:
                return property + "<" + value;
            case GREATEREQUAL:
                return property + ">=" + value;
            case LESSEQAUL:
                return property + "<=" + value;
            case LIKEANY:
            case LIKESTART:
            case LIKEEND:
                return property + "like" + value;
            case ISNULL:
                return origion + " is null";
            case ISNOTNULL:
                return origion + " is not null";
            default:
                return property + "=" + value;
        }
    }
}
