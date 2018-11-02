package com.gdsp.platform.log.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.model.PageRegisterVO;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.func.service.IPageRegisterService;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.auth.model.UserRoleVO;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.role.service.IRoleQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.log.service.IAuthorityLogService;
import com.gdsp.platform.systools.datadic.model.RoleAuthorityVO;
import com.gdsp.platform.systools.datalicense.model.DataLicenseVO;
import com.gdsp.platform.systools.datalicense.service.IDataLicenseService;

@Service
@Transactional(readOnly = true)
public class AuthorityLogServiceImpl implements IAuthorityLogService {
	
	@Autowired
    private IUserQueryPubService userQueryPubService;
	@Autowired
    private IPowerMgtQueryPubService powerMgtPubService;
	@Autowired
    private IUserRoleQueryPubService userRoleQueryPubService;
	@Autowired
    private IRoleQueryPubService     roleQueryPubService;
	@Autowired
	IMenuRegisterService      munuRegisterService;
	@Autowired
	IPageRegisterService  pageRegisterService;
	@Autowired
	IDataLicenseService dataLicenseService;

	/**
     * 查询用户角色的list
     * @return
     */
	@Override
	public List<UserVO> getUserRoleList() {
		//先查询出所有用户和角色关系list
    	List<UserRoleVO> userRolePage = userRoleQueryPubService.queryAllUserRoleRelations();
    	List<UserVO>  userRoleList = new ArrayList<UserVO>();
    	List<UserVO>  alluserList = userQueryPubService.findAllUsersList();
    	List<RoleVO>  roleList = roleQueryPubService.findAllRoleList();
    	//根据用户对应关系和角色对应关系，筛选出要查询的属性
    	if(userRolePage != null && userRolePage.size() > 0){
    		for (UserRoleVO userRoleVO : userRolePage) {
        		String userid = userRoleVO.getPk_user();
    			String roleid = userRoleVO.getPk_role();
    			UserVO userVO = new UserVO();
    			if(alluserList != null && alluserList.size() > 0){
	    			for (UserVO user : alluserList) {
	    				if(userid.equals(user.getId())){
	    					userVO.setUsername(user.getUsername());
	    					userVO.setAccount(user.getAccount());
	    					userVO.setOrgname(user.getOrgname());
	    					break;
	    				}
	    			}
    			}
    			if(roleList != null && roleList.size() > 0){
	    			for (RoleVO role : roleList) {
	    				if(roleid.equals(role.getId())){
	    					userVO.setRolename(role.getRolename());
	    					break;
	    				}
	    			}
    			}
    			if((userVO.getUsername())!=""&&(userVO.getUsername())!=null&&(userVO.getRolename())!=""&&(userVO.getRolename())!=null){
    			    userRoleList.add(userVO);
                }
        	}
        	return userRoleList;
    	}else{
    		return null;
    	}
	}

	/**
     * 查询角色功能的list
     * @return
     */
	@Override
	public List<RoleVO> getRoleFuncList() {
		//先查询角色和功能之间的list
    	List<PowerMgtVO>  roleMenuList = powerMgtPubService.queryAllRoleMenuList();
    	List<PowerMgtVO>  rolePageList = powerMgtPubService.queryAllRolePageList();
    	//查询所有末级菜单的菜单id和菜单名
    	List<MenuRegisterVO> allMenuList = munuRegisterService.queryAllMenuList(); 
    	List<MenuRegisterVO> lastLevelMenuList = new ArrayList<MenuRegisterVO>();
    	if(allMenuList != null && allMenuList.size() > 0){
    		for (MenuRegisterVO menuRegisterVO : allMenuList) {
        		if(menuRegisterVO.getFuntype() == 2||menuRegisterVO.getFuntype() == 3){
        			lastLevelMenuList.add(menuRegisterVO);
        		}
    		}
    	}
    	//查询所有页面id和页面名称
    	List<PageRegisterVO> allPageList  = pageRegisterService.queryAllPageList();
    	List<RoleVO>  allRoleList = roleQueryPubService.findAllRoleList();
    	if(roleMenuList !=null && roleMenuList.size()>0){
    		roleMenuList.addAll(rolePageList);
    	}
    	List<RoleVO>  roleFuncList = new ArrayList<RoleVO>();
    	if(roleMenuList !=null && roleMenuList.size()>0){
    		for (PowerMgtVO powerMgtVO : roleMenuList) {
        		String role_id = powerMgtVO.getPk_role();
        		RoleVO roleVO = new RoleVO();
        		if(lastLevelMenuList !=null && lastLevelMenuList.size()>0){
	        		for (MenuRegisterVO menuRegisterVO : lastLevelMenuList) {
	    				if(powerMgtVO.getResource_id().equals(menuRegisterVO.getId())){
	    					roleVO.setFunname(menuRegisterVO.getFunname());
	    					break;
	    				}
	    			}
        		}
        		if(allPageList !=null && allPageList.size()>0){
	        		for (PageRegisterVO pageRegisterVO : allPageList) {
	        			if(powerMgtVO.getResource_id().equals(pageRegisterVO.getId())){
	    					roleVO.setFunname(pageRegisterVO.getFunname());
	    					break;
	    				}
	    			}
        		}
        		if(allRoleList !=null && allRoleList.size()>0){
	        		for (RoleVO role : allRoleList) {
	    				if(role_id.equals(role.getId())){
	    					roleVO.setRolename(role.getRolename());
	    					break;
	    				}
	    			}
        		}
        		if((roleVO.getFunname())!=""&&(roleVO.getFunname())!=null&&(roleVO.getRolename())!=""&&(roleVO.getRolename())!=null){
        			roleFuncList.add(roleVO);
        		}
    		}
        	return roleFuncList;
    	}else{
    		return null;
    	}
	}

