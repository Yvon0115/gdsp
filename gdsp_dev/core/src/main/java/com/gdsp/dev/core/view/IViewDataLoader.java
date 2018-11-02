package com.gdsp.dev.core.view;

/**
 * 上下文数据隐式加载器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface IViewDataLoader {

    /**
     * 直接取值方式
     * @return 加载的数据
     */
    public Object getValue();

    /**
     * 通过键值获取数据
     * @param parameter 键值
     * @return 加载的数据
     */
    public Object getValue(String... parameter);
}
