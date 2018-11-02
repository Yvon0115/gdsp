package com.gdsp.platform.grant.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.platform.grant.user.model.UserDataIOVO;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * 用户服务
 * @author wwb
 * @version 1.0 2015/6/18
 */
public interface IUserService {
	/**
	 * 导入用户数据
	 * @param vos 用户VOss
	 * @author lt
	 * @date 2017年11月7日 上午11:09:41
	 */
    public int importUsers(List<UserDataIOVO> vos);
    /**
     * @Title: synchroCheck
     * (账号唯一性校验)
     * @param account
     * @return boolean    返回值说明
     *      */
     public boolean synchroCheck(String account);
     /**
      * 唯一性校验
      * @param account 账户名
      * @param id 主键
      * @return 是否唯一
      */
     public boolean isUniqueUser(String account, String id);

    /**
     * 用户预览查询，查询用户及所属用户组名、角色名、机构名
     * @return Page<UserVO>
     */
    public Page<UserVO> queryUserPreByIds(String[] userIds, String[] userGroupIds, String[] roleIds, String[] orgIds, Pageable pageable);

  
    /*2016.12.26
     * 接口为用户管理以外应用使用，故添加到IUserQueryPubService
     * public Page<UserVO> queryUserByOrgId(String pk_org, Condition condition, Pageable page, Sorter sort, boolean containLower);
     
     * public Page<UserVO> queryUserByCondition(Condition condition, Pageable page, Sorter sort);

     * public List<UserVO> queryUserListByCondition(Condition condition, Sorter sort);
     
     * public List<UserVO> queryUserListByUserAndCond(String userId, boolean containSelf, String addCond);
     
     * public Page<UserVO> queryUserPageByUser(String userId, Pageable pageable, boolean containSelf);
     
     * public Page<UserVO> queryUserPageByUserAndCond(String userId, Pageable pageable, boolean containSelf, String addCond);
     
     * public Map queryUserByUserIds(String[] userIds);
     
     */
    
    /*2016.12.26
     * 添加到IUserOptPubService
     *    public boolean saveUser(UserVO vo);
     */
    
    
    /*2016.12.26
     * 没有被调用
     * public Page<UserVO> queryUserPageByAddCond(Condition condition, String addCond, Pageable page);
     * public Page<UserVO> queryUserByIds(String[] userIds, Pageable page);
     * public List<UserVO> queryUserListByUser(String userId, boolean containSelf);
     */
    
    /**
     * 根据账号集合查询用户集合
     * 此查询对用户私人及敏感信息作相应限制，仅用来验证用户的某些状态
     * @author yucl
     * @param accountList
     * @return
     */
    public List<UserVO> queryUserByAccountList(List<String> accountList);
}