	 /**
     * 查询角色数据权限的list
     * @return
     */
	@Override
	public List<RoleAuthorityVO> getRoleDataLists() {
		//查询角色数据权限list
    	List<DataLicenseVO> roleDataList = dataLicenseService.queryAllRoleDataList();
    	List<RoleAuthorityVO> dimList = dataLicenseService.queryAllDimList();
    	List<RoleAuthorityVO> dimValueList = dataLicenseService.queryAllDimValueList();
    	List<RoleAuthorityVO>  roleDataLists = new ArrayList<RoleAuthorityVO>();
    	List<RoleVO>  roleList = roleQueryPubService.findAllRoleList();
    	if(roleDataList != null && roleDataList.size()>0){
    		for (DataLicenseVO dataLicenseVO : roleDataList) {
        		RoleAuthorityVO roleAuthorityVO = new RoleAuthorityVO();
        		if(dimList != null && dimList.size()>0){
	        		for (RoleAuthorityVO dimVO : dimList) {
	    				if(dataLicenseVO.getPk_dic().equals(dimVO.getId())){
	    					roleAuthorityVO.setDic_name(dimVO.getDic_name());
	    					break;
	    				}
	    			}
        		}
        		if(dimValueList != null && dimValueList.size()>0){
	        		for (RoleAuthorityVO dimValueVO : dimValueList) {
	        			if(dataLicenseVO.getPk_dicval().equals(dimValueVO.getId())){
	    					roleAuthorityVO.setDimvl_name(dimValueVO.getDimvl_name());
	    					break;
	    				}
	    			}
        		}
        		if(roleList != null && roleList.size()>0){
	        		for (RoleVO role : roleList) {
	    				if(dataLicenseVO.getPk_role().equals(role.getId())){
	    					roleAuthorityVO.setRolename(role.getRolename());
	    					break;
	    				}
	    			}
        		}
        		if((roleAuthorityVO.getDic_name())!=""&&(roleAuthorityVO.getDic_name())!=null&&(roleAuthorityVO.getRolename())!=""&&(roleAuthorityVO.getRolename())!=null){
        			roleDataLists.add(roleAuthorityVO);
        		}
    		}
        	return roleDataLists;
    	}else{
    		return null;
    	}
    }
	
	/**
     * 条件查询用户角色list
     */
	@Override
	public List<UserVO> queryUserRoleList(String uaoParam,String rname){
		List<UserVO> userRoleList = this.getUserRoleList();
		List<UserVO>  selectUserList = new ArrayList<UserVO>();
		if(uaoParam == null && rname == null && userRoleList != null && userRoleList.size()>0){
    		selectUserList = userRoleList;
    	}
    	if(uaoParam == null && rname != null && userRoleList != null && userRoleList.size()>0){
    		for (UserVO uservo : userRoleList) {
    			if(uservo.getRolename().contains(rname)){
    				selectUserList.add(uservo);
    			}
    		}
    	}
    	if(uaoParam != null && rname == null && userRoleList != null && userRoleList.size()>0){
    		for (UserVO uservo : userRoleList) {
    			if(uservo.getUsername().contains(uaoParam)||uservo.getAccount().contains(uaoParam)||uservo.getOrgname().contains(uaoParam)){
    				selectUserList.add(uservo);
    			}
    		}
    	}
    	if(uaoParam != null && rname != null && userRoleList != null && userRoleList.size()>0){
    		for (UserVO uservo : userRoleList) {
    			if((uservo.getUsername().contains(uaoParam)||uservo.getAccount().contains(uaoParam)||uservo.getOrgname().contains(uaoParam))&&(uservo.getRolename().contains(rname))){
    				selectUserList.add(uservo);
    			}
    		}
    	}
    	return selectUserList;
	}
	
