package com.gdsp.platform.grant.role.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.role.model.RoleVO;

/**
 * 角色服务
 * @author wwb
 * @version 1.0 2015/6/18
 */
public interface IRoleService {

    /**
     * 保存角色
     * @param vo 角色
     * @return 是否成功
     */
    public boolean saveRole(RoleVO vo);

    /**
     * 根据机构id查询机构及下级的角色列表
     * @param pk_org 机构id
     * @param condition 查询条件
     * @param containLower 是否含下级机构角色，默认为“包含”
     * @param page 分页请求
     * @return 分页用户列表
     */
    public Page<RoleVO> queryRoleById(String pk_org, Condition condition, Pageable page, boolean containLower);

    /**
     * 通用查询方法
     * @param condition 查询条件
     * @param page 分页请求
     * @param sort 排序规则
     * @return 分页结果
     */
    public Page<RoleVO> queryRoleByCondition(Condition condition, Pageable page, Sorter sort);

    /**
     * 通用查询方法
     * @param condition 查询条件
     * @param sort 排序规则
     * @return List
     */
    public List<RoleVO> queryRoleListByCondition(Condition condition, Sorter sort);

    /**
     * @Title: uniqueCheck
     * (唯一性校验)
     * @param account
     * @return boolean true 唯一  false 不唯一
     *      */
    public boolean uniqueCheck(RoleVO vo);
}
