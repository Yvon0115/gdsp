package com.gdsp.platform.grant.auth.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.auth.dao.IOrgPowerDao;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.auth.service.IOrgPowerService;

@Service
@Transactional(readOnly = true)
public class OrgPowerServiceImpl implements IOrgPowerService {

    @Autowired
    private IOrgPowerDao orgPowerDao;

    // TODO 无调用者  待删除
    /*@Override
    public Page<OrgPowerVO> queryOrgRoleByRoleId(String roleID, Condition orgCon, Pageable pageable, Sorter sort) {
        orgCon.addExpression("ro.pk_role", roleID);
        return orgPowerDao.queryOrgRoleByRoleId(roleID, orgCon, pageable, sort);
    }*/

    @Override
    public List<OrgPowerVO> queryOrgPowerByCondition(Condition cond) {
        return orgPowerDao.queryOrgPowerByCondition(cond);
    }
}
