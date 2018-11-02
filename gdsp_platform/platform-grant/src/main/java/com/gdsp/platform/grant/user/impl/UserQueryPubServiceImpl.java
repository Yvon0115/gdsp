package com.gdsp.platform.grant.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.user.dao.IUserDao;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.grant.utils.GrantUtils;

@Service
@Transactional(readOnly = true)
public class UserQueryPubServiceImpl implements IUserQueryPubService {

    @Autowired
    private IUserDao            userDao;
    @Autowired
    private IOrgQueryPubService orgQueryPubService;
    @Autowired
    private IOrgPowerQueryPubService orgPowerPubService;

    @Override
    public UserVO load(String id) {
        UserVO useVO = userDao.load(id);
        if(!(useVO==null)){
        	if(useVO.getUsertype()==GrantConst.USERTYPE_USER){
           	 OrgVO orgVO = orgQueryPubService.load(useVO.getPk_org());
                useVO.setOrgname(orgVO.getOrgname());
           }
        	return useVO;
        }else{
        	return null;
        }
        
        
    }
    @Override
    public UserVO queryUserByAccount(String account) {
        return userDao.queryUserByAccount(account);
    }
    //流程模块调用
    @Override
    public List<UserVO> queryUserPreByIds(String[] userIds, String[] userGroupIds, String[] roleIds, String[] orgIds) {
        return userDao.queryUserPreByIds(userIds, userGroupIds, roleIds, orgIds);
    }

    @Override
    public List<UserVO> querUsersByOrgIds(List list) {
        return userDao.querUsersByOrgIds(list);
    }

    @Override
    public List<UserVO> querUsersByUserIds(List list) {
    	Condition condition = new Condition();
    	if(list!=null && list.size()>0){
    		condition.addExpression("u.id", list, "in");
    	}
       List<UserVO> querUserByUserIds = userDao.queryUserList(condition);
       return handlerResult(querUserByUserIds);
    }

    @Override
    public List<UserVO> queryUserListByUserIds(List<String> userIds, String username) {
    	 Condition condition = new Condition();
    	 String UserId = AppContext.getContext().getContextUserId();
    	 condition.addExpression("u.id", UserId, "<>");
    	if(userIds!=null && userIds.size()>0){
    		condition.addExpression("u.id", userIds, "in");
    	}
         List<UserVO> querUserByUserIdList = userDao.queryNoAuthUserList(condition,username);
         return handlerResult(querUserByUserIdList);
    }
    
    /**
     * xue  2017.3.5
     * 查询机构id，对是否是管理员进行判断
     */
    @Override
    public List<UserVO> queryUserByCondtion(UserVO userVO, String loginUserId, boolean containLower) {
    	// OrgVO orgVO = orgQueryPubService.load(userCond.getPk_org());
    	// String innercode = orgVO.getInnercode();
    	List<String> orgIdList = new ArrayList<String>();
        if(containLower){
        	List<OrgVO> childOrgList = new ArrayList<OrgVO>();
        	if (GrantConst.USERTYPE_ADMIN == userVO.getUsertype()) {    // 如果是管理员，查询所有机构
        		childOrgList = orgQueryPubService.queryAllOrgList();
			}else{
				childOrgList = orgQueryPubService.querySelftAndChildOrgListById(userVO.getPk_org());
				List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(loginUserId);//获取机构
				childOrgList.retainAll(orgVOs);
				for (OrgVO orgVO : childOrgList) {
	              orgIdList.add(orgVO.getId());
	          }
			}
        }
        
      //  return userDao.queryUserByOrgId(userVO, innercode, loginUserId, containLower);
        List<UserVO> queryUserByOrgId = userDao.queryUserByOrgId(userVO,orgIdList,loginUserId);
        return handlerResult(queryUserByOrgId);
    }
    
    @Override
    public List<UserVO> findAllUsersList() {
    	List<UserVO> userList = userDao.findAllUsersList();;
    	List<OrgVO> orgList = orgQueryPubService.findAllOrgList();
    	if(userList != null && userList.size() > 0){
    		for (UserVO userVO : userList) {
    			if(orgList != null && orgList.size() > 0){
    				for (OrgVO orgVO : orgList) {
    					if(userVO.getPk_org().equals(orgVO.getId())){
    						userVO.setOrgname(orgVO.getOrgname());
    						break;
    					}
    				}
    			}
    		}
    		return userList;
    	}else{
    		return null;
		}
    }
    
