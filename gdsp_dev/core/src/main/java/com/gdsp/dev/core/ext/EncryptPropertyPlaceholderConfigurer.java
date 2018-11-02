package com.gdsp.dev.core.ext;

import java.util.ArrayList;
import java.util.List;

import com.gdsp.dev.core.utils.EncryptAndDecodeUtils;

import com.gdsp.dev.core.utils.FileUtils;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 数据源属性加解密配置器
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	/** 需要解密的属性名 */
	private String[] encryptPropNames = null;
	// 从application.properties中读取秘钥
	private String encryptKey = FileUtils.getFileIO("encryptKey", true);
	// 从application.properties中读取数据源密码加密模式
	private String dsPasswordModel = FileUtils.getFileIO("dsPasswordModel", true);

	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		// 获取数据源类型,从datasource.properties文件中读取.
		String datasourceType = FileUtils.getFileIO("dataSourceType", false);
		// 获取需要加解密的属性名
		String[] dbTypeArray = datasourceType.split(",");
		List<String> paramnameList = new ArrayList<String>();
		if (dbTypeArray != null && dbTypeArray.length > 0) {
			for (String dbType : dbTypeArray) {
				paramnameList.add(dbType + ".jdbc.username");
				paramnameList.add(dbType + ".jdbc.password");
			}
		}
		encryptPropNames = paramnameList.toArray(new String[paramnameList .size()]);
		if (isEncrypt(propertyName) && !"show".equals(dsPasswordModel)) {    // 如果属性名在数组中，而且是非开发模式，则该属性值需要解密
			String decryptValue = EncryptAndDecodeUtils.getDecryptString( propertyValue, encryptKey);
			return decryptValue;
		} else {    // 否则正常输出
			return propertyValue;
		}
	}

	/**
	 * 判断属性是否需要加解密
	 * @param propertyName
	 */
	private boolean isEncrypt(String propertyName) {
		if (encryptPropNames != null && encryptPropNames.length > 0) {
			for (String encryptpropertyName : encryptPropNames) {
				if (encryptpropertyName.equals(propertyName))
					return true;
			}
		}
		return false;
	}

}
