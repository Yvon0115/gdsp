/*
 * 类名: DimGroupRltVO
 * 创建人: wly    
 * 创建时间: 2016年2月4日
 */
package com.gdsp.platform.systools.indexanddim.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 维度组关联维度表 <br/>
 * 
 * @author wly
 */
public class DimGroupRltVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            pk_dim;               // 维度
    private String            pk_dimgroup;          // 维度组  
    private DimGroupVO        dimGroupVO;
    private DimVO             dimVO;

    public String getPk_dim() {
        return pk_dim;
    }

    public void setPk_dim(String pk_dim) {
        this.pk_dim = pk_dim;
    }

    public String getPk_dimgroup() {
        return pk_dimgroup;
    }

    public void setPk_dimgroup(String pk_dimgroup) {
        this.pk_dimgroup = pk_dimgroup;
    }

    public DimGroupVO getDimGroupVO() {
        return dimGroupVO;
    }

    public void setDimGroupVO(DimGroupVO dimGroupVO) {
        this.dimGroupVO = dimGroupVO;
    }

    public DimVO getDimVO() {
        return dimVO;
    }

    public void setDimVO(DimVO dimVO) {
        this.dimVO = dimVO;
    }
}
