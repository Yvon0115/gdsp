package com.gdsp.ptbase.portal.dao;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.auth.model.UserDefaultPageVO;

/**
 * Portal页处理
 * @author wqh
 * 2016年11月22日 下午4:50:49
 */
@MBDao
public interface IPortalPageDao {

	/**
	 * 添加默认首页
	 * @param vo
	 */
	void insert(UserDefaultPageVO vo);
	
	/**
	 * 更新默认首页
	 * @param vo
	 */
	void updateUserHomePage(UserDefaultPageVO vo);
	
	/**
	 * 删除默认首页
	 * @param vo
	 */
	void deleteUserHomePage(UserDefaultPageVO vo);
	
}
