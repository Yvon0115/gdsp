/**
 * 
 */
package com.gdsp.portal.sso.user.service;

import java.util.List;

import com.gdsp.dev.core.cases.service.ICrudService;
import com.gdsp.portal.sso.user.model.UserSyncDataVO;

/**
 * 用户同步公共接口
 * @author yucl
 * @version 3.0 2018-2-6
 * @since 1.6
 */
public interface IUserSyncPubService extends ICrudService<UserSyncDataVO>{
	
	/**
	 * 同步用户数据操作公共接口
	 * @param userList
	 * @return
	 */
	Boolean synchronizeUserData(List<?> userList);
	
	/**
	 * 同步删除用户数据公共接口
	 * @param deleteList
	 * @return
	 */
	Boolean synchronizeUserDelete(List<?> deleteList);
	
	/**
	 * 同步添加用户数据公共接口
	 * @param insertList
	 * @return
	 */
	Boolean SynchronizeUserInsert(List<?> insertList);
	
	/**
	 * 同步更新用户数据公共接口
	 * @param updateList
	 * @return
	 */
	Boolean SynchronizeUserUpdate(List<?> updateList);
//	/**
//	 * 查询临时表所有用户数据
//	 * @return
//	 */
//	List<UserSyncDataVO> queryAllTmpUsers();
//	
//	/**
//	 * 查询待分配机构的用户
//	 * @return
//	 */
//	List<UserSyncDataVO> queryTmpUsers2Allocated();
//	
//	/**
//	 * 将选中待分配临时表用户存入正式用户表
//	 * @param allocatingUsers
//	 */
//	void saveTmpSync2Users(List<UserSyncDataVO> allocatingUsers,String pk_org);
	
}
