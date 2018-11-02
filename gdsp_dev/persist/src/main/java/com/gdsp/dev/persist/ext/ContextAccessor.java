package com.gdsp.dev.persist.ext;

import java.util.Map;

import org.apache.ibatis.ognl.OgnlException;
import org.apache.ibatis.ognl.PropertyAccessor;
import org.apache.ibatis.scripting.xmltags.DynamicContext;

import com.gdsp.dev.core.data.jdbc.DialectHelper;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.query.QueryBatisHelper;

/**
 * 用于覆盖DynamicContext的Ognl属性访问控制
 * @author paul.yang
 * @version 1.0 2014年10月27日
 * @since 1.6
 */
public class ContextAccessor implements PropertyAccessor {

    /* (non-Javadoc)
     * @see org.apache.ibatis.ognl.PropertyAccessor#getProperty(java.util.Map, java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object getProperty(Map context, Object target, Object name)
            throws OgnlException {
        Map map = (Map) target;
        Object result = map.get(name);
        if (result != null) {
            return result;
        }
        Object parameterObject = map.get(DynamicContext.PARAMETER_OBJECT_KEY);
        if (!map.containsKey("_searcher_convert")) {
            map.put("_searcher_convert", "Y");
            if (parameterObject instanceof Condition) {
                parameterObject = QueryBatisHelper.getCondition((Condition) parameterObject);
                map.put(DynamicContext.PARAMETER_OBJECT_KEY, parameterObject);
            } else if (parameterObject instanceof Map) {
                MybatisObjectWrapperFactory.convertCondition((Map) parameterObject, null);
            }
        }
        if ("utils".equalsIgnoreCase(name.toString())) {
            return DialectHelper.getDialect();
        }
        if (parameterObject instanceof Map) {
            return ((Map) parameterObject).get(name);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.ognl.PropertyAccessor#setProperty(java.util.Map, java.lang.Object, java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void setProperty(Map context, Object target, Object name, Object value)
            throws OgnlException {
        Map map = (Map) target;
        map.put(name, value);
    }
}