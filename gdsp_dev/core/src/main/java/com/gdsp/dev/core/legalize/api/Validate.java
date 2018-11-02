package com.gdsp.dev.core.legalize.api;

import com.gdsp.dev.core.legalize.model.LegalizeInfo;

/**
 * 自定义校对器.
 * 默认license只进行日期是否过期的认证.
 * 如果需要增加MAC地址或IP地址及其他一些自定义的认证,
 * 可以实现这个接口.
 * 
 * @author yangzh
 * @version 1.0 2018年7月2日
 * @since 1.6
 *
 */
public interface Validate extends LegalizeInfoAwre{
	/**
	 * 验证通过返回true,验证不通过返回false.
	 * 如果返回false,认证器会直接调用System.exit(-1)
	 * 关闭程序进程.
	 * @return
	 * @throws Exception
	 */
	public boolean success() throws Exception;
	/**
	 * 当不通过时返回错误信息
	 * @return
	 */
	public String getFailMsg();
}
