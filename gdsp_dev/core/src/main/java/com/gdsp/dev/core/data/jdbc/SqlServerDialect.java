package com.gdsp.dev.core.data.jdbc;

/**
 * SqlServer数据库方言
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class SqlServerDialect extends DefaultDialect implements Dialect {

    @Override
    public String getPaginationSql(String sql, int pageNo, int pageSize) {
        return "select top " + pageSize + " from (" + sql
                + ") t where t.id not in (select top " + (pageNo - 1) * pageSize + " t1.id from ("
                + sql + ") t1)";
    }

    @Override
    public String length(String field) {
        return "len(" + field + ")";
    }
}