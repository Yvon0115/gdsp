package com.gdsp.platform.grant.auth.model;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.BaseEntity;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * 用户角色关联关系表
 * @author wwb 
 * @version 1.0 2015/6/15
 */
public class UserRoleVO extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String            pk_user;              // 用户
    private String            pk_role;              // 角色
    private UserVO            userVO;
    private RoleVO            roleVO;
    private DDateTime  		 	  agingEndDate;        	// 时效截止日期(权限时效开启时有效)
    private String            isPrompted;           //是否已发送权限到期提醒(Y/N)
    protected DDateTime       createTime;           // 创建时间
	protected String          createBy;             // 创建人

    public String getPk_user() {
        return pk_user;
    }

    public void setPk_user(String pk_user) {
        this.pk_user = pk_user;
    }

    public String getPk_role() {
        return pk_role;
    }

    public void setPk_role(String pk_role) {
        this.pk_role = pk_role;
    }

    public DDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
    
    public DDateTime getAgingEndDate() {
		return agingEndDate;
	}

	public void setAgingEndDate(DDateTime agingEndDate) {
		this.agingEndDate = agingEndDate;
	}

	public String getIsPrompted() {
		return isPrompted;
	}

	public void setIsPrompted(String isPrompted) {
		this.isPrompted = isPrompted;
	}
}
