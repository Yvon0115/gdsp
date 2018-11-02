package com.gdsp.platform.workflow.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.DelegateDetailVO;
import com.gdsp.platform.workflow.model.DelegateVO;

/**
 * 流程委托服务
 * @author gdsp
 *
 */
public interface IDelegateService {

    /**
     * 保存流程委托
     */
    public boolean saveDelegate(DelegateVO delegateVO, DelegateDetailVO delegateDetailVO, String deploymentid);

    /**
     * 删除流程委托
     * @param ids
     * @return 是否生效
     */
    public boolean deleteDelegate(String... ids);

    /**
     * 根据主键查询流程委托
     * @param id
     * @return 流程委托对象
     */
    public DelegateVO findDelegateById(String id);

    /**
     * 通用分页查询（流程委托）
     * @param condition 查询条件
     * @param pageable	分页请求
     * @param sort		排序规则
     * @return	分页结果（page）
     */
    public Page<DelegateVO> queryDelegateByCondition(Condition condition, Sorter sort, Pageable pageable);

    /**
     * 通用集合查询（流程委托）
     * @param condition 查询条件
     * @param sort		排序规则
     * @return	列表(List)
     */
    public List<DelegateVO> queryDelegateListByCondition(Condition condition, Sorter sort);

    /**
     * 查询相同代理人的一条信息
     * @param sort		排序规则
     * @param pageable	分页请求
     * @return	分页结果（page）
     */
    public Page<DelegateVO> queryDelegateUnderDistinct(Sorter sort, Pageable pageable);

    /**
     * 根据主键查询流程委托
     * @param id
     * @return 流程委托对象
     */
    public List<DelegateVO> findDelegateByAcceptIdAltCreateTime(String accpetId, String createTime);

    /**
     * 根据委托人，部署id以及在有效时间之内的代理人id
     * @param id
     * @return 流程委托对象
     */
    public List<DelegateVO> findAcceptId(String userId, String deploymentId);

    /**
     * 根据委托主键查找该委托下的部署id
     * @param delegateId
     * @return List<String>
     */
    public List<String> queryDeploymentIdByDelegateId(String delegateId);
}
