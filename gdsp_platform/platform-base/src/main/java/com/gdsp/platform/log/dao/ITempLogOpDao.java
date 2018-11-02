package com.gdsp.platform.log.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.log.model.TempLogOpVO;

@MBDao
public interface ITempLogOpDao{

	List<TempLogOpVO> queryByServiceIdAndTimeRange(@Param("serviceId")String serviceId,@Param("start")Long start,@Param("end")Long end);
	
	Integer queryByTableName(@Param("tableName")String tableName,@Param("condition")Condition condition);
	
	int delete(@Param("ids")String[] ids);
	
	void insertBatchOpVo(@Param("opList")List<TempLogOpVO> tempOpList);
	
	/*
     * 根据表名和查询条件获取表记录旧值
     */
    public List<String> queryFormerValueByCondition(@Param("tablename")String tablename,@Param("condition")String condition);
}
