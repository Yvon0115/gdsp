package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.HistoryVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;

/**
 * 流程审批历史扩展
 * 无 update()
 * @author gdsp
 *
 */
@MBDao
public interface IProcessHistoryDao {

    public boolean saveProcessHistory(ProcessHistoryVO processHistoryVO);

    public boolean deleteProcessHistory(String[] ids);

    public ProcessHistoryVO findProcessHistoryById(String id);

    public Page<ProcessHistoryVO> queryProcessHistoryByCondition(Pageable pageable, @Param("condition") Condition condition, @Param("sort") Sorter sort);

    public List<ProcessHistoryVO> queryProcessHistoryListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    // 查询所有流程历史,需要验证管理员身份 
    public Page<HistoryVO> queryAllHistoryForAdmin(Pageable pageable, @Param("condition") Condition condition, @Param("sort") Sorter sort);

    // 查询单个历史流程详情
    public Page<ProcessHistoryVO> queryProcessHistoryByInsId(@Param("procInsId") String procInsId, @Param("sort") Sorter sort, Pageable pageable);
    
    public Page<HistoryVO> queryAllHistory();

    public List<ProcessHistoryVO> queryProcessHistoryByProcInstID(@Param("procInsId") String procInstID);
    
    //根据流程实例id查询审批结果
    public int queryApproveResultByInstId(@Param("proInstId")String proInstId);

	public void deleteProcessHisByTaskIdAndProinstId(@Param("taskId")String taskId,@Param("proInstId") String processInstanceId);
}
