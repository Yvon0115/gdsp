package com.gdsp.dev.persist.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BaseWrapper;

/**
 * 保证所有map键值都是全小写或全大写
 *
 * @author Paul.Yang E-mail:yaboocn@qq.com
 * @version 1.0 2015-12-17
 * @since 1.7
 */
public class CaseMapWrapper extends BaseWrapper {

    /**
     * 是否为小写
     */
    private boolean             lowerCase = true;
    /**
     * 实际存储的map
     */
    private Map<String, Object> map;

    /**
     * 元对象
     * @param metaObject 元的对象
     * @param map map对象
     */
    public CaseMapWrapper(MetaObject metaObject, Map<String, Object> map, boolean lowerCase) {
        super(metaObject);
        this.lowerCase = lowerCase;
        this.map = map;
        if (!map.isEmpty()) {
            HashMap<String, Object> temp = new HashMap<>();
            for (String key : map.keySet()) {
                String nkey = key.toLowerCase();
                if (nkey.equals(key))
                    continue;
                temp.put(nkey, map.get(key));
            }
            map.putAll(temp);
        }
    }

    /**
     * 转换大小写
     * @param name 名称
     * @return 根据设置转换大小写
     */
    protected String convertCase(String name) {
        return lowerCase ? name.toLowerCase() : name.toUpperCase();
    }

    @Override
    public Object get(PropertyTokenizer prop) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, map);
            return getCollectionValue(prop, collection);
        } else {
            return map.get(convertCase(prop.getName()));
        }
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, map);
            setCollectionValue(prop, collection, value);
        } else {
            map.put(convertCase(prop.getName()), value);
        }
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        return name;
    }

    @Override
    public String[] getGetterNames() {
        return map.keySet().toArray(new String[map.keySet().size()]);
    }

    @Override
    public String[] getSetterNames() {
        return map.keySet().toArray(new String[map.keySet().size()]);
    }

    @Override
    public Class<?> getSetterType(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                return Object.class;
            } else {
                return metaValue.getSetterType(prop.getChildren());
            }
        } else {
            name = convertCase(name);
            if (map.get(name) != null) {
                return map.get(name).getClass();
            } else {
                return Object.class;
            }
        }
    }

    public Class<?> getGetterType(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                return Object.class;
            } else {
                return metaValue.getGetterType(prop.getChildren());
            }
        } else {
            name = convertCase(name);
            if (map.get(name) != null) {
                return map.get(name).getClass();
            } else {
                return Object.class;
            }
        }
    }

    public boolean hasSetter(String name) {
        return true;
    }

    public boolean hasGetter(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            if (map.containsKey(convertCase(prop.getIndexedName()))) {
                MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
                if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                    return true;
                } else {
                    return metaValue.hasGetter(prop.getChildren());
                }
            } else {
                return false;
            }
        } else {
            return map.containsKey(convertCase(prop.getName()));
        }
    }

    public MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        set(prop, map);
        return MetaObject.forObject(map, metaObject.getObjectFactory(), metaObject.getObjectWrapperFactory());
    }

    public boolean isCollection() {
        return false;
    }

    public void add(Object element) {
        throw new UnsupportedOperationException();
    }

    public <E> void addAll(List<E> element) {
        throw new UnsupportedOperationException();
    }
}
