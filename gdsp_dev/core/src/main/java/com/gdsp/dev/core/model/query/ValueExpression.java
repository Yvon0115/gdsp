package com.gdsp.dev.core.model.query;

import java.sql.Types;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.base.utils.data.DataType;
import com.gdsp.dev.base.utils.data.TypeConvert;
import com.gdsp.dev.core.data.jdbc.JdbcTypeUtils;

/**
 * 简单值查询表达式
 * @author yaboocn
 * @version 1.0 2014年5月9日
 * @since 1.7
 */
public class ValueExpression implements IExpression {

    /**
     * 序列id
     */
    private static final long serialVersionUID = -1127113431925423258L;
    /**
     * 查询的实体属性名
     */
    private String            propertyName     = null;
    /**
     * 属性名测试正则表达式
     */
    private static Pattern    propertyParttern = Pattern.compile("^\\w+[\\w+\\.]*\\w+$");
    /**
     * 数据类型
     */
    private DataType          dataType         = DataType.STRING;
    /**
     * 数据类型
     */
    private int               jdbcType         = Types.OTHER;
    /**
     * 表达式值
     */
    private Object            value            = null;
    /**
     * 操作符
     */
    private Operator          operator         = Operator.EQUAL;

    /**
     * 构造方法
     */
    public ValueExpression() {}

    /**
     * 表达式构造方法,操作符为等于
     * @param propertyName 属性名
     * @param value 属性值
     */
    public ValueExpression(String propertyName, Object value) {
        this(propertyName, value, Operator.EQUAL);
    }

    /**
     * 表达式构造方法
     * @param propertyName 属性名
     * @param value 属性值
     * @param operator 操作符
     */
    public ValueExpression(String propertyName, Object value, Operator operator) {
        setPropertyName(propertyName);
        setOperator(operator);
        setValue(value);
    }

    /**
     * 表达式构造方法
     * @param propertyName 属性名
     * @param value 属性值
     * @param operator 操作符
     */
    public ValueExpression(String propertyName, Object value, String operator) {
        setPropertyName(propertyName);
        setOperator(Operator.parse(operator));
        setValue(value);
    }

    /**
     * 表达式构造方法
     * @param propertyName 属性名
     * @param value 属性值
     * @param operator 操作符
     */
    public ValueExpression(String propertyName, Object value, DataType dataType, Operator operator) {
        setPropertyName(propertyName);
        if (dataType != DataType.UNKNOWN)
            setDataType(dataType);
        setOperator(operator);
        setValue(value);
    }

    /**
     * 表达式构造方法
     * @param propertyName 属性名
     * @param value 属性值
     * @param operator 操作符
     */
    public ValueExpression(String propertyName, Object value, String dataType, String operator) {
        setPropertyName(propertyName);
        if (dataType != null) {
            setDataType(dataType);
        }
        setOperator(Operator.parse(operator));
        setValue(value);
    }

    /**
     * 取得表达式属性名
     * @return 属性名
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * 设置表达式属性名
     * @param propertyName 属性名
     */
    public void setPropertyName(String propertyName) {
        if (propertyName.contains(" ")) {
            String[] temps = propertyName.split(" ");
            propertyName = temps[0];
            setDataType(temps[1]);
            if (temps.length > 2) {
                setOperator(Operator.parse(temps[2]));
            }
        }
        if (!propertyParttern.matcher(propertyName).matches())
            throw new DevRuntimeException("非法查询条件");
        this.propertyName = propertyName;
    }

    /**
     * 取得表达式条件值
     * @return 条件值
     */
    public Object getValue() {
        return value;
    }

    /**
     * 设置表达式条件值
     * @param value 条件值
     */
    @SuppressWarnings("rawtypes")
	public void setValue(Object value) {
        if (this.operator == Operator.ISNULL || this.operator == Operator.ISNOTNULL)
            return;
        if (value == null) {
            this.value = null;
            return;
        }
        //in操作符值在转换程表达式时再转换见ValueExpressionTranslator
        if (this.operator == Operator.IN || this.operator == Operator.NOTIN) {
            this.value = value;
            return;
        }
        if (value.getClass().isArray()) {
            value = StringUtils.join((Object[]) value);
        } else if (value instanceof List) {
            value = StringUtils.join((List) value);
        }
        this.value = TypeConvert.translate(dataType, value);
    }

    /**
     * 取得表达式操作符
     * @return 表达式操作符
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * 设置表达式操作符
     * @param operator 表达式操作符
     */
    public void setOperator(Operator operator) {
        if (this.operator == Operator.ISNULL || this.operator == Operator.ISNOTNULL)
            this.value = null;
        this.operator = operator;
    }

    /**
     * 取得字段数据类型
     * @return 字段数据类型
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * 设置字段数据类型
     * @param dataType 字段数据类型
     */
    public void setDataType(String dataType) {
        int i = dataType.indexOf(":");
        if (i >= 0) {
            jdbcType = JdbcTypeUtils.parseJdbcType(dataType.substring(i + 1));
            dataType = dataType.substring(0, i);
        }
        DataType dt = DataType.parse(dataType);
        setDataType(dt);
    }

    /**
     * 设置字段数据类型
     * @param dataType 字段数据类型
     */
    public void setDataType(DataType dataType) {
        if (dataType == DataType.UNKNOWN)
            return;
        this.dataType = dataType;
    }

    /**
     * 取得jdbc类型
     * @return jdbc类型
     */
    public int getJdbcType() {
        if (jdbcType == Types.OTHER)
            return JdbcTypeUtils.convert2JdbcType(dataType);
        return jdbcType;
    }

    /**
     * 设置jdbc类型
     * @param jdbcType jdbc类型
     */
    public void setJdbcType(int jdbcType) {
        this.jdbcType = jdbcType;
    }

    @Override
    public String getType() {
        return TYPE_SIMPLE;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ValueExpression))
            return false;
        return this.toString().equals(obj.toString());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (getValue() == null)
            return getPropertyName() + " " + getDataType() + " " + getOperator();
        else
            return getPropertyName() + " " + getDataType() + " " + getOperator() + getValue();
    }

    /**
     * 判断当前表达式是否合法
     * @return 布尔值
     */
    public boolean isValidate() {
        if (StringUtils.isBlank(propertyName))
            return false;
        if (getOperator() == Operator.ISNULL || getOperator() == Operator.ISNOTNULL)
            return true;
        return value != null;
    }
}
