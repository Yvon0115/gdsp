package com.gdsp.dev.persist.ext;

import java.util.List;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.springframework.data.domain.Page;

/**
 * mybatis对象工厂类,用于创建返回对象
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class MybatisObjectFactory extends DefaultObjectFactory {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 6555120406412614826L;

    /* (non-Javadoc)
     * @see org.apache.ibatis.reflection.factory.DefaultObjectFactory#isCollection(java.lang.Class)
     */
    @Override
    public <T> boolean isCollection(Class<T> type) {
        if (Page.class.isAssignableFrom(type)) {
            return true;
        }
        return super.isCollection(type);
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.reflection.factory.DefaultObjectFactory#create(java.lang.Class, java.util.List, java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes,
            List<Object> constructorArgs) {
        if (Page.class.isAssignableFrom(type)) {
            return (T) PageHelper.packPage();
        }
        return super.create(type, constructorArgTypes, constructorArgs);
    }
}
