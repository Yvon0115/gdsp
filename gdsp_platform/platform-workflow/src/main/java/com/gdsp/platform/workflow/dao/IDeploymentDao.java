package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.DeploymentAltCategoryVO;
import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.model.TaskVO;

/**
 * 
 * @author sun
 *
 */
@MBDao
public interface IDeploymentDao {

    public boolean saveDeployment(DeploymentVO deploymentVO);

    public boolean updateDeployment(DeploymentVO deploymentVO);

    public boolean deleteDeployment(String[] ids);

    public DeploymentVO findDeploymentById(String id);

    public Page<DeploymentVO> queryDeploymentByCondition(@Param("condition") Condition condition, Pageable pageable, @Param("sort") Sorter sort);

    public List<DeploymentVO> queryDeploymentListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    public Page<TaskVO> queryTaskByCondition(@Param("condition") Condition condition, @Param("list")List<String> userIds, Pageable pageable);

    public DeploymentVO findByDeploymentCode(String deplpymentcode);

    public boolean deleteDeploymentByDeploymentId(String delopymentID);

    public DeploymentVO findByProcDefId(@Param("procDefId") String processDefinitionId);

    public boolean setUpDeployment(String[] ids);

    public boolean stopDeployment(String[] ids);

    // 流程监控(管理员权限)
    public Page<TaskVO> queryProcForAdmin(@Param("condition") Condition condition, Pageable pageable, @Param("sort") Sorter sort);

    // 我的流程(包括我发起的和我参与的)
    public Page<ProcessHistoryVO> queryAllMyProcByCondition(@Param("userId") String userId, @Param("condition") Condition condition, Pageable pageable, @Param("sort") Sorter sort);

    // 关联流程类型表进行查询
    public Page<DeploymentAltCategoryVO> queryDeploymentAltCategory(@Param("condition") Condition condition, Pageable pageable, @Param("sort") Sorter sort);

    // 统计指定流程编码
    public int countByCode(@Param("codeValue") String codeValue);

    /**
     * 根据userid查询被驳回的任务vo
     * @param userId
     * @return
     */
    public List<TaskVO> findRejectedTasks(@Param("userId")String userId);
    
    /**
     * 查询个人不可撤回流程实例id集合
     * @param user
     * @return
     */
    public List<String> findIrrevocableProcInstIds(@Param("userId")String user);
}
