package com.gdsp.platform.grant.auth.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.grant.auth.dao.IOrgPowerDao;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.auth.service.IOrgPowerOptPubService;
import com.gdsp.platform.log.service.OpLog;

@Service
@Transactional(readOnly = false)
public class OrgPowerOptPubServiceImpl implements IOrgPowerOptPubService {

    @Autowired
    private IOrgPowerDao orgPowerDao;

    @Override
    public void insert(OrgPowerVO vo) {
        orgPowerDao.insert(vo);
    }

    @Override
    @OpLog
    public boolean deleteOrgPower(String[] ids) {
        orgPowerDao.delete(ids);
        return true;
    }

    @Override
    @OpLog
    public boolean deleteRoleOrgPower(String roleID) {
        orgPowerDao.deleteRoleOrgPower(new String[] { roleID });
        return true;
    }

    @Override
    @OpLog
    public List<OrgPowerVO> addOrgPowerOnRole(String roleID, String[] orgIDs,String[] checkedIDs) {
        if (StringUtils.isEmpty(roleID))
            return null;
        //如果没有修改后没有选中机构，则把角色与机构关系全部删除
        if ((ArrayUtils.isEmpty(orgIDs) || StringUtils.isEmpty(orgIDs[0])) && ArrayUtils.isNotEmpty(checkedIDs) && StringUtils.isNotEmpty(checkedIDs[0])) {
            // 删除已有的关联		
            orgPowerDao.deleteRoleOrgPower(new String[] { roleID });
            return null;
        }
        List<OrgPowerVO> list = new ArrayList<>();
        for (String orgID : orgIDs) {
            OrgPowerVO orgPowerVO = new OrgPowerVO();
            orgPowerVO.setPk_role(roleID);
            orgPowerVO.setResource_id(orgID);
            list.add(orgPowerVO);
        }
        //如果修改前没有机构与角色关系,则增加修改够选择的所有机构与角色关系
        if(ArrayUtils.isNotEmpty(orgIDs) && StringUtils.isNotEmpty(orgIDs[0]) && (ArrayUtils.isEmpty(checkedIDs) || StringUtils.isEmpty(checkedIDs[0]))){
            orgPowerDao.insertBatch(list);
            return list;
        }
        //修改后相比修改前新增的机构
        String addIds = "";
        //修改后相比修改前减少的机构
        String delIds = "";
        for(String orgID : orgIDs){
        	boolean isExists = false;
        	for(String checkedID : checkedIDs){
        		if(orgID.equals(checkedID)){
        			isExists = true;
        			break;
        		}
        	}
        	if(!isExists){
        		addIds += orgID + ",";
        	}
        }
        for(String checkedID : checkedIDs){
        	boolean isExists = false;
        	for(String orgID : orgIDs){
        		if(checkedID.equals(orgID)){
        			isExists = true;
        			break;
        		}
        	}
        	if(!isExists){
        		delIds += checkedID + ",";
        	}
        }
        String[] addIDs = addIds.split(",");
        String[] deleteIDs = delIds.split(",");
        if (!ArrayUtils.isEmpty(deleteIDs) && !StringUtils.isEmpty(deleteIDs[0])) {
            //删除减少的机构与角色关联		
            orgPowerDao.deleteByRoleIdAndOrgIds(roleID, deleteIDs);
        }
        if(!ArrayUtils.isEmpty(addIDs) && !StringUtils.isEmpty(addIDs[0])){
        	List<OrgPowerVO> addList = new ArrayList<>();
            for (String addID : addIDs) {
                OrgPowerVO orgPowerVO = new OrgPowerVO();
                orgPowerVO.setPk_role(roleID);
                orgPowerVO.setResource_id(addID);
                addList.add(orgPowerVO);
            }
            //新增增加的机构与角色关联
            orgPowerDao.insertBatch(addList);
        }
        return list;
    }

	@Override
	public void deleteRoleOrgPower(String[] ids) {
		orgPowerDao.deleteRoleOrgPower(ids);		
	}
}
