/**
 * 
 */
package com.gdsp.integration.framework.reportentry.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.integration.framework.reportentry.model.HistoryChangeVO;


/**
 * @author wangliyuan
 *
 */
@MBDao
public interface HistoryChangeDao extends ICrudDao<HistoryChangeVO> {
    
    /**
     * 
     * @param condition
     * @param page
     * @param sort
     * @return
     */
    List<HistoryChangeVO> queryByCondition(@Param("condition")Condition condition,@Param("sort")Sorter sort);
    /**
     * 
     * @param changeHistoryVO
     */
    void save(HistoryChangeVO changeHistoryVO);
    /**
     * 
     * @param changeHistoryVO
     * @return 
     */
    int update(HistoryChangeVO changeHistoryVO);
    /**
     * 
     * @param id
     */
    void delete(String id);
    /**
     * 
     * @param id
     * @return
     */
    HistoryChangeVO load(String id);
    /**
     * 
     * @param link_id
     * @param page
     * @return
     */
    List<HistoryChangeVO> findHistoryChangeVOBylinkId(@Param("link_id") String link_id);
    /**
     * 
     * @param condition
     * @param sort
     * @return
     */
    List<HistoryChangeVO> queryByConditionReturnList(@Param("condition")Condition condition,@Param("sort")Sorter sort);
    /**
     * 
     * @param link_id
     * @param opType
     * @return
     */
    HistoryChangeVO findVOBylinkId(@Param("link_id") String link_id,@Param("opType")String opType);
    /**
     * 
     * @param condition
     * @return
     */
    int findHistoryChangeVOByCon(@Param("condition") Condition condition);
}
