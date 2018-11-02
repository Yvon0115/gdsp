package com.gdsp.platform.log.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.LogDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.log.model.DetailOpLogVO;
import com.gdsp.platform.log.model.OperationLogVO;

//@MBDao
@LogDao
public interface IOperationLogDao {
    /**
     * 查询所有操作日志列表
     * @return
     */
    public List<OperationLogVO> operationLogList(@Param("condition")Condition condition);
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
    public Page<DetailOpLogVO> findDetailByCondition(@Param("id")String id,Pageable page);
    
    public List<DetailOpLogVO> findDetailInfoById(@Param("id")String id);
    
}
