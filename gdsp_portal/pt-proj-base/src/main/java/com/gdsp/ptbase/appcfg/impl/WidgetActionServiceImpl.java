package com.gdsp.ptbase.appcfg.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.ptbase.appcfg.dao.IWidgetActionDao;
import com.gdsp.ptbase.appcfg.model.WidgetActionVO;
import com.gdsp.ptbase.appcfg.service.IWidgetActionService;

/**
 * 
* @ClassName: WidgetActionServiceImpl
* (接口的实现类)
* @author hongwei.xu
* @date 2015年7月22日 下午5:07:11
*
 */
@Service
@Transactional(readOnly = true)
public class WidgetActionServiceImpl implements IWidgetActionService {

    @Resource
    private IWidgetActionDao dao;

    @Override
    @Transactional(readOnly = false)
    public WidgetActionVO insert(WidgetActionVO vo) {
        List existsType = dao.existsType(vo.getWidgettype());
        if (existsType == null || existsType.size() == 0) {
            vo.setSortnum(0);
        } else {
            int findSortnum = dao.findSortnum(vo.getWidgettype());
            vo.setSortnum(findSortnum + 1);
        }
        dao.insert(vo);
        return vo;
    }

    @Override
    @Transactional
    public WidgetActionVO update(WidgetActionVO vo) {
        dao.update(vo);
        return vo;
    }

    @Override
    @Transactional
    public void delete(String... ids) {
        dao.delete(ids);
    }

    @Override
    public WidgetActionVO load(String id) {
        return dao.load(id);
    }

    @Override
    public List<WidgetActionVO> findActionsByWidgetId(String widgetId, String widgetType) {
        return dao.findActionsByWidget(widgetId, widgetType, WidgetActionVO.ALL_WIDGETTYPE);
    }

    @Override
    public Map<String, WidgetActionVO> findAllActions() {
        return dao.findAllActions();
    }

    @Override
    public Page<WidgetActionVO> queryWidgetActionVOByCondition(
            Condition condition, Pageable page) {
        
        return dao.queryWidgetActionVOByCondition(condition, page);
    }

    @Override
    public Page<WidgetActionVO> findWidgetActionVOByWidgetIDs(String widgetid,
            Pageable page) {
        
        return dao.findWidgetActionVOByWidgetIDs(widgetid, page);
    }

    @Override
    public List<String> isPreset(String[] id) {
        
        return dao.isPreset(id);
    }

    @Override
    public boolean synchroCheck(WidgetActionVO vo) {
        return dao.existSameCode(vo) == 0;
    }
}
