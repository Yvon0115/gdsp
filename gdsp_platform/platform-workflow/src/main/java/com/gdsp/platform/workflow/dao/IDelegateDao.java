package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.DelegateVO;

@MBDao
public interface IDelegateDao {

    public void insertDelegate(DelegateVO delegateVO);

    public void deleteDelegate(String[] ids);

    public void updateDelegate(DelegateVO delegateVO);

    public DelegateVO findDelegateById(String id);

    public Page<DelegateVO> queryDelegateByCondition(
            @Param("condition") Condition condition, @Param("sort") Sorter sort,
            Pageable pageable);

    public List<DelegateVO> queryDelegateListByCondition(
            @Param("condition") Condition condition, @Param("sort") Sorter sort);

    public Page<DelegateVO> queryDelegateUnderDistinct(
            @Param("sort") Sorter sort, Pageable pageable);

    public List<DelegateVO> findDelegateByAcceptIdAltCreateTime(
            String accpetId, String createTime);

    public List<DelegateVO> findAcceptId(@Param("userId") String userId, @Param("deploymentId") String deploymentId);

    public List<String> queryDeploymentIdByDelegateId(String delegateId);
}
