package com.gdsp.platform.grant.auth.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.auth.dao.IUserRoleDao;
import com.gdsp.platform.grant.auth.service.IUserRoleService;
import com.gdsp.platform.grant.role.dao.IRoleDao;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserService;
import com.gdsp.platform.grant.utils.GrantUtils;

@SuppressWarnings("deprecation")
@Service
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements IUserRoleService {

    @Autowired
    private IUserRoleDao         userRoleDao;
    @Autowired
    private IUserService         userService;
    @Autowired
    private IRoleDao             roleDao;

    /*@Override
    public List<UserVO> queryUserListByRoleId(String roleID, Sorter sort) {
        return userRoleDao.queryUserListByRoleId(roleID, sort);
    }*/

    @Override
    public Page<RoleVO> queryRolePorwer(String id, Condition condition,
            Pageable pageable) {
        String addCond = GrantUtils.getOrgPowerCondByUser(id, "pk_org");
        return roleDao.queryRolePorwer(addCond, condition, pageable);
    }
    /*
    @Override
    public Page<UserVO> queryUserRoleByRoleId(String roleID, Condition condition, Pageable page) {
        if (StringUtils.isEmpty(roleID))
            return null;
        Condition conditions = new Condition();
        conditions.addExpression("ur.pk_role", roleID);
        List<String> userIds = userRoleDao.queryUserIdsByRoleId(roleID);
        String[] strings = userIds.toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
        condition.addExpression("u.id", strings, "in");
        Sorter sort = new Sorter(Direction.ASC, "u.account");
        Page<UserVO> queryUserByIds = userService.queryUserByCondition(condition, page, sort);
        return queryUserByIds;
    }

    	@Override
    	public Page<UserVO> queryUserByRoleId(String roleID, Pageable page, Sort sort) {
   		return userRoleDao.queryUserByRoleId(roleID, page, sort);
   	}
   	*/
}
