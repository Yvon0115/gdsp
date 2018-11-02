/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.core.scan;

import org.springframework.beans.factory.FactoryBean;

/**
 * 扫描bean工厂类
 * @author paul.yang
 * @version 1.0 15-1-8
 * @since 1.6
 */
public interface IScanFactoryBean<T> extends FactoryBean<T> {

    /**
     * 设置bean继承的接口或父类
     * @param clazz 接口或父类
     */
    void setBeanSuperClass(Class<T> clazz);

    /**
     * 设置bean继承的接口或父类
     * @param clazz 接口或父类
     */
    void setOriginalClass(Class<T> clazz);
}
