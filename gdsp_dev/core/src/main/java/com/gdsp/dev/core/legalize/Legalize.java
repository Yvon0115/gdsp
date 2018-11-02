package com.gdsp.dev.core.legalize;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.apache.commons.lang.StringUtils;

import com.gdsp.dev.core.legalize.api.ResultHandler;
import com.gdsp.dev.core.legalize.api.Validate;
import com.gdsp.dev.core.legalize.core.LegalizeFactory;
import com.gdsp.dev.core.legalize.model.LegalizeInfo;
import com.gdsp.dev.core.legalize.util.Log;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseContentException;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
/**
 * 核心证书认证器
 * 
 * @author yangzh
 * @version 1.0 2018年7月2日
 * @since 1.6
 *
 */
public class Legalize {
	/**
	 * 证书过期
	 */
	public final String ERR_EXPIRE  = "ERR_0001";
	/**
	 * 证书认证失败,具体原因看后面跟的Exception
	 */
	public final String ERR_FAILARE = "ERR_0002";
	/**
	 * 证书认证时必要的信息没有注入,包括公钥别名、证书库密码、系统识别码、lic证书路径、publicCerts.store公钥库路径
	 */
	public final String ERR_INFO_EMPTY = "ERR_0007";
	/**
	 * 用于保存认证后的证书中的信息
	 */
	private LegalizeInfo info;
	/**
	 * 自定义验证器
	 */
	private List<Validate> validates = null;
	/**
	 * 结果处理器
	 */
	private List<ResultHandler> resultHandlers = null;
	
	
 	/**
	 * 公钥别名,在生成公钥时指定
	 */
	private String pubAlias;
	/**
	 * 证书库密码.该密码是在使用keytool生成密钥对时设置的密钥库的访问密码
	 */
	private String keyStorePwd;
	/**
	 * 系统识别码
	 */
	private String onlykey;
	/**
	 * 证书路径
	 */
	private String licPath;
	/**
	 * 公钥库路径
	 */
	private String pubPath;
	
	/**
	 * 初始化证书的相关参数
	 * @return LicenseParam 初始化证书的参数
	 */
	private LicenseParam initLicenseParams() {
		if(StringUtils.isEmpty(keyStorePwd))Log.error(ERR_INFO_EMPTY,"证书库密码不能为空");
		if(StringUtils.isEmpty(pubPath))Log.error(ERR_INFO_EMPTY,"公钥库路径不能为空");
		if(StringUtils.isEmpty(pubAlias))Log.error(ERR_INFO_EMPTY,"公钥库别名不能为空");
		if(StringUtils.isEmpty(onlykey))Log.error(ERR_INFO_EMPTY,"系统唯一标识不能为空");
		Class<Legalize> clazz = Legalize.class;
		Preferences pre = Preferences.userNodeForPackage(clazz);
		CipherParam cipherParam = new DefaultCipherParam(keyStorePwd);
		KeyStoreParam pubStoreParam = new DefaultKeyStoreParam(clazz, pubPath, pubAlias, keyStorePwd, null);
		LicenseParam licenseParam = new DefaultLicenseParam(onlykey, pre, pubStoreParam, cipherParam);
		return licenseParam;
	}
	/**
	 * 获取证书管理器
	 * @return
	 */
	private LicenseManager getLicenseManager() {
		return LegalizeFactory.getManager(initLicenseParams());
	}

	/**
	 * 安装证书证书
	 * 
	 * @param 存放证书的路径
	 * @return
	 * @throws Exception 
	 */
	private void install() throws Exception {
		LicenseManager licenseManager = getLicenseManager();
		//安装证书
		licenseManager.install(new File(licPath));
		//返回证书中的信息
		LicenseContent content = licenseManager.verify();
		info = new LegalizeInfo(content.getInfo(),(Properties)content.getExtra());
		//辅助认证
		if(this.validates!=null){
			for(Validate v : this.validates){
				v.setLegalizeInfo(info);
				if(!v.success()){//如果校验失败,则停止进程
					System.exit(-1);
				}
			}
		}
	}

	/**
	 * 验证证书的合法性
	 */
	public void vertify() {
		try {
			this.install();
		} catch (LicenseContentException ex) {
			//如果证书过期，报ERR_0001
			Log.error(ERR_EXPIRE,ex);
		} catch (Exception e) {
			//如果其他错误，报ERR_0002
			Log.error(ERR_FAILARE,e);
		}
		this.runResultHandler();
		Log.success(getInfo().getProductName());
	}
	
	/**
	 * 调用结果处理器，此方法是为加密AppConfig属性的设计的，
	 * 如果AppConfig或其他属性需要加密,
	 * 可以在生成证书时加入那些属性,同时将属性文件中的相应属性去掉,
	 * 然后在这个方法中读取解密属性在设置回去.
	 * 不需要改动获取属性的方法.
	 * @param legalize
	 */
	private void runResultHandler(){
		if(this.resultHandlers!=null){
			for(ResultHandler handler : resultHandlers){
				handler.setLegalizeInfo(this.info);
				handler.doHandler();
			}
		}
	}
	/**
	 * 返回认证后的信息
	 * @return
	 */
	public LegalizeInfo getInfo(){
		return info;
	}
	/**
	 * 注册一个辅助认证器
	 * @param validate
	 */
	public void registerValidate(Validate validate){
		if(validates ==null){
			validates = new ArrayList<>();
		}
		validates.add(validate);
	}
	
	/**
	 * 注册一个辅助认证器
	 * @param validate
	 */
	public void registerResultHandler(ResultHandler handler){
		if(resultHandlers ==null){
			resultHandlers = new ArrayList<>();
		}
		resultHandlers.add(handler);
	}
	/**
	 * 得到公钥别名
	 * @return 公钥别名
	 */
	public String getPubAlias() {
		return pubAlias;
	}
	/**
	 * 设置公钥别名
	 * @param pubAlias 公钥别名
	 */
	public void setPubAlias(String pubAlias) {
		this.pubAlias = pubAlias;
	}
	/**
	 * 获取证书库密码
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
	 * 获取系统唯一标识码
	 * @return 系统唯一标识码
	 */
	public String getOnlykey() {
		return onlykey;
	}
	/**
	 * 设置系统唯一标识码
	 * @param onlykey 系统唯一标识码
	 */
	public void setOnlykey(String onlykey) {
		this.onlykey = onlykey;
	}
	/**
	 * 获取证书路径
	 * @return .lic证书路径
	 */
	public String getLicPath() {
		return licPath;
	}
	/**
	 * 设置证书路径
	 * @param licPath .lic证书路径
	 */
	public void setLicPath(String licPath) {
		this.licPath = licPath;
	}
	/**
	 * 获取公钥路径
	 * @return 公钥路径
	 */
	public String getPubPath() {
		return pubPath;
	}
	/**
	 * <pre>
	 * 设置公钥路径,此路径必须是在classpath的相对路径,不是绝对路径
	 * 通过Class.getResourceAsStream(pubPath)可以获取InputStream
	 * </pre>
	 * @param pubPath 公钥路径
	 */
	public void setPubPath(String pubPath) {
		this.pubPath = pubPath;
	}
}