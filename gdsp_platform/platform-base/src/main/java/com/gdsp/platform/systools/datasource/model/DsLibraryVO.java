/*
 * Copyright (c)  Business Intelligence Unified Portal. All rights reserved.
 * 
 */
package com.gdsp.platform.systools.datasource.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 这里用一句话描述这个类的作用
 * @author yuchenglong
 * @date 2017年9月19日 下午3:11:58
 */
public class DsLibraryVO extends AuditableEntity {

    /**
     * @Fields serialVersionUID(用一句话描述这个变量表示什么)
     */ 
    private static final long serialVersionUID = 2073956395560144349L;
    /** 数据源编码类型标识*/
    private String ds_type;
    /** 数据源产品版本号*/
    private String ds_version;
    /** 数据源jdbc驱动限定名*/
    private String qualifiedClassName;
    /** 数据源驱动jar包获取路径*/
    private String jarPath;
    
    public String getDs_type() {
        return ds_type;
    }
    
    public void setDs_type(String ds_type) {
        this.ds_type = ds_type;
    }
    
    public String getDs_version() {
        return ds_version;
    }
    
    public void setDs_version(String ds_version) {
        this.ds_version = ds_version;
    }
    
    public String getQualifiedClassName() {
        return qualifiedClassName;
    }
    
    public void setQualifiedClassName(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }
    
    public String getJarPath() {
        return jarPath;
    }
    
    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }
    
}
