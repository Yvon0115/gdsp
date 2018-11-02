package com.gdsp.dev.web.mvc.tags;

import java.util.Map;

/**
 * 模板指令接口
 * @author yaboocn
 * @version 1.0 2010-3-1
 * @since 1.0
 */
public abstract class SingletonTemplateTag implements TemplateTag {

    /**
     * 按线程保存属性变量
     */
    private ThreadLocal<Map<String, Object>> threadLocalPropteis = new ThreadLocal<>();

    @Override
    public void setProperties(Map<String, Object> properties) {
        threadLocalPropteis.set(properties);
    }

    public Map<String, Object> getProperties() {
        return threadLocalPropteis.get();
    }

    @Override
    public void close() {
        threadLocalPropteis.remove();
    }

    @Override
    public boolean useBody() {
        return false;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
