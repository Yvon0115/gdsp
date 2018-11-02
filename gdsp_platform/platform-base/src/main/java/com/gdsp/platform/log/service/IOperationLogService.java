package com.gdsp.platform.log.service;

import java.util.List;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.log.model.DetailOpLogVO;
import com.gdsp.platform.log.model.OperationLogVO;

public interface IOperationLogService {
    /**
     * 查询操作日志列表
     * @return
     */
    public List<OperationLogVO> operationLogList(Condition condition);
    /**
     * 
    * @Title: load
    * (加载操作对象)
    * @param id
    * @return    参数说明
    * @return OperationLogVO    返回值说明
    *      */
    public OperationLogVO load(String id);
    /**
     * 根据上级表的id查询字段变化详细信息
     */
    public List<DetailOpLogVO> queryDetailByCondition(String id);
    
    /**
     * 获取csv导出内容list
     * @param opLogList
     * @return
     */
    public List<String> getContentList(List<OperationLogVO> opLogList);
    
}
