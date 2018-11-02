package com.gdsp.platform.workflow.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.NodeInfoVO;

public interface INodeInfoService {

    /**
     * 保存流程节点信息
     * @param nodeInfoVO 流程节点
     * @return 是否生效
     */
    public boolean saveNodeInfo(NodeInfoVO nodeInfoVO);

    /**
     * 删除流程节点信息
     * @param ids 主键
     * @return 是否生效
     */
    public boolean deleteNodeInfo(String... ids);

    /**
     * 根据主键查询流程节点信息
     * @param id 
     * @return 流程节点信息
     */
    public NodeInfoVO findNodeInfoById(String id);

    /**
     * 通用分页查询（流程节点信息）
     * @param condition 查询条件
     * @param pageable	分页请求
     * @param sort		排序规则
     */
    public Page<NodeInfoVO> queryNodeInfoByCondition(Condition condition, Pageable pageable, Sorter sort);

    /**
     * 通用集合查询（流程节点信息）
     * @param condition 查询条件
     * @param sort		排序规则
     * @return 列表(List)
     */
    public List<NodeInfoVO> queryNodeInfoListByCondition(Condition condition, Sorter sort);

    /**
     * 根据布署ID查询流程节点信息
     * @param id 
     * @return 流程节点信息
     */
    public List<NodeInfoVO> findNodeInfoByDeploymentId(String delopymentID);

    /**
     * 删除流程节点信息
     * @param delopymentID 布署ID
     * @return 是否生效
     */
    public boolean deleteNodeInfoByDeploymentId(String delopymentID);
}
