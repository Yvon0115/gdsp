/**
 * 
 */
package com.gdsp.portal.sso.user.model;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 同步用户模型
 * @author yucl
 * @version 3.0 2018-2-6
 * @since 1.6
 */
public class UserSyncDataVO extends AuditableEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6313326713388017665L;
	/**
	 * 同步的用户id
	 */
	private String userId;
	/**
	 * 用户账号
	 */
	private String account;
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 是否被锁定
	 */
	private String isLocked;
	/**
	 * 是否停用
	 */
	private String isDisabled;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 座机号
	 */
	private String tel;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 描述
	 */
	private String memo;
	/**
	 * 同步用户的创建日期
	 */
	private DDateTime userCreateTime;
	/**
	 * 同步用户的最后修改日期
	 */
	private DDateTime userLastModifyTime;
	/**
	 * 同步状态（是否已同步）
	 */
	private String syncState;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}
	public String getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
	}
	public DDateTime getUserCreateTime() {
		return userCreateTime;
	}
	public void setUserCreateTime(DDateTime userCreateTime) {
		this.userCreateTime = userCreateTime;
	}
	public DDateTime getUserLastModifyTime() {
		return userLastModifyTime;
	}
	public void setUserLastModifyTime(DDateTime userLastModifyTime) {
		this.userLastModifyTime = userLastModifyTime;
	}
	public String getSyncState() {
		return syncState;
	}
	public void setSyncState(String sync_state) {
		this.syncState = sync_state;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