	/**
     * 条件查询角色功能list
     */
	@Override
	public List<RoleVO> queryRoleFuncList(String rolename,String funcname){
		List<RoleVO>  roleFuncList = this.getRoleFuncList();
		List<RoleVO>  selectRoleFuncList = new ArrayList<RoleVO>();
		if(rolename == null && funcname == null && roleFuncList != null && roleFuncList.size() > 0){
    		selectRoleFuncList = roleFuncList;
    	}
    	if(rolename == null && funcname != null && roleFuncList != null && roleFuncList.size() > 0){
    		for (RoleVO rolevo : roleFuncList) {
    			if(rolevo.getFunname().contains(funcname)){
    				selectRoleFuncList.add(rolevo);
    			}
    		}
    	}
    	if(rolename != null && funcname == null && roleFuncList != null && roleFuncList.size() > 0){
    		for (RoleVO rolevo : roleFuncList) {
    			if(rolevo.getRolename().contains(rolename)){
    				selectRoleFuncList.add(rolevo);
    			}
    		}
    	}
    	if(rolename != null && funcname != null && roleFuncList != null && roleFuncList.size() > 0){
    		for (RoleVO rolevo : roleFuncList) {
    			if((rolevo.getRolename().contains(rolename))&&(rolevo.getFunname().contains(funcname))){
    				selectRoleFuncList.add(rolevo);
    			}
    		}
    	}
    	return selectRoleFuncList;
	}
	
	/**
     * 条件查询用户功能list
     */
	@Override
	public List<UserVO> queryUserFuncList(String uaoParameter,String functionname){
		List<UserVO> userRoleList = this.getUserRoleList();
    	List<RoleVO>  roleFuncList = this.getRoleFuncList();
    	List<UserVO>  userFuncList = new ArrayList<UserVO>();
    	if(userRoleList != null && userRoleList.size() > 0){
    		for (UserVO userVO : userRoleList) {
    			if(roleFuncList != null && roleFuncList.size() > 0){
    				for (RoleVO roleVO : roleFuncList) {
            			UserVO userFuncVO = new UserVO();
        				userFuncVO.setUsername(userVO.getUsername());
        				userFuncVO.setAccount(userVO.getAccount());
        				userFuncVO.setOrgname(userVO.getOrgname());
        				userFuncVO.setFunname(roleVO.getFunname());
            			if(roleVO.getRolename() != null && roleVO.getRolename().equals(userVO.getRolename())){
            				userFuncList.add(userFuncVO);
            			}
        			}
    			}
    		}
    	}
    	List<UserVO>  selectUserFuncList = new ArrayList<UserVO>();
    	if(uaoParameter == null && functionname == null && userFuncList != null && userFuncList.size() > 0){
    		selectUserFuncList = userFuncList;
    	}
    	if(uaoParameter == null && functionname != null && userFuncList != null && userFuncList.size() > 0){
    		for (UserVO uservo : userFuncList) {
    			if(uservo.getFunname().contains(functionname)){
    				selectUserFuncList.add(uservo);
    			}
    		}
    	}
    	if(uaoParameter != null && functionname == null && userFuncList != null && userFuncList.size() > 0){
    		for (UserVO uservo : userFuncList) {
    			if(uservo.getUsername().contains(uaoParameter)||uservo.getAccount().contains(uaoParameter)||uservo.getOrgname().contains(uaoParameter)){
    				selectUserFuncList.add(uservo);
    			}
    		}
    	}
    	if(uaoParameter != null && functionname != null && userFuncList != null && userFuncList.size() > 0){
    		for (UserVO uservo : userFuncList) {
    			if((uservo.getUsername().contains(uaoParameter)||uservo.getAccount().contains(uaoParameter)||uservo.getOrgname().contains(uaoParameter))&&(uservo.getFunname().contains(functionname))){
    				selectUserFuncList.add(uservo);
    			}
    		}
    	}
    	return selectUserFuncList;
	}
	
