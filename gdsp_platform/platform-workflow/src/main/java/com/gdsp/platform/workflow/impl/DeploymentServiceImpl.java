package com.gdsp.platform.workflow.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.workflow.dao.IDeploymentDao;
import com.gdsp.platform.workflow.model.DelegateVO;
import com.gdsp.platform.workflow.model.DeploymentAltCategoryVO;
import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.model.TaskVO;
import com.gdsp.platform.workflow.service.IDelegateService;
import com.gdsp.platform.workflow.service.IDeploymentService;

@Service
@Transactional(readOnly = true)
public class DeploymentServiceImpl implements IDeploymentService {

    @Autowired
    private IDeploymentDao deploymentDao;
    @Autowired
    private IUserQueryPubService   userQueryPubService;
    @Autowired
    private IDelegateService delegateService;

    @Transactional
    @Override
    public boolean saveDeployment(DeploymentVO deploymentVO) {
        return deploymentDao.saveDeployment(deploymentVO);
    }
    
    @Transactional
    @Override
    public boolean updateDeployment(DeploymentVO deploymentVO){
    	return deploymentDao.updateDeployment(deploymentVO);
    }

    @Transactional
    @Override
    public boolean deleteDeployment(String... ids) {
        return deploymentDao.deleteDeployment(ids);
    }

    @Override
    public DeploymentVO findDeploymentById(String id) {
        return deploymentDao.findDeploymentById(id);
    }

    @Override
    public Page<DeploymentVO> queryDeploymentByCondition(Condition condition,
            Pageable page, Sorter sort) {
        return deploymentDao.queryDeploymentByCondition(condition, page, sort);
    }

    @Override
    public List<DeploymentVO> queryDeploymentListByCondition(Condition condition, Sorter sort) {
        return deploymentDao.queryDeploymentListByCondition(condition, sort);
    }

    /* 权限拆分，通用查询不可直接在SQL中查询用户表修改为掉用公共接口查询用户
     * 修改人：于成龙
     * 修改时间：2017年11月10日 */
    @Override
    public Page<TaskVO> queryTaskByCondition(Condition condition, String userId, Pageable page) {
    	List<String> userIds = new ArrayList<>();
    	/**start*/
    	List<String> delegateUserIds = new ArrayList<>();
    	delegateUserIds.add(userId);
    	Condition cond = new Condition();
    	cond.addExpression("acceptid", userId);
    	List<DelegateVO> delegatePeples = delegateService.queryDelegateListByCondition(cond,null);
    	if(delegatePeples.size()>0){
    		for(DelegateVO VO : delegatePeples){
    			DDateTime delegateStartTime = VO.getStartTime();
    			DDateTime delegateEndTime = VO.getEndTime();
    			DDateTime nowTime = new DDateTime();
    			if(nowTime.getMillis() > delegateStartTime.getMillis()
    					&& nowTime.getMillis() < delegateEndTime.getMillis())
    				delegateUserIds.add(VO.getUserId());
    		}
    	}
    	/** end*/
    	Page<TaskVO> taskByCondition = deploymentDao.queryTaskByCondition(condition, delegateUserIds, page);
    	//遍历获取用户id集合
    	if(taskByCondition.getContent().size()>0){
    		for(TaskVO VO : taskByCondition.getContent()){
    			userIds.add(VO.getUserId());
    		}
    	}
    	//调用用户公共接口查询对应用户集合
    	List<UserVO> users = userQueryPubService.querUsersByUserIds(userIds);
    	if(null != users && users.size()>0){
    		for(TaskVO VO : taskByCondition.getContent()){
    			for(UserVO user : users){
    				if(VO.getUserId().equals(user.getId())){
    					VO.setUserId(user.getUsername());
    				}
    			}
    		}
    	}
        return taskByCondition;
    }

    @Override
    public DeploymentVO findByDeploymentCode(String deplpymentcode) {
        return deploymentDao.findByDeploymentCode(deplpymentcode);
    }

    @Transactional
    @Override
    public boolean deleteDeploymentByDeploymentId(String delopymentID) {
        return deploymentDao.deleteDeploymentByDeploymentId(delopymentID);
    }

    @Transactional
    @Override
    public boolean setUpDeployment(String... ids) {
        return deploymentDao.setUpDeployment(ids);
    }

    @Transactional
    @Override
    public boolean stopDeployment(String... ids) {
        return deploymentDao.stopDeployment(ids);
    }

    @Override
    public DeploymentVO findByProcDefId(String processDefinitionId) {
        return deploymentDao.findByProcDefId(processDefinitionId);
    }

    @Override
    public Page<TaskVO> queryProcForAdmin(Condition condition,
            Pageable pageable, Sorter sort) {
        
        return deploymentDao.queryProcForAdmin(condition, pageable, sort);
    }

    @Override
    public Page<ProcessHistoryVO> queryTaskAllByCondition(String userId,
            Condition condition, Pageable pageable, Sorter sort) {
        return deploymentDao.queryAllMyProcByCondition(userId, condition, pageable, sort);
    }

    @Override
    public Page<DeploymentAltCategoryVO> queryDeploymentAltCategory(Condition condition, Pageable pageable, Sorter sort) {
        return deploymentDao.queryDeploymentAltCategory(condition, pageable, sort);
    }

    @Override
    public int countByCode(String codeValue) {
        return deploymentDao.countByCode(codeValue);
    }
    
    @Override
    public List<TaskVO> queryRejectedTasks(String user) {
        return deploymentDao.findRejectedTasks(user);
    }

    @Override
    public List<String> queryIrrevocableProcInstIds(String user) {
        return deploymentDao.findIrrevocableProcInstIds(user);
    }
}
