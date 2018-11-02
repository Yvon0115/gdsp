/*
 * 类名: DimGroupVO
 * 创建人: wly    
 * 创建时间: 2016年2月3日
 */
package com.gdsp.platform.systools.indexanddim.model;

import java.util.List;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.dev.core.utils.tree.model.ITreeEntity;

/**
 * 维度分组管理VO类<br/>
 * 
 * @author wly
 */
public class DimGroupVO extends AuditableEntity implements ITreeEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static String     tableName        = "bp_dimgroup";
    private String            groupname;                       //维度分组名称
    private String            groupcode;                       //维度分组编码
    private String            innercode;                       //内部关联码
    private String            memo;                            //描述
    private String            pk_fatherid;                     //父节点
    private List<DimGroupVO>  dimdir;                          // 子目录

    public List<DimGroupVO> getDimdir() {
        return dimdir;
    }

    public void setDimdir(List<DimGroupVO> dimdir) {
        this.dimdir = dimdir;
    }

    public String getPk_fatherid() {
        return pk_fatherid;
    }

    public void setPk_fatherid(String pk_fatherid) {
        this.pk_fatherid = pk_fatherid;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getInnercode() {
        return innercode;
    }

    public void setInnercode(String innercode) {
        this.innercode = innercode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}
