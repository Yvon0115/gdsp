package com.gdsp.dev.core.data.jdbc;

import org.apache.commons.lang3.StringUtils;

/**
 * Oracel数据库方言
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class OracleDialect extends DefaultDialect implements Dialect {

    @Override
    public String getPaginationSql(String sql, int offset, int limit) {
        return "select * from (select rownum rn, t.* from (" + sql
                + ") t"
                + ") t1 where t1.rn > " + offset + " and t1.rn <= " + (offset + limit);
    }

    @Override
    public String getDateColumnCondition(String columnName, String format) {
        return "to_char(" + columnName + ",'" + format + "')";
    }

    @Override
    public String substr(String field, String pos) {
        return "substr(" + field + "," + pos + ")";
    }

    @Override
    public String substr(String field, String pos, String len) {
        return "substr(" + field + "," + pos + "," + len + ")";
    }

    @Override
    public String concat(String... fields) {
        return StringUtils.join(fields, "||");
    }
}