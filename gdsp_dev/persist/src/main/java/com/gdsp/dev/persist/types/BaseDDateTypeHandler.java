package com.gdsp.dev.persist.types;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.core.common.CommonHelper;

/**
 * 自定义日期型基础处理类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public abstract class BaseDDateTypeHandler<E extends DDate> extends BaseTypeHandler<E> {

    /**
     * 时间精度
     */
    protected int timeScale = CommonHelper.getTimeScale();

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.BaseTypeHandler#setNonNullParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
            DDate parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == JdbcType.DATE) {
            ps.setDate(i, new Date(parameter.getMillis()));
        } else if (jdbcType == JdbcType.BIGINT) {
            if (timeScale > 0) {
                ps.setLong(i, parameter.getMillis() / timeScale);
            } else {
                ps.setLong(i, parameter.getMillis());
            }
        } else if (jdbcType == JdbcType.INTEGER) {
            if (timeScale > 0) {
                ps.setInt(i, (int) parameter.getMillis() / timeScale);
            } else {
                ps.setInt(i, (int) parameter.getMillis() / 1000);
            }
        } else {
            ps.setString(i, parameter.toString());
        }
    }
}
