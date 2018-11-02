package com.gdsp.platform.grant.auth.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import com.gdsp.platform.grant.auth.dao.IUserRoleDao;
import com.gdsp.platform.grant.auth.model.UserRoleVO;
import com.gdsp.platform.grant.auth.service.IUserRoleOptPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.role.dao.IRoleDao;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.role.service.IRoleQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.log.service.OpLog;

@Service
@Transactional(readOnly = false)
public class UserRoleOptPubServiceImpl implements IUserRoleOptPubService {

    @Autowired
    private IUserRoleDao         		userRoleDao;
    @Autowired
    private IUserQueryPubService 		userPubService;
    @Autowired
    private IRoleDao             		roleDao;
    @Autowired
    private ISystemConfExtService 		systemConfExtService;
    @Autowired
    private IRoleQueryPubService 		roleQueryPubService;
    @Autowired
    private IUserRoleQueryPubService 	userRoleQueryPubService ;
    
    /**  */
    @OpLog
    @Override
    public List<UserRoleVO> addUserRoleOnRole(String roleID, String[] userIds,String agingEndDate) {
        UserRoleVO userRoleVO;
        // 用户ID转换成数组
    	String[] userIDs = userIds[0].replace("[", "").replace("]", "").replace("\"", "").split(",");
        // 权限时效相关
        DBoolean agingStatus = DBoolean.valueOf(systemConfExtService.queryGrantAgingConfigs().getStatus());
    	DBoolean roleAging = DBoolean.valueOf(roleQueryPubService.load(roleID).getAgingLimit());
    	// 添加用户时默认为最大时效，时效作为参数传递是因为添加逻辑可能会变
    	DDateTime aging;
		aging = new DDateTime(agingEndDate);
        for (String userID : userIDs) {
            userRoleVO = new UserRoleVO();
            userRoleVO.setPk_role(roleID);
            userRoleVO.setPk_user(userID);
            userRoleVO.setIsPrompted("N");
        	if (agingStatus.booleanValue()) {
    			if (roleAging.booleanValue()) {
    				// 只有系统开启权限时效且角色为时效角色时才保存时效
    				userRoleVO.setAgingEndDate(aging);
    			}
    		}
            userRoleDao.insert(userRoleVO);
        }
        return null;
    }

    @OpLog
    @Override
    public List<UserRoleVO> addUserRoleOnUser(String userID, String[] roleIDs,boolean isAgingRole) {
        UserRoleVO userRoleVO = new UserRoleVO();
        DDateTime aging = new DDateTime();
        if(!isAgingRole){
            aging = null;
            for (String roleID : roleIDs) {
                userRoleVO = new UserRoleVO();
                userRoleVO.setPk_role(roleID);
                userRoleVO.setPk_user(userID);
                userRoleVO.setAgingEndDate(aging);
                userRoleVO.setIsPrompted("N");
                userRoleDao.insert(userRoleVO);
            }
        }else{
            for (String roleID : roleIDs) {
                RoleVO rolevo = roleQueryPubService.load(roleID);
                String permissionAging = rolevo.getPermissionAging();
                String agingUnit = rolevo.getAgingUnit();
                DDateTime dates = new DDateTime();
                String datetime = "";
                if (SystemConfigConst.GRANT_AGING_UNIT_DAY.equals(agingUnit)) {
                	int days = Integer.parseInt(permissionAging);
                	dates = dates.getDateAfter(days);
                	String date = dates.toString();
                	date = date.substring(0, 11);
                	datetime = date + "23:59:59";
                } else if (SystemConfigConst.GRANT_AGING_UNIT_HOUR.equals(agingUnit)) {
                	int hour = Integer.parseInt(permissionAging);
                	long mins = (long)hour * DTime.MILLIS_PERHOUR;
                	long nowmins = dates.getMillis();
                	long newmins = nowmins+mins;
                	dates = new DDateTime(newmins);
                	datetime = dates.toString();
                	String format = "yyyy-MM-dd HH";
                	dates = new DDateTime(datetime,format);
                	datetime = dates.toString();
                	datetime = datetime + "00:00";
                }
                aging = new DDateTime(datetime);
                userRoleVO = new UserRoleVO();
                userRoleVO.setAgingEndDate(aging);
                userRoleVO.setPk_role(roleID);
                userRoleVO.setPk_user(userID);
                userRoleVO.setIsPrompted("N");
                userRoleDao.insert(userRoleVO);
            }
        }
        return null;
    }

