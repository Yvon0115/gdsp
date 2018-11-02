package com.gdsp.dev.core.model.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * 查询条件逻辑表达式表达式
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class ChainLogicExpression implements IExpression {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 5682864471973760964L;
    /**
     * 表达式列表
     */
    private List<IExpression> expressions;
    /**
     * 操作符
     */
    private boolean           isOr             = false;

    /**
     * 构造方法
     */
    public ChainLogicExpression() {}

    /**
     * 表达式构造方法
     * @param propertyName 属性名
     * @param value 属性值
     * @param operator 操作符
     */
    public ChainLogicExpression(boolean isOr, IExpression... expressions) {
        addExpression(expressions);
        setOr(isOr);
    }

    /**
     * 取得链式逻辑表达式中包含的表达式列表
     * @return 表达式列表
     */
    public List<IExpression> getExpressions() {
        return expressions;
    }

    /**
     * 设置链式逻辑表达式中包含的表达式列表
     * @param expressions 表达式列表
     */
    public void setExpressions(List<IExpression> expressions) {
        this.expressions = expressions;
    }

    /**
     * 添加表达式到链式表达式中
     * @param expression 表达式数组
     */
    public void addExpression(IExpression... expressions) {
        if (this.expressions == null)
            this.expressions = new ArrayList<IExpression>();
        CollectionUtils.addAll(this.expressions, expressions);
    }

    /**
     * 判断是否为‘或’逻辑操作
     * @return 布尔值
     */
    public boolean isOr() {
        return isOr;
    }

    /**
     * 设置是否为‘或’逻辑操作
     * @param isOr 布尔值
     */
    public void setOr(boolean isOr) {
        this.isOr = isOr;
    }

    @Override
    public String getType() {
        return TYPE_CHAINLOGIC;
    }

    /**
     * 取得表达式大小
     * @return 表达式大小
     */
    public int size() {
        if (expressions == null)
            return 0;
        return expressions.size();
    }
}
