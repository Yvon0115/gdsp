package com.gdsp.dev.core.data.query;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;

/**
 * 查询助手类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public final class QueryHelper {

    /**
     * 取得QL工厂对象
     * @return 工厂对象
     */
    public static IExpressionTranslatorService getQueryTranslatorService() {
        return AppContext.getContext().lookup(IExpressionTranslatorService.class);
    }

    /**
     * 取得一组表达式的预编译条件
     * @param condition 查询条件对象
     * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
     * @return 预编译条件
     */
    public static PrepareCondition getPreCondition(Condition condition) {
        if (condition == null)
            return null;
        return getQueryTranslatorService().getPreCondition(condition.toExpressions(), condition.getFromAlias());
    }

    /**
     * 取得一组表达式的查询条件
     * @param condition 查询条件对象
     * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
     * @return 查询条件
     */
    public static String getCondition(Condition condition) {
        if (condition == null)
            return null;
        return getQueryTranslatorService().getCondition(condition.toExpressions(), condition.getFromAlias());
    }

    /**
     * 根据别名取得别名转化后的属性全名
     * @param property 属性名
     * @param froms 别名映射
     * @return 属性全名
     */
    public static String getFullPropertyName(String property, String... froms) {
        if (froms == null)
            return property;
        Map<String, String> aliases = new HashMap<String, String>();
        String mainFrom = null;
        for (String from : froms) {
            if (from.indexOf(" as ") >= 0) {
                String[] names = from.split(" as ");
                if(names.length==2&&!StringUtils.isEmpty(names[0])&&StringUtils.isEmpty(names[1]))
                {
                    aliases.put(names[0].trim(), names[1].trim());
                    if (mainFrom == null)
                        mainFrom = names[1].trim();
                }
            } else if (from.indexOf(" ") >= 0) {
                String[] names = from.split(" ");
                if(names.length==2&&!StringUtils.isEmpty(names[0])&&StringUtils.isEmpty(names[1]))
                {
                    aliases.put(names[0].trim(), names[1].trim());
                    if (mainFrom == null)
                        mainFrom = names[1].trim();
                }
            }
        }
        int pos = property.indexOf(".");
        if (pos <= 0) {
            if (mainFrom != null)
                return mainFrom += "." + property;
            return property;
        }
        String name = property.substring(0, pos);
        String a = aliases.get(name);
        if (a != null) {
            property = a + property.substring(pos);
        }
        return property;
    }
}
