package com.gdsp.platform.systools.indexanddim.dao;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.QueryDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.systools.indexanddim.model.IndexDicVO;

@MBDao
public interface IndexDicDao extends QueryDao<IndexDicVO> {

    /**
     * 查询所有指数分类信息
     */
    public void queryAllByHandler(MapListResultHandler<IndexDicVO> map, @Param("condition") Condition cond, @Param("sort") Sorter sort);
}
