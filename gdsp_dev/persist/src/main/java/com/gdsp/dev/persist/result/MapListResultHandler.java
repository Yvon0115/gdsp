package com.gdsp.dev.persist.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import com.gdsp.dev.base.exceptions.DevRuntimeException;

/**
 * 基于map+list的结果处理
 * 该处理会把行结果对象指定字段值相同的放到一个List中,并将List以这个字段值为键值放入map中
 * @author paul.yang
 * @version 1.0 2015-7-23
 * @since 1.6
 */
public class MapListResultHandler<E> implements ResultHandler {

    /**
     * 空值时的键值
     */
    public static final String         NULL_KEY = "__null_key__";
    /**
     * 键值字段
     */
    private String                     mapKey   = null;
    /**
     * 结果map
     */
    private final Map<String, List<E>> mappedResults;
    /**
     * 判断行对象是否为map
     */
    private int                        isMap    = -1;

    /**
     * 构造方法
     * @param mapKey 作为键值的字段名
     */
    public MapListResultHandler(String mapKey) {
        this.mapKey = mapKey;
        mappedResults = new HashMap<>();
    }

    /**
     * 构造方法
     * @param mapKey 作为键值的字段名
     * @param map 已有的map对象
     */
    public MapListResultHandler(String mapKey, Map<String, List<E>> map) {
        this.mapKey = mapKey;
        mappedResults = map;
    }

    @Override
    public void handleResult(ResultContext context) {
        @SuppressWarnings("unchecked")
        E row = (E) context.getResultObject();
        String key = getKey(row);
        List<E> list = mappedResults.get(key);
        if (list == null) {
            list = new ArrayList<>();
            mappedResults.put(key, list);
        }
        list.add(row);
    }

    /**
     * 取得结果map对象
     * @return 结果map对象
     */
    public Map<String, List<E>> getResult() {
        return mappedResults;
    }

    /**
     * 取得行键值
     * @param row 行对象
     * @return 行键值
     */
    private String getKey(E row) {
        if (isMap == -1) {
            isMap = (row instanceof Map) ? 1 : 0;
        }
        Object key;
        if (isMap == 1) {
            key = ((Map<?, ?>) row).get(mapKey);
        } else {
            try {
                key = BeanUtils.getProperty(row, mapKey);
            } catch (Exception e) {
                throw new DevRuntimeException("Map key is not exists", e);
            }
        }
        if (key == null)
            return NULL_KEY;
        String k = key.toString();
        if (k.length() == 0)
            return NULL_KEY;
        return k;
    }
}
