/*
 * 类名: DimRltVO
 * 创建人: wly    
 * 创建时间: 2016年2月22日
 */
package com.gdsp.platform.systools.indexanddim.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 *  维度关联维值表<br/>
 * 
 * @author wly
 */
public class DimRltVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            pk_dim;               //维度id
    private String            pk_dimvalue;          //维值id
    private String            id;
    private DimVO             dimVO;
    private DimValueVO        dimValueVO;

    public DimVO getDimVO() {
        return dimVO;
    }

    public void setDimVO(DimVO dimVO) {
        this.dimVO = dimVO;
    }

    public DimValueVO getDimValueVO() {
        return dimValueVO;
    }

    public void setDimValueVO(DimValueVO dimValueVO) {
        this.dimValueVO = dimValueVO;
    }

    public String getPk_dim() {
        return pk_dim;
    }

    public void setPk_dim(String pk_dim) {
        this.pk_dim = pk_dim;
    }

    public String getPk_dimvalue() {
        return pk_dimvalue;
    }

    public void setPk_dimvalue(String pk_dimvalue) {
        this.pk_dimvalue = pk_dimvalue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
