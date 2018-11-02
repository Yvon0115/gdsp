package com.gdsp.platform.grant.auth.service;

import java.util.List;

import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.org.model.OrgVO;

/**
 * 
 * @ClassName: IOrgPowerPubService 
 * @Description: 机构权限服务
 * @author jingf
 * @date 2016年8月2日 下午4:30:22 
 *
 */
public interface IOrgPowerQueryPubService {

    /****
    * @Title: queryOrgListByUser
    * @Description: 查询用户有管理权限的机构。管理员查询所有机构，非管理员查询用户关联机构信息和用户关联的角色所对应的机构
    * @param userID 用户id
    * @return    参数说明
    * @return List<OrgVO>    机构列表
    *      */
    public List<OrgVO> queryOrgListByUser(String userID);

    /**
     * 
     * @Title: queryOrgPowerListByRole 
     * @Description: 通过角色ID查询机构和角色关系
     * @param roleID
     * @return List<OrgPowerVO>    返回类型
     */
    public List<OrgPowerVO> queryOrgPowerListByRole(String roleID);
}
