package com.gdsp.dev.persist.ext;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

/**
 * 扩展MapperRegistry，以更好的支持annotaion
 * 
 * @author paul.yang
 * @version 1.0 2014年10月28日
 * @since 1.6
 */
public class MybatisMapperRegistry extends MapperRegistry {

    protected MybatisConfiguration                       config;
    protected final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<Class<?>, MapperProxyFactory<?>>();

    /**
     * 构造方法
     * 
     * @param config
     */
    public MybatisMapperRegistry(MybatisConfiguration config) {
        super(null);
        this.config = config;
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers
                .get(type);
        if (mapperProxyFactory == null)
            throw new BindingException("Type " + type
                    + " is not known to the MapperRegistry.");
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new BindingException("Error getting mapper instance. Cause: "
                    + e, e);
        }
    }

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {
                throw new BindingException("Type " + type
                        + " is already known to the MapperRegistry.");
            }
            boolean loadCompleted = false;
            try {
                knownMappers.put(type, new MapperProxyFactory<T>(type));
                // It's important that the type is added before the parser is
                // run
                // otherwise the binding may automatically be attempted by the
                // mapper parser. If the type is already known, it won't try.
                MybatisMapperAnnotationBuilder parser = new MybatisMapperAnnotationBuilder(
                        config, type);
                parser.parse();
                loadCompleted = true;
            } finally {
                if (!loadCompleted) {
                    knownMappers.remove(type);
                }
            }
        }
    }

    /**
     * @return the config
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(MybatisConfiguration config) {
        this.config = config;
    }

    /**
     * @since 3.2.2
     */
    public Collection<Class<?>> getMappers() {
        return Collections.unmodifiableCollection(knownMappers.keySet());
    }
}
