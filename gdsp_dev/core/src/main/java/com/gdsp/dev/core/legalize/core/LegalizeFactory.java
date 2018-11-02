package com.gdsp.dev.core.legalize.core;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
/**
 * 单例模式下的证书管理器
 * 
 * @author yangzh
 * @version 1.0 2018年7月2日
 * @since 1.6
 *
 */
public class LegalizeFactory{
	/**
	 * 证书管理器单例
	 */
	private static LicenseManager licenseManager;
	private LegalizeFactory(){}
	/**
	 * 返回单例的证书管理器
	 * @param param
	 * @return
	 */
	public static synchronized  LicenseManager getManager(LicenseParam param)
	{
		if(licenseManager==null)
		{
			licenseManager=new LicenseManager(param);
		}
		return licenseManager;
	}
}