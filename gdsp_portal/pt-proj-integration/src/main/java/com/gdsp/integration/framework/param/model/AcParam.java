package com.gdsp.integration.framework.param.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class AcParam extends AuditableEntity {

    private static final long serialVersionUID = 17733261132358183L;
    private String            displayName;                          //显示名称
    private String            name;                                 //名称
    private String            type;                                 //类型：(char:字符型，int:整型，number:数值型，date:日期类型)
    private String            viewType;                             //显示类型（文本、下拉、checkbox、时间控件）
    private String            pid;                                  //父ID
    private String            cubeTextFormat;                       //Cube参数格式
    private String            dataFromType;                         //数据来源类型：数据源类型(manual:手工，docList:自定义列表,)
    private String            defaultValue;                         //系统默认值
    private String            mustput;                              //Y:必输
    private Integer           colspan;                              //列数
    private String            dataFrom;                             //数据源选择

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCubeTextFormat() {
        return cubeTextFormat;
    }

    public void setCubeTextFormat(String cubeTextFormat) {
        this.cubeTextFormat = cubeTextFormat;
    }

    public String getDataFromType() {
        return dataFromType;
    }

    public void setDataFromType(String dataFromType) {
        this.dataFromType = dataFromType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getMustput() {
        return mustput;
    }

    public void setMustput(String mustput) {
        this.mustput = mustput;
    }

    public Integer getColspan() {
        return colspan;
    }

    public void setColspan(Integer colspan) {
        this.colspan = colspan;
    }

    public String getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom;
    }
}