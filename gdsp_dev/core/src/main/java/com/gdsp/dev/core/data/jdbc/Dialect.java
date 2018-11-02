package com.gdsp.dev.core.data.jdbc;

/**
 * jdbc方言接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface Dialect {

    /**
     * 获取分页sql
     * @param sql 原始查询sql
     * @param offset 开始记录索引（从0开始计数）
     * @param limit 每页记录大小
     * @return 数据库相关的分页sql
     */
    String getPaginationSql(String sql, int offset, int limit);

    /**
     * 取得日期类型字段作为查询条件的表达式
     * @param columnName 字段名
     * @param format 日期格式
     * @return 表达式
     */
    String getDateColumnCondition(String columnName, String format);

    /**
     * 字符串截取
     * @param field 字段名也可以是带单引号字符串
     * @param pos 起始位置
     * @return 转换后串
     */
    String substr(String field, String pos);

    /**
     * 字符串截取
     * @param field 字段名也可以是带单引号字符串
     * @param pos 起始位置
     * @param len 截取长度
     * @return 转换后串
     */
    String substr(String field, String pos, String len);

    /**
     * 字符串截取
     * @param field 字段名也可以是带单引号字符串
     * @return 转换后串
     */
    String length(String field);

    /**
     * 字符连接
     * @param fields 多个字段或字符串
     * @return 连接后串
     */
    String concat(String... fields);
}