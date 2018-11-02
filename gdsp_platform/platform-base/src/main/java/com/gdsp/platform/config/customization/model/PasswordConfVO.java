/**
 * 
 */
package com.gdsp.platform.config.customization.model;

/**
 * 
 * @Description:密码安全策略设置VO
 * @author guoyang
 * @date 2016年12月2日
 */
public class PasswordConfVO {

	private Integer timeLimit;                             //密码时限
	
	private Integer pwdLength;                             //密码长度
	
	private String  pwdNumberState;                        //密码含有数字状态（Y：需要；N：不需要）
	
	private String  pwdCharacterState;                     //密码含有字符状态（Y：需要；N：不需要）
	
	private String  pwdEnglishState;					   //密码含有英文字母状态(Y:需要;N:不需要)
	
	private String  pwdCaseState;                          //密码含有大小写状态（Y：需要；N：不需要）

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Integer getPwdLength() {
		return pwdLength;
	}

	public void setPwdLength(Integer pwdLength) {
		this.pwdLength = pwdLength;
	}

	public String getPwdNumberState() {
		return pwdNumberState;
	}

	public void setPwdNumberState(String pwdNumberState) {
		this.pwdNumberState = pwdNumberState;
	}

	public String getPwdCharacterState() {
		return pwdCharacterState;
	}

	public void setPwdCharacterState(String pwdCharacterState) {
		this.pwdCharacterState = pwdCharacterState;
	}

	public String getPwdCaseState() {
		return pwdCaseState;
	}

	public void setPwdCaseState(String pwdCaseState) {
		this.pwdCaseState = pwdCaseState;
	}

	public String getPwdEnglishState() {
		return pwdEnglishState;
	}

	public void setPwdEnglishState(String pwdEnglishState) {
		this.pwdEnglishState = pwdEnglishState;
	}
}
