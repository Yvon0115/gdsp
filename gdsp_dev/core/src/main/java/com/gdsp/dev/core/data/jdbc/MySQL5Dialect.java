package com.gdsp.dev.core.data.jdbc;

import org.apache.commons.lang3.StringUtils;

/**
 * MySql数据库方言
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class MySQL5Dialect extends DefaultDialect implements Dialect {

    @Override
    public String concat(String... fields) {
        return "concat(" + StringUtils.join(fields, ",") + ")";
    }
}
