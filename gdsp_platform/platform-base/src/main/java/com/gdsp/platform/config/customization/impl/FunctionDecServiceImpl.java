package com.gdsp.platform.config.customization.impl;




import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.config.customization.dao.IFunctionDecDao;
import com.gdsp.platform.config.customization.model.FuncDecDataIOVO;
import com.gdsp.platform.config.customization.model.FunctionDecVO;
import com.gdsp.platform.config.customization.service.IFunctionDecService;


/**
 * 
* @ClassName: FunctionDecServiceImpl
* (接口的实现类)
* @author songxiang
* @date 2015年10月28日 下午12:53:39
*
 */
@Transactional(readOnly=false)
@Service
public class FunctionDecServiceImpl implements IFunctionDecService {

	@Autowired
	private IFunctionDecDao dao;
	
	@Override
	public Page<FunctionDecVO> queryFunctionDecVOsPages(Condition condition,
			Pageable page) {
		return dao.queryFunctionDecVOsPages(condition, page);
	}
	
	@Override
	public Map queryMenuRegisterVOsByCondReturnMap(Condition condition,
			Sort sort) {
		MapListResultHandler handler = new MapListResultHandler("parentid");
		dao.queryMenuRegisterVOMapListByCondition(condition, sort,handler);
		return handler.getResult();
      	     }

	@Override
	public FunctionDecVO loadFunctionDecVOById(String id) {
		
		return dao.load(id);
	}

	@Override
	public void update(FunctionDecVO functionDecVO) {
		dao.update(functionDecVO);
		
	}

	@Override
	public List<FunctionDecVO> queryFunctionDecVOListByCondition(String id) {
		
		return dao.queryFunctionDecVOListByCondition(id);
	}

	@Override
	public List<FuncDecDataIOVO> findExportList(String commonDirId) {
		if(StringUtils.isBlank(commonDirId)){
			return null;
		}
		String[] commonDirIdArr = commonDirId.split(",");
		List<FuncDecDataIOVO> funcDecDataList = dao.findExportFunDecList(commonDirIdArr);
		return funcDecDataList;
	}

	
	
}
