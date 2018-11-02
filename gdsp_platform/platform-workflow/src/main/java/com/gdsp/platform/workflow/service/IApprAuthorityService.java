package com.gdsp.platform.workflow.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.ApprAuthorityVO;

/**
 * 审批权限服务
 * @author gdsp
 *
 */
public interface IApprAuthorityService {

    /**
     * 保存审批权限
     * @param apprAuthorityVO 审批权限
     * @return 是否生效
     */
    public boolean saveApprAuthority(ApprAuthorityVO apprAuthorityVO);

    /**
     * 删除审批权限
     * @param ids 主键
     * @return 是否生效
     */
    public boolean deleteApprAuthority(String... ids);

    /**
     * 根据主键查询审批权限
     * @param id 
     * @return 审批权限对象
     */
    public ApprAuthorityVO findApprAuthorityById(String id);

    /**
     * 通用分页查询（审批权限）
     * @param condition 查询条件
     * @param pageable	分页请求
     * @param sort		排序规则
     * @return	分页结果（page）
     */
    public Page<ApprAuthorityVO> queryApprAuthorityByCondition(Condition condition, Pageable pageable, Sorter sort);

    /**
     * 通用集合查询（审批权限）
     * @param condition 查询条件
     * @param sort		排序规则
     * @return 列表(List)
     */
    public List<ApprAuthorityVO> queryApprAuthorityListByCondition(Condition condition, Sorter sort);

    /**
     * 删除审批权限
     * @param ids 流程节点信息表ID
     * @return 是否生效
     */
    boolean deleteApprAuthorityByNodeInfoIDs(String[] ids);
}
