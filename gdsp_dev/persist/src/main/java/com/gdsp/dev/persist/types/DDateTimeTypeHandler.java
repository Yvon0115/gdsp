package com.gdsp.dev.persist.types;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gdsp.dev.base.lang.DDateTime;

/**
 * 自定义日期时间类型处理类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class DDateTimeTypeHandler extends BaseDDateTypeHandler<DDateTime> {

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet, java.lang.String)
     */
    @Override
    public DDateTime getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return parseDate(rs.getObject(columnName));
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet, int)
     */
    @Override
    public DDateTime getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return parseDate(rs.getObject(columnIndex));
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.CallableStatement, int)
     */
    @Override
    public DDateTime getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return parseDate(cs.getObject(columnIndex));
    }

    /**
     * 解析date类型
     * @param v 值对象
     * @return date对象
     */
    protected DDateTime parseDate(Object v) {
        if (v != null && v.toString().length() > 0) {
            if (timeScale > 0 && v instanceof Number) {
                return new DDateTime(((Number) v).longValue() * timeScale);
            }
            return DDateTime.parseDDateTime(v);
        }
        return null;
    }
}
