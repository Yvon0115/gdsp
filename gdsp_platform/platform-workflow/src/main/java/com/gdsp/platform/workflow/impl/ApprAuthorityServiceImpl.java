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
import com.gdsp.platform.workflow.dao.IApprAuthorityDao;
import com.gdsp.platform.workflow.model.ApprAuthorityVO;
import com.gdsp.platform.workflow.service.IApprAuthorityService;

@Service
@Transactional(readOnly = true)
public class ApprAuthorityServiceImpl implements IApprAuthorityService {

    @Autowired
    private IApprAuthorityDao apprAuthorityDao;

    @Transactional
    @Override
    public boolean saveApprAuthority(ApprAuthorityVO apprAuthorityVO) {
        
        if (StringUtils.isNotEmpty(apprAuthorityVO.getId())) {
            return apprAuthorityDao.updateApprAuthority(apprAuthorityVO);
        } else {
            return apprAuthorityDao.saveApprAuthority(apprAuthorityVO);
        }
    }

    @Transactional
    @Override
    public boolean deleteApprAuthority(String... ids) {
        
        return apprAuthorityDao.deleteApprAuthority(ids);
    }

    @Override
    public ApprAuthorityVO findApprAuthorityById(String id) {
        
        return apprAuthorityDao.findApprAuthorityById(id);
    }

    @Override
    public Page<ApprAuthorityVO> queryApprAuthorityByCondition(
            Condition condition, Pageable pageable, Sorter sort) {
        
        return apprAuthorityDao.queryAppraAuthorityByCondition(condition, sort, pageable);
    }

    @Override
    public List<ApprAuthorityVO> queryApprAuthorityListByCondition(
            Condition condition, Sorter sort) {
        
        return apprAuthorityDao.queryApprAuthorityListByCondition(condition, sort);
    }

    @Override
    @Transactional
    public boolean deleteApprAuthorityByNodeInfoIDs(String[] ids) {
        
        return apprAuthorityDao.deleteApprAuthorityByNodeInfoIDs(ids);
    }
}
