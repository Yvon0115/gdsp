package com.gdsp.dev.web.legalize;

import java.util.List;

import com.gdsp.dev.core.legalize.api.ResultHandler;
import com.gdsp.dev.core.legalize.api.Validate;
/**
 * Spring与认证器集成一起需要的属性
 * 
 * 
 * @author yangzh
 * @version 1.0 2018年7月3日
 * @since 1.6
 *
 */
public class AbstractLegalizeInitiazing {
	/**
	 * 注入的认证结果监听
	 */
	protected List<ResultHandler> resultHandlers;
	/**
	 * 注入的自定义验证器
	 */
	protected List<Validate> validates;
	/**
	 * 公钥别名,在生成公钥时指定
	 */
	protected String pubAlias;
	/**
	 * 证书库密码.该密码是在使用keytool生成密钥对时设置的密钥库的访问密码
	 */
	protected String keyStorePwd;
	/**
	 * 系统识别码
	 */
	protected String onlykey;
	/**
	 * 证书路径
	 */
	protected String licPath;
	/**
	 * 公钥库路径
	 */
	protected String pubPath;
	/**
	 * 公钥别名
	 * @return
	 */
	public String getPubAlias() {
		return pubAlias;
	}
	/**
	 * 设置公钥别名
	 * @param pubAlias
	 */
	public void setPubAlias(String pubAlias) {
		this.pubAlias = pubAlias;
	}
	/**
	 * 证书库密码
	 * @return 证书库密码
	 */
	public String getKeyStorePwd() {
		return keyStorePwd;
	}
	/**
	 * 设置证书库密码
	 * @param keyStorePwd 证书库密码
	 */
	public void setKeyStorePwd(String keyStorePwd) {
		this.keyStorePwd = keyStorePwd;
	}
	/**
	 * 系统识别码
	 * @return 系统识别码
	 */
	public String getOnlykey() {
		return onlykey;
	}
	/**
	 * 设置系统识别码
	 * @param onlykey 系统识别码
	 */
	public void setOnlykey(String onlykey) {
		this.onlykey = onlykey;
	}
	/**
	 * 证书路径
	 * @return 证书路径
	 */
	public String getLicPath() {
		return licPath;
	}
	/**
	 * 设置证书路径
	 * @param licPath 证书路径
	 */
	public void setLicPath(String licPath) {
		this.licPath = licPath;
	}
	/**
	 * 公钥库路径
	 * @return 公钥库路径
	 */
	public String getPubPath() {
		return pubPath;
	}
	/**
	 * 设置公钥库路径
	 * @param pubPath
	 */
	public void setPubPath(String pubPath) {
		this.pubPath = pubPath;
	}

	/**
	 * 自定义验证器
	 * @return
	 */
	public List<Validate> getValidates() {
		return validates;
	}
	
	/**
	 * 认证结果监听
	 * @return
	 */
	public List<ResultHandler> getResultHandlers() {
		return resultHandlers;
	}
	
	/**
	 * 认证结果监听
	 * @param resultHandlers
	 */
	public void setResultHandlers(List<ResultHandler> resultHandlers) {
		this.resultHandlers = resultHandlers;
	}
	/**
	 * 自定义验证器
	 * @param validates
	 */
	public void setValidates(List<Validate> validates) {
		this.validates = validates;
	}
}
