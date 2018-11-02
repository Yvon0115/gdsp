/**
 * 
 */
package com.gdsp.portal.sso.user.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.portal.sso.user.model.UserSyncDataVO;

/**
 * 用户同步dao接口
 * @author yucl
 * @version 3.0 2018-2-7
 * @since 1.6
 */
@MBDao
public interface IUserSyncTmpDao extends ICrudDao<UserSyncDataVO> {

	/**
	 * 查询所有已存用户临时表数据
	 * @return
	 */
	List<UserSyncDataVO> findTmpUsers();

	/**
	 * 批量插入用户临时数据
	 * @param needInsertList
	 */
	int insertTmpUsers(@Param("needInsertList") List<UserSyncDataVO> needInsertList);

	/**
	 * 批量更新用户临时表数据
	 * @param needUpdateUsers
	 */
	void updateTmpUsers(@Param("updateUsers") List<UserSyncDataVO> needUpdateUsers);

	/**
	 * 批量更新临时表用户同步状态
	 * @param needUpdateState
	 */
	void updateUserStates(@Param("needUpdateState") List<UserSyncDataVO> needUpdateState);

	/**
	 * 查询带分配机构用户
	 * @return List<UserSyncDataVO>
	 */
	List<UserSyncDataVO> findUsersNeedAllocated();
	
	/**
	 * 查询待分配机构用户分页数据
	 * @return
	 */
	Page<UserSyncDataVO> findTmpUser2Allocated(@Param("pageable") Pageable pageable);

	/**
	 * 根据账号集合查询临时表用户集合
	 * @param accountList
	 * @return
	 */
	List<UserSyncDataVO> findTmpUserByAccount(@Param("accountList") List<String> accountList);
	
	/**
	 * 根据id集合查询临时表用户集合
	 * @param ids
	 * @return
	 */
	List<UserSyncDataVO> findSyncTmpUsersByIds(@Param("ids") List<String> ids);

}
