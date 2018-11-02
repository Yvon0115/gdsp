package com.gdsp.ptbase.appcfg.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.ptbase.appcfg.dao.IWidgetCdnTemplateDao;
import com.gdsp.ptbase.appcfg.model.WidgetCdnTemplateVO;
import com.gdsp.ptbase.appcfg.service.IWidgetCdnTemplateService;

/**
 * 
* @ClassName: WidgetCdnTemplateServiceImpl
* (接口的实现类)
* @author songxiang
* @date 2015年10月28日 下午12:52:23
*
 */
@Service
@Transactional
public class WidgetCdnTemplateServiceImpl implements IWidgetCdnTemplateService {

    @Autowired
    private IWidgetCdnTemplateDao dao;

    @Override
    public void insert(WidgetCdnTemplateVO widgetCdnTemplateVO) {
        if (StringUtils.isNotEmpty(widgetCdnTemplateVO.getId())) {
            dao.update(widgetCdnTemplateVO);
        } else {
            dao.insert(widgetCdnTemplateVO);
        }
    }

    @Override
    public Page<WidgetCdnTemplateVO> queryByCondition(Condition condition, Pageable page) {
        return dao.queryByCondition(condition, page);
    }

    @Override
    public void deletes(String... ids) {
        dao.deletes(ids);
    }

    @Override
    public void update(WidgetCdnTemplateVO widgetCdnTemplateVO) {
        dao.update(widgetCdnTemplateVO);
    }

    @Override
    public WidgetCdnTemplateVO load(String id) {
        return dao.load(id);
    }

    @Override
    public List<WidgetCdnTemplateVO> queryByConditionNoPage(Condition condition) {
        
        return dao.queryByConditionNoPage(condition);
    }
}
