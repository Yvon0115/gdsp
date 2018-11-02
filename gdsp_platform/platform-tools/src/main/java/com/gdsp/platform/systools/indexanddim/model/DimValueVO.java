/*
 * 类名: DimValueVO
 * 创建人: wly    
 * 创建时间: 2016年2月22日
 */
package com.gdsp.platform.systools.indexanddim.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 维值信息表 <br/>
 * 
 * @author wly
 */
public class DimValueVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static String     tableName        = "bp_dimgvalue";
    private String            dimvalue;                         //维值
    private String            dimvaluememo;                     //维值描述
    private String            innercode;                        //内部关联码
    private String            pk_fatherid;                      //父级id

    public String getInnercode() {
        return innercode;
    }

    public void setInnercode(String innercode) {
        this.innercode = innercode;
    }

    public static String getTableName() {
        return tableName;
    }

    public static void setTableName(String tableName) {
        DimValueVO.tableName = tableName;
    }

    public String getDimvalue() {
        return dimvalue;
    }

    public void setDimvalue(String dimvalue) {
        this.dimvalue = dimvalue;
    }

    public String getDimvaluememo() {
        return dimvaluememo;
    }

    public void setDimvaluememo(String dimvaluememo) {
        this.dimvaluememo = dimvaluememo;
    }

    public String getPk_fatherid() {
        return pk_fatherid;
    }

    public void setPk_fatherid(String pk_fatherid) {
        this.pk_fatherid = pk_fatherid;
    }
}