	@Override
	public List<UserVO> queryUserByOrgId(String pk_org, Integer userType,String userId,String userName, boolean containLower) {
		if (StringUtils.isEmpty(pk_org))
			return null;
		Condition condition = new Condition();
		if (userType!=null) {
			condition.addExpression("u.usertype", userType);
		}
		if (StringUtils.isNotEmpty(userId)) {
			condition.addExpression("u.id", userId, "<>");
		}
		if(StringUtils.isNotEmpty(userName)){
			condition.addExpression("u.username", userName, "like");
		}
		// 增加机构查询条件
		if (containLower) {
			List<OrgVO> queryChildOrgListById = orgQueryPubService.querySelftAndChildOrgListById(pk_org);
			List<String> orgIdList = new ArrayList<String>();
			for (OrgVO orgVO : queryChildOrgListById) {
				orgIdList.add(orgVO.getId());
			}
			condition.addExpression("u.pk_org", orgIdList, "in");
		} else {
			condition.addExpression("u.pk_org", pk_org);
		}
		List<UserVO> userContent = userDao.queryUserList(condition);
		return handlerResult(userContent);
	}

	@Override
	public List<UserVO> queryUserInfo(String userId, Integer userType,String userName) {
		Condition condition = new Condition();
		if(StringUtils.isNotEmpty(userId)){
			condition.addExpression("u.id", userId, "<>");	
		}
		if(userType!=null){
			condition.addExpression("u.usertype", userType);
		}
		if(StringUtils.isNotEmpty(userName)){
			condition.addExpression("u.username", userName, "like");
		}
		List<UserVO> queryUserContent = userDao.queryUserList(condition);
		return handlerResult(queryUserContent);
	}

	@Override
	public List<UserVO> queryUserList(String userId, String userName) {
		 Condition condition = new Condition();
		if(StringUtils.isNotEmpty(userId)){
			 condition.addExpression("u.id", userId, "in");
		}
		if(StringUtils.isNotEmpty(userName)){
			 condition.addExpression("u.username", userName, "like");
		}
		List<UserVO> queryUserList = userDao.queryUserList(condition);
		return handlerResult(queryUserList);
	}
	 @Override
	    public List<UserVO> queryUserListByUserAndCond(String userId, boolean containSelf, String addCond) {
	        if (StringUtils.isBlank(userId)) {
	            return null;
	        }
	        UserVO userVO = userDao.load(userId);
	        if (StringUtils.isBlank(addCond)) {
	            addCond = "  u.usertype = " + GrantConst.USERTYPE_USER;
	        } else
	            addCond += " and u.usertype = " + GrantConst.USERTYPE_USER;
	        if (!containSelf) {
	            addCond += " and u.id <>'" + userId + "' ";
	        }
	        // 根据用户类型设置条件查询
	        if (userVO.getUsertype() != GrantConst.USERTYPE_ADMIN) {
	            // 用户有管理权限的机构用户
	            addCond += " and " + GrantUtils.getOrgPowerCondByUser(userId, "u.pk_org");
	        }
	      List<UserVO> queryUserListByAddCond = userDao.queryUserListByAddCond(addCond);
	      return handlerResult(queryUserListByAddCond);
	    }

