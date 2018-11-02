package com.gdsp.platform.grant.auth.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import com.gdsp.platform.grant.auth.dao.IOrgPowerDao;
import com.gdsp.platform.grant.auth.dao.IUserRoleDao;
import com.gdsp.platform.grant.auth.model.UserRoleVO;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.role.dao.IRoleDao;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.role.service.IRoleQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;

@Service
@Transactional(readOnly = true)
public class UserRoleQueryPubServiceImpl implements IUserRoleQueryPubService {

    @Autowired
    private IUserRoleDao         		userRoleDao;
    @Autowired
    private IUserQueryPubService 		userPubService;
    @Autowired
    private IRoleDao             		roleDao;
    @Autowired
    private IRoleQueryPubService        roleQueryPubService;
    @Autowired
    private IOrgPowerDao 				orgPowerDao;
    @Autowired
	private ISystemConfExtService 	    confExtService;
	
    /**实现更改  lyf*/
    @Override
    public List<RoleVO> queryRoleListByUserId(String userID) {
        List<UserRoleVO> relationsByUserId = new ArrayList<UserRoleVO>();
        List<RoleVO> roleList = new ArrayList<RoleVO>();
        relationsByUserId = queryUserRoleRelationsByUserId(userID);
        if(relationsByUserId==null||relationsByUserId.size() == 0){
            return roleList;
        }else{
            Condition conds = new Condition();
            List<String> roleIds = new ArrayList<String>();
            for (UserRoleVO userRoleVO : relationsByUserId) {
                roleIds.add(userRoleVO.getPk_role());
            }
            if(roleIds.size()>0){
            	conds.addExpression("id", roleIds, "in");
            }else{
            	conds.addExpression("id", "("+"''"+")", "in");
            }
            roleList = roleDao.queryRoleListByCondition(conds, null);
            for (RoleVO roleVO : roleList) {
                for (UserRoleVO relations : relationsByUserId) {
                    if(roleVO.getId().equals(relations.getPk_role())){
                        roleVO.setAgingEndDate(relations.getAgingEndDate());
                    }
                }
            }
        }
         return roleList;
    }

    @Override
	public List<RoleVO> queryFilteredRoleListByUserId(String userID) {
    	List<RoleVO> roleList = new ArrayList<RoleVO>();
    	roleList = queryRoleListByUserId(userID);
    	String agingStatus = confExtService.queryGrantAgingConfigs().getStatus();
        if ("Y".equalsIgnoreCase(agingStatus)) {
 			Iterator<RoleVO> ite = roleList.iterator();
 			Date nowTime = new Date();
 			while (ite.hasNext()) {
 				RoleVO urv = ite.next();
 				DDate agingEndTime = urv.getAgingEndDate();
 				if (agingEndTime != null && (agingEndTime.getMillis() < nowTime.getTime())) {
 					ite.remove();
				}
			}
 		}
		return roleList;
	}

	@Override
    public List<UserVO> queryUserListByRoleId(String roleID) {
        if (StringUtils.isEmpty(roleID))
            return null;
        /* 原来代码 
        List<String> userIds = userRoleDao.queryUserIdsByRoleId(roleID);
        String[] userIdsArr = userIds.toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
        List<UserVO> queryUserByIds = userPubService.queryUsersByIds(userIdsArr, username);
        */
        
        // 查询更改  wangqinghua 2016/12/23  原因：权限拆分
        // 查询中间表获取所选角色关联的所有用户id
//        List<String> userIds = userRoleDao.queryUserIdsByRoleId(roleID);
        
        Condition condition = new Condition();
        condition.addExpression("pk_role", roleID, "=");
        List<UserRoleVO> relations = userRoleDao.queryUserRoleRelationsByCondition(condition);
        List<String> userIds = new ArrayList<String>();
        for (UserRoleVO userRoleVO : relations) {
			userIds.add(userRoleVO.getPk_user());
		}
        // 根据用户ids查询用户信息，可模糊搜索（条件为username）
        if (userIds.size() == 0) {
			userIds.add("");
		}
        List<UserVO> users = userPubService.queryUserListByUserIds(userIds, null);
        for (UserVO userVO : users) {
			for (UserRoleVO userRoleVO : relations) {
				if (userVO.getId().equals(userRoleVO.getPk_user())) {
					userVO.setAgingEndDate(userRoleVO.getAgingEndDate());
				}
			}
		}
        
//        return userRoleService.queryUserListByRoleId(roleID, sort);
        return users;
    }
    
