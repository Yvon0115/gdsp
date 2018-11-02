package com.gdsp.platform.grant.usergroup.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.usergroup.model.UserGroupVO;

/**
 * 用户组服务
 * @author wwb
 * @version 1.0 2015/6/18
 */
public interface IUserGroupService {

    /**
     * 保存用户组
     * @param vo 用户组
     * @return 是否成功
     */
    public boolean saveUserGroup(UserGroupVO vo);

    /**
     * 删除用户组
     * @param ids 用户组ids
     */
    public boolean deleteUserGroup(String... ids);

    /**
     * 根据主键查询用户组
     * @return 用户组对象
     */
    public UserGroupVO load(String id);

    /**
     * 通用查询方法
     * @param condition 查询条件
     * @param page 分页请求
     * @param sort 排序规则
     * @return 分页结果
     */
    public Page<UserGroupVO> queryUserGroupByCondition(Condition condition, Pageable page, Sorter sort);

    /**
     * 通用查询方法
     * @param condition 查询条件
     * @param sort 排序规则
     * @return 分页结果
     */
    public List<UserGroupVO> queryUserGroupListByCondition(Condition condition, Sorter sort);

    /**
     * @Title: uniqueCheck
     * (唯一性校验)
     * @param account
     * @return boolean true 唯一  false 不唯一
     *      */
    public boolean uniqueCheck(UserGroupVO vo);
}
