package com.gdsp.dev.core.model.query;

/**
 * 查询条件逻辑表达式表达式
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class LogicExpression implements IExpression {

    /**
     * 序列id
     */
    private static final long serialVersionUID = -1756448873214671761L;
    /**
     * 左侧表达式
     */
    private IExpression       left             = null;
    /**
     * 右侧表达式
     */
    private IExpression       right            = null;
    /**
     * 操作符
     */
    private boolean           isOr             = false;

    /**
     * 构造方法
     */
    public LogicExpression() {}

    /**
     * 表达式构造方法
     * @param propertyName 属性名
     * @param value 属性值
     * @param operator 操作符
     */
    public LogicExpression(IExpression left, IExpression right, boolean isOr) {
        setLeft(left);
        setRight(right);
        setOr(isOr);
    }

    /**
     * 取得左侧表达式
     * @return 左侧表达式
     */
    public IExpression getLeft() {
        return left;
    }

    /**
     * 设置左侧表达式
     * @param left 左侧表达式
     */
    public void setLeft(IExpression left) {
        this.left = left;
    }

    /**
     * 取得右侧表达式
     * @return 右侧表达式
     */
    public IExpression getRight() {
        return right;
    }

    /**
     * 设置右侧表达式
     * @param right 右侧表达式
     */
    public void setRight(IExpression right) {
        this.right = right;
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
        return TYPE_LOGIC;
    }
}
