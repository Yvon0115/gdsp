package com.gdsp.platform.func.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.func.dao.IPageRegisterDao;
import com.gdsp.platform.func.model.PageRegisterVO;
import com.gdsp.platform.func.service.IPageRegisterService;
import com.gdsp.platform.grant.auth.service.IPowerMgtOptPubService;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.log.service.OpLog;

@Transactional(readOnly = false)
@Service
public class PageRegisterImpl implements IPageRegisterService {

    @Autowired
    private IPageRegisterDao         pageDao;
    @Autowired
    private IPowerMgtQueryPubService powerMgtPubService;
    @Autowired
    private IPowerMgtOptPubService   powerMgtUpdatePubService;

    @Override
    @CacheEvict(value = "pageRegister", allEntries = true)
    public void insertPageRegister(PageRegisterVO vo) {
        pageDao.insert(vo);
    }

    @Transactional
    @Override
    public void sort(String[] pageIDs) {
        for (int i = 0; i < pageIDs.length; i++) {
            PageRegisterVO vo = pageDao.load(pageIDs[i]);
            // 设置顺序,不足5位前面补零
            vo.setDispcode(String.format("%05d", i));
            pageDao.update(vo);
        }
    }

    @Transactional
    @Override
    @CacheEvict(value = "pageRegister", allEntries = true)
    @OpLog
    public void deletePageRegister(String id) {
        pageDao.delete(id);
        // 删除已分配页面信息
        powerMgtUpdatePubService.deletePageRoleByPageID(id);
    }

    @Override
    public List<PageRegisterVO> queryPageRegisterListByCondition(Condition condition, Sorter sort) {
        return pageDao.queryPageRegisterListByCondition(condition, sort);
    }

    @Override
    public int loadPageID(String pageId) {
        
        return pageDao.loadPageID(pageId);
    }

    @Override
    public PageRegisterVO loadPageRegister(String id) {
        return pageDao.load(id);
    }

    @Override
    public boolean synchroCheck(String funname, String menuid) {
        return isUniqueName(funname, menuid, null);
    }

    @Override
    public boolean isUniqueName(String funname, String menuid, String id) {
        return pageDao.existSameName(funname, menuid, id) == 0;
    }

    @Override
    @Cacheable(value = "pageRegister", key = "#root.methodName")
    public List<PageRegisterVO> queryAllPageList() {
        return pageDao.queryAllPageList();
    }
}
