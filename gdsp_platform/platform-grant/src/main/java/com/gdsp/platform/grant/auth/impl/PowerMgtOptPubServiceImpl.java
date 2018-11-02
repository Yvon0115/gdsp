package com.gdsp.platform.grant.auth.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.grant.auth.dao.IPowerMgtDao;
import com.gdsp.platform.grant.auth.model.PagePowerVO;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.auth.service.IPowerMgtOptPubService;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.log.service.OpLog;

@Service
@Transactional(readOnly = false)
public class PowerMgtOptPubServiceImpl implements IPowerMgtOptPubService {

    @Autowired
    private IPowerMgtDao         powerMgtDao;
    @Autowired
    private IMenuRegisterService menuService;

    @Override
    public boolean deleteRolePowerMgt(String[] roleID) {
        powerMgtDao.deleteRoleMenuPower(roleID);
        return true;
    }

    @Override
    public boolean deletePageRoleByPageID(String pageID) {
        powerMgtDao.deletePagePowerByPageID(pageID);
        return true;
    }

    @Override
    @OpLog
    public boolean deletePageRole(String[] ids) {
        powerMgtDao.deletePagePower(ids);
        return true;
    }

    @Override
    @OpLog
    public void insertMenuPower(PowerMgtVO powerMgtVO) {
        powerMgtDao.insert(powerMgtVO);
    }

    @Override
    @OpLog
    public void addUserRoleOnPage(String pageID, int objtype, String[] roleIDs) {
        PagePowerVO powerVO;
        for (String roleID : roleIDs) {
            powerVO = new PagePowerVO();
            powerVO.setPk_role(roleID);
            powerVO.setResource_id(pageID);
            powerVO.setObjtype(objtype);
            powerMgtDao.insertPagePower(powerVO);
        }
    }

	@Override
	public void deletePagePowerByRoleIds(String[] roleIds) {
		powerMgtDao.deleteRolePagePower(roleIds);
	}
	
	@Transactional
    @Override
    @OpLog
    public Object addPowerMgtOnRole(List<MenuRegisterVO> userMenuList, String roleId, String menuIds,String checkedMenuIds) {
        if (StringUtils.isEmpty(roleId))
            return false;
        
        String[] menuIDs = menuIds.split(",");
        String[] checkedMenuIDs = checkedMenuIds.split(",");
        
        // 如果修改后菜单为空，且修改前不为空，说明当前操作的角色被取消了所有的菜单权限
        if ((ArrayUtils.isEmpty(menuIDs) || StringUtils.isEmpty(menuIDs[0])) 
        		&& (ArrayUtils.isNotEmpty(checkedMenuIDs) && StringUtils.isNotEmpty(checkedMenuIDs[0]))) {
        	powerMgtDao.deleteUserMenuPower(userMenuList, roleId);    // 删除已有的关联
            return true;
        }
        
        // 如果修改前没有关联任何菜单，且选择的菜单不为空，则为当前操作的角色增加菜单关联关系
        if(ArrayUtils.isNotEmpty(menuIDs) && StringUtils.isNotEmpty(menuIDs[0]) && (ArrayUtils.isEmpty(checkedMenuIDs) || StringUtils.isEmpty(checkedMenuIDs[0]))){
            //处理菜单ID，只插入最后一级借点，类型为，234.（管理菜单，业务菜单，页面菜单）
            List<String> queryMenuIdsByType345 = menuService.queryMenuIdsByType234(menuIDs);
            List<PowerMgtVO> list = new ArrayList<>();
            if (queryMenuIdsByType345 != null) {
                for (String menuID : queryMenuIdsByType345) {
                	PowerMgtVO powerMgtVO = new PowerMgtVO();
                    powerMgtVO.setPk_role(roleId);
                    powerMgtVO.setResource_id(menuID);
                    list.add(powerMgtVO);
                }
            }
        	powerMgtDao.insertBatch(list);
            return true;
        }
        
        //修改后相比修改前新增的机构
        String addIds = "";
        //修改后相比修改前减少的机构
        String delIds = "";
        for(String menuID : menuIDs){
        	boolean isExists = false;
        	for(String checkedMenuID : checkedMenuIDs){
        		if(menuID.equals(checkedMenuID)){
        			isExists = true;
        			break;
        		}
        	}
        	if(!isExists){
        		addIds += menuID + ",";
        	}
        }
        for(String checkedMenuID : checkedMenuIDs){
        	boolean isExists = false;
        	for(String menuID : menuIDs){
        		if(checkedMenuID.equals(menuID)){
        			isExists = true;
        			break;
        		}
        	}
        	if(!isExists){
        		delIds += checkedMenuID + ",";
        	}
        }
        String[] addIDs = addIds.split(",");
        String[] deleteIDs = delIds.split(",");
        if (!ArrayUtils.isEmpty(deleteIDs) && !StringUtils.isEmpty(deleteIDs[0])) {
            //删除减少的菜单与角色关联		
        	powerMgtDao.deleteByRoleIdAndMenuIds(roleId, deleteIDs);
        }
        if(!ArrayUtils.isEmpty(addIDs) && !StringUtils.isEmpty(addIDs[0])){
        	//处理菜单ID，只插入最后一级借点，类型为，234.（管理菜单，业务菜单，页面菜单）
            List<String> queryAddMenuIdsByType345 = menuService.queryMenuIdsByType234(addIDs);
        	List<PowerMgtVO> addList = new ArrayList<PowerMgtVO>();
        	if (queryAddMenuIdsByType345 != null) {
	            for (String menuID : queryAddMenuIdsByType345) {
	            	PowerMgtVO powerMgtVO = new PowerMgtVO();
	                powerMgtVO.setPk_role(roleId);
	                powerMgtVO.setResource_id(menuID);
	                addList.add(powerMgtVO);
	            }
        	}
            //新增增加的机构与角色关联
            powerMgtDao.insertBatch(addList);
        }
        return true;
    }
	
