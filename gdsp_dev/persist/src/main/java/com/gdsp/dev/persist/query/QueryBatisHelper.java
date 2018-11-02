package com.gdsp.dev.persist.query;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort.Order;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;

/**
 * 查询助手类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public final class QueryBatisHelper {

    /**
     * 条件名称
     */
    public final static String MYBATIS_CON = "_CONDITION_";

    /**
     * 取得QL工厂对象
     * @return 工厂对象
     */
    public static IBatisTranslatorService getQueryTranslatorService() {
        return AppContext.getContext().lookup(IBatisTranslatorService.class);
    }

    /**
     * 取得一组表达式的查询条件
     * @param expression 表达式
     * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
     * @return 查询条件
     */
    public static Map<String, Object> getCondition(Condition condition) {
        return getCondition(null, condition);
    }

    /**
     * 取得一组表达式的查询条件
     * @param expression 表达式
     * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
     * @return 查询条件
     */
    /**
     * 取得一组表达式的查询条件
     * @param paramKey
     * @param condition
     * @return
     */
    public static Map<String, Object> getCondition(String paramKey, Condition condition) {
        if (condition == null)
            return null;
        return getQueryTranslatorService().toCondition(paramKey, condition);
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

    /**
     * 取得排序串
     * @param sort 排序对象
     * @param froms 别名映射
     * @return 排序串
     */
    public static String getSort(Sorter sort, String... froms) {
        if (sort == null)
            return null;
        Iterator<Order> it = sort.iterator();
        String orderString = "";
        while (it.hasNext()) {
            Order order = it.next();
            String protperty = getFullPropertyName(order.getProperty(), froms);
            if (orderString.length() > 0)
                orderString += ",";
            orderString += protperty + " " + order.getDirection().toString();
        }
        return orderString;
    }

    /**
     * 将条件转化为带索引的条件串
     * @param where 原始条件
     * @param startIndex 开始索引
     * @return 转化后的条件串
     */
    public static String getIndexCondition(String where, int startIndex) {
        if (where == null || !where.contains("?"))
            return where;
        if (startIndex == 0)
            startIndex++;
        String[] parts = where.split("\\?");
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (String part : parts) {
            if (part.length() == 0)
                continue;
            result.append(part);
            count++;
            if (count < parts.length) {
                result.append("?").append(startIndex);
                startIndex++;
            }
        }
        return result.toString();
    }
}
