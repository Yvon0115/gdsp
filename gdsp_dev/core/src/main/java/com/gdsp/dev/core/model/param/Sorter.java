package com.gdsp.dev.core.model.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

/**
 * 重新实现Sort接口,解决序列化问题
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class Sorter extends Sort implements Serializable {

    /**
     * 序列id
     */
    private static final long  serialVersionUID = 1950586375398326477L;
    /**
     * 为了忽略父类的内容，并且构建父类对象，需要一个默认排序规则
     */
    private final static Order ignoreOrder      = new Order("ignore");
    /**
     * 排序列表
     */
    private List<Sort.Order>   newOrders;

    /**
     * 将排序数组转化成列表
     * @param orders 排序数组
     * @return 排序列表
     */
    public static List<Sort.Order> toList(Sort.Order... orders) {
        if (orders == null)
            return null;
        ArrayList<Sort.Order> os = new ArrayList<Sort.Order>();
        for (Sort.Order order : orders) {
            os.add(order);
        }
        return os;
    }

    /**
     * 构造方法通，过{@link Sort.Order}数组构造{@link Sorter}
     * @param orders 排序对象数组
     */
    public Sorter(Sort.Order... orders) {
        this(toList(orders));
    }

    /**
     * 构造方法 {@link Sorter}.
     * @param orders 排序对象数组.
     */
    public Sorter(List<Sort.Order> orders) {
        super(toList(ignoreOrder));
        this.newOrders = orders;
    }

    /**
     * 构造方法 {@link Sorter}，使用默认排序方向
     * @param properties 属性字符串列表
     */
    public Sorter(String... properties) {
        this(Sorter.DEFAULT_DIRECTION, properties);
    }

    /**
     * 构造方法 {@link Sorter}
     * @param direction 排序方向
     * @param properties 属性数组
     */
    public Sorter(Sort.Direction direction, String... properties) {
        this(direction, properties == null ? new ArrayList<String>() : Arrays.asList(properties));
    }

    /**
     * 构造方法 {@link Sorter}
     * @param direction 排序方向
     * @param properties 属性列表
     */
    public Sorter(Direction direction, List<String> properties) {
        super(toList(ignoreOrder));
        if (properties == null || properties.isEmpty()) {
            return;
        }
        this.newOrders = new ArrayList<Sort.Order>(properties.size());
        for (String property : properties) {
            this.newOrders.add(new Order(direction, property));
        }
    }

    /**
     * 合并两个组排序规则
     * @param sort 另一组排序规则
     * @return 合并后排序规则
     */
    public Sorter and(Sorter sort) {
        if (sort == null) {
            return this;
        }
        ArrayList<Sort.Order> these = new ArrayList<Sort.Order>(this.newOrders);
        for (Sort.Order order : sort) {
            these.add(order);
        }
        return new Sorter(these);
    }

    /**
     * Returns the order registered for the given property.
     * @param property the given property
     * @return {@link Sort.Order} object
     */
    public Sort.Order getOrderFor(String property) {
        for (Sort.Order order : this) {
            if (order.getProperty().equals(property)) {
                return order;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Sort.Order> iterator() {
        return this.newOrders.iterator();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Sorter)) {
            return false;
        }
        Sorter that = (Sorter) obj;
        return this.newOrders.equals(that.newOrders);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + newOrders.hashCode();
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return StringUtils.collectionToCommaDelimitedString(newOrders);
    }

    /**
     * 自定义排序对象，解决hessian2和dubbo序列化问题
     */
    public static class Order extends Sort.Order implements Serializable {

        /**
         * 序列id
         */
        private static final long serialVersionUID = -8481058628274756455L;

        public Order(String property) {
            this(DEFAULT_DIRECTION, property == null ? "_ignore" : property);
        }

        public Order(Direction direction, String property) {
            super(direction, property == null ? "_ignore" : property);
        }
    }
}