    @Override
    public List<UserVO> queryRoleUnauthorizedUsers(String roleId,String loginUserID, String username) {
        // 登录用户有权限的机构的用户，不含角色已经关联的用户及登录用户,以及超级管理员
        UserVO userVO = userPubService.load(loginUserID);
        
        // 所选角色已关联的用户ids
        List<String> userIds = userRoleDao.queryUserIdsByRoleId(roleId);
        
        // 当前登录用户已关联的角色ids
        List<String> orgIds =   orgPowerDao.queryOrgListByUser(loginUserID);
//        		userRoleDao.queryRoleIdsByUserId(loginUserID);
        
        List<UserVO> noAuthUsers = userPubService.
        		queryNoAuthUserListByOrgIds(userIds, orgIds, userVO.getUsertype(), username);
       
        
//        return userRoleDao.queryUsersByRoleIDAndUserIDAndUserName(roleId, loginUserID, userVO.getUsertype(), username);
        return noAuthUsers;
    }

    
	@Override
	public List<UserRoleVO> findAllUserRoleRelations() {
		
		// 1. 查出所有关联关系
		List<UserRoleVO> allRelations = queryAllUserRoleRelations();
		// 2. 分别取出用户id和角色id的集合
		List<String> userIds = new ArrayList<String>();
		List<String> roleIds = new ArrayList<String>();
		for (UserRoleVO urvo : allRelations) {
			userIds.add(urvo.getPk_user());
			roleIds.add(urvo.getPk_role());
		}
		// 3. 分别查询用户和角色列表
		List<UserVO> userList = userPubService.querUsersByUserIds(userIds);
		List<RoleVO> roleList = roleQueryPubService.queryRoleListByRoleIds(roleIds);
		
		// 4. 添加用户和角色属性
		for (UserRoleVO ur : allRelations) {
			for (UserVO  userVO: userList) {
				if (ur.getPk_user().equals(userVO.getId())) {
					ur.setUserVO(userVO);;
					break;
				}
			}
			for (RoleVO roleVO : roleList) {
				if (ur.getPk_role().equals(roleVO.getId())) {
					ur.setRoleVO(roleVO);
					break;
				}
			}
		}
		
		return allRelations;
	}
	
	@Override
	public List<UserRoleVO> queryAllUserRoleRelations() {
		return userRoleDao.findAllRelations();
	}

    @Override
    public List<UserRoleVO> queryUserRoleRelationsByUserId(String userID) {
    	List<UserRoleVO> userRoleVOs = userRoleDao.queryUserRoleRelationsByUserId(userID);
    	List<String> roleids = new ArrayList<String>();
    	for (UserRoleVO userRoleVO : userRoleVOs) {
			String roleid = userRoleVO.getPk_role();
			roleids.add(roleid);
		}
    	List<RoleVO> rolevos = roleQueryPubService.queryRoleListByRoleIds(roleids);
    	for (UserRoleVO userRoleVO : userRoleVOs) {
    		String roleid = userRoleVO.getPk_role();
			for (RoleVO roleVO : rolevos) {
				String roleId = roleVO.getId();
				if(roleid.equalsIgnoreCase(roleId)){
					userRoleVO.setRoleVO(roleVO);
				}
			}
		}
        return userRoleVOs;
    }

	@Override
	public List<UserVO> queryUsersByCondition(String roleID, Condition cond) {
		if (StringUtils.isEmpty(roleID))
            return null;
		Condition condition = new Condition();
        condition.addExpression("pk_role", roleID, "=");
        List<UserRoleVO> relations = userRoleDao.queryUserRoleRelationsByCondition(condition);
        List<String> userIds = new ArrayList<String>();
        for (UserRoleVO userRoleVO : relations) {
			userIds.add(userRoleVO.getPk_user());
		}
        // 根据用户ids查询用户信息，可模糊搜索
        if (userIds.size() == 0) {
			userIds.add("");
		}
        List<UserVO> users = userPubService.queryUserListByCondition(userIds, cond);
        for (UserVO userVO : users) {
			for (UserRoleVO userRoleVO : relations) {
				if (userVO.getId().equals(userRoleVO.getPk_user())) {
					userVO.setAgingEndDate(userRoleVO.getAgingEndDate());
				}
			}
		}
        return users;
	}

	@Override
	public List<UserVO> queryRoleUnauthorizedUsersByCondition(String roleId, String loginUserID, Condition cond) {
		// 登录用户有权限的机构的用户，不含角色已经关联的用户及登录用户,以及超级管理员
        UserVO userVO = userPubService.load(loginUserID);
        
        // 所选角色已关联的用户ids
        List<String> userIds = userRoleDao.queryUserIdsByRoleId(roleId);
        
        // 当前登录用户已关联的角色ids
        List<String> orgIds =   orgPowerDao.queryOrgListByUser(loginUserID);
//        		userRoleDao.queryRoleIdsByUserId(loginUserID);
        
        List<UserVO> noAuthUsers = userPubService.
        		queryNoAuthUserListByCondition(userIds, orgIds, userVO.getUsertype(), cond);
        return noAuthUsers;
	}
	
	
}
