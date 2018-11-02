package com.gdsp.platform.grant.role.model;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 角色表
 * @author wwb 
 * @version 1.0 2015/6/15
 */
public class RoleVO extends AuditableEntity {

    private static final long serialVersionUID = 1L;
    private String            rolename;             // 角色名
    private String            pk_org;               // 所属机构
    private String            memo;                 // 描述
    private String            orgname;              // 机构名称
    private String            leaf;                 //（给指标权限管理用，判断是机构还是角色）
    private String			  agingLimit;			// 时效角色标识
    private DDate             agingEndDate;         // 时效截止日期(展示用)
    private String            permissionAging;      //权限时效
    private String            agingUnit;            //时效单位(1表示“小时”,2表示“天”)
    private String            funname;             //菜单名称    末级菜单和页面菜单
    
    public DDate getAgingEndDate() {
		return agingEndDate;
	}

	public void setAgingEndDate(DDate agingEndDate) {
		this.agingEndDate = agingEndDate;
	}

	public String getAgingLimit() {
		return agingLimit;
	}

	public void setAgingLimit(String agingLimit) {
		this.agingLimit = agingLimit;
	}

	public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getPk_org() {
        return pk_org;
    }

    public void setPk_org(String pk_org) {
        this.pk_org = pk_org;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }
    
    public String getFunname() {
		return funname;
	}

	public void setFunname(String funname) {
		this.funname = funname;
	}

    public String getPermissionAging() {
        return permissionAging;
    }

    public void setPermissionAging(String permissionAging) {
        this.permissionAging = permissionAging;
    }

    public String getAgingUnit() {
        return agingUnit;
    }

    public void setAgingUnit(String agingUnit) {
        this.agingUnit = agingUnit;
    }
}
