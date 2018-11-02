package com.gdsp.ptbase.appcfg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.ptbase.appcfg.model.LayoutColumnVO;
import com.gdsp.ptbase.appcfg.model.PageVO;

@MBDao
public interface IWidgetQueryDao {

    List<PageVO> queryOutResByCondition(@Param("cond") Condition cond, @Param("sort") Sorter sort);

    List<LayoutColumnVO> getColumnInfoByLayout(String layout_id);
}
