/**
 * 
 */
package com.gdsp.platform.schedule.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.platform.comm.message.service.IMessageService;
import com.gdsp.platform.comm.message.utils.MessageConst;
import com.gdsp.platform.config.customization.model.GrantAgingVO;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import com.gdsp.platform.grant.auth.model.UserRoleVO;
import com.gdsp.platform.grant.auth.service.IUserRoleOptPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.schedule.service.AbstractJobImpl;

/**
 * 权限时效提醒  后台任务
 * @Description:
 * @author
 * @date 2016年12月14日
 */
public class GrantAgingWarningJob extends AbstractJobImpl{
	@Autowired
	private IUserRoleQueryPubService userRoleQueryPubService;
	@Autowired
	private IUserRoleOptPubService userRoleOptPubService;
	@Autowired
	private ISystemConfExtService    systemConfExtService;
	@Autowired
    private IMessageService     messageService;

	@Override
	protected void executeJob() throws BusinessException {
		//查询权限时效配置
		GrantAgingVO grantAging = systemConfExtService.queryGrantAgingConfigs();
		//如开启,且到期提醒时间如大于0,查询用户角色关系表所有数据
		if(grantAging != null && "Y".equals(grantAging.getStatus()) && grantAging.getLeadTime() > 0){
			sendGrantAgingMessages(grantAging);
		}
	}

	/**
	 * 
	 * @param grantAging
	 */
	private void sendGrantAgingMessages(GrantAgingVO grantAging) {
		// 扫描关联关系全表
		List<UserRoleVO> userRoleList = userRoleQueryPubService.findAllUserRoleRelations();
		Integer leadTime = grantAging.getLeadTime();
		DDate today = DDate.parseDDate(new DDate());//new出来的时间需要格式化，去掉时分秒的数据。
		List<String> promptedIds = new ArrayList<String>();
		for(UserRoleVO userRoleVO : userRoleList){
			DDate agingEndDate = userRoleVO.getAgingEndDate();
			if (agingEndDate != null) {
				DDate warningDate = DDate.parseDDate(agingEndDate.getDateBefore(leadTime));//提醒时间也需要格式化，去掉时分秒的数据，仅仅比较日期		lijun 20170421		
				int result = today.compareTo(warningDate);
				// 超过指定提醒时间发送通知
				if(result >= 0 && "N".equals(userRoleVO.getIsPrompted())){
					promptedIds.add(userRoleVO.getId());
					messageService.senderMessage(userRoleVO.getUserVO().getId(),
							userRoleVO.getUserVO().getUsername(), 
							MessageConst.SYSTEM_MESSAGE_ID, 
							MessageConst.SYSTEM_MESSAGE_NAME,
		                    "权限时效到期提醒", 
		                    userRoleVO.getRoleVO().getRolename() 
		                    + "角色将于" + agingEndDate.getYEAR() 
		                    + "年" + agingEndDate.getMONTH() 
		                    + "月" + agingEndDate.getDAY() + "日到期,如需延长权限时效请及时与管理员联系！"
		            );
				}
			}
		}
		// 消息发送过后将标志位置为“已提醒”
		String[] ids = promptedIds.toArray(new String[promptedIds.size()]);
		if(ids.length > 0){
			userRoleOptPubService.updateIsPromptedState(ids);
		}
	}
	

}
