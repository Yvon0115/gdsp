package com.gdsp.dev.core.scan;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;

/**
 * 通过扫描指定annotation完成
 *
 * @author yaboocn
 * @version 1.0 2014年5月7日
 * @since 1.7
 */
public class BeanScannerConfigurer implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {

    /**
     * spring 上下文
     */
    private ApplicationContext     applicationContext;
    /**
     * 当前扫描对象的beanName
     */
    private String                 beanName;
    /**
     * 是否处理属性配置
     */
    private boolean                processPropertyPlaceHolders;
    /**
     * 名称
     */
    private BeanNameGenerator      nameGenerator;
    /**
     * 类扫描规则列表
     */
    private List<IBeanScannerRule> rules = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (this.processPropertyPlaceHolders) {
            processPropertyPlaceHolders();
        }
        for (IBeanScannerRule rule : rules) {
            ClassPathBeanScanner scanner = new ClassPathBeanScanner(registry, rule);
            scanner.setResourceLoader(this.applicationContext);
            scanner.setBeanNameGenerator(this.nameGenerator);
            scanner.registerFilters();
            scanner.scan(rule.getBasePackages());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.rules, "Property 'rules' is required");
        isTrue(this.rules.size() > 0, "Property 'rules' is required");
    }

    /**
     * 设置是否处理属性变量替换
     *
     * @param processPropertyPlaceHolders 布尔值
     */
    public void setProcessPropertyPlaceHolders(boolean processPropertyPlaceHolders) {
        this.processPropertyPlaceHolders = processPropertyPlaceHolders;
    }

    /**
     * 取得名称生成器
     *
     * @return 名称生成器
     */
    public BeanNameGenerator getNameGenerator() {
        return nameGenerator;
    }

    /**
     * 设置名称生成器
     *
     * @param nameGenerator 名称生成器
     */
    public void setNameGenerator(BeanNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    /**
     * 取得规则列表
     * @return 规则列表
     */
    public List<IBeanScannerRule> getRules() {
        return rules;
    }

    /**
     * 设置规则列表
     * @param rules 规则列表
     */
    public void setRules(List<IBeanScannerRule> rules) {
        this.rules = rules;
    }

    /**
     * 属性文件处理
     */
    protected void processPropertyPlaceHolders() {
        Map<String, PropertyResourceConfigurer> prcs = applicationContext.getBeansOfType(PropertyResourceConfigurer.class);
        if (prcs.isEmpty() || !(applicationContext instanceof GenericApplicationContext))
            return;
        BeanDefinition mapperScannerBean = ((GenericApplicationContext) applicationContext)
                .getBeanFactory().getBeanDefinition(beanName);
        // PropertyResourceConfigurer does not expose any methods to explicitly perform
        // property placeholder substitution. Instead, create a BeanFactory that just
        // contains this mapper scanner and post process the factory.
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerBeanDefinition(beanName, mapperScannerBean);
        for (PropertyResourceConfigurer prc : prcs.values()) {
            prc.postProcessBeanFactory(factory);
        }
    }
}
