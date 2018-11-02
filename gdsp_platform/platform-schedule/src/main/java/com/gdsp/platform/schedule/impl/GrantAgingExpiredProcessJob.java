/**
 * 
 */
package com.gdsp.platform.schedule.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.platform.config.customization.model.GrantAgingVO;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import com.gdsp.platform.grant.auth.service.IUserRoleOptPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.schedule.service.AbstractJobImpl;

/**
 * 权限时效过期处理  后台任务
 * @Description: 删除过期的时效角色与用户关联关系
 * @author wqh
 * @since 2016年12月14日
 */
public class GrantAgingExpiredProcessJob extends AbstractJobImpl {
	@Autowired
	private IUserRoleQueryPubService userRoleQueryPubService;
	@Autowired
	private IUserRoleOptPubService userRoleOptPubService;
	@Autowired
	private ISystemConfExtService    systemConfExtService;

	@Override
	protected void executeJob() throws BusinessException {
		//查询权限时效配置
		GrantAgingVO grantAging = systemConfExtService.queryGrantAgingConfigs();				
		//如开启,查询用户角色关系表所有数据
		if("Y".equals(grantAging.getStatus())){
			userRoleOptPubService.deleteOverdueRelations();
		}	
	}

}
