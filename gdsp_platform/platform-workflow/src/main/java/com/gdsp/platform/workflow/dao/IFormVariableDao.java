package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.FormVariableVO;

/**
 * 
 * @author sun
 *
 */
@MBDao
public interface IFormVariableDao {

    public List<FormVariableVO> queryFormVariableListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    public boolean insertFormVariable(FormVariableVO formVariableVO);

    //根据formtypeid删除
    public boolean deleteFormVariable(String[] ids);
}
