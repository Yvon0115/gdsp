package com.gdsp.platform.grant.auth.model;

import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.role.model.RoleVO;

/**
 * 机构权限表
 * 
 * @author wwb
 * @version 1.0 2015/6/15
 */
public class OrgPowerVO extends PowerMgtVO {

    private static final long serialVersionUID = 1L;
    private OrgVO             orgVO;
    private RoleVO            roleVO;

    public OrgVO getOrgVO() {
        return orgVO;
    }

    public void setOrgVO(OrgVO orgVO) {
        this.orgVO = orgVO;
    }

    public RoleVO getRoleVO() {
        return roleVO;
    }

    public void setRoleVO(RoleVO roleVO) {
        this.roleVO = roleVO;
    }
}
