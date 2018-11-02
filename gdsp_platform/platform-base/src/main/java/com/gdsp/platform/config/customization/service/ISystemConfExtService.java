package com.gdsp.platform.config.customization.service;


import java.util.List;

import com.gdsp.platform.config.customization.model.GrantAgingVO;
import com.gdsp.platform.config.customization.model.MailServiceConfVO;
import com.gdsp.platform.config.customization.model.PasswordConfVO;
import com.gdsp.platform.config.customization.model.SystemConfExtVO;

/**
 * 
* 
* @Description:系统配置扩展服务接口
* @author guoyang
* @date 2016年12月2日
 */
public interface ISystemConfExtService {
	
	/**
	 * 查询权限时效配置信息
	 * @return
	 */
	public GrantAgingVO queryGrantAgingConfigs();
	
	/**
	 * 查询密码安全策略配置信息
	 * @return
	 */
	public PasswordConfVO queryPasswordConf();
	
	/**
	 * 批量更新系统配置扩展信息
	 * @param systemConfExtVOs
	 */
	public void updateBatch(List<SystemConfExtVO> systemConfExtVOs);

	/**
	 *  查询邮件服务配置信息
	 * @author yucl
	 * @date 2018/6/21 14:21
	 * @return com.gdsp.platform.config.customization.model.MailServiceConfVO
	 **/
    public MailServiceConfVO queryMailServiceConfs();
}
