package com.gdsp.ptbase.appcfg.service;

import java.util.List;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.ptbase.appcfg.model.LayoutColumnVO;
import com.gdsp.ptbase.appcfg.model.PageVO;

public interface IWidgetQueryService {

    /**
     * 查询目录下的的外部资源,将OutResourceVO转为PageVO
     * 
     * @param pid
     * @return
     * @throws Exception
     */
    public List<PageVO> queryOutResByCondition(Condition cond, Sorter sort);

    public List<LayoutColumnVO> getColumnInfoByLayout(String layout_id);
}
