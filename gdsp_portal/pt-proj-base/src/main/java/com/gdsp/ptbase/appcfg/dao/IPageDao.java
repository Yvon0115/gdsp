package com.gdsp.ptbase.appcfg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.ptbase.appcfg.model.PageVO;

@MBDao
public interface IPageDao {

    void insert(PageVO page);

    void update(PageVO page);

    void delete(@Param("ids") String... ids);

    PageVO load(String id);

    List<PageVO> findListByCondition(@Param("cond") Condition cond, @Param("sort") Sorter sort);

    Integer findCountByCondition(@Param("cond") Condition cond);

    Integer findMaxSortNum(String dir_id);

    List<PageVO> queryPageVOListByDirid(@Param("dir_id") String dir_id,@Param("cond") Condition cond, @Param("sort") Sorter sort);

    void batchUpdate(PageVO page);

	void updateDefLayout(@Param("layout_id")String layout_id, @Param("page_id")String page_id);
}