	/**
     * 条件查询角色数据权限list
     */
	@Override
	public List<RoleAuthorityVO> queryRoleDataList(String rlname,String datalimited){
		List<RoleAuthorityVO> roleDataLists = this.getRoleDataLists();
		List<RoleAuthorityVO>  selectRoleDataList = new ArrayList<RoleAuthorityVO>();
		if(rlname == null && datalimited == null && roleDataLists != null && roleDataLists.size() > 0){
    		selectRoleDataList = roleDataLists;
    	}
    	if(rlname == null && datalimited != null && roleDataLists != null && roleDataLists.size() > 0){
    		for (RoleAuthorityVO roleAuthorityVO : roleDataLists) {
    			if(roleAuthorityVO.getDic_name().contains(datalimited)||roleAuthorityVO.getDimvl_name().contains(datalimited)){
    				selectRoleDataList.add(roleAuthorityVO);
    			}
    		}
    	}
    	if(rlname != null && datalimited == null && roleDataLists != null && roleDataLists.size() > 0){
    		for (RoleAuthorityVO roleAuthorityVO : roleDataLists) {
    			if(roleAuthorityVO.getRolename().contains(rlname)){
    				selectRoleDataList.add(roleAuthorityVO);
    			}
    		}
    	}
    	if(rlname != null && datalimited != null && roleDataLists != null && roleDataLists.size() > 0){
    		for (RoleAuthorityVO roleAuthorityVO : roleDataLists) {
    			if((roleAuthorityVO.getRolename().contains(rlname))&&(roleAuthorityVO.getDic_name().contains(datalimited)||roleAuthorityVO.getDimvl_name().contains(datalimited))){
    				selectRoleDataList.add(roleAuthorityVO);
    			}
    		}
    	}
    	return selectRoleDataList;
	}
	
	/**
     * 条件查询用户数据权限list
     */
	@Override
	public List<RoleAuthorityVO> queryUserDataList(String usaoParam,String dataauthority){
		List<UserVO> userRoleList = this.getUserRoleList();
    	List<RoleAuthorityVO> roleDataLists = this.getRoleDataLists();
    	List<RoleAuthorityVO>  userDataList = new ArrayList<RoleAuthorityVO>();
    	if(userRoleList != null && userRoleList.size() > 0){
    		for (UserVO userVO : userRoleList) {
    			if(roleDataLists != null && roleDataLists.size() > 0){
    				for (RoleAuthorityVO roleAuthorityVO : roleDataLists) {
            			RoleAuthorityVO userDataVO = new RoleAuthorityVO();
        				userDataVO.setUsername(userVO.getUsername());
        				userDataVO.setAccount(userVO.getAccount());
        				userDataVO.setOrgname(userVO.getOrgname());
        				userDataVO.setDic_name(roleAuthorityVO.getDic_name());
        				userDataVO.setDimvl_name(roleAuthorityVO.getDimvl_name());
        				if(userVO.getRolename() != null&&userVO.getRolename().equals(roleAuthorityVO.getRolename())){
        					userDataList.add(userDataVO);
        				}
            		}
    			}
    		}
    	}
    	List<RoleAuthorityVO>  selectUserDataList = new ArrayList<RoleAuthorityVO>();
    	if(usaoParam == null && dataauthority == null && userDataList != null && userDataList.size() > 0){
    		selectUserDataList = userDataList;
    	}
    	if(usaoParam == null && dataauthority != null && userDataList != null && userDataList.size() > 0){
    		for (RoleAuthorityVO roleAuthorityVO : userDataList) {
    			if(roleAuthorityVO.getDic_name().contains(dataauthority)||roleAuthorityVO.getDimvl_name().contains(dataauthority)){
    				selectUserDataList.add(roleAuthorityVO);
    			}
    		}
    	}
    	if(usaoParam != null && dataauthority == null && userDataList != null && userDataList.size() > 0){
    		for (RoleAuthorityVO roleAuthorityVO : userDataList) {
    			if(roleAuthorityVO.getUsername().contains(usaoParam)||roleAuthorityVO.getAccount().contains(usaoParam)||roleAuthorityVO.getOrgname().contains(usaoParam)){
    				selectUserDataList.add(roleAuthorityVO);
    			}
    		}
    	}
    	if(usaoParam != null && dataauthority != null && userDataList != null && userDataList.size() > 0){
    		for (RoleAuthorityVO roleAuthorityVO : userDataList) {
    			if((roleAuthorityVO.getUsername().contains(usaoParam)||roleAuthorityVO.getAccount().contains(usaoParam)||roleAuthorityVO.getOrgname().contains(usaoParam))
    			&&(roleAuthorityVO.getDic_name().contains(dataauthority)||roleAuthorityVO.getDimvl_name().contains(dataauthority))){
    				selectUserDataList.add(roleAuthorityVO);
    			}
    		}
    	}
    	return selectUserDataList;
	}

}
