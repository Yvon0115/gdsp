package com.gdsp.dev.core.data.jdbc;

import java.sql.Types;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.base.utils.data.DataType;
import com.gdsp.dev.core.common.CommonHelper;

/**
 * 将数据类型转换成jdbc类型
 * @author paul.yang
 * @version 1.0 14-12-20
 * @since 1.6
 */
public final class JdbcTypeUtils {

    /**
     * 平台类型到mybatis类型转换
     * @param type 平台数据类型
     * @return jdbc type使用的数据库类型
     */
    public static int convert2JdbcType(DataType type) {
        /**
         * 平台类型到mybatis类型转换
         */
        switch (type) {
            case BYTE:
                return Types.BIT;
            case SHORT:
                return Types.SMALLINT;
            case INT:
                return Types.INTEGER;
            case LONG:
                return Types.BIGINT;
            case FLOAT:
                return Types.FLOAT;
            case DOUBLE:
                return Types.REAL;
            case BIGDECIMAL:
                return Types.DECIMAL;
            case BOOLEAN:
                return Types.BOOLEAN;
            case DBOOLEAN:
                return Types.BOOLEAN;
            case DATE:
                return Types.DATE;
            case TIME:
                return Types.TIME;
            case DATETIME:
                return CommonHelper.getDDateJdbcType();
            case DDATE:
                return CommonHelper.getDDateJdbcType();
            case DTIME:
                return CommonHelper.getDDateJdbcType();
            case DDATETIME:
                return CommonHelper.getDDateJdbcType();
            case BINARY:
                return Types.BLOB;
            default:
                return Types.VARCHAR;
        }
    }

    /**
     * 平台类型到mybatis类型转换
     * @param typeName JDBC数据类型名
     * @return jdbc type使用的数据库类型
     */
    public static int parseJdbcType(String typeName) {
        if (StringUtils.isEmpty(typeName))
            return Types.OTHER;
        typeName = typeName.toUpperCase();
        /**
         * 平台类型到mybatis类型转换
         */
        if (typeName.equals("BIT")) {
            return Types.BIT;
        } else if (typeName.equals("SMALLINT")) {
            return Types.SMALLINT;
        } else if (typeName.equals("INTEGER")) {
            return Types.INTEGER;
        } else if (typeName.equals("BIGINT")) {
            return Types.BIGINT;
        } else if (typeName.equals("FLOAT")) {
            return Types.FLOAT;
        } else if (typeName.equals("REAL")) {
            return Types.REAL;
        } else if (typeName.equals("DECIMAL")) {
            return Types.DECIMAL;
        } else if (typeName.equals("BOOLEAN")) {
            return Types.BOOLEAN;
        } else if (typeName.equals("DATE")) {
            return Types.DATE;
        } else if (typeName.equals("TIME")) {
            return Types.TIME;
        } else if (typeName.equals("TIMESTAMP")) {
            return Types.TIMESTAMP;
        } else if (typeName.equals("BLOB")) {
            return Types.BLOB;
        } else if (typeName.equals("VARCHAR")) {
            return Types.VARCHAR;
        } else if (typeName.equals("CHAR")) {
            return Types.CHAR;
        } else if (typeName.equals("TEXT") || typeName.equals("LONGVARCHAR")) {
            return Types.LONGVARCHAR;
        } else {
            return Types.OTHER;
        }
    }
}
