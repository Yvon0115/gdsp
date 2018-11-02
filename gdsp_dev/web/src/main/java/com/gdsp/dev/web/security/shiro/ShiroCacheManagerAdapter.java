package com.gdsp.dev.web.security.shiro;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * shiro缓存管理适配器
 * @author paul.yang
 * @version 1.0 2014年10月29日
 * @since 1.6
 */
public class ShiroCacheManagerAdapter implements CacheManager {

    /**
     * 基于spring的缓存管理
     */
    private org.springframework.cache.CacheManager cacheManager;

    /**
     * 设置spring cache manager
     * @param cacheManager pring的缓存管理
     */
    public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @SuppressWarnings("unchecked")
	@Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        org.springframework.cache.Cache springCache = cacheManager.getCache(name);
        return new SpringCacheWrapper(springCache);
    }

    /**
     * shiro缓存适配器
     * @author paul.yang
     * @version 1.0 2014年10月29日
     * @since 1.6
     */
    @SuppressWarnings("rawtypes")
	static class SpringCacheWrapper implements Cache {

        /**
         * spring缓存对象
         */
        private org.springframework.cache.Cache springCache;

        /**
         * 构造方法
         * @param springCache 基于spring的缓存对象
         */
        SpringCacheWrapper(org.springframework.cache.Cache springCache) {
            this.springCache = springCache;
        }

        @Override
        public Object get(Object key) throws CacheException {
            Object value = springCache.get(key);
            if (value instanceof SimpleValueWrapper) {
                return ((SimpleValueWrapper) value).get();
            }
            return value;
        }

        @Override
        public Object put(Object key, Object value) throws CacheException {
            springCache.put(key, value);
            return value;
        }

        @Override
        public Object remove(Object key) throws CacheException {
            springCache.evict(key);
            return null;
        }

        @Override
        public void clear() throws CacheException {
            springCache.clear();
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException("invoke spring cache abstract size method not supported");
        }

        @Override
        public Set keys() {
            throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported");
        }

        @Override
        public Collection values() {
            throw new UnsupportedOperationException("invoke spring cache abstract values method not supported");
        }
    }
}
