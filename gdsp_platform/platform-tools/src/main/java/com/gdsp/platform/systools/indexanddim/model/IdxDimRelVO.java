package com.gdsp.platform.systools.indexanddim.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 指标维度关联关系VO类
 * @author Administrator
 *
 */
public class IdxDimRelVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            indexid;              //指标ID
    private String            dimid;                //维度ID
    private DimVO             dimVO;

    public DimVO getDimVO() {
        return dimVO;
    }

    public void setDimVO(DimVO dimVO) {
        this.dimVO = dimVO;
    }

    public String getIndexid() {
        return indexid;
    }

    public void setIndexid(String indexid) {
        this.indexid = indexid;
    }

    public String getDimid() {
        return dimid;
    }

    public void setDimid(String dimid) {
        this.dimid = dimid;
    }
}
