package com.gdsp.platform.grant.auth.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * 用户关联角色服务
 * @deprecated  仅供grant模块调用（且仅在用户表与角色表都在门户的时候可调用）<br>
 * 提供查询结果的分页实现
 * @author wwb
 * @version 1.0 2015/6/23
 */
public interface IUserRoleService {

   /*
    * 没有被调用
    */
   // public Page<UserVO> queryUserRoleByRoleId(String roleID, Condition condition, Pageable page);

    /**
     * 根据角色id查询角色的用户列表
     * @param roleID 角色id
     * @param sort 排序规则
     * @return 分页结果
     */
//    public List<UserVO> queryUserListByRoleId(String roleID, Sorter sort);

    /**
     * 根据用户查询拥有的权限
     * @param id
     * @param condition
     * @param pageable
     * @return
     */
    public Page<RoleVO> queryRolePorwer(String id, Condition condition,Pageable pageable);
    
}
