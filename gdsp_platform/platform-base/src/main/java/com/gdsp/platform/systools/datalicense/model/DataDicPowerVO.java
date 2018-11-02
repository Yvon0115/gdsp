package com.gdsp.platform.systools.datalicense.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.platform.systools.datadic.model.DataDicVO;

public class DataDicPowerVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -3731553002842262922L;
    private String            pk_dataSource;                           //数据源id
    private String            pk_dataDicId;                            //数据字典id
    private DataDicVO         dataDicVO;                               //数据字典VO

    public String getPk_dataSource() {
        return pk_dataSource;
    }

    public void setPk_dataSource(String pk_dataSource) {
        this.pk_dataSource = pk_dataSource;
    }

    public String getPk_dataDicId() {
        return pk_dataDicId;
    }

    public void setPk_dataDicId(String pk_dataDicId) {
        this.pk_dataDicId = pk_dataDicId;
    }

    public DataDicVO getDataDicVO() {
        return dataDicVO;
    }

    public void setDataDicVO(DataDicVO dataDicVO) {
        this.dataDicVO = dataDicVO;
    }
}
