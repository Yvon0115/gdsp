package com.gdsp.dev.persist.ext;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;

import com.gdsp.dev.persist.annotation.DataSource;

/**
 * mybatis配置扩展主要是为了扩展MapperRegistry，以更好的支持annotaion
 * @author paul.yang
 * @version 1.0 2014年10月28日
 * @since 1.6
 */
public class MybatisConfiguration extends Configuration {

    /**
     * mybatis statement id use datasource,{@link DataSource}
     */
    protected Map<String, String> statementDataSource = null;

    /**
     * 构造方法
     */
    public MybatisConfiguration() {
        super();
        statementDataSource = new HashMap<>();
        mapperRegistry = new MybatisMapperRegistry(this);
    }

    /**
     * 设置映射注册类映射注册类
     * @param mapperRegistry mybatis mapping registry
     */
    public void setMapperRegistry(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    /**
     * Get dao mybatis statement id to datasource map
     * @return map<method,datasource>
     */
    public Map<String, String> getDataSources() {
        return statementDataSource;
    }

    /**
     * Set mybatis statement id to datasource name map
     * @param statementDataSource map<method,datasource>
     */
    public void setDataSources(Map<String, String> statementDataSource) {
        this.statementDataSource = statementDataSource;
    }

    /**
     * Add method datasource config
     * @param statementId mybatis statement id
     * @param datasource datasource name
     */
    public void putDataSource(String statementId, String datasource) {
        this.statementDataSource.put(statementId, datasource);
    }

    /**
     * Get datasource name by dao method string
     * @param statementId mybatis statement id
     * @return datasource name
     */
    public String getDataSource(String statementId) {
        return statementDataSource.get(statementId);
    }
}
