package com.gdsp.platform.systools.datasource.model;

import javax.sql.DataSource;

/**
* @ClassName: SimpleDatasourceVO
* @Description: 自定义的包含SimpleDatasource的数据源
* @author lianyanfei
* @date 2018年1月12日 下午1:53:26
*
*/ 
public class SimpleDatasourceVO {
	/** jdbc模板*/
    private DataSource dataSource;
    /** 数据源连接对象获取内部错误状态码*/
    private String code;
    /** 数据源连接对象获取错误返回信息*/
    private String message;
    
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
}
