package com.gdsp.platform.grant.demo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.QueryDao;
import com.gdsp.dev.persist.impl.QueryService;
import com.gdsp.platform.grant.demo.dao.IDemoDao;
import com.gdsp.platform.grant.demo.model.DemoVO;
@Service
public class DemoQueryPubServiceImpl extends QueryService<DemoVO>{
	@Autowired
	private IDemoDao demoDao;

	@Override
	public List<DemoVO> findAll() {
		return super.findAll();
	}

	@Override
	public Page<DemoVO> findPage(Pageable pageable) {
		return super.findPage(pageable);
	}

	@Override
	public List<DemoVO> findListByCondition(Condition condition) {
		return super.findListByCondition(condition);
	}

	@Override
	public Page<DemoVO> findPageByCondition(Condition condition, Pageable page) {
		return super.findPageByCondition(condition, page);
	}

	@Override
	public List<DemoVO> findListByIds(String[] ids) {
		return super.findListByIds(ids);
	}

	@Override
	protected QueryDao<DemoVO> getDao() {
		return demoDao;
	}

}
