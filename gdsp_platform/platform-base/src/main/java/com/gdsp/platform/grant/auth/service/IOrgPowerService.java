package com.gdsp.platform.grant.auth.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;

/**
 * 机构权限服务
 * @author wwb
 * @version 1.0 2015/6/18
 */
public interface IOrgPowerService {

    /** TODO 无调用者  待删除
     * 通过角色ID查询机构角色关系
     * @param roleID
     * @param orgCon
     * @param pageable
     * @param sort
     * @return Page<OrgPowerVO> 返回带分页的机构角色关系VO集合
     */
//    public Page<OrgPowerVO> queryOrgRoleByRoleId(String roleID, Condition orgCon, Pageable pageable, Sorter sort);

    /**
     * 
     * @Title: queryOrgPowerByCondition 
     * @Description: 通用查询方法
     * @param cond
     * @return List<OrgPowerVO>    返回机构授权集合
     */
    public List<OrgPowerVO> queryOrgPowerByCondition(Condition cond);
}
