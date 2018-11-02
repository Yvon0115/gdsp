/**
 * 
 */
package com.gdsp.portal.sso.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserOptPubService;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.user.service.IUserService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.schedule.service.AbstractJobImpl;
import com.gdsp.portal.sso.user.model.UserSyncDataVO;
import com.gdsp.portal.sso.user.service.IUserSyncPubService;
import com.gdsp.portal.sso.user.service.IUserSyncTmpService;

/**
 * 同步用户定时任务
 * @author yucl
 * @version 3.0 2018-2-27
 * @since 1.6
 */
public class SyncUserDataJob extends AbstractJobImpl {

	@Resource
	private IUserSyncPubService userSyncPubService;

	@Resource
	private IUserSyncTmpService userSyncTmpService;
	
	@Resource
	private IUserService userService;
	
	@Resource
	private IUserQueryPubService userQueryPubService;
	
	@Resource
	private IUserOptPubService userOptPubService;
	
	@Override
	protected void executeJob() throws BusinessException {
		//1.取出正式表所有数据，并将所有帐号筛选出来
		//2.取出临时表所有数据，并将所有帐号筛选出来
		//3.深度拷贝后，根据集合运算将删除、更新的帐号筛选出来
		//4.删除操作如果这个被删除帐号正式表中已经存在则将用户状态置为停用
		//5.更新操作为全量更新，需要注意的是停用启用状态
		//6.插入操作为手动执行，对应添加用户功能节点
		List<String> userAccountList = new ArrayList<>();
		List<String> tmpAccountList = new ArrayList<>();
		List<UserSyncDataVO> allTmpUsers = userSyncTmpService.queryAllTmpUsers();
		if(null == allTmpUsers || allTmpUsers.size() == 0){
			throw new BusinessException(UserSyncPubServiceImpl.QUERYTMP_ERROR, "内部错误，请联系管理员！");
		}
		for(UserSyncDataVO userSyncVO : allTmpUsers){
			tmpAccountList.add(userSyncVO.getAccount());
		}
		List<UserVO> allUsersList = userQueryPubService.findAllUsersList();
		for(UserVO userVO : allUsersList){
			userAccountList.add(userVO.getAccount());
		}
		
		//深度拷贝
		Mapper mapper = new DozerBeanMapper();
		List<String> deleteAccountList = mapper.map(userAccountList, List.class);
		
		//集合运算
		deleteAccountList.removeAll(tmpAccountList);
		if(deleteAccountList.size() > 0){
			//已删除用户，且已被添加到正式表则执行停用
			List<UserVO> usersByAccountList = userService.queryUserByAccountList(deleteAccountList);
			for(UserVO userVO : usersByAccountList){
				userVO.setIsdisabled(DBoolean.TRUE.toString());
				userOptPubService.update(userVO);
			}
		}
			
		//更新用户
		userAccountList.retainAll(tmpAccountList);
		//筛选出所有需要更新的正式表数据
		if(userAccountList.size() > 0){
			List<UserVO> queryUserByAccountList = userService.queryUserByAccountList(userAccountList);
			List<UserSyncDataVO> tmpUsersByAccountList = userSyncTmpService.queryTmpUsersByAccountList(userAccountList);
			List<UserVO> updateUserList = new ArrayList<>();
			for(UserVO userVO : queryUserByAccountList){
				for(UserSyncDataVO userSyncData : tmpUsersByAccountList){
					if(userVO.getAccount().equals(userSyncData.getAccount())){
						this.assignUpdateUser(userVO, userSyncData);
						updateUserList.add(userVO);
					}
				}
			}
			//执行正式表更新操作：更新用户名、用户状态、创建时间、最后修改时间
			for(UserVO vo : updateUserList){
				userOptPubService.update(vo);
			}
		}
	}

	/**
	 * 设置需更新用户属性值
	 * @param userVO
	 * @param userSyncData
	 */
	private void assignUpdateUser(UserVO userVO, UserSyncDataVO userSyncData) {
		//停用状态更新,其他状态以portal为准。即登录服务器启用portal已停用用户需要portal管理员赋给权限
		userVO.setIsdisabled(DBoolean.TRUE.toString().equals(userSyncData.getIsDisabled()) ? DBoolean.TRUE.toString() : userVO.getIsdisabled());
		userVO.setIslocked(userSyncData.getIsLocked());
		userVO.setUsername(userSyncData.getName());
		userVO.setMobile(userSyncData.getMobile());
		userVO.setTel(userSyncData.getTel());
		userVO.setEmail(userSyncData.getEmail());
		userVO.setSex(userSyncData.getSex());
		userVO.setCreateTime(userSyncData.getUserCreateTime());
		userVO.setLastModifyTime(userSyncData.getUserLastModifyTime());
		userVO.setMemo(userSyncData.getMemo());
//		userVO.setUsertype(userSyncData.getUsertype());
		userVO.setUsertype(GrantConst.USERTYPE_USER);
	}

}
