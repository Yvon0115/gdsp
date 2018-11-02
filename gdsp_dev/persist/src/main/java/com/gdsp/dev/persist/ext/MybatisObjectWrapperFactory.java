package com.gdsp.dev.persist.ext;

import java.util.Map;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.CollectionWrapper;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.springframework.data.domain.Page;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.query.QueryBatisHelper;

/**
 * mybatis对象包装工厂类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class MybatisObjectWrapperFactory extends DefaultObjectWrapperFactory {

    /**
     * map结果类型key的大小写转换设置，0：默认不计转换大小写 1 LowerCase转换 2 UpperCase转换
     */
    private int resultMapKeyLowerCase = 0;

    /* (non-Javadoc)
     * @see org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory#hasWrapperFor(java.lang.Object)
     */
    @Override
    public boolean hasWrapperFor(Object object) {
        if (object instanceof Page || object instanceof Condition) {
            return true;
        }
        if (object instanceof ParamMap) {
            return true;
        }
        if (object instanceof Map && resultMapKeyLowerCase > 0) {
            return true;
        }
        return super.hasWrapperFor(object);
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory#getWrapperFor(org.apache.ibatis.reflection.MetaObject, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        if (object instanceof Page) {
            return new CollectionWrapper(metaObject, ((Page<Object>) object).getContent());
        }
        if (object instanceof Condition) {
            return new MapWrapper(metaObject, QueryBatisHelper.getCondition((Condition) object));
        }
        if (object instanceof ParamMap) {
            Map<String, Object> params = (Map<String, Object>) object;
            convertCondition(params, null);
            return new MapWrapper(metaObject, params);
        }
        if (object instanceof Map) {
            return new CaseMapWrapper(metaObject, (Map<String, Object>) object, resultMapKeyLowerCase < 2);
        }
        return super.getWrapperFor(metaObject, object);
    }

    /**
     * 递归将参数中的Searcher转换为Map串
     * @param map 可能包含Map的参数
     * @param key Searcher的ognl访问路径(如,设定参数为@Param("a")Condition con,则key为a
     */
    public static void convertCondition(Map<String, Object> map, String key) {
        for (Map.Entry<String, Object> e : map.entrySet()) {
            if (e.getValue() instanceof Condition) {
                String keyPath;
                if (key == null) {
                    keyPath = e.getKey();
                } else {
                    keyPath = key + "." + e.getKey();
                }
                map.put(keyPath, QueryBatisHelper.getCondition(keyPath, (Condition) e.getValue()));
            } else if (e.getValue() instanceof Map) {
                String keyPath;
                if (key == null) {
                    keyPath = e.getKey();
                } else {
                    keyPath = key + "." + e.getKey();
                }
                @SuppressWarnings("unchecked")
                Map<String, Object> m = (Map<String, Object>) e.getValue();
                convertCondition(m, keyPath);
            }
        }
    }

    /**
     * 设置是否对map key进行大小写转换
     * @param resultMapKeyCase 整形
     */
    public void setResultMapKeyCase(int resultMapKeyCase) {
        this.resultMapKeyLowerCase = resultMapKeyCase;
    }
}
