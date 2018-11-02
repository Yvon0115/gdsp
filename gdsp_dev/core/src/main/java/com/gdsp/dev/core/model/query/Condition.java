package com.gdsp.dev.core.model.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 查询类,封装查询条件
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class Condition implements Serializable {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 6256786995910707674L;
    /**
     * 条件列表
     */
    private List<IExpression> expressions      = null;
    /**
     * 自由条件列表
     */
    private List<String>      freeConditions   = null;
    /**
     * 自由搜索值
     */
    private String            freeValue        = null;
    /**
     * 前置逻辑操作符,为空时不增加逻辑操作符
     */
    private String            preLogic         = null;
    /**
     * 用于属性别名转换的别名定义,字符串形式如:User as u,
     * 该别名应用场景,如果定义的条件名为User.name 实际使用的表别名为u,需要在查询执行时进行替换
     * User.name将被替换成u.name
     */
    private String[]          fromAlias        = null;

    /**
     * 添加查询条件
     * @param condition 条件名称
     */
    public void addExpressions(IExpression... condition) {
        if (this.expressions == null)
            this.expressions = new ArrayList<IExpression>();
        if (this.expressions.size() == 0) {
            CollectionUtils.addAll(this.expressions, condition);
        } else {
            for (IExpression con : condition) {
                if (this.expressions.indexOf(con) < 0) {
                    this.expressions.add(con);
                }
            }
        }
    }

    /**
     * 添加查询条件
     * @param condition 条件名称
     * @param value 条件值
     * @param dataType 数据类型
     * @param operator 操作符
     */
    public void addExpression(String condition, Object value, String dataType, String operator) {
        ValueExpression exp = new ValueExpression(condition, value, dataType, operator);
        this.addExpressions(exp);
    }

    /**
     * 添加查询条件
     * @param condition 条件名称
     * @param value 条件值
     * @param operator 操作符
     */
    public void addExpression(String condition, Object value, String operator) {
        ValueExpression exp = new ValueExpression(condition, value, operator);
        this.addExpressions(exp);
    }

    /**
     * 添加查询条件
     * @param condition 条件名称
     * @param value 条件值
     */
    public void addExpression(String condition, Object value) {
        ValueExpression exp = new ValueExpression(condition, value);
        this.addExpressions(exp);
    }

    /**
     * 设置自由条件属性列表
     * @param freeConditions 自由条件属性列表
     */
    public void setFreeConditions(String... freeConditions) {
        List<String> freeCons = new ArrayList<String>();
        CollectionUtils.addAll(freeCons, freeConditions);
        this.freeConditions = freeCons;
    }

    /**
     * 添加自由条件属性
     * @param freeCondition 自由条件属性
     */
    public void setFreeExpressions(String freeValue, String... freeCondition) {
        if (freeCondition == null || freeCondition.length == 0 || StringUtils.isEmpty(freeValue))
            return;
        setFreeConditions(freeCondition);
        setFreeValue(freeValue);
    }

    /**
     * 添加自由条件属性
     * @param freeCondition 自由条件属性
     */
    public void addFreeCondition(String... freeCondition) {
        if (this.freeConditions == null)
            this.freeConditions = new ArrayList<String>();
        if (this.freeConditions.size() == 0) {
            CollectionUtils.addAll(this.freeConditions, freeCondition);
        } else {
            for (String con : freeCondition) {
                if (this.freeConditions.indexOf(con) < 0) {
                    this.freeConditions.add(con);
                }
            }
        }
    }

    /**
     * 转换成表达式列表
     * @return 表达式列表
     */
    public List<IExpression> toExpressions() {
        List<IExpression> cons = getExpressions();
        if (StringUtils.isEmpty(getFreeValue()))
            return cons;
        ChainLogicExpression expression = new ChainLogicExpression();
        if (cons != null && cons.size() > 0) {
            for (IExpression con : cons) {
                expression.addExpression(con);
            }
        }
        List<String> freeCons = getFreeConditions();
        String freeValue = getFreeValue();
        if (freeCons != null && freeCons.size() > 0 && StringUtils.isNotEmpty(freeValue)) {
            ChainLogicExpression freeExp = new ChainLogicExpression(true);
            for (String freeCon : freeCons) {
                IExpression exp = new ValueExpression(freeCon, freeValue, Operator.LIKEANY);
                freeExp.addExpression(exp);
            }
            expression.addExpression(freeExp);
        }
        List<IExpression> expressions = new ArrayList<>();
        expressions.add(expression);
        return expressions;
    }

    //getter and setter
    /**
     * 取得条件列表
     * @return 条件列表
     */
    public List<IExpression> getExpressions() {
        return expressions;
    }

    /**
     * 设置条件列表
     * @param expressions 条件列表
     */
    public void setExpressions(List<IExpression> expressions) {
        this.expressions = expressions;
    }

    /**
     * 取得自由条件值
     * @return 条件值
     */
    public String getFreeValue() {
        return freeValue;
    }

    /**
     * 设置自由条件值
     * @param freeValue 自由条件值
     */
    public void setFreeValue(String freeValue) {
        this.freeValue = freeValue;
    }

    /**
     * 取得自由条件属性列表
     * @return 自由条件属性列表
     */
    public List<String> getFreeConditions() {
        return freeConditions;
    }

    /**
     * 设置自由条件属性列表
     * @param freeConditions 自由条件属性列表
     */
    public void setFreeConditions(List<String> freeConditions) {
        this.freeConditions = freeConditions;
    }

    /**
     * 取得条件前置逻辑操作符
     * @return 前置逻辑操作符
     */
    public String getPreLogic() {
        return preLogic;
    }

    /**
     * 取得条件前置逻辑操作符
     * @param preLogic 前置逻辑操作符
     */
    public void setPreLogic(String preLogic) {
        this.preLogic = preLogic;
    }

    /**
     * 取得条件对应的的表(实体)别名定义类表
     * @return 表别名定义类表
     */
    public String[] getFromAlias() {
        return fromAlias;
    }

    /**
     * 设置条件对应的的表(实体)别名定义类表
     * @param fromAlias 表别名定义类表
     */
    public void setFromAlias(String... fromAlias) {
        this.fromAlias = fromAlias;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        List<IExpression> es = toExpressions();
        if (es != null && es.size() > 0) {
            if (preLogic != null)
                builder.append(preLogic).append(" ");
            builder.append(StringUtils.join(es, " and "));
            return builder.toString();
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Condition))
            return false;
        return obj.toString().equals(toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
