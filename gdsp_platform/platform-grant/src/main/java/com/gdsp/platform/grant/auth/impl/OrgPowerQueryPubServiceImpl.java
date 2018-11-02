package com.gdsp.platform.grant.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.grant.auth.dao.IOrgPowerDao;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;

@Service
@Transactional(readOnly = true)
public class OrgPowerQueryPubServiceImpl implements IOrgPowerQueryPubService {

    @Autowired
    private IOrgPowerDao         orgPowerDao;
    @Autowired
    private IOrgQueryPubService  orgQueryPubService;
    @Autowired
    private IUserQueryPubService userPubService;

    @Override
    public List<OrgVO> queryOrgListByUser(String userID) {
        if (StringUtils.isBlank(userID)) {
            return null;
        }
        UserVO userVO = userPubService.load(userID);
        // 根据用户类型查询
        if (userVO != null && userVO.getUsertype() == GrantConst.USERTYPE_ADMIN) {
            // 超级管理员，管理所有机构
            return orgQueryPubService.queryAllOrgList();
        }
        // 用户有管理权限的机构
        List<String> OrgIDs = orgPowerDao.queryOrgListByUser(userID);
        List<OrgVO> orgVOs = orgQueryPubService.queryOrgListByIDs(OrgIDs);
        return orgVOs;
    }

    @Override
    public List<OrgPowerVO> queryOrgPowerListByRole(String roleID) {
        return orgPowerDao.queryOrgPowerListByRole(roleID);
    }
}
