package com.gdsp.platform.grant.usergroup.impl;

import java.util.List;

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
import com.gdsp.platform.grant.usergroup.dao.IUserGroupDao;
import com.gdsp.platform.grant.usergroup.model.UserGroupVO;
import com.gdsp.platform.grant.usergroup.service.IUserGroupService;

@Service
@Transactional(readOnly = true)
public class UserGroupServiceImpl implements IUserGroupService {

    @Autowired
    private IUserGroupDao    userGroupDao;
    @Autowired
    private IUserGroupRltDao rltDao;

    @Transactional
    @Override
    public boolean saveUserGroup(UserGroupVO vo) {
        if (StringUtils.isNotEmpty(vo.getId())) {
            userGroupDao.update(vo);
        } else {
            userGroupDao.insert(vo);
        }
        return true;
    }

    @Transactional
    @Override
    public boolean deleteUserGroup(String... ids) {
        userGroupDao.delete(ids);
        // 同步删除已添加用户信息
        rltDao.deleteRltForGroup(ids);
        return true;
    }

    @Override
    public UserGroupVO load(String id) {
        return userGroupDao.load(id);
    }

    @Override
    public Page<UserGroupVO> queryUserGroupByCondition(Condition condition, Pageable page, Sorter sort) {
        return userGroupDao.queryUserGroupByCondition(condition, page, sort);
    }

    @Override
    public List<UserGroupVO> queryUserGroupListByCondition(Condition condition, Sorter sort) {
        return userGroupDao.queryUserGroupListByCondition(condition, sort);
    }

    @Override
    public boolean uniqueCheck(UserGroupVO vo) {
        return userGroupDao.existSameUserGroup(vo) == 0;
    }
}
