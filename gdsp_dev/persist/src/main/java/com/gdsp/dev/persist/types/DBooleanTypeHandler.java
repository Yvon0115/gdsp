/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.persist.types;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.gdsp.dev.base.lang.DBoolean;

/**
 * 自定义日期时间类型处理类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class DBooleanTypeHandler extends BaseTypeHandler<DBoolean> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
            DBoolean parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
        if (jdbcType == JdbcType.BOOLEAN) {
            ps.setBoolean(i, parameter.booleanValue());
        } else if (jdbcType == JdbcType.SMALLINT) {
            ps.setShort(i, (short) parameter.intValue());
        } else if (jdbcType == JdbcType.INTEGER) {
            ps.setInt(i, parameter.intValue());
        } else {
            ps.setString(i, parameter.toString());
        }
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet, java.lang.String)
     */
    @Override
    public DBoolean getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String d = rs.getString(columnName);
        if (StringUtils.isNotEmpty(d)) {
            return DBoolean.valueOf(d);
        }
        return DBoolean.FALSE;
    }

    @Override
    public DBoolean getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String d = rs.getString(columnIndex);
        if (StringUtils.isNotEmpty(d)) {
            return DBoolean.valueOf(d);
        }
        return DBoolean.FALSE;
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.CallableStatement, int)
     */
    @Override
    public DBoolean getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String d = cs.getString(columnIndex);
        if (StringUtils.isNotEmpty(d)) {
            return DBoolean.valueOf(d);
        }
        return DBoolean.FALSE;
    }
}
