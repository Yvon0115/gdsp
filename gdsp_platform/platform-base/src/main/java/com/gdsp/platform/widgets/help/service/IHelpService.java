package com.gdsp.platform.widgets.help.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.widgets.help.model.HelpVO;

/**
 * 角色服务
 * @author wwb
 * @version 1.0 2015/6/18
 */
public interface IHelpService {

    Page<HelpVO> queryHelpVosPageByCondition(Condition condition, Pageable pageable,
            Sorter sort);

    HelpVO load(String id);

    void saveHelpVO(HelpVO helpVO);

    void deleteHelp(String[] id);
}
