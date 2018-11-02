package com.gdsp.dev.web.legalize;

import org.springframework.beans.factory.InitializingBean;

import com.gdsp.dev.core.legalize.Legalize;
import com.gdsp.dev.core.legalize.api.ResultHandler;
import com.gdsp.dev.core.legalize.api.Validate;
import com.gdsp.dev.core.legalize.validate.MacValidate;
/**
 * Spring启动时进行license的认证
 * 
 * @author yangzh
 * @version 1.0 2018年7月2日
 * @since 1.6
 *
 */
public class SpringLegalizeInitializing  extends AbstractLegalizeInitiazing implements InitializingBean{
	
	@Override
	public final void afterPropertiesSet() throws Exception {
		validateLinense();
	}
	/**
	 * <pre>
	 * 验证证书的入口方法,执行的步骤是：
	 * 1.初始化Legalize;
	 * 2.注册spring中配置的自定义校验器;
	 * 3.调用java方法注册自定义校验器;
	 * 4.注册spring中配置的结果处理器;
	 * 5.调用java方法注册结果处理器;
	 * 6.开始认证
	 * </pre>
	 * @throws Exception
	 */
	protected void validateLinense() throws Exception{
		Legalize legalize = new Legalize();
		this.setConfig(legalize);
		this.registerSpringValidate(legalize);
		this.registerJavaValidate(legalize);
		this.registerSpringResultHandler(legalize);
		this.registerJavaResultHandler(legalize);
		legalize.vertify();
	}
	/**
	 * 初始化核心认证器
	 * @param legalize 核心认证器
	 */
	protected void setConfig(Legalize legalize){
		legalize.setKeyStorePwd(keyStorePwd);	//设置初始化Legalize
		legalize.setLicPath(licPath);			//设置证书路径
		legalize.setOnlykey(onlykey);			//设置系统唯一标识
		legalize.setPubAlias(pubAlias);			//设置公钥别名
		legalize.setPubPath(pubPath);			//设置公钥路径
	}
	/**
	 * 注册spring中配置的认证方法;
	 * @param legalize 核心认证器
	 */
	protected void registerSpringValidate(Legalize legalize){
		if(validates!=null){
			for(Validate v : validates){
				legalize.registerValidate(v);
			}
		}
	}
	/**
	 * <pre>
	 * 调用java方法注册自定义校验器,如果不想在spring中注册自定义校验器,可以覆盖此方法注册
	 * 默认注册了MAC地址的认证
	 * </pre>
	 * @param legalize 核心认证器
	 */
	protected void registerJavaValidate(Legalize legalize){
		legalize.registerValidate(new MacValidate());
	}
	
	/**
	 * 注册spring中配置的结果处理器;
	 * @param legalize 核心认证器
	 */
	protected void registerSpringResultHandler(Legalize legalize){
		if(resultHandlers!=null){
			for(ResultHandler r : resultHandlers){
				legalize.registerResultHandler(r);
			}
		}
	}
	/**
	 * <pre>
	 * 调用java方法注册结果处理器,如果不想在spring中注册结果处理器,可以覆盖此方法注册.
	 * 默认为空方法.
	 * </pre>
	 * @param legalize 核心认证器
	 */
	protected void registerJavaResultHandler(Legalize legalize){
	}
}
