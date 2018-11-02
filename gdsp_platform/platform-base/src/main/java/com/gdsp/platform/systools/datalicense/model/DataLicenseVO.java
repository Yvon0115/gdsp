/**
 * 
 */
package com.gdsp.platform.systools.datalicense.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.platform.systools.datadic.model.DataDicValueVO;


/**
 * @author wangliyuan
 *
 */
public class DataLicenseVO extends DataDicValueVO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            pk_role;             // 角色id
    private String            pk_dic;              // 数据字典id
    private String            pk_dicval;           // 数据字典值id
    
    public String getPk_role() {
        return pk_role;
    }
    
    public void setPk_role(String pk_role) {
        this.pk_role = pk_role;
    }
    
    public String getPk_dic() {
        return pk_dic;
    }
    
    public void setPk_dic(String pk_dic) {
        this.pk_dic = pk_dic;
    }
    
    public String getPk_dicval() {
        return pk_dicval;
    }
    
    public void setPk_dicval(String pk_dicval) {
        this.pk_dicval = pk_dicval;
    }
    
    
    
}
