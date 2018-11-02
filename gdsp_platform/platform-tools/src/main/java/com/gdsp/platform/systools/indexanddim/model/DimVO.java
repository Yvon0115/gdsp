/*
 * 类名: DimVO
 * 创建人: wly   
 * 创建时间: 2016年2月4日
 */
package com.gdsp.platform.systools.indexanddim.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 维度信息表 <br/>
 * 
 * @author wly
 */
public class DimVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            dimname;              //维度名称
    private String            dimfieldname;         //维度字段名称
    private String            dimdatatable;         //维度数据表
    private String            dimtablename;         //维度数据表名
    private String            dimmemo;              //维度说明
    private String            memo;                 //描述
    private String            dimtype;              //维度类型
    private String            widgettype;           //控件类型

    public String getDimtablename() {
        return dimtablename;
    }

    public void setDimtablename(String dimtablename) {
        this.dimtablename = dimtablename;
    }

    public String getDimname() {
        return dimname;
    }

    public void setDimname(String dimname) {
        this.dimname = dimname;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDimfieldname() {
        return dimfieldname;
    }

    public void setDimfieldname(String dimfieldname) {
        this.dimfieldname = dimfieldname;
    }

    public String getDimdatatable() {
        return dimdatatable;
    }

    public void setDimdatatable(String dimdatatable) {
        this.dimdatatable = dimdatatable;
    }

    public String getDimmemo() {
        return dimmemo;
    }

    public void setDimmemo(String dimmemo) {
        this.dimmemo = dimmemo;
    }

    public String getDimtype() {
        return dimtype;
    }

    public void setDimtype(String dimtype) {
        this.dimtype = dimtype;
    }

    public String getWidgettype() {
        return widgettype;
    }

    public void setWidgettype(String widgettype) {
        this.widgettype = widgettype;
    }
}
