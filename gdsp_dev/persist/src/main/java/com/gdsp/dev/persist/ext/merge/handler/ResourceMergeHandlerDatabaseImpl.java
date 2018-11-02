package com.gdsp.dev.persist.ext.merge.handler;

import javax.sql.DataSource;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import com.gdsp.dev.base.exceptions.DevException;

/**
 * 数据库方式实现 dao xml 分离
 * 便于因为数据组不习惯使用svn提交，后续可以考虑数据库方式存储merge xml,但会增加额外的工作量
 * @author gdsp
 *
 */
public class ResourceMergeHandlerDatabaseImpl extends ResourceMergeHandler {
    /**
     * 日志对象
     */
    private final static Log                  logger                         = LogFactory.getLog(ResourceMergeHandlerDatabaseImpl.class);

    /**
     * 数据源
     */
    private DataSource dataSource;
    /**
     * 存储merge xml的表名
     */
    private String     tableName;
    /**
     * 存储列名映射关系
     * 
     */
    private String     columnMap;

    @Override
    public void initAllMergeInfo() {
        logger.error("请实现数据库访问方法！",new DevException("请实现数据库访问方法！"));
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(String columnMap) {
        this.columnMap = columnMap;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
