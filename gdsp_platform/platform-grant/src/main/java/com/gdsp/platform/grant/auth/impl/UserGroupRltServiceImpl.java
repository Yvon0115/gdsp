package com.gdsp.platform.grant.auth.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
//jingfeng@192.9.148.102:29418/gdsp_platform
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.auth.dao.IUserGroupRltDao;
import com.gdsp.platform.grant.auth.model.UserGroupRltVO;
import com.gdsp.platform.grant.auth.service.IUserGroupRltService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.grant.utils.GrantUtils;

@Service
@Transactional(readOnly = true)
public class UserGroupRltServiceImpl implements IUserGroupRltService {

    @Autowired
    private IUserGroupRltDao     rltDao;
    @Resource
    private IOrgQueryPubService orgQueryPubService;
    @Resource
    private IUserQueryPubService userPubService;

    @Transactional
    @Override
    public List<UserGroupRltVO> addRltOnUserGroup(String groupID, String[] userIDs) { //
        if (StringUtils.isEmpty(groupID))
            return null;
        // 删除已有的关联
        // rltDao.deleteRltForGroup(new String[] { groupID });
        if (ArrayUtils.isEmpty(userIDs))
            return null;
        UserGroupRltVO rltVO;
        //
        for (String userID : userIDs) {
            rltVO = new UserGroupRltVO();
            rltVO.setPk_usergroup(groupID);
            rltVO.setPk_user(userID);
            rltDao.insert(rltVO);
        }
        return null;
    }

    @Transactional
    @Override
    public boolean deleteUserGroupRlt(String[] ids) {
        rltDao.delete(ids);
        return true;
    }

    @Override
    public Page<UserGroupRltVO> queryUserGroupRltByGroupId(String groupID, Condition condition, Pageable page) {
        if (StringUtils.isEmpty(groupID))
            return null;       
        /*权限拆分-------------------------------2016.12.28
        * condition.addExpression("rlt.pk_usergroup", groupID);
        return rltDao.queryUserGroupRltByCond(condition, page);
        */
//      String userName = condition.getFreeValue();
//        Page<UserGroupRltVO> queryUserGroupRltByCond = rltDao.queryUserGroupRltByCond(groupID, page);
//        List<UserGroupRltVO> content = queryUserGroupRltByCond.getContent();
//        List<String> list = new ArrayList<String>();
//        for(UserGroupRltVO userGroup : content){
//        	list.add(userGroup.getPk_user());
//        }
//        
      	List<UserVO> uservos = queryUserListByGroupId(groupID,null);
      	List<String> list = new ArrayList<String>();
      	for (UserVO userVO : uservos) {
      		list.add(userVO.getId());
		}
      	List<UserVO> querUsersByUserIds = userPubService.queryUserListByCondition(list, condition);
      	List<String> orgIds = new ArrayList<String>();
        List<String> userIDs = new ArrayList<String>();
        for(UserVO userVO :querUsersByUserIds){
        	orgIds.add(userVO.getPk_org());
        	userIDs.add(userVO.getId());
        }
        List<OrgVO> queryOrgListByIDs = orgQueryPubService.queryOrgListByIDs(orgIds);
        for(UserVO uservo: querUsersByUserIds){
        	for(OrgVO orgvo:queryOrgListByIDs){
        		if(uservo.getPk_org().equals(orgvo.getId())){
        			uservo.setOrgname(orgvo.getOrgname());
        		}
        	}
        }
        Condition con = new Condition();
        String users = null;
        if(userIDs.size() == 0){
        	users = "''";
        	con.addExpression("rlt.pk_user", users, "in");
        }else{
        	con.addExpression("rlt.pk_user", userIDs, "in");
        }
        con.addExpression("rlt.pk_usergroup", groupID,"=");
        Page<UserGroupRltVO> queryUserGroupRltByCondition = rltDao.queryUserGroupRltByCondition(con,page);
        List<UserGroupRltVO> contents = queryUserGroupRltByCondition.getContent();
        for(UserGroupRltVO usergroupVO:contents){
        	for(UserVO userVO:querUsersByUserIds){
        		if(usergroupVO.getPk_user().equals(userVO.getId())){
        			usergroupVO.setUserVO(userVO);	
        		}
        	}
        }
        return queryUserGroupRltByCondition;
    }
/*权限拆分-----------------------------------------2016.12.29
 * 未被调用
    @Override
    public Page<UserVO> queryUserByGroupId(String groupID, Pageable page) {
        return rltDao.queryUserByGroupId(groupID, page);
    }
*/
    @Override
    public List<UserVO> queryUserListByGroupId(String groupID, Sorter sort) {
    /*权限拆分--------------------------------------2016.12.29
     * 
     * return rltDao.queryUserListByGroupId(groupID, sort);
        
     */
    	List<UserGroupRltVO> queryUerIdsByGroupId = rltDao.queryUerIdsByGroupId(groupID);
    	List<String> list = new ArrayList<String>();
    	for(UserGroupRltVO usergroup:queryUerIdsByGroupId){
    		list.add(usergroup.getPk_user());
    	}
    	return userPubService.querUsersByUserIds(list);
    }
    @Override
    public Page<UserVO> queryUserForUserGroupPower(Condition condition, String groupID, String loginUserID, Pageable page) {
        UserVO userVO = userPubService.load(loginUserID);
        String addCond = " usertype = " + GrantConst.USERTYPE_USER + " and u.id not in (select pk_user from rms_usergroup_rlt where pk_usergroup = '" + groupID + "') ";
        // 根据用户类型设置条件查询
        if (userVO.getUsertype() != GrantConst.USERTYPE_ADMIN) {
            // 用户有管理权限的机构用户
            addCond += " and " + GrantUtils.getOrgPowerCondByUser(loginUserID, "pk_org");
            addCond += " and u.id <>'" + loginUserID + "' ";
        }
        /**----lyf 2016.12.28修改 原因：权限拆分----**/
        Page<UserVO> uservos = rltDao.queryUserForUserGroupByAddCond(condition, addCond,page);
        List<UserVO> content = uservos.getContent();
        Set<String> set = new HashSet<>(); 
        List<String> list = new ArrayList<String>();
        for(UserVO uservo : content){
            set.add(uservo.getPk_org());
        }
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            list.add(it.next()); 
        }
        List<OrgVO> queryOrgListByIDs = orgQueryPubService.queryOrgListByIDs(list);
        for(UserVO uservo:content){
            for(OrgVO orgvo:queryOrgListByIDs){
                if(uservo.getPk_org().equals(orgvo.getId())){
                    uservo.setOrgname(orgvo.getOrgname());
                }
            }
        }
        return uservos;
        /**----此次修改结束----**/
    }

    @Override
    public List<UserVO> queryGroupByUser(String userID) {
    	/*权限拆分-------------------------------------2016.12.29
    	 * 
    	 * return rltDao.queryGroupByUser(userID);
    	 */
    	List<UserGroupRltVO> queryUerIdsByPkUser = rltDao.queryUerIdsByPkUser(userID);
    	if(queryUerIdsByPkUser!=null && queryUerIdsByPkUser.size()>0){
    		List<String> list = new ArrayList<String>();
        	for(UserGroupRltVO usergroup:queryUerIdsByPkUser){
        		list.add(usergroup.getPk_user());
        	}
        	return userPubService.querUsersByUserIds(list);
    	}
    	else{
    		return null;
    	}
    }
}
