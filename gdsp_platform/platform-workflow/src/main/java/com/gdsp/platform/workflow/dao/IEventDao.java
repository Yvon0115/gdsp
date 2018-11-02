package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.EventVO;

/**
 * 
 * @author sun
 *
 */
@MBDao
public interface IEventDao {

    public List<EventVO> queryEventListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    public List<EventVO> queryEventListByInput(String event);
}
