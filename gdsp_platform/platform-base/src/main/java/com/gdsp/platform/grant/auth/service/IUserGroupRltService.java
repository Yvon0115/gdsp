package com.gdsp.platform.grant.auth.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.auth.model.UserGroupRltVO;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * 用户关联用户组服务
 * @author wwb
 * @version 1.0 2015/6/18
 */
public interface IUserGroupRltService {

    /**
     * 用户组上增加用户关联用户组
     * @param groupID 用户组id
     * @param userIDs 用户IDs
     */
    public List<UserGroupRltVO> addRltOnUserGroup(String groupID, String[] userIDs);

    /**
     * 删除用户关联用户组
     * @param ids 用户关联用户组IDs
     */
    public boolean deleteUserGroupRlt(String[] ids);

    /**
     * 根据用户组id查询用户组的用户关系列表
     * @param groupID 用户组id
     * @param page 分页请求
     * @return 分页用户关联用户组列表
     */
    public Page<UserGroupRltVO> queryUserGroupRltByGroupId(String groupID, Condition condition, Pageable page);
/*权限拆分----------------------------------------2016.12.29
 * 未被调用
    public Page<UserVO> queryUserByGroupId(String groupID, Pageable page);
    
   
    */
    public List<UserVO> queryUserListByGroupId(String groupID, Sorter sort);
    /**
     * 根据用户组id查询可以分配给用户组的用户列表
     * @param condition 
     * @param groupID 角色id
     * @param page 分页请求
     * @return 分页用户列表
     */
    public Page<UserVO> queryUserForUserGroupPower(Condition condition, String groupID, String loginUserID, Pageable page);

    /**
     * 
     * @Title: queryGroupByUser 
     * @Description: 通过用户ID查询用户所属用户组
     * @param id	用户ID
     * @return List<UserVO>  用户VO
     */
    public List<UserVO> queryGroupByUser(String id);
}
