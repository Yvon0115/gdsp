package com.gdsp.dev.core.legalize.api;

import com.gdsp.dev.core.legalize.model.LegalizeInfo;
/**
 * 实现此接口可以设置证书信息
 * @author Administrator
 *
 */
public interface LegalizeInfoAwre {
	/**
	 * 将证书中的信息传给认证器
	 * @param info 证书包括的信息
	 */
	public void setLegalizeInfo(LegalizeInfo info);
}
