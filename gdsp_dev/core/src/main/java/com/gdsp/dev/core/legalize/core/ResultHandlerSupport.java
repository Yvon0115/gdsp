package com.gdsp.dev.core.legalize.core;

import java.util.Properties;

import com.gdsp.dev.core.legalize.api.ResultHandler;
import com.gdsp.dev.core.legalize.core.LegalizeAwreSupport;
/**
 * license认证结果处理的顶层父类
 * 
 * @author yangzh
 * @version 1.0 2018年7月2日
 * @since 1.6
 *
 */
public abstract class ResultHandlerSupport extends LegalizeAwreSupport implements ResultHandler{
	/**
	 * 获取证书里注册的扩展属性,由上层应用决定怎么处理
	 */
	@Override
	public void doHandler(){
		Properties pro = legalizeInfo.getConfig();
		this.read(pro);
	}
	/**
	 * <pre>
	 * 读取证书里的扩展属性进行操作,由上层应用决定
	 * 
	 * 1.属性要不要存储到LegalizeContext;
	 * 2.将哪些属性存储到LegalizeContext中;
	 * </pre>
	 * @param info 证书里注册的扩展属性
	 */
	protected abstract void read(Properties info);
}
