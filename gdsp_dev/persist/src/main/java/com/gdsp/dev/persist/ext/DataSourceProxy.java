package com.gdsp.dev.persist.ext;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.InfrastructureProxy;

import com.gdsp.dev.core.common.AppContext;

/**
 * 自定义数据源代理，可同时使用多个数据源
 * @author paul.yang
 * @version 1.0 14-12-17
 * @since 1.6
 */
public class DataSourceProxy implements DataSource, InfrastructureProxy {

    /**
     * Proxy target datasource map
     */
    private Map<String, DataSource> targets           = null;
    private String                  defaultDataSource = null;

    /**
     * Constructs method
     * @param dses datasource map
     */
    public DataSourceProxy() {
        targets = new HashMap<>();
    }

    /**
     * Constructs method
     * @param dses datasource map
     */
    public DataSourceProxy(Map<String, DataSource> dses) {
        targets = dses;
    }

    /**
     * Get proxied datasource map<name:datasource>
     * @return datasource map
     */
    public Map<String, DataSource> getTargets() {
        return targets;
    }

    public void setTargets(Map<String, DataSource> targets) {
        this.targets = targets;
    }

    /**
     * add proxied datasource
     * @param key datasource name
     * @param target datasource object
     */
    public void putTarget(String key, DataSource target) {
        targets.put(key, target);
    }

    /**
     * Get default datasource name
     * @return datasource name
     */
    public String getDefault() {
        return defaultDataSource;
    }

    /**
     * Set default datasource name
     * @param defaultDataSource datasource name
     */
    public void setDefault(String defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    /**
     * Get current invoke datasource name;
     * @return datasource name
     */
    public String getDataSourceKey() {
        String ds = AppContext.getContext().getDataSource();
        if (StringUtils.isEmpty(ds)) {
            ds = getDefault();
        }
        return ds;
    }

    /**
     * Get current invoke datasource
     * @return datasource object
     */
    public DataSource getDataSource() {
        return targets.get(getDataSourceKey());
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getDataSource().getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getDataSource().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        getDataSource().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        getDataSource().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return getDataSource().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return getDataSource().getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getDataSource().isWrapperFor(iface);
    }

    @Override
    public int hashCode() {
        return getDataSource().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DataSourceProxy that = (DataSourceProxy) o;
        if (getDataSource() != null ? !getDataSource().equals(that.getDataSource()) : that.defaultDataSource != null)
            return false;
        if (targets != null ? !targets.equals(that.targets) : that.targets != null)
            return false;
        return true;
    }

    @Override
    public Object getWrappedObject() {
        return getDataSourceKey();
    }
}
