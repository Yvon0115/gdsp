package com.gdsp.dev.core.data.jdbc;

import org.apache.commons.lang3.StringUtils;

/**
 * 默认方言实现
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class DefaultDialect implements Dialect {

    @Override
    public String getPaginationSql(String sql, int offset, int limit) {
        return sql + " limit " + offset + "," + limit;
    }

    @Override
    public String getDateColumnCondition(String columnName, String format) {
        return columnName;
    }

    @Override
    public String substr(String field, String pos) {
        return "substring(" + field + "," + pos + ")";
    }

    @Override
    public String substr(String field, String pos, String len) {
        return "substring(" + field + "," + pos + "," + len + ")";
    }

    @Override
    public String length(String field) {
        return "length(" + field + ")";
    }

    @Override
    public String concat(String... fields) {
        return StringUtils.join(fields, "+");
    }
}
