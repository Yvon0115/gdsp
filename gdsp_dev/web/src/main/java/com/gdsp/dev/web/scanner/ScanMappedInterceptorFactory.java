package com.gdsp.dev.web.scanner;

import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.MappedInterceptor;

import com.gdsp.dev.core.scan.IScanFactoryBean;

/**
 * 拦截器工厂类
 * @author paul.yang
 * @version 1.0 15-1-9
 * @since 1.6
 */
public class ScanMappedInterceptorFactory implements IScanFactoryBean<MappedInterceptor> {

    private Class<HandlerInterceptor> interceptorClass = null;

    @SuppressWarnings("rawtypes")
	@Override
    public void setBeanSuperClass(Class clazz) {}

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void setOriginalClass(Class clazz) {
        interceptorClass = clazz;
    }

    @Override
    public MappedInterceptor getObject()  {
        Interceptor interceptor = interceptorClass.getAnnotation(Interceptor.class);
        return new MappedInterceptor(interceptor.value(), interceptor.excudes(), BeanUtils.instantiateClass(interceptorClass));
    }

    @Override
    public Class<?> getObjectType() {
        return MappedInterceptor.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
