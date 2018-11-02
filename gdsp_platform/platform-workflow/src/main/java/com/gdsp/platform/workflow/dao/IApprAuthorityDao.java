package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.ApprAuthorityVO;

@MBDao
public interface IApprAuthorityDao {

    public boolean saveApprAuthority(ApprAuthorityVO apprAuthorityVO);

    public boolean deleteApprAuthority(String[] ids);

    public boolean updateApprAuthority(ApprAuthorityVO apprAuthorityVO);

    public ApprAuthorityVO findApprAuthorityById(String id);

    public Page<ApprAuthorityVO> queryAppraAuthorityByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort, Pageable pageable);

    public List<ApprAuthorityVO> queryApprAuthorityListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    public boolean deleteApprAuthorityByNodeInfoIDs(String[] ids);
}
