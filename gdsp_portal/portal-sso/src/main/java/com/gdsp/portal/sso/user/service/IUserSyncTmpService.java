/**
 * 
 */
package com.gdsp.portal.sso.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.cases.service.ICrudService;
import com.gdsp.portal.sso.user.model.UserSyncDataVO;

/**
 * 用户同步接口
 * @author yucl
 * @version 3.0 2018-2-6
 * @since 1.6 
 */
public interface IUserSyncTmpService extends ICrudService<UserSyncDataVO>{
	
	/**
	 * 查询临时表所有用户数据
	 * @return
	 */
	List<UserSyncDataVO> queryAllTmpUsers();
	
	/**
	 * 根据id集合查询临时表数据
	 * @param ids
	 * @return UserSyncDataVO
	 */
	List<UserSyncDataVO> queryTmpUsersByIds(List<String> ids);
	
	/**
	 * 根据帐号集合查询临时表用户集合
	 * @return
	 */
	List<UserSyncDataVO> queryTmpUsersByAccountList(List<String> accountList);
	/**
	 * 批量插入临时表用户数据
	 * @param userTmpList
	 * @return
	 */
	int insertBatchTmpUsers(List<UserSyncDataVO> userTmpList);
	
	/**
	 * 查询待分配机构的用户
	 * @return
	 */
	List<UserSyncDataVO> queryTmpUsers2Allocated();
	
	/**
	 * 查询待分配机构的用户
	 * @return
	 */
	Page<UserSyncDataVO> queryTmpUserPage2Allocated(Pageable pageable);
	
	/**
	 * 将选中待分配临时表用户存入正式用户表
	 * @param allocatingUsers
	 */
	void saveTmpSync2Users(List<UserSyncDataVO> UserSyncDatas, String pk_org);

	/**
	 * 批量更新临时表用户数据
	 * @param updateBatchList
	 */
	void updateBatchTmpUsers(List<UserSyncDataVO> updateBatchList);

}