    @Override
    public boolean deleteUserRole(String loginUserID, String[] roleIds) {
        UserVO user = userPubService.load(loginUserID);
        //管理用户可以直接删除，非管理用户可以删除自己管理权限下的权限
        if (user.getUsertype() == GrantConst.USERTYPE_ADMIN) {
            userRoleDao.delete(roleIds);
        } else {
        	
        	/** ---- 查询更改  wqh 2016/12/22  原因：权限拆分 -------------------------*/
        	// 更改前
//            List<RoleVO> roles = roleDao.queryRolePorwerPub(loginUserID);
        	// 更改后
        	List<String> roleIdList = Arrays.asList(roleIds);
        	List<RoleVO> roles = roleQueryPubService.queryRoleListByRoleIds(roleIdList);
        	/**---------------------- 更改至此结束 ----------------------------------*/
        	
            if (roles == null || roles.size() <= 0) {
                return false;
            }
            /** ------dao查询方法修改,操作日志拦截器参数拦截问题,不能使用两个集合类参数,因此改为循环 ------------*/
            for(String id : roleIds){
                userRoleDao.deleteUserRole(roles, id);
            }
        }
        return true;
    }

    @OpLog
    @Override
    public boolean deleteUserRoles(String roleId, String[] id) {
        userRoleDao.deleteUserRoles(roleId, id);
        return true;
    }

    @OpLog
    @Override
	public boolean deleteUserRoleRelations(String[] ids) {
		userRoleDao.deleteRelationsByIds(ids);
		return true;
	}
    
    @OpLog
	@Override
	public boolean modifyUserAgingOnRole(String roleID, String[] userIds,
			String agingEndDate,String dateType) {
		// 用户ID转换成数组
    	String[] userIDs = userIds[0].replace("[", "").replace("]", "").replace("\"", "").split(",");
    	DDateTime aging = new DDateTime();
    	if(agingEndDate != null){
        	if("天".equalsIgnoreCase(dateType)){
        		agingEndDate = agingEndDate+" 23:59:59";
        	}
        	aging = new DDateTime(agingEndDate);
    	}
		UserRoleVO userRoleVO;
		for (String userID : userIDs) {
			userRoleVO = new UserRoleVO();
			userRoleVO.setPk_role(roleID);
			userRoleVO.setPk_user(userID);
			userRoleVO.setAgingEndDate(aging);
			userRoleVO.setIsPrompted("N");
			userRoleDao.update(userRoleVO);
		}
		return true;
	}

    @OpLog
	@Override
	public boolean modifyRoleAgingOnUser(String userID, String[] roleIDs,
			String agingEndDate,String dateType) {
	    UserRoleVO userRoleVO;
	   // 判断时效是否为天
    	if(SystemConfigConst.GRANT_AGING_UNIT_DAY.equalsIgnoreCase(dateType)){
    		agingEndDate = agingEndDate+" 23:59:59";
    	}
    	DDateTime aging = new DDateTime(agingEndDate);
        for (String roleID : roleIDs) {
            userRoleVO = new UserRoleVO();
            userRoleVO.setAgingEndDate(aging);
            userRoleVO.setPk_role(roleID);
            userRoleVO.setPk_user(userID);
            userRoleVO.setIsPrompted("N");
            userRoleDao.update(userRoleVO);
        }
		return true;
	}

    @OpLog
	@Override
	public void updateIsPromptedState(String[] ids) {
		userRoleDao.updateIsPromptedByIds(ids);
	}

	@Override
	public void deleteOverdueRelations() {
		// 删除过期的用户角色关联关系
		List<UserRoleVO> userRoleList = userRoleQueryPubService.findAllUserRoleRelations();
		DDate today = new DDate();
		List<String> needsDeleteIds = new ArrayList<String>();
		//循环数据,根据用户角色关系表中到期时间,当前系统时间,判断是否要删除当前用户角色关系数据
		for(UserRoleVO userRoleVO : userRoleList){
			DDate agingEndDate = userRoleVO.getAgingEndDate();
			if (agingEndDate != null) {
				int result = today.compareTo(agingEndDate);
    			if(result >= 0){
    				needsDeleteIds.add(userRoleVO.getId());
    			}
			}
		}
		String[] ids = needsDeleteIds.toArray(new String[needsDeleteIds.size()]);
		if(ids.length > 0){
			deleteUserRoleRelations(ids);
		}
	}
	
}
