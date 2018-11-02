package com.gdsp.dev.core.legalize.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在认证通过中往控制台打印一些信息
 * 
 * @author yangzh
 * @version 1.0 2018年7月2日
 * @since 1.6
 *
 */
public class Log {
	private static Logger log = LoggerFactory.getLogger(Log.class);
	/**
	 * <pre>
	 * 输出错误信息.目前内置的错误代码有:
	 * ERR_0001   证书已过期
	 * ERR_0002   证书认证失败
	 * ERR_0003   MAC地址清单为空
	 * ERR_0004   本机MAC地址不在允许清单内
	 * ERR_0005   获取本地MAC地址错误
	 * ERR_0006   获取本地MAC地址时未报错，但MAC地址的列表为空.
	 * ERR_0007   证书初始化信息不全.
	 * 
	 * 消息的格式为:"证书认证错误.错误码:{code}.{message}"
	 * 输出完此信息后，结束进程
	 * </pre>
	 * @param code
	 * @param message
	 */
	public static void error(String code,String message){
		log.error("\u8BC1\u4E66\u8BA4\u8BC1\u9519\u8BEF.\u9519\u8BEF\u7801:"+code+"."+message);
		System.exit(-1);
	}
	/**
	 * 消息的格式为:"证书认证错误.错误码:{code}.{message}"
	 * 输出完此信息后，结束进程
	 * @param code
	 * @param ex
	 */
	public static void error(String code,Exception ex){
		error(code,ex.getClass().getName()+"."+ex.getMessage());
	}
	/**
	 * <pre>
	 * 如果认证成功,在控制台打印认证成功信息.
	 * 消息的格式为:认证成功.产品名:{产品名}
	 * </pre>
	 * @param productName 产品名
	 */
	public static void success(String productName){
		log.info("\u8BA4\u8BC1\u6210\u529F.\u4EA7\u54C1\u540D\u662F:"+productName);
	}
}
