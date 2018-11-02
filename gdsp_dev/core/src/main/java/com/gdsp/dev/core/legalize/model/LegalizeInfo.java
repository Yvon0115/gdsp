package com.gdsp.dev.core.legalize.model;

import java.util.Properties;

/**
 * 证书信息
 * 
 * @author yangzh
 * @version 1.0 2018年7月2日
 * @since 1.6
 *
 */
public class LegalizeInfo {
	/**
	 * 产品名,对应证书里的info属性
	 */
	private String productName;
	/**
	 * 扩展属性，对应证书里的Extra属性
	 */
	private Properties config;
	/**
	 * 创建一个由产品名和扩展属性组成的实例
	 * @param productName 产品名,对应证书里的info属性
	 * @param config 扩展属性，对应证书里的Extra属性
	 */
	public LegalizeInfo(String productName, Properties config) {
		super();
		this.productName = productName;
		this.config = config;
	}
	/**
	 * 获取证书里注册的产品名
	 * @return 产品名
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 获取证书里注册的扩展属性
	 * @return 扩展属性对象，可以是任意对象
	 */
	public Properties getConfig() {
		return config;
	}
	
}
