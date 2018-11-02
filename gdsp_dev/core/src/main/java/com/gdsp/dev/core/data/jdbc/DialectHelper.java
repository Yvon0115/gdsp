package com.gdsp.dev.core.data.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.common.AppContext;

/**
 * 方言助手类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public final class DialectHelper {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(DialectHelper.class);

    /**
     * 取得QL工厂对象
     * @return 工厂对象
     */
    public static Dialect getDialect() {
        return AppContext.getContext().lookup(Dialect.class);
    }

    /**
     * 根据数据源生成
     * @param dbType 数据库类型
     * @return 方言对象
     */
    public static Dialect getDialect(String dbType) {
        if (StringUtils.equalsIgnoreCase(dbType, "h2")) {
            return new H2Dialect();
        } else if (StringUtils.equalsIgnoreCase(dbType, "mysql")) {
            return new MySQL5Dialect();
        } else if (StringUtils.equalsIgnoreCase(dbType, "oracle")) {
            return new OracleDialect();
        } else if (StringUtils.equalsIgnoreCase(dbType, "sqlserver")) {
            return new SqlServerDialect();
        } else if(StringUtils.equalsIgnoreCase(dbType, "db2")){
        	return new DB2Dialect();
        }else {
            throw new IllegalArgumentException("Unknown Database Type of " + dbType);
        }
    }

    /**
     * 根据数据源获取数据库jdbcurl
     * @param dataSource
     * @return
     */
    public static String getJdbcUrlFromDataSource(DataSource dataSource) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (connection == null) {
                throw new IllegalStateException("Connection returned by DataSource [" + dataSource + "] was null");
            }
            return connection.getMetaData().getURL();
        } catch (SQLException e) {
            logger.error("Could not get database url", e);
            throw new BusinessException("Could not get database url", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("no important", e);
                }
            }
        }
    }
}
