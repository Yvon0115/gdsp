package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.NodeInfoVO;

@MBDao
public interface INodeInfoDao {

    public boolean saveNodeInfo(NodeInfoVO nodeInfoVO);

    public boolean deleteNodeInfo(String[] ids);

    public boolean updateNodeInfo(NodeInfoVO nodeInfoVO);

    public NodeInfoVO findNodeInfoById(String id);

    public Page<NodeInfoVO> queryNodeInfoByCondition(Pageable pageable, @Param("condition") Condition condition, @Param("sort") Sorter sort);

    public List<NodeInfoVO> queryNodeInfoListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    public List<NodeInfoVO> findNodeInfoByDeploymentId(String delopymentID);

    public boolean deleteNodeInfoByDeploymentId(String delopymentID);
}
