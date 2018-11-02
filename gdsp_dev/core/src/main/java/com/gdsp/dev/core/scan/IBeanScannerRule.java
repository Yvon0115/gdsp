/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.core.scan;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * Bean扫描处理接口
 * @author paul.yang
 * @version 1.0 15-1-8
 * @since 1.6
 */
public interface IBeanScannerRule {

    /**
     * 取得需要扫描带有注解限制类,可以为空
     * @return 注解类
     */
    Class<? extends Annotation> getAnnotationClass();

    /**
     * 取得需要扫描的需要继承的接口
     * @return 扫描过滤接口
     */
    Class<?> getMarkerInterface();

    /**
     * 是否只扫描接口
     * @return 布尔值
     */
    boolean isOnlyScanInterface();

    /**
     * 是否只扫描实现类
     * @return 布尔值
     */
    boolean isOnlyScanConcreteClass();

    /**
     * bean需要从规则配置中注入的属性名
     * @return 属性名列表
     */
    String[] getPropertieNames();

    /**
     * 扫描bean工厂类
     * @return 工厂类
     */
    @SuppressWarnings("rawtypes")
	Class<? extends FactoryBean> getFactoryBeanClass();

    /**
     * bean的实现接口类
     * @return 接口类
     */
    Class<?> getBeanSuperClass();

    /**
     * 取得扫描的包规则数组
     * @return 包规则数组
     */
    String[] getBasePackages();

    /**
     * bean定义处理接口,通常用于合并一些bean
     * @param definition bean定义
     * @return 是否继续处理
     */
    boolean handle(GenericBeanDefinition definition);

    /**
     * 是否排序
     * @return 布尔值
     */
    boolean isOrder();
}
