package com.gdsp.platform.systools.datadic.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
/**
* @ClassName: DataDicValueVO
* (数据字典详细信息VO)
* @author qishuo
* @date 2016年12月09日 
*
*/
public class DataDicValueVO extends AuditableEntity {

    private static final long serialVersionUID = -6179521377460208013L;
    /**
     * 维值名称
     */
    private String dimvl_name;
    /**
     * 维值编码
     */
    private String dimvl_code;
    /**
     * 维值描述
     */
    private String dimvl_desc;
    /**
     * 上级维值id
     */
    private String pk_fatherId;
    /**
     * 数据字典id
     */
    private String pk_dicId;
    /**
     * 上级名称
     */
    private String parentName;
    /**
     * 上级编码
     */
    private String parentCode;

    private String isChecked;
    
    
    public String getIsChecked() {
        return isChecked;
    }


    
    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }


    public String getParentName() {
        return parentName;
    }

    
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    
    public String getParentCode() {
        return parentCode;
    }

    
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getDimvl_name() {
        return dimvl_name;
    }
    
    public void setDimvl_name(String dimvl_name) {
        this.dimvl_name = dimvl_name;
    }
    
    public String getDimvl_code() {
        return dimvl_code;
    }
    
    public void setDimvl_code(String dimvl_code) {
        this.dimvl_code = dimvl_code;
    }
    
    public String getDimvl_desc() {
        return dimvl_desc;
    }
    
    public void setDimvl_desc(String dimvl_desc) {
        this.dimvl_desc = dimvl_desc;
    }
    
    public String getPk_fatherId() {
        return pk_fatherId;
    }
    
    public void setPk_fatherId(String pk_fatherId) {
        this.pk_fatherId = pk_fatherId;
    }
    
    public String getPk_dicId() {
        return pk_dicId;
    }
    
    public void setPk_dicId(String pk_dicId) {
        this.pk_dicId = pk_dicId;
    }
    
}
