package com.gdsp.platform.config.global.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 系统码表VO
 * @author songxiang
 * @date 2015年10月28日 下午2:03:25
 */
public class DefDocVO extends AuditableEntity {

    private static final long serialVersionUID = 529607213889345603L;
    /**
     * 详细类型编码
     */
    private String            doc_code;                              // varchar(50),
    /**
     * 详细类型名称
     */
    private String            doc_name;                              // varchar(128),
    /**
     * 详细类型描述
     */
    private String            doc_desc;                              // varchar(256),
    /**
     * 分类号
     */
    private int               sortnum;                               // int,
    /**
     * 类型ID
     */
    private String            type_id;                               // varchar(32),
    private String            checked;                               //view上使用，标记此数据是否被选中（史英杰添加，在订阅报表中使用）
    /**
     * 上级ID
     */
    private String            pk_fatherId;                          //上级ID
    /**
     * 上级编码
     */
    private String            parentCode;                          //上级编码
    /**
     * 上级名称
     */
    private String            parentName;                         //上级名称
    public String getDoc_code() {
        //给报表服务的参数处理，2为报表参数
        if ("##NULL##".equals(doc_code)) {
            //			doc_code = null;
        }
        return doc_code;
    }

    public void setDoc_code(String doc_code) {
        this.doc_code = doc_code;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_desc() {
        return doc_desc;
    }

    public void setDoc_desc(String doc_desc) {
        this.doc_desc = doc_desc;
    }

    public int getSortnum() {
        return sortnum;
    }

    public void setSortnum(int sortnum) {
        this.sortnum = sortnum;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    } 
    
    public String getParentCode() {
        return parentCode;
    }
    
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    
    public String getPk_fatherId() {
        return pk_fatherId;
    }

    
    public void setPk_fatherId(String pk_fatherId) {
        this.pk_fatherId = pk_fatherId;
    }
    
}