	 @Override
	    public void setMenuIsChecked(Map userMenuMap, Map roleMenuMap) {
	        if (roleMenuMap != null && userMenuMap != null && userMenuMap.size() > 0) {
	            Set roleMenuKeySet = roleMenuMap.keySet();
	            Set userMenuKeySet = userMenuMap.keySet();
	            Iterator roleIter = roleMenuKeySet.iterator();
	            Iterator userIter = userMenuKeySet.iterator();
	            while (roleIter.hasNext()) {
	                String roleKey = (String) roleIter.next();
	                if (roleKey.equals("__null_key__")) {
	                    List<MenuRegisterVO> roleMenuListNullKey = (List) roleMenuMap.get("__null_key__");
	                    List<MenuRegisterVO> userMenuListNullKey = (List) userMenuMap.get("__null_key__");
	                    if (roleMenuListNullKey != null && roleMenuListNullKey.size() > 0) {
	                        for (MenuRegisterVO user : userMenuListNullKey) {
	                            if (userMenuListNullKey != null && userMenuListNullKey.size() > 0) {
	                                for (MenuRegisterVO role : roleMenuListNullKey) {
	                                    if (role.getId().equals(user.getId())) {
	                                        user.setIsChecked("Y");
	                                    }
	                                }
	                            }
	                        }
	                    }
	                } else {
	                    List<MenuRegisterVO> roleMenuListParentId = (List) roleMenuMap.get(roleKey);
	                    List<MenuRegisterVO> userMenuListNullKey = (List) userMenuMap.get(roleKey);
	                    if (userMenuListNullKey != null && userMenuListNullKey.size() > 0) {
	                        for (MenuRegisterVO user : userMenuListNullKey) {
	                            if (userMenuListNullKey != null && userMenuListNullKey.size() > 0) {
	                                if (roleMenuListParentId != null && roleMenuListParentId.size() > 0) {
	                                    for (MenuRegisterVO role : roleMenuListParentId) {
	                                        if (role.getId().equals(user.getId())) {
	                                            user.setIsChecked("Y");
	                                        }
	                                    }
	                                }
	                            }
	                        }
	                    }
	                } //end else
	            } //end while
	        } // end if
	    }
	
	
	
}
