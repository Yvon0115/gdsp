package com.gdsp.dev.core.legalize.core;

import java.util.Properties;

import com.gdsp.dev.core.legalize.Legalize;
import com.gdsp.dev.core.legalize.api.Validate;
import com.gdsp.dev.core.legalize.model.LegalizeInfo;
/**
 * 辅助认证接口的顶级父类.
 * 从证书里获取扩展属性(Properties),给子类进行验证.
 * 
 * @author yangzh
 * @version 1.0 2018年7月2日
 * @since 1.6
 *
 */
public abstract class ValidateSupport extends LegalizeAwreSupport implements Validate {
	/**
	 * 错误信息,子类可以直接用
	 */
	protected String msg;

	@Override
	public boolean success() throws Exception {
		return validateInfo();
	}

	@Override
	public String getFailMsg() {
		return msg;
	}
	
	/**
	 * 验证证书的所有信息
	 * @return
	 */
	protected boolean validateInfo(){
		if(legalizeInfo!=null){
			Properties pro  = legalizeInfo.getConfig();
			return validateProperty(pro);
		}
		return false;
	}
	/**
	 * 
	 * 验证证书的扩展属性
	 * 
	 * @param pro 扩展属性
	 * @return true - 通过验证;false - 验证不通过
	 */
	protected abstract boolean validateProperty(Properties pro);
}
