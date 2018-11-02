/**
 * 
 */
package com.gdsp.integration.framework.reportentry.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.reportentry.model.HistoryChangeVO;


/**
 * 报表历史变更服务
 * @author wangliyuan
 *
 */
public interface IHistoryChangeService {
    
    /**
     * 通过条件查询历史变更信息
     * @param condition
     * @param page
     * @param sort
     * @return
     */
    public List<HistoryChangeVO> queryByCondition(Condition condition,Sorter sort);
    /**
     * 保存
     * @param historyChangeVO
     */
    public boolean saveHistoryChange(HistoryChangeVO historyChangeVO);
    /**
     * 
     * @param id
     * @return
     */
    public HistoryChangeVO load(String id);
    /**
     * 通过link_id查询历史变更信息
     * @param link_id
     * @param page
     * @return
     */
    public List<HistoryChangeVO> findHistoryChangeVOBylinkId(String link_id);
    /**
     * 
     * @param id
     */
    public void delete(String id);
    /**
     * 
     * @param condition
     * @param sort
     * @return
     */
    public List<HistoryChangeVO> queryByConditionReturnList(Condition condition, Sorter sort);
    /**
     * 
     * @param link_id
     * @param opType
     * @return
     */
    HistoryChangeVO findHistoryChangeVOBylinkId(String link_id,String opType);
    /**
     * 
     * @param condition
     * @return
     */
    int findHistoryChangeVOByCon(Condition condition,HistoryChangeVO historyChangeVO);
    
}