	@Override
	public Map<String, List<UserVO>> queryUserByUserIds(String[] userIds) {
		 @SuppressWarnings("rawtypes")
		MapListResultHandler handler = new MapListResultHandler("id");
		 if(userIds != null && userIds.length > 0){
		     userDao.queryUserByUserIds(handler, userIds);
		     return handler.getResult();
		 }else{
		     return new HashMap<String,List<UserVO>>();
		 }
	        
	}
	@Override
	public List<UserVO> queryNoAuthUserListByOrgIds(List<String> userIds,List<String> orgIds,Integer userType,String userName) {
		 Condition condition = new Condition();
		 String UserId = AppContext.getContext().getContextUserId();
    	 condition.addExpression("u.id", UserId, "<>");
		 if(userIds!=null && userIds.size()>0){
			 condition.addExpression("u.id", userIds, "not in");
		 }
		 if(orgIds!=null && orgIds.size()>0){
			 if(userType==GrantConst.USERTYPE_USER){
				 condition.addExpression("u.pk_org", orgIds, "in");
			 }
		 }
		 List<UserVO> queryNoAuthUserList = userDao.queryNoAuthUserList(condition,userName);
		 return handlerResult(queryNoAuthUserList);
	}
	@Override
	public List<UserVO> queryUserForAreaPower(String userIds, String orgIds,String userName,boolean flag) {
		 Condition condition = new Condition();
		 if(StringUtils.isNotEmpty(userName)){
			 condition.addExpression("u.username", userName, "like");
		}
		 if (userIds.length() > 0) {
	            userIds = userIds.substring(0, userIds.length() - 1);
	            if(flag){
	            	condition.addExpression("u.id", userIds, "in");
	            }else{
	            	condition.addExpression("u.id", userIds, "not in");
	            }   
	        }
		 if (orgIds.length() > 0) {
	            orgIds = orgIds.substring(0, orgIds.length() - 1);
	            condition.addExpression("u.pk_org", orgIds, "in");
	        }
		List<UserVO> queryUserList = userDao.queryUserList(condition);
		return  handlerResult(queryUserList);
		
	}
	/**
	 * 公用方法，给返回的用户list添加机构名称
	 * @param list
	 * @return
	 * 2016年12月26日
	 * win
	 */
	public List<UserVO> handlerResult(List<UserVO> list){
		if(list!=null && list.size()>0){
			List<String> orgIdList = new ArrayList<String>();
			for (UserVO userVO : list) {
				orgIdList.add(userVO.getPk_org());
			}
			List<OrgVO> queryOrgListByIDs = orgQueryPubService.queryOrgListByIDs(orgIdList);
			for (UserVO uservo : list) {
				if(uservo.getUsertype()==GrantConst.USERTYPE_ADMIN){
					continue;
				}
				for (OrgVO orgvo : queryOrgListByIDs) {
					if (uservo.getPk_org().equals(orgvo.getId())) {
						uservo.setOrgname(orgvo.getOrgname());
						break;
					}
				}
			}
		}
	  return list;
	}
	
	@Override
	public UserVO queryUserAuthInfoByAccount(String account) {
		return userDao.queryUserAuthInfoByAccount(account);
	}
	
	@Override
	public List<UserVO> queryUserListByCondition(List<String> userIds, Condition condition) {
	   	String UserId = AppContext.getContext().getContextUserId();
	   	condition.addExpression("u.id", UserId, "<>");
	   	if(userIds!=null && userIds.size()>0){
	   		condition.addExpression("u.id", userIds, "in");
	   	}
        List<UserVO> querUserByUserIdList = userDao.queryNoAuthUserListByCondition(condition);
        return handlerResult(querUserByUserIdList);
	}
	
	@Override
	public List<UserVO> queryUsersByCondtion(UserVO userVO, String loginUserId, boolean containLower, Condition cond) {
    	List<String> orgIdList = new ArrayList<String>();
        if(containLower){
        	List<OrgVO> childOrgList = new ArrayList<OrgVO>();
        	if (GrantConst.USERTYPE_ADMIN == userVO.getUsertype()) {    // 如果是管理员，查询所有机构
        		childOrgList = orgQueryPubService.queryAllOrgList();
			}else{
				childOrgList = orgQueryPubService.querySelftAndChildOrgListById(userVO.getPk_org());
				List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(loginUserId);//获取机构
				childOrgList.retainAll(orgVOs);
				for (OrgVO orgVO : childOrgList) {
	              orgIdList.add(orgVO.getId());
	          }
			}
        }
        List<UserVO> queryUserByOrgId = userDao.queryUserByOrgIdCondition(userVO,orgIdList,loginUserId,cond);
        return handlerResult(queryUserByOrgId);
	}
	@Override
	public List<UserVO> queryNoAuthUserListByCondition(List<String> userIds, List<String> orgIds, Integer userType,
			Condition cond) {
		 String UserId = AppContext.getContext().getContextUserId();
		 cond.addExpression("u.id", UserId, "<>");
		 if(userIds!=null && userIds.size()>0){
			 cond.addExpression("u.id", userIds, "not in");
		 }
		 if(orgIds!=null && orgIds.size()>0){
			 if(userType==GrantConst.USERTYPE_USER){
				 cond.addExpression("u.pk_org", orgIds, "in");
			 }
		 }
		 List<UserVO> queryNoAuthUserList = userDao.queryNoAuthUserListByCondition(cond);
		 return handlerResult(queryNoAuthUserList);
	}
}
