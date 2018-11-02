package com.gdsp.integration.framework.param.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.integration.framework.param.dao.IAcParamLibraryDao;
import com.gdsp.integration.framework.param.model.AcParamLibrary;
import com.gdsp.integration.framework.param.service.IParamLibraryService;

@Service
@Transactional(readOnly = true)
public class ParamLibraryServiceImpl implements IParamLibraryService {

    @Autowired
    private IAcParamLibraryDao dao;

    @Transactional(readOnly = false)
    @Override
    public void addParamLbrary(AcParamLibrary vo) {
        dao.insert(vo);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateParamLibrary(AcParamLibrary vo) {
        dao.updateByPrimaryKeySelective(vo);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteParamLibrary(String id) {
        dao.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteParamLibraryByIds(String[] ids) {
        dao.deleteParamLibraryByIds(ids);
    }

    @Override
    public AcParamLibrary load(String id) {
        return dao.load(id);
    }

    @Override
    public List<AcParamLibrary> queryAllParamLibrary() {
        return dao.queryAllParamLibrary();
    }

    @Override
    public boolean checkParamLibraryName(AcParamLibrary vo) {
        return dao.existsParamLibraryName(vo) == 0;
    }

    @Override
    public Map queryByCondition(Condition condition,
            Sorter sort) {
        MapListResultHandler<Map> handler = new MapListResultHandler<Map>("pid");
        dao.queryTree(handler,condition,sort);
        return handler.getResult();
    }

    @Override
    public Map queryParamLibraryTreeMap(
            String id) {
        MapListResultHandler<Map> handler = new MapListResultHandler<Map>("pid");
        dao.queryTreeMap(handler, id);
        return handler.getResult();
    }
}
