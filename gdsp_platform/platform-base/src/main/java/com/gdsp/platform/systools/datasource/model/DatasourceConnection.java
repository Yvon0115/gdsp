/*
 * Copyright (c)  Business Intelligence Unified Portal. All rights reserved.
 * 
 */ 
package com.gdsp.platform.systools.datasource.model;

import java.sql.Connection;

/**
 * 数据源连接对象返回类型封装
 * @author yucl
 * @date 2017年10月31日 下午5:31:45
 */
public class DatasourceConnection {
    /** 数据源连接对象*/
    private Connection connection;
    /** 数据源连接对象获取内部错误状态码*/
    private String code;
    /** 数据源连接对象获取错误返回信息*/
    private String message;
    
    public Connection getConnection() {
        return connection;
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
