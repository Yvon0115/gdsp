package com.gdsp.platform.systools.datadic.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
/**
 * 
* 
* @Description:数据权限VO
* @author
* @date 2016年12月12日
 */
public class RoleAuthorityVO extends AuditableEntity {

	/**
     * 序列化id
     */
	private static final long serialVersionUID = -9145798780499691141L;
	
	private String            username;                             //用户名
	private String            account;                              //账户
	private String            orgname;                              //所属机构名
    private String            rolename;                             // 角色名称
    private String            dic_name;                          // 数据维度名
    private String            dimvl_name;                         // 数据维度值
    private String            dic_code;                          // 数据维度编码
    private String            dimvl_code;                         // 数据维度值编码
	
    public String getDic_code() {
        return dic_code;
    }
    
    public void setDic_code(String dic_code) {
        this.dic_code = dic_code;
    }
    
    public String getDimvl_code() {
        return dimvl_code;
    }
    
    public void setDimvl_code(String dimvl_code) {
        this.dimvl_code = dimvl_code;
    }
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getDic_name() {
		return dic_name;
	}
	public void setDic_name(String dic_name) {
		this.dic_name = dic_name;
	}
	public String getDimvl_name() {
		return dimvl_name;
	}
	public void setDimvl_name(String dimvl_name) {
		this.dimvl_name = dimvl_name;
	}

}
