package com.gdsp.dev.web.scanner;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import com.gdsp.dev.core.scan.IBeanScannerRule;

/**
 * @author paul.yang
 * @version 1.0 15-1-9
 * @since 1.6
 */
public class InterceptorScanRule implements IBeanScannerRule {

    /**
     * 扫描的包路径
     */
    private String basePackage = null;

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return Interceptor.class;
    }

    @Override
    public Class<?> getMarkerInterface() {
        return null;
    }

    @Override
    public boolean isOnlyScanInterface() {
        return false;
    }

    @Override
    public boolean isOnlyScanConcreteClass() {
        return true;
    }

    @Override
    public String[] getPropertieNames() {
        return null;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public Class<? extends FactoryBean> getFactoryBeanClass() {
        return ScanMappedInterceptorFactory.class;
    }

    @Override
    public Class<?> getBeanSuperClass() {
        return null;
    }

    @Override
    public String[] getBasePackages() {
        if (basePackage == null)
            return new String[0];
        return StringUtils.tokenizeToStringArray(basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
    }

    @Override
    public boolean handle(GenericBeanDefinition definition) {
        return true;
    }

    @Override
    public boolean isOrder() {
        return true;
    }

    /**
     * 取得扫描的基础包路径
     * @return 包路径,多个用逗号隔开
     */
    public String getBasePackage() {
        return basePackage;
    }

    /**
     * 设置扫描的基础包路径
     * @param basePackage 包路径,多个用逗号隔开
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
