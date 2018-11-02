package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.EventTypeVO;

/**
 * 
 * @author sun
 *
 */
@MBDao
public interface IEventTypeDao {

    public List<EventTypeVO> queryEventTypeListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);
}
