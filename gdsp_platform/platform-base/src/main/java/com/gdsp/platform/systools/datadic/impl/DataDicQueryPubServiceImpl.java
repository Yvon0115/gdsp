/*
 * Copyright (c)  Business Intelligence Unified Portal. All rights reserved.
 * 
 */ 
package com.gdsp.platform.systools.datadic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.systools.datadic.dao.IDataDicDao;
import com.gdsp.platform.systools.datadic.model.DataDicVO;
import com.gdsp.platform.systools.datadic.model.DataDicValueVO;
import com.gdsp.platform.systools.datadic.service.IDataDicQueryPubService;
import com.gdsp.platform.systools.datadic.service.IDataDicService;

/**
 * 数据字典查询公共接口实现类
 * @author lt
 * @date 2017年10月24日 下午3:55:51
 */
@Service
@Transactional(readOnly = true)
public class DataDicQueryPubServiceImpl implements IDataDicQueryPubService{
	@Autowired
    private IDataDicDao dataDicDao;
	@Autowired
	private IDataDicService dataDicService;
	
	@Override
	public DataDicVO getDataDicTypeInfo(String typeCode) {
		List<DataDicVO> list = dataDicDao.queryDataDicValByTypeCode(typeCode);
		if(list.size()>0){
			return list.get(0);
		}
		else {
			return null;
		}
	}
	
	@Override
	public List<DataDicValueVO> getDataDicValueList(String typeCode) {
		return dataDicService.getDatadicValueByTypeCode(typeCode);

	}
	@Override
	public DataDicValueVO getDataDicValueByCode(String typeCode, String dataCode) {
		return dataDicDao.queryDataDicValueByCode(typeCode,dataCode);
	}
	

}
