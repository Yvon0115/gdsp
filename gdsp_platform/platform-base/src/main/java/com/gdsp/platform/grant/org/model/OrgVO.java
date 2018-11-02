package com.gdsp.platform.grant.org.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.dev.core.utils.tree.model.ITreeEntity;

/**
 * 机构表
 * @author wwb
 * @version 1.0 2015/6/15
 */
public class OrgVO extends AuditableEntity implements ITreeEntity {

    private static final long serialVersionUID = 1L;
    private static String     tableName        = "rms_orgs";
    private String            pk_fatherorg;                 //上级组织
    private String            orgcode;                      //机构编码
    private String            orgname;                      //机构名
    private String            innercode;                    //内部码
    private String            memo;                         //描述
    private String            shortname;                    //机构简称
    private String            isChecked;                    //是否选中  Y选中N未选中
    private String            pk_fatherName;                //上级组织名称
    private String            createByName;                 //创建人姓名
    private String            lastModifyByName;             //最后修改人姓名
    private String            leaf;                         //（给指标权限管理用，判断是机构还是角色）

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getPk_fatherName() {
        return pk_fatherName;
    }

    public void setPk_fatherName(String pk_fatherName) {
        this.pk_fatherName = pk_fatherName;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getPk_fatherorg() {
        return pk_fatherorg;
    }

    public void setPk_fatherorg(String pk_fatherorg) {
        this.pk_fatherorg = pk_fatherorg;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
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

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getTableName() {
        return tableName;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getLastModifyByName() {
        return lastModifyByName;
    }

    public void setLastModifyByName(String lastModifyByName) {
        this.lastModifyByName = lastModifyByName;
    }
}
