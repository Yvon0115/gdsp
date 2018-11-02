package com.gdsp.dev.core.legalize.api;
/**
 * 结果处理器
 * 此接口用于license基础认证(日期过期、Validate)完成后
 * 通过上层应用处理license认证的结果
 * 
 * @author yangzh
 * @version 1.0 2018年7月2日
 * @since 1.6
 *
 */
public interface ResultHandler extends LegalizeInfoAwre{
	/**
	 * 处理license认证的结果.在认证能过后调用.
	 * 
	 */
	void doHandler();
}
