package com.gdsp.platform.log.service;

import java.util.List;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.log.model.TempLogOpVO;

public interface ITempLogOpService {
	
	public List<TempLogOpVO> queryByServiceIdAndTimeRange(String serviceId,Long start,Long end);
	
	public Integer queryByTableName(String tableName,Condition condition);
	
	public void deleteTempLogOP(String[] ids);
	
	public void deleteTempLogContent(String[] ids);
}
