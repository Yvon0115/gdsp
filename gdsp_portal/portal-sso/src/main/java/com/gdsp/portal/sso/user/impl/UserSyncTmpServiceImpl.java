/**
 * 
 */
package com.gdsp.portal.sso.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.impl.CrudService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserOptPubService;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.portal.sso.user.dao.IUserSyncTmpDao;
import com.gdsp.portal.sso.user.model.UserSyncDataVO;
import com.gdsp.portal.sso.user.service.IUserSyncTmpService;

/**
 * 用户同步接口实现类
 * @author yucl
 * @version 3.0 2018-3-6
 * @since 1.6
 */
@Service
@Transactional
public class UserSyncTmpServiceImpl extends CrudService<UserSyncDataVO> implements IUserSyncTmpService {

	/**
	 * 日志记录异常写入文件
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserSyncTmpServiceImpl.class);
	
	@Resource
	private IUserSyncTmpDao userSyncTmpDao;
	@Resource
	private IUserQueryPubService userQueryPubService;
	@Resource
	private IUserOptPubService userOptPubService;
	
	@Override
	public ICrudDao<UserSyncDataVO> getDao() {
		return userSyncTmpDao;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserSyncDataVO> queryAllTmpUsers() {
		return userSyncTmpDao.findTmpUsers();
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserSyncDataVO> queryTmpUsers2Allocated() {
		List<UserSyncDataVO> tmpUsers2Allocated = userSyncTmpDao.findUsersNeedAllocated();
		return tmpUsers2Allocated;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<UserSyncDataVO> queryTmpUserPage2Allocated(Pageable pageable) {
		return userSyncTmpDao.findTmpUser2Allocated(pageable);
	}

	@Override
	public void saveTmpSync2Users(List<UserSyncDataVO> UserSyncDatas, String pk_org) {
		if(StringUtils.isEmpty(pk_org)){
			throw new BusinessException("分配用户需要选择机构！");
		}
		List<String> allocatingAccounts = new ArrayList<>();
		for(UserSyncDataVO userSync : UserSyncDatas){
			allocatingAccounts.add(userSync.getAccount());
		}
		List<UserSyncDataVO> allocatingUsers  = userSyncTmpDao.findTmpUserByAccount(allocatingAccounts);
		List<UserVO> allUsersList = userQueryPubService.findAllUsersList();
		List<UserVO> updateList = new ArrayList<>();
		List<UserVO> insertList = new ArrayList<>();
		Boolean isExists;//正是表是否存在标识
		for(UserSyncDataVO VO : allocatingUsers){
			//默认不存在
			isExists = false;
			for(UserVO userVO : allUsersList){
				//比对正是表是否已有该用户，有则代表临时表数据添加过后被删除，需更新
				if(userVO.getAccount().equals(VO.getAccount())){
					isExists = true;
					assignUpdateUser(userVO,VO);
					updateList.add(userVO);
					break;
				}
			}
			if(isExists){
				//判断下一个
				continue;
			}else{
				//正是表不存在
				UserVO insertUser = generatInsertUser(VO, pk_org);
				insertList.add(insertUser);
			}
		}
			
		//插入
		for(UserVO VO : insertList){
			try {
				userOptPubService.insert(VO);
			} catch (Exception e) {
				logger.error("批量插入用户表异常，产生异常单条用户数据帐号为："+VO.getAccount());
				throw new BusinessException("批量插入用户数据异常！",e);
			}
		}
		//更新
		for(UserVO User : updateList){
			try {
				userOptPubService.update(User);
				userOptPubService.transOrg(User.getId(), pk_org);
			} catch (Exception e) {
				logger.error("批量更新用户表异常，产生异常单条用户数据帐号为："+User.getAccount());
				throw new BusinessException("批量更新用户数据异常！",e);
			}
		}
		//更改添加用户的临时表状态
		try {
			userSyncTmpDao.updateUserStates(allocatingUsers);
		} catch (Exception e) {
			logger.error("批量更新临时表异常，数据已回滚，请联系管理员！");
			throw new BusinessException(UserSyncPubServiceImpl.UPDATEING_ERROR,"内部错误！",e);
		}
	}

	@Override
	public int insertBatchTmpUsers(List<UserSyncDataVO> userTmpList) {
		return userSyncTmpDao.insertTmpUsers(userTmpList);
	}

	@Override
	public void updateBatchTmpUsers(List<UserSyncDataVO> updateBatchList) {
		userSyncTmpDao.updateTmpUsers(updateBatchList);
	}

	@Override
	public List<UserSyncDataVO> queryTmpUsersByAccountList(List<String> accountList) {
		return userSyncTmpDao.findTmpUserByAccount(accountList);
	}
	
	/**
	 * 设置需更新用户属性值
	 * @param userVO
	 * @param userSyncData
	 */
	private void assignUpdateUser(UserVO userVO, UserSyncDataVO userSyncData) {
		//停用状态更新,其他状态以portal为准；即登录服务器启用portal已停用用户需要portal管理员赋给权限
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
		//将临时表中对应数据同步状态更新
		userSyncData.setSyncState(DBoolean.TRUE.toString());
	}
	
	/**
	 * 设置需插入用户属性值
	 * @param userVO
	 * @param userSyncData
	 */
	private UserVO generatInsertUser(UserSyncDataVO userSyncData ,String pk_org) {
		UserVO userVO = new UserVO();
		if(StringUtils.isNotEmpty(userSyncData.getUserId())){
			userVO.setId(userSyncData.getUserId());
		}
		userVO.setPk_org(pk_org);
		userVO.setAccount(userSyncData.getAccount());
		userVO.setUsername(userSyncData.getName());
		userVO.setIslocked(userSyncData.getIsLocked());
		userVO.setIsdisabled(userSyncData.getIsDisabled());
		userVO.setMobile(userSyncData.getMobile());
		userVO.setTel(userSyncData.getTel());
		userVO.setEmail(userSyncData.getEmail());
		userVO.setSex(userSyncData.getSex());
		userVO.setCreateTime(userSyncData.getUserCreateTime());
		userVO.setLastModifyTime(userSyncData.getUserLastModifyTime());
		userVO.setMemo(userSyncData.getMemo());
//		userVO.setUsertype(userSyncData.getUsertype());
		userVO.setUsertype(GrantConst.USERTYPE_USER);
		//将临时表中对应数据同步状态更新
		userSyncData.setSyncState(DBoolean.TRUE.toString());
		return userVO;
	}

	@Override
	public List<UserSyncDataVO> queryTmpUsersByIds(List<String> ids) {
		return userSyncTmpDao.findSyncTmpUsersByIds(ids);
	}

}
