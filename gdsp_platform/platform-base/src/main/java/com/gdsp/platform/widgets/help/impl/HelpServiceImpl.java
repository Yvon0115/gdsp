package com.gdsp.platform.widgets.help.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.widgets.help.dao.IHelpDao;
import com.gdsp.platform.widgets.help.model.HelpVO;
import com.gdsp.platform.widgets.help.service.IHelpService;

@Service
@Transactional(readOnly = true)
public class HelpServiceImpl implements IHelpService {

    @Autowired
    private IHelpDao helpDao;

    @Override
    public Page<HelpVO> queryHelpVosPageByCondition(Condition condition,
            Pageable pageable, Sorter sort) {
        return helpDao.queryHelpVosPageByCondition(condition, pageable, sort);
    }

    @Override
    public HelpVO load(String id) {
        return helpDao.load(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveHelpVO(HelpVO helpVO) {
        if (StringUtils.isBlank(helpVO.getId())) {
            helpDao.insert(helpVO);
        } else {
            helpDao.update(helpVO);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteHelp(String[] id) {
        for (int i = 0; i < id.length; i++) {
            HelpVO vo = this.load(id[i]);
            String path = AppConfig.getInstance().getString("portal.help.upload.addr") + vo.getAttach_path();
            new File(path).delete();
        }
        helpDao.delete(id);
    }
}
