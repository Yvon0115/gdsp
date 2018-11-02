package com.gdsp.dev.persist.utils;

/**
 * 将数据类型转换成mybatis类型
 * @author paul.yang
 * @version 1.0 14-12-20
 * @since 1.6
 */
public final class MybatisTypeUtils {
    //    /**
    //     * 平台类型到mybatis类型转换
    //     * @param type 平台数据类型
    //     * @return mybatis使用的数据库类型
    //     */
    //    public static JdbcType convert2MybatisType(DataType type) {
    //
    //        /**
    //         * 平台类型到mybatis类型转换
    //         */
    //        switch (type) {
    //            case BYTE:
    //                return JdbcType.SMALLINT;
    //            case SHORT:
    //                return JdbcType.SMALLINT;
    //            case INT:
    //                return JdbcType.INTEGER;
    //            case LONG:
    //                return JdbcType.BIGINT;
    //            case FLOAT:
    //                return JdbcType.FLOAT;
    //            case DOUBLE:
    //                return JdbcType.REAL;
    //            case BIGDECIMAL:
    //                return JdbcType.DECIMAL;
    //            case BOOLEAN:
    //                return JdbcType.BOOLEAN;
    //            case DBOOLEAN:
    //                return JdbcType.BOOLEAN;
    //            case DATE:
    //                return JdbcType.DATE;
    //            case TIME:
    //                return JdbcType.TIME;
    //            case DATETIME:
    //                return JdbcType.DATE;
    //            case DDATE:
    //                return JdbcType.DATE;
    //            case DTIME:
    //                return JdbcType.TIME;
    //            case DDATETIME:
    //                return JdbcType.DATE;
    //            case BINARY:
    //                return JdbcType.BLOB;
    //            default:
    //                return JdbcType.VARCHAR;
    //        }
    //    }
    //
    //    /**
    //     * 将平台数据类型转换成mybatis类型串
    //     * @param type 平台数据类型
    //     * @return mybatis类型串
    //     */
    //    public static String convert2TypeString(DataType type){
    //        if(type == null)
    //            return null;
    //        JdbcType jdbcType = convert2MybatisType(type);
    //        if(jdbcType == JdbcType.VARCHAR)
    //            return null;
    //        return convert2MybatisType(type).toString();
    //    }
}
