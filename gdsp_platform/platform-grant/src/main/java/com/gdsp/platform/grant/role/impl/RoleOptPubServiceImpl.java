package com.gdsp.platform.grant.role.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.auth.service.IOrgPowerOptPubService;
import com.gdsp.platform.grant.auth.service.IPowerMgtOptPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleOptPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.role.dao.IRoleDao;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.role.service.IRoleOptPubService;
import com.gdsp.platform.log.service.OpLog;

@Service
@Transactional(readOnly = false)
public class RoleOptPubServiceImpl implements IRoleOptPubService {

    @Autowired
    private IRoleDao     roleDao;
    @Autowired
    private IUserRoleOptPubService userRoleOptPubService;
    @Autowired
    private IUserRoleQueryPubService userRoleQueryPubService;
    @Autowired
    private IOrgPowerOptPubService orgPowerOptPubService;
    @Autowired
    private IPowerMgtOptPubService powerMgtOptPubService;
    @Autowired
    private IOrgQueryPubService orgQueryPubService;

    @Override
    @OpLog
    public void insert(RoleVO vo) {
        roleDao.insert(vo);
     // 角色建立后，默认有所在机构及下级机构的权限
        String orgID = vo.getPk_org();
        //重构接口修改
        /**lyf 2016.12.23修改 权限拆分start**/
        //List<OrgVO> orgVOs = orgQueryPubService.queryChildOrgListById(orgID,true);
        /**end**/
//        List<OrgVO> orgVOs = orgQueryPubService.queryChildOrgListById(orgID);
        
        List<OrgVO> orgVOs = orgQueryPubService.querySelftAndChildOrgListById(orgID);
        OrgPowerVO orgPowerVO = null;
        if(orgVOs != null && orgVOs.size() > 0){
        	for (OrgVO orgVO : orgVOs) {
        		orgPowerVO = new OrgPowerVO();
        		orgPowerVO.setPk_role(vo.getId());
        		orgPowerVO.setResource_id(orgVO.getId());
        		orgPowerOptPubService.insert(orgPowerVO);
        	}
        }
    }

    @Override
    @OpLog
    public void update(RoleVO vo) {
        //更新角色表信息
        roleDao.update(vo);
        /*String roleID = vo.getId();
        //(修改 by lyf 原因：修改完角色后，如果时效时间发生了变化，需要更新角色用户关联表中的关联时效)
        List<UserVO> uservos = userRoleQueryPubService.queryUsersByRoleId(roleID,null);
        if(uservos.size() > 0){
            List<String> userids = new ArrayList<String>();
            for (UserVO uservo : uservos) {
                String userID = uservo.getId();
                userids.add(userID);
            }
            String[] userIDs = userids.toArray(new String[userids.size()]);
            String permissionAging = vo.getPermissionAging();
            String agingUnit = vo.getAgingUnit();
            Date nowDate = new Date();
            String date = null;
            
            if(SystemConfigConst.GRANT_AGING_UNIT_DAY.equals(agingUnit)){
            	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
                Calendar ca = Calendar.getInstance();
                int permissionTime = Integer.parseInt(permissionAging);
                ca.add(Calendar.DATE, permissionTime);
                nowDate = ca.getTime();
                long milines = nowDate.getTime();
                long seconds = milines%(86400000);
                long newMilines = milines-seconds-8*60*60*1000;
                Date dates = new Date(newMilines);
                date = format.format(dates)+"23:59:59";
            }else if(SystemConfigConst.GRANT_AGING_UNIT_HOUR.equals(agingUnit)){
            	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar ca = Calendar.getInstance();
                int permissionTime = Integer.parseInt(permissionAging);
                ca.add(Calendar.HOUR, permissionTime);
                nowDate = ca.getTime();
                long milines = nowDate.getTime();
                long seconds = milines%(3600000);
                long newMilines = milines-seconds;
                Date dates = new Date(newMilines);
                date = format.format(dates);
            }
            userRoleOptPubService.modifyUserAgingOnRole(roleID, userIDs, date,null);
        }*/
    }

    @Override
    @OpLog
    public boolean deleteRole(String[] roleIds) {
        // 删除角色关联机构信息
        orgPowerOptPubService.deleteRoleOrgPower(roleIds);
        // 删除角色关联菜单信息
        powerMgtOptPubService.deleteRolePowerMgt(roleIds);
        // 删除角色关联用户信息
        userRoleOptPubService.deleteUserRole(AppContext.getContext().getContextUserId(), roleIds);
        // 删除角色关联页面信息
        powerMgtOptPubService.deletePagePowerByRoleIds(roleIds);
        // 删除角色
        roleDao.delete(roleIds);
        return true;
    }

	@Override
	public boolean setRoleAgingStatus(String roleID, String agingLimit) {
		// 只更新角色时效标识
		roleDao.updateRoleAgingStatus(roleID, agingLimit);
		return true;
	}
}
