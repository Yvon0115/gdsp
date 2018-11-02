package com.gdsp.platform.grant.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.dao.QueryDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.demo.model.DemoVO;

@MBDao
public interface IDemoDao extends ICrudDao<DemoVO>, QueryDao<DemoVO> {

	@Override
	int insert(DemoVO entity);

	@Override
	int update(DemoVO entity);

	@Override
	DemoVO load(String id);

	@Override
	int delete(String[] ids);

	@Override
	List<DemoVO> findAll();

	@Override
	Page<DemoVO> findPage(Pageable pageable);

	@Override
	List<DemoVO> findListByCondition(Condition condition);

	@Override
	Page<DemoVO> findPageByCondition(@Param("cond")Condition condition, Pageable page);

	@Override
	List<DemoVO> findListByIds(String[] ids);

	public int existSameDemo(DemoVO demoVO);
}
