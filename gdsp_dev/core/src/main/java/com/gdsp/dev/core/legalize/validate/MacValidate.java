package com.gdsp.dev.core.legalize.validate;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.gdsp.dev.core.legalize.constants.Constants;
import com.gdsp.dev.core.legalize.core.ValidateSupport;
import com.gdsp.dev.core.legalize.util.Log;
import com.gdsp.dev.core.utils.mac.MacHolder;
/**
 * MAC地址校验,在证书过期校验后执行.
 * 
 * @author yangzh
 * @version 1.0 2018年7月2日
 * @since 1.6
 *
 */
public class MacValidate extends ValidateSupport {
	/**
	 * 证书中没有配置MAC地址
	 */
	public final String ERR_MAC_LIST_EMPTY    = "ERR_0003";
	/**
	 * 本机的MAC地址和证书上的MAC地址不匹配
	 */
	public final String ERR_MAC_NOT_MATCH     = "ERR_0004";
	/**
	 * 在获取本机MAC地址时出现错误
	 */
	public final String ERR_GET_LOCAL_MAC_ERR = "ERR_0005";
	/**
	 * 在获取本机MAC地址时没有报错，但获取的MAC地址列表为空
	 */
	public final String ERR_LOCAL_MAC_EMPTY   = "ERR_0006";
	/**
	 * 对Mac地址进行校验
	 */
	@Override
	protected boolean validateProperty(Properties pro) {
		//获取证书中注册的MAC地址列表,key为mac.list
		String mac = pro.getProperty(Constants.MAC_LIST);
		//如果证书中注册的MAC地址列表为空，报ERR_0003
		if(StringUtils.isEmpty(mac)||mac.trim().length()==0){
			Log.error(ERR_MAC_LIST_EMPTY, "");
			return false;
		}
		String[] macArr = mac.trim().split(",");
	
		List<String> localMacs = null;
		try {
			//获取本机MAC地址的列表
			localMacs = MacHolder.getInstance().getAllLocalMac();
		} catch (UnknownHostException | SocketException e) {
			//如果获取本机MAC地址的过程中出错，报ERR_0005
			Log.error(ERR_GET_LOCAL_MAC_ERR, e);
			return false;
		}
		//如果本机的MAC地址为空，报ERR_0006
		if (localMacs.size()==0) {
			Log.error(ERR_LOCAL_MAC_EMPTY, "");
			return false;
		}
		//进行MAC地址匹配
		if(macArr!=null && macArr.length>0){
			for(String localMac : localMacs){
				for(String m :macArr){
					if(m.equals(localMac)){
						return true;
					}
				}
			}
		}
		//如果本机的MAC地址和证书上的MAC地址不匹配，报ERR_0004
		Log.error(ERR_MAC_NOT_MATCH, "");
		return false;
	}

}
