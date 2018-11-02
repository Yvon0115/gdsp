package com.gdsp.platform.log.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.log.dao.ITempLogContentDao;
import com.gdsp.platform.log.dao.ITempLogOpDao;
import com.gdsp.platform.log.model.TempLogOpVO;
import com.gdsp.platform.log.service.ITempLogOpService;

@Service
@Transactional
public class TempLogOpServiceImpl implements ITempLogOpService {
	
	@Resource
	private ITempLogOpDao tempLogOpDao;
	@Resource
	private ITempLogContentDao tempLogContentDao;

	@Override
	public List<TempLogOpVO> queryByServiceIdAndTimeRange(String serviceId,Long start,Long end) {
		return tempLogOpDao.queryByServiceIdAndTimeRange(serviceId,start,end);
	}

	@Override
	public Integer queryByTableName(String tableName, Condition condition) {
		return tempLogOpDao.queryByTableName(tableName, condition);
	}

	@Override
	public void deleteTempLogOP(String[] ids) {
		tempLogOpDao.delete(ids);		
	}

	@Override
	public void deleteTempLogContent(String[] ids) {
		tempLogContentDao.delete(ids);
	}
	
	
}
