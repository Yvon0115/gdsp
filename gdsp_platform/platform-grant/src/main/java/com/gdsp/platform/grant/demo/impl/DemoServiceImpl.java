package com.gdsp.platform.grant.demo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdsp.platform.grant.demo.dao.IDemoDao;
import com.gdsp.platform.grant.demo.model.DemoVO;
import com.gdsp.platform.grant.demo.service.IDemoService;
@Service
public class DemoServiceImpl implements IDemoService{
	@Autowired
	private IDemoDao demoDao;

	@Override
	public boolean uniqueCheck(DemoVO demoVO) {
		return demoDao.existSameDemo(demoVO) == 0;
	}
	
}
