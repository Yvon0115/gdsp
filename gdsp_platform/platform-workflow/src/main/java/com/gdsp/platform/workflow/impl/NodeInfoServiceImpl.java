package com.gdsp.platform.workflow.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.dao.INodeInfoDao;
import com.gdsp.platform.workflow.model.NodeInfoVO;
import com.gdsp.platform.workflow.service.INodeInfoService;

@Service
@Transactional(readOnly = true)
public class NodeInfoServiceImpl implements INodeInfoService {

    @Autowired
    private INodeInfoDao nodeInfoDao;

    @Transactional
    @Override
    public boolean saveNodeInfo(NodeInfoVO nodeInfoVO) {
        
        if (StringUtils.isNotEmpty(nodeInfoVO.getId())) {
            return nodeInfoDao.updateNodeInfo(nodeInfoVO);
        } else {
            return nodeInfoDao.saveNodeInfo(nodeInfoVO);
        }
    }

    @Transactional
    @Override
    public boolean deleteNodeInfo(String... ids) {
        
        return nodeInfoDao.deleteNodeInfo(ids);
    }

    @Override
    public NodeInfoVO findNodeInfoById(String id) {
        
        return nodeInfoDao.findNodeInfoById(id);
    }

    @Override
    public Page<NodeInfoVO> queryNodeInfoByCondition(Condition condition,
            Pageable pageable, Sorter sort) {
        
        return nodeInfoDao.queryNodeInfoByCondition(pageable, condition, sort);
    }

    @Override
    public List<NodeInfoVO> queryNodeInfoListByCondition(Condition condition,
            Sorter sort) {
        
        return nodeInfoDao.queryNodeInfoListByCondition(condition, sort);
    }

    @Override
    public List<NodeInfoVO> findNodeInfoByDeploymentId(String delopymentID) {
        return nodeInfoDao.findNodeInfoByDeploymentId(delopymentID);
    }

    @Override
    @Transactional
    public boolean deleteNodeInfoByDeploymentId(String delopymentID) {
        return nodeInfoDao.deleteNodeInfoByDeploymentId(delopymentID);
    }
}
