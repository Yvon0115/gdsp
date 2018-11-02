package com.gdsp.dev.core.scan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanMetadataAttributeAccessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

/**
 * Bean扫描器根据定义规则进行扫描
 * @author paul.yang
 * @version 1.0 15-1-8
 * @since 1.6
 */
public class ClassPathBeanScanner extends ClassPathBeanDefinitionScanner {

    /**
     * 扫描规则
     */
    private IBeanScannerRule rule   = null;
    /**
     * 是否已排序
     */
    private boolean          sorted = false;
    /**
     * 日志变量
     */
    protected static final Logger                    logger             = LoggerFactory.getLogger(ClassPathBeanScanner.class);

    /**
     * 构造方法
     * @param registry 注册
     * @param rule 扫描规则
     */
    public ClassPathBeanScanner(BeanDefinitionRegistry registry, IBeanScannerRule rule) {
        super(registry, false);
        this.rule = rule;
    }

    /**
     * 根据规则注册类扫描过滤器
     */
    public void registerFilters() {
        boolean acceptAllInterfaces = true;
        // if specified, use the given annotation and / or marker interface
        if (this.rule.getAnnotationClass() != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.rule.getAnnotationClass()));
            acceptAllInterfaces = false;
        }
        // override AssignableTypeFilter to ignore matches on the actual marker interface
        if (this.rule.getMarkerInterface() != null) {
            addIncludeFilter(new AssignableTypeFilter(this.rule.getMarkerInterface()) {

                @Override
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
            acceptAllInterfaces = false;
        }
        if (acceptAllInterfaces) {
            // default include filter that accepts all classes
            addIncludeFilter(new TypeFilter() {

                public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                    return true;
                }
            });
        }
        if (this.rule.isOnlyScanInterface()) {
            // exclude 不是接口的
            addExcludeFilter(new TypeFilter() {

                public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                    return !metadataReader.getClassMetadata().isInterface();
                }
            });
        } else if (this.rule.isOnlyScanConcreteClass()) {
            // exclude 不是类的
            addExcludeFilter(new TypeFilter() {

                public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                    return !metadataReader.getClassMetadata().isConcrete();
                }
            });
        }
        // exclude package-info.java
        addExcludeFilter(new TypeFilter() {

            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                String className = metadataReader.getClassMetadata().getClassName();
                return className.endsWith("package-info");
            }
        });
    }

    /**
     * 扫描指定包路径按要求生成bean定义
     * @param basePackages 包路径数组
     * @return bean定义集合
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> definitions = super.doScan(basePackages);
        if (definitions.isEmpty()) {
            logger.warn("No Bean Class in rule was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
            return definitions;
        }
        //排序处理
        if (rule.isOrder()) {
            ArrayList<BeanDefinitionHolder> list = new ArrayList<BeanDefinitionHolder>();
            list.addAll(definitions);
            Collections.sort(list, new Comparator<BeanDefinitionHolder>() {

                @Override
                public int compare(BeanDefinitionHolder o1, BeanDefinitionHolder o2) {
                    Class<?> clazz1, clazz2;
                    try {
                        clazz1 = ClassUtils.forName(o1.getBeanDefinition().getBeanClassName(), getResourceLoader().getClassLoader());
                    } catch (ClassNotFoundException ex) {
                        // Class not regularly loadable - can't determine a match that way.
                        logger.debug(ex.getMessage(), ex);
                        return 1;
                    }
                    try {
                        clazz2 = ClassUtils.forName(o2.getBeanDefinition().getBeanClassName(), getResourceLoader().getClassLoader());
                    } catch (ClassNotFoundException e) {
                        // Class not regularly loadable - can't determine a match that way.
                        logger.debug(e.getMessage(), e);
                        return -1;
                    }
                    Order order1 = clazz1.getAnnotation(Order.class);
                    Order order2 = clazz2.getAnnotation(Order.class);
                    if (order1 == null) {
                        if (order2 != null)
                            return 1;
                        else
                            return 0;
                    } else if (order2 == null) {
                        return -1;
                    } else if (order1.value() == order2.value()) {
                        return 0;
                    } else if (order1.value() < order2.value()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
            definitions.clear();
            definitions.addAll(list);
        }
        setSorted(true);
        BeanMetadataAttributeAccessor ruleWrapper = new BeanMetadataAttributeAccessor();
        ruleWrapper.setSource(rule);
        BeanDefinitionRegistry registry = getRegistry();
        for (BeanDefinitionHolder holder : definitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            if (logger.isDebugEnabled()) {
                logger.debug("Creating MapperFactoryBean with name '" + holder.getBeanName()
                        + "' and '" + definition.getBeanClassName() + "' mapperInterface");
            }
            if (!rule.handle(definition))
                continue;
            /*handling when FactoryBeanClass in rule is null*/
            if (rule.getFactoryBeanClass() != null) {
                if (IScanFactoryBean.class.isAssignableFrom(rule.getFactoryBeanClass())) {
                    definition.getPropertyValues().add("beanSuperClass", rule.getBeanSuperClass());
                    definition.getPropertyValues().add("originalClass", definition.getBeanClassName());
                }
                definition.setBeanClass(rule.getFactoryBeanClass());
            }
            String[] propNames = rule.getPropertieNames();
            if (propNames != null && propNames.length > 0) {
                for (String propName : propNames) {
                    Object o = ruleWrapper.getAttribute(propName);
                    if (o != null) {
                        definition.getPropertyValues().add("sqlSessionFactory", o);
                    }
                }
            }
            //definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            if (checkCandidate(holder.getBeanName(), definition)) {
                registerBeanDefinition(holder, registry);
            }
        }
        return definitions;
    }

    /**
     * 是否已对bean定义进行排序
     * @return 布尔值
     */
    public boolean isSorted() {
        return sorted;
    }

    /**
     * 设置是否已排序
     * @param sorted 布尔值
     */
    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    /**
     * {@inheritDoc}
     */
    protected void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) {
        if (!isSorted()) {
            return;
        }
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            logger.warn("Skipping FactoryBeanClass with name '" + beanName
                    + "' and '" + beanDefinition.getBeanClassName() + "' super class"
                    + ". Bean already defined with the same name!");
            return false;
        }
    }
}
