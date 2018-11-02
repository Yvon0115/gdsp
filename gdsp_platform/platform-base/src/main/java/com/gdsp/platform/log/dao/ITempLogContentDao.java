package com.gdsp.platform.log.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.log.model.DetailOpLogVO;
import com.gdsp.platform.log.model.TempLogContentVO;
import com.gdsp.platform.log.model.TempLogOpVO;

@MBDao
public interface ITempLogContentDao{
	
	int delete(@Param("ids")String[] ids);
	
	void insertBatch(@Param("opContentList")List<TempLogContentVO> tempOpContentList);
	
}
