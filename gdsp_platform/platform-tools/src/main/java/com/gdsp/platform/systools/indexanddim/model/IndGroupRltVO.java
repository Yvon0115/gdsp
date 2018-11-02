package com.gdsp.platform.systools.indexanddim.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class IndGroupRltVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            pk_index;             // 指标
    private String            pk_indexgroup;        // 指标组
    private IndTreeVO         indTreeVO;
    private IndexInfoVO       indexInfoVO;

    public String getPk_index() {
        return pk_index;
    }

    public void setPk_index(String pk_index) {
        this.pk_index = pk_index;
    }

    public String getPk_indexgroup() {
        return pk_indexgroup;
    }

    public void setPk_indexgroup(String pk_indexgroup) {
        this.pk_indexgroup = pk_indexgroup;
    }

    public IndTreeVO getIndTreeVO() {
        return indTreeVO;
    }

    public void setIndTreeVO(IndTreeVO indTreeVO) {
        this.indTreeVO = indTreeVO;
    }

    public IndexInfoVO getIndexInfoVO() {
        return indexInfoVO;
    }

    public void setIndexInfoVO(IndexInfoVO indexInfoVO) {
        this.indexInfoVO = indexInfoVO;
    }
}
