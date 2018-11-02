package com.gdsp.ptbase.appcfg.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.ptbase.appcfg.model.PageVO;
import com.gdsp.ptbase.appcfg.model.PageWidgetVO;

public interface IPageService {

    public PageVO insert(PageVO page);

    public PageVO update(PageVO page);

    public void delete(String... ids);

    public PageVO load(String id);

    List<PageVO> queryPageVOListByDirid(String dir_id,Condition condition,Sorter sort);

    void batchUpdate(List<PageVO> page);

    public List<PageVO> findListByCondition(Condition cond, Sorter sort);

    public int findCountByCondition(Condition cond);

    public int findMaxSortNum(String dir_id);

    public void sort(String[] page_id);

    public void savePageConfig(PageVO page, String del_comp, PageWidgetVO[] pw1, PageWidgetVO[] pw2, PageWidgetVO[] pw3);

	public void updateDefLayout(String string);
}
