package com.gdsp.platform.grant.user.model;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.common.IContextUser;
import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.dev.core.utils.excel.ExcelVOAttribute;
import com.gdsp.dev.web.security.login.AuthInfo;

/**
 * 用户表
 * @author wwb 
 * @version 1.0 2015/6/15
 */
public class UserVO extends AuditableEntity implements AuthInfo {

    private static final long serialVersionUID = 2785841782743804257L;
    @ExcelVOAttribute(name = "账号(必填)", column = "A", isExport = true)
    private String            account;                                // 账号
    @ExcelVOAttribute(name = "姓名(必填)", column = "B", isExport = true)
    private String            username;                               // 用户名
    private Integer           usertype;                               // '0:超级管理员,1:用户' null default '1',
    private String            onlyadmin;                              //只有管理权限
    @ExcelVOAttribute(name = "密码(必填)", column = "C", isExport = true)
    private String            user_password;                          // 密码
    @ExcelVOAttribute(name = "手机", column = "D", isExport = true)
    private String            mobile;                                 // 手机
    @ExcelVOAttribute(name = "邮箱", column = "F", isExport = true)
    private String            email;                                  // 邮箱
    @ExcelVOAttribute(name = "性别", column = "G", combo = { "男", "女" }, isExport = true)
    private Integer           sex;                                    // '0:男,1:女' null default '0',
    private String            pk_org;                                 // 所属机构
    private String            isreset;                                // 重置状态  Y为需重置，N为不需要重置
    private DDateTime         update_pwd_time;						  //密码修改时间
    private String            islocked;                               // 是否锁定
    private DDateTime         abletime;                               // 启动时间
    private DDateTime         disabletime;                            // 停用时间
    @ExcelVOAttribute(name = "备注", column = "J", isExport = true)
    private String            memo;                                   // 描述
    @ExcelVOAttribute(name = "固定电话", column = "E", isExport = true)
    private String            tel;                                    // 座机
    //增加三个字段
    private String            groupname;                              //用户组名称
    private String            rolename;                               //角色名称
    @ExcelVOAttribute(name = "所属机构名称(必填)", column = "H", isExport = true)
    private String            orgname;                                //机构名称
    @ExcelVOAttribute(name = "机构编码(必填)", column = "I", isExport = true)
    private String            orgcode;
    private String            leaf;                                   //（给指标权限管理用，判断是机构还是角色）
    private String            funname;                                //菜单名称    末级菜单和页面菜单
    private DDate         	  agingEndDate;                           // 权限时效截止日期
    private String            isdisabled;  
                                 // 是否停用 
    public DDate getAgingEndDate() {
		return agingEndDate;
	}

	public void setAgingEndDate(DDate agingEndDate) {
		this.agingEndDate = agingEndDate;
	}
    
    public String getIsreset() {
		return isreset;
	}

	public void setIsreset(String isreset) {
		this.isreset = isreset;
	}

	public String getOrgcode() {
        return orgcode;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPk_org() {
        return pk_org;
    }

    public void setPk_org(String pk_org) {
        this.pk_org = pk_org;
    }

    public String getIslocked() {
        return islocked;
    }

    public void setIslocked(String islocked) {
        this.islocked = islocked;
    }

    public DDateTime getAbletime() {
        return abletime;
    }

    public void setAbletime(DDateTime abletime) {
        this.abletime = abletime;
    }

    public DDateTime getDisabletime() {
        return disabletime;
    }

    public void setDisabletime(DDateTime disabletime) {
        this.disabletime = disabletime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOnlyadmin() {
        return onlyadmin;
    }

    public void setOnlyadmin(String onlyadmin) {
        this.onlyadmin = onlyadmin;
    }

    @Override
    public String getPassword() {
        return user_password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
	public DDateTime getLastUpdatePasswordTime() {
		return getUpdate_pwd_time();
	}

    public DDateTime getUpdate_pwd_time() {
        return update_pwd_time;
    }
    
	public void setUpdate_pwd_time(DDateTime update_pwd_time) {
		this.update_pwd_time = update_pwd_time;
	}
	
	public String getFunname() {
		return funname;
	}

	public void setFunname(String funname) {
		this.funname = funname;
	}

    
    
    public String getIsdisabled() {
		return isdisabled;
	}

	public void setIsdisabled(String isdisabled) {
		this.isdisabled = isdisabled;
	}

	@Override
    public boolean isDisabled() {
        return "Y".equalsIgnoreCase(getIsdisabled());
    }
    
	@Override
    public boolean isLocked() {
        return "Y".equalsIgnoreCase(getIslocked());
    }
	
	@Override
	public boolean isNeedReset() {
		return "Y".equalsIgnoreCase(getIsreset());
	}

	@Override
	public int getType() {
		return getUsertype().intValue();
	}
}
