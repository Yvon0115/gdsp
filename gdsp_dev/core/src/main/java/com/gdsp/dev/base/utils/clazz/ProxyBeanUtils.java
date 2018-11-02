package com.gdsp.dev.base.utils.clazz;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

/**
 * 动态bean处理工具类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class ProxyBeanUtils {

    /**
     * 创建Bean生成器
     * @param source 原始Bean
     * @param properties 附加属性
     * @return Bean生成器
     */
    public static BeanGenerator createBeanGenerator(Object source, String... properties) {
        BeanMap sourceMap = BeanMap.create(source);
        return createBeanGenerator(sourceMap, properties);
    }

    /**
     * 创建Bean生成器
     * @param source 原始Bean
     * @param propertyMap 附加属性
     * @return Bean生成器
     */
    public static BeanGenerator createBeanGenerator(Object source, Map<String, Class<?>> propertyMap) {
        BeanMap sourceMap = BeanMap.create(source);
        return createBeanGenerator(sourceMap, propertyMap);
    }

    /**
     * 创建Bean生成器
     * @param sourceMap 原始Bean对应beanMap
     * @param properties 附加属性
     * @return Bean生成器
     */
    public static BeanGenerator createBeanGenerator(BeanMap sourceMap, String... properties) {
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(sourceMap.getBean().getClass());
        int count = 0;
        for (String prop : properties) {
            if (sourceMap.containsKey(prop))
                continue;
            generator.addProperty(prop, String.class);
            count++;
        }
        if (count == 0)
            return null;
        return generator;
    }

    /**
     * 创建Bean生成器
     * @param sourceMap 原始Bean对应beanMap
     * @param propertyMap 附加属性
     * @return Bean生成器
     */
    public static BeanGenerator createBeanGenerator(BeanMap sourceMap, Map<String, Class<?>> propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(sourceMap.getBean().getClass());
        Set<String> keySet = propertyMap.keySet();
        int count = 0;
        for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
            String key = (String) i.next();
            if (sourceMap.containsKey(key))
                continue;
            generator.addProperty(key, propertyMap.get(key));
            count++;
        }
        if (count == 0)
            return null;
        return generator;
    }

    /**
     * 创建继承于原始Bean的新对象
     * @param source 原始Bean
     * @param properties 附加属性
     * @return 新对象
     */
    public static <X> X createProxyBean(X source, String... properties) {
        BeanMap sourceMap = BeanMap.create(source);
        BeanGenerator generator = createBeanGenerator(source, properties);
        if (generator == null)
            return source;
        @SuppressWarnings("unchecked")
        X target = (X) generator.create();
        BeanMap targetMap = BeanMap.create(target);
        targetMap.putAll(sourceMap);
        return target;
    }

    /**
     * 创建继承于原始Bean的新对象
     * @param source 原始Bean
     * @param propertyMap 附加属性
     * @return 新对象
     */
    public static <X> X createProxyBean(X source, Map<String, Class<?>> propertyMap) {
        BeanMap sourceMap = BeanMap.create(source);
        BeanGenerator generator = createBeanGenerator(source, propertyMap);
        if (generator == null)
            return source;
        @SuppressWarnings("unchecked")
        X target = (X) generator.create();
        BeanMap targetMap = BeanMap.create(target);
        targetMap.putAll(sourceMap);
        return target;
    }
}