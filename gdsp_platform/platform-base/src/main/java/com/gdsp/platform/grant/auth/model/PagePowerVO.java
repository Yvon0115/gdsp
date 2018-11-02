package com.gdsp.platform.grant.auth.model;

import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * 菜单权限表
 * 
 * @author wwb
 * @version 1.0 2015/6/15
 */
public class PagePowerVO extends PowerMgtVO {

    private static final long serialVersionUID = 1L;
    private int               objtype;              // '1:用户,2:角色' null default '1',
    private UserVO            userVO;
    private RoleVO            roleVO;

    public String getTableName() {
        return "rms_power_page";
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public RoleVO getRoleVO() {
        return roleVO;
    }

    public void setRoleVO(RoleVO roleVO) {
        this.roleVO = roleVO;
    }

    public int getObjtype() {
        return objtype;
    }

    public void setObjtype(int objtype) {
        this.objtype = objtype;
    }
}
