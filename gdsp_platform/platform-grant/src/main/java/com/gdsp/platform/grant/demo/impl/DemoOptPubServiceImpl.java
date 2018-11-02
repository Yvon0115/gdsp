package com.gdsp.platform.grant.demo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.exceptions.MessageException;
import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.impl.CrudService;
import com.gdsp.platform.grant.demo.dao.IDemoDao;
import com.gdsp.platform.grant.demo.model.DemoVO;

@Service
public class DemoOptPubServiceImpl extends CrudService<DemoVO> {
	@Autowired
	private IDemoDao demoDao;
	@Override
	public DemoVO load(String id) {
		return super.load(id);
	}

	@Override
	public DemoVO insert(DemoVO entity) {
		return super.insert(entity);
	}

	@Override
	public DemoVO update(DemoVO entity) {
		return super.update(entity);
	}

	@Override
	public void delete(String... id) {
		super.delete(id);
	}

	@Override
	public void validate(DemoVO entity) {
		if(demoDao.existSameDemo(entity)==1){
			throw new MessageException("已存在该用户");
		}
	}

	@Override
	public ICrudDao<DemoVO> getDao() {
		return demoDao;
	}
}
