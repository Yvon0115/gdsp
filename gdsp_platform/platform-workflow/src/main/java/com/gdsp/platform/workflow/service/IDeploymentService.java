package com.gdsp.platform.workflow.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.DeploymentAltCategoryVO;
import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.model.TaskVO;

/**
 * 流程部署服务
 * @author sun
 */
public interface IDeploymentService {

    /**
     * 保存流程部署
     * @param deploymentVO
     * @return
     */
    public boolean saveDeployment(DeploymentVO deploymentVO);
    
    /**
     * 修改流程部署
     * @param deploymentVO
     * @return
     */
    public boolean updateDeployment(DeploymentVO deploymentVO);

    /**
     * 删除流程部署
     * @param ids 流程部署ids
     */
    public boolean deleteDeployment(String... ids);

    /**
     * 启用流程部署
     * @param ids 流程部署ids
     */
    public boolean setUpDeployment(String... ids);

    /**
     * 停用流程部署
     * @param ids 流程部署ids
     */
    public boolean stopDeployment(String... ids);

    /**
     * 根据主键查询流程部署
     * @return 流程部署对象
     */
    public DeploymentVO findDeploymentById(String id);

    /**
     * 根据部署编码查询流程部署
     * @return 流程部署对象
     */
    public DeploymentVO findByDeploymentCode(String deplpymentcode);

    /**
     * 通用分页查询方法
     * @param condition 查询条件
     * @param page 分页请求
     * @param sort 排序规则
     * @return 分页结果
     */
    public Page<DeploymentVO> queryDeploymentByCondition(Condition condition, Pageable pageable, Sorter sort);

    /**
     * 通用集合结果查询方法
     * @param condition 查询条件
     * @param sort 排序规则
     * @return List
     */
    public List<DeploymentVO> queryDeploymentListByCondition(Condition condition, Sorter sort);

    /**
     * 查询个人待办列表
     * @param condition 查询条件
     * @param page 分页请求
     * @return 分页结果
     */
    public Page<TaskVO> queryTaskByCondition(Condition condition, String userId, Pageable pageable);

    /**
     * 删除流程部署
     * @param delopymentID 流程部署id
     */
    public boolean deleteDeploymentByDeploymentId(String delopymentID);

    /**
     * 通过流程定义ID查询
     * @return 部署对象
     */
    public DeploymentVO findByProcDefId(String processDefinitionId);

    /**
     * 查询流程监控(管理员权限)
     * @param userId
     * @param condition
     * @param pageable
     * @param sort
     * @return 所有受监控流程
     */
    public Page<TaskVO> queryProcForAdmin(Condition condition, Pageable pageable, Sorter sort);

    /**
     * 我的流程
     * @param userId	当前用户
     * @param condition
     * @param pageable
     */
    public Page<ProcessHistoryVO> queryTaskAllByCondition(String userId,
            Condition condition, Pageable pageable, Sorter sort);

    /**
     * 关联流程类型表进行查询
     * @param sort 排序规则
     * @return	列表(List)
     */
    public Page<DeploymentAltCategoryVO> queryDeploymentAltCategory(Condition condition, Pageable pageable, Sorter sort);

    /**
     * 统计指定流程编码
     * @param codeValue 流程编码
     * @return int codeValue在数据库中的个数
     */
    public int countByCode(String codeValue);

    /**
     * 查询被驳回待办
     * @param user
     * @return
     */
    public List<TaskVO> queryRejectedTasks(String user);

    /**
     * 查询个人不可撤回流程实例id集合
     * @param user
     * @return
     */
    public List<String> queryIrrevocableProcInstIds(String user);
}
