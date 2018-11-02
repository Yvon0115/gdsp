package com.gdsp.platform.grant.role.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.auth.dao.IOrgPowerDao;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.role.dao.IRoleDao;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.role.service.IRoleService;
import com.gdsp.platform.log.service.OpLog;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao            roleDao;
    @Autowired
    private IOrgPowerDao        orgPowerDao;
    @Autowired
    private IOrgQueryPubService orgPubService;

    @Transactional
    @OpLog
    @Override
    public boolean saveRole(RoleVO vo) {
        if (StringUtils.isNotEmpty(vo.getId())) {
            roleDao.update(vo);
        } else {
            roleDao.insert(vo);
            // 角色建立后，默认有所在机构及下级机构的权限
            String orgID = vo.getPk_org();
            /**lyf 2016.12.23修改 权限拆分start**/
            List<OrgVO> orgVOs = orgPubService.querySelftAndChildOrgListById(orgID);
            /**end**/
//            List<OrgVO> orgVOs = orgPubService.queryChildOrgListById(orgID);
            OrgPowerVO orgPowerVO;
            for (OrgVO orgVO : orgVOs) {
                orgPowerVO = new OrgPowerVO();
                orgPowerVO.setPk_role(vo.getId());
                orgPowerVO.setResource_id(orgVO.getId());
                orgPowerDao.insert(orgPowerVO);
            }
        }
        return true;
    }

    @Override
    public Page<RoleVO> queryRoleById(String pk_org, Condition condition, Pageable page, boolean containLower) {
        if (StringUtils.isEmpty(pk_org))
            return null;
        Sorter sort = new Sorter(Direction.ASC, "r.rolename");
        // 增加机构查询条件
        if (containLower) {
            /**lyf 2016.12.23修改 权限拆分start**/
            List<OrgVO> orgVOs = orgPubService.querySelftAndChildOrgListById(pk_org);
            /**end**/
            /*Condition orgCond = new Condition();
            Sorter orgsort = new Sorter(Direction.ASC, "orgcode");
            List<OrgVO> orgVOs = orgService.queryChildOrgListById(pk_org, orgCond, orgsort, true);*/
            String orgids = "";
            for (OrgVO orgVO : orgVOs) {
                orgids += "'" + orgVO.getId() + "',";
            }
            if (orgids.length() > 0) {
                orgids = orgids.substring(0, orgids.length() - 1);
            }
            condition.addExpression("r.pk_org", orgids, "in");
        } else {
            condition.addExpression("r.pk_org", pk_org);
        }
        if (StringUtils.isEmpty(pk_org)) {
            return null;
        }
        Page<RoleVO> roleVOs = roleDao.queryRoleByCondition(condition, page, sort);
        //set orgname
        if (roleVOs.getContent() != null && roleVOs.getContent().size() > 0) {
            List<String> orgids = new ArrayList<String>();
            for (RoleVO roleVO : roleVOs.getContent()) {
                orgids.add(roleVO.getPk_org());
            }
            List<OrgVO> orgVOs = orgPubService.queryOrgListByIDs(orgids);
            if (orgVOs != null && orgVOs.size() > 0) {
                for (RoleVO roleVO : roleVOs.getContent()) {
                    for (OrgVO orgVO : orgVOs) {
                        if (roleVO.getPk_org().equals(orgVO.getId())) {
                            roleVO.setOrgname(orgVO.getOrgname());
                            break;
                        }
                    }
                }
            }
        }
        return roleVOs;
    }

    @Override
    public Page<RoleVO> queryRoleByCondition(Condition condition, Pageable page, Sorter sort) {
        return roleDao.queryRoleByCondition(condition, page, sort);
    }

    @Override
    public List<RoleVO> queryRoleListByCondition(Condition condition, Sorter sort) {
        return roleDao.queryRoleListByCondition(condition, sort);
    }

    @Override
    public boolean uniqueCheck(RoleVO vo) {
        return roleDao.existSameRole(vo) == 0;
    }
}
