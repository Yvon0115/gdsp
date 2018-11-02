package com.gdsp.platform.grant.auth.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.web.security.login.AuthInfo;
import com.gdsp.dev.web.security.login.IUserGrantService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;

@Service
@Transactional(readOnly = true)
public class UserGrantServiceImpl implements IUserGrantService {

    @Autowired
    private IUserQueryPubService     userQueryPubService;
    @Autowired
    private IUserRoleQueryPubService userRolePubService;

    @Override
    public List<String> findRolesByUserId(String userId) {
        List<RoleVO> rolevoList = userRolePubService.queryRoleListByUserId(userId);
        if (rolevoList.isEmpty()) {
            return null;
        }
        List<String> retList = new ArrayList<String>();
        for (RoleVO roleVO : rolevoList) {
            retList.add(roleVO.getId());
        }
        return retList;
    }

    @Override
    public AuthInfo findAuthInfoByAccount(String account) throws BusinessException {
    	return userQueryPubService.queryUserAuthInfoByAccount(account);
//        return userPubService.queryUserByAccount(account);
    }

    // 去除无调用者的接口
    /*@Override
    public AuthInfo findAuthInfoByUserId(String userId) throws BusinessException {
        return userPubService.load(userId);
    }*/
}
