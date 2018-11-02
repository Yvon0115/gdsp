package com.gdsp.platform.workflow.dao;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.DelegateDetailVO;

@MBDao
public interface IDelegateDetailDao {

    public void insertDelegateDetail(DelegateDetailVO delegateDetailVO);

    public void deleteDelegateDetail(String kid);
    /*
    public DelegateVO findDelegateById(String id);
    
    public Page<DelegateVO> queryDelegateByCondition(
    		@Param("condition") Condition condition, @Param("sort") Sorter sort,
    		Pageable pageable);
    
    public List<DelegateVO> queryDelegateListByCondition(
    		@Param("condition") Condition condition, @Param("sort") Sorter sort);
    
    public Page<DelegateVO> queryDelegateUnderDistinct(
    		@Param("sort") Sorter sort, Pageable pageable);
    
    public List<DelegateVO> findDelegateByAcceptIdAltCreateTime(
    		String accpetId, String createTime);*/
}
