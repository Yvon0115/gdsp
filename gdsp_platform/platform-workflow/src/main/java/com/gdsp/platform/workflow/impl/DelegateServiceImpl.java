package com.gdsp.platform.workflow.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.workflow.dao.IDelegateDao;
import com.gdsp.platform.workflow.dao.IDelegateDetailDao;
import com.gdsp.platform.workflow.model.DelegateDetailVO;
import com.gdsp.platform.workflow.model.DelegateVO;
import com.gdsp.platform.workflow.service.IDelegateService;

@Service
@Transactional(readOnly = true)
public class DelegateServiceImpl implements IDelegateService {

    @Autowired
    private IDelegateDao       delegateDao;
    @Autowired
    private IDelegateDetailDao delegateDetailDao;
    @Autowired
    private IUserQueryPubService   userQueryPubService;

    @Override
    @Transactional
    public boolean saveDelegate(DelegateVO delegateVO, DelegateDetailVO delegateDetailVO, String deploymentid) {
        // 更新的时候，主表是执行的更新操作，明细表是先删除，然后再插入。
    	String[] deploymentidArr = new String[]{};
    	if(deploymentid != null)
    		deploymentidArr = deploymentid.split(",");
        //***更新***
        if (StringUtils.isNotEmpty(delegateVO.getId())) {
            delegateDao.updateDelegate(delegateVO);
            delegateDetailDao.deleteDelegateDetail(delegateVO.getId());
            delegateDetailVO.setKid(delegateVO.getId());
            for (int i = 0; i < deploymentidArr.length; i++) {
                delegateDetailVO.setId(null);
                delegateDetailVO.setDeploymentId(deploymentidArr[i]);
                delegateDetailDao.insertDelegateDetail(delegateDetailVO);
            }
        } else {
            //***插入***
            delegateDao.insertDelegate(delegateVO);
            delegateDetailVO.setKid(delegateVO.getId());
            for (int i = 0; i < deploymentidArr.length; i++) {
                delegateDetailVO.setDeploymentId(deploymentidArr[i]);
                delegateDetailDao.insertDelegateDetail(delegateDetailVO);
                delegateDetailVO.setId(null);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteDelegate(String... ids) {
        delegateDao.deleteDelegate(ids);
        for (int i = 0; i < ids.length; i++) {
            delegateDetailDao.deleteDelegateDetail(ids[i]);
        }
        return true;
    }

    @Override
    @Transactional
    public DelegateVO findDelegateById(String id) {
        return delegateDao.findDelegateById(id);
    }

    @SuppressWarnings("null")
	@Override
    public Page<DelegateVO> queryDelegateByCondition(Condition condition,
            Sorter sort, Pageable pageable) {
    	/* 权限拆分，通用查询不可直接在SQL中查询用户表修改为掉用公共接口查询用户
         * 修改人：于成龙
         * 修改时间：2017年11月10日 
    	String currentUserID = UserUtils.getCurrentUserID();
    	UserVO userVO = userQueryPubService.load(currentUserID);
    	String currentUserID = UserUtils.getCurrentUserID();
    	UserVO userVO = userQueryPubService.load(currentUserID);
    	Page<DelegateVO> delegateByCondition = delegateDao.queryDelegateByCondition(condition, sort, pageable);
    	if(null != delegateByCondition && null != userVO){
    		for(DelegateVO VO : delegateByCondition.getContent()){
    			VO.setUserName(userVO.getUsername());
    		}
    	}*/
    	/* 查询所有委托流程中被委托人ID，根据被委托人ID查询用户信息
    	 * 修改人：雷婷
    	 * 修改时间：2017年12月21日 
    	 */
    	List<String> userIds = new ArrayList<String>();
    	Page<DelegateVO> delegateByCondition = delegateDao.queryDelegateByCondition(condition, sort, pageable);
    	if(delegateByCondition.getContent().size()>0){
    		for(DelegateVO VO : delegateByCondition.getContent()){
    			userIds.add(VO.getAcceptId());
    		}
    	}
    	//调用用户公共接口查询对应用户集合
    	List<UserVO> users = userQueryPubService.querUsersByUserIds(userIds);
    	if(null != users && users.size()>0){
    		for(DelegateVO VO : delegateByCondition.getContent()){
    			for(UserVO user : users){
    				if(VO.getAcceptId().equals(user.getId())){
    					VO.setUserName(user.getUsername());
    				}
    			}
    		}
    	}
        return delegateByCondition;
    }
    

    @Override
    public List<DelegateVO> queryDelegateListByCondition(Condition condition,
            Sorter sort) {
        return delegateDao.queryDelegateListByCondition(condition, sort);
    }

    @Override
    public Page<DelegateVO> queryDelegateUnderDistinct(Sorter sort,
            Pageable pageable) {
        return delegateDao.queryDelegateUnderDistinct(sort, pageable);
    }

    @Override
    public List<DelegateVO> findDelegateByAcceptIdAltCreateTime(String accpetId, String createTime) {
        return delegateDao.findDelegateByAcceptIdAltCreateTime(accpetId, createTime);
    }

    @Override
    public List<DelegateVO> findAcceptId(String userId, String deploymentId) {
        return delegateDao.findAcceptId(userId, deploymentId);
    }

    @Override
    public List<String> queryDeploymentIdByDelegateId(String delegateId) {
        return delegateDao.queryDeploymentIdByDelegateId(delegateId);
    }
}
