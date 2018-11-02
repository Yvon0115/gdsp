package com.gdsp.platform.func.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.func.dao.IButnRegisterDao;
import com.gdsp.platform.func.model.ButnRegisterVO;
import com.gdsp.platform.func.service.IButnRegisterService;

@Transactional(readOnly = true)
@Service
public class ButnRegisterImpl implements IButnRegisterService {

    @Autowired
    private IButnRegisterDao dao;

    @Override
    public Page<ButnRegisterVO> queryBtnRegister(Condition condition, Sorter sort, Pageable pageable) {
        
        return dao.queryBtnRegister(condition, sort, pageable);
    }

    @Override
    public ButnRegisterVO loadButnRegisterVOById(String id) {
        
        return dao.loadButnRegisterVOById(id);
    }

    @Override
    @Transactional
    public void updateButnRegister(ButnRegisterVO butnRegisterPages) {
        
        dao.updateButnRegister(butnRegisterPages);
    }

    @Override
    @Transactional
    public void insertButnRegister(ButnRegisterVO butnRegisterPages) {
        
        dao.insertButnRegister(butnRegisterPages);
    }

    @Override
    @Transactional
    public void deleteButnRegister(String... id) {
        
        dao.deleteButnRegister(id);
    }

    @Override
    public List<ButnRegisterVO> queryButnByFunCode(String funcode) {
        
        return dao.queryButnByFunCode(funcode);
    }

    @Override
    public List<ButnRegisterVO> queryButnByParentID(String parentid) {
        
        return dao.queryButnByParentID(parentid);
    }
}
