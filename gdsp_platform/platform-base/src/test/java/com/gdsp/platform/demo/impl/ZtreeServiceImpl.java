/**  
* @Title: ZtreeServiceImpl.java
* @Package com.gdsp.platform.demo.impl
* (用一句话描述该文件做什么)
* @author 连长
* @date 2017年6月13日 下午4:48:46
* @version V1.0  
*/ 
package com.gdsp.platform.demo.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdsp.platform.demo.dao.ZtreeDemoDao;
import com.gdsp.platform.demo.model.ZtreeOrgVO;
import com.gdsp.platform.demo.service.ZtreeService;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;

/**
* @ClassName: ZtreeServiceImpl
* (这里用一句话描述这个类的作用)
* @author shiyingjie
* @date 2017年6月13日 下午4:48:46
*
*/
@Service
public class ZtreeServiceImpl implements ZtreeService {
	
	@Autowired
    private IUserQueryPubService userPubService;
	@Autowired
	private ZtreeDemoDao ztreeDemoDao;
	
	
	@Override
	public List<ZtreeOrgVO> queryRootOrgOnZtree() {
		List<ZtreeOrgVO> ztreeOrgVOs = ztreeDemoDao.queryRootOrgOnZtree();
		return ztreeOrgVOs;
	}
	@Override
	public List<ZtreeOrgVO> queryOrgsByIdOnZtree(String id) {
		List<ZtreeOrgVO> ztreeOrgVOs = ztreeDemoDao.queryOrgsByIdOnZtree(id);
		return ztreeOrgVOs;
	}
	@Override
	public List<ZtreeOrgVO> queryOrgListByUserOnZtree(String userID) {
		if (StringUtils.isBlank(userID)) {
            return null;
        }
        UserVO userVO = userPubService.load(userID);
        // 根据用户类型查询
        if (userVO != null && userVO.getUsertype() == GrantConst.USERTYPE_ADMIN) {
            // 超级管理员，管理所有机构
            return ztreeDemoDao.queryAllOrgListOnZtree();
        }
        // 用户有管理权限的机构
        List<String> OrgIDs = ztreeDemoDao.queryOrgListByUserOnZtree(userID);
        if(OrgIDs.size()==0){
        	OrgIDs.add("");
    	}
        return ztreeDemoDao.queryOrgListByIDsOnZtree(OrgIDs);
	}
	
}
