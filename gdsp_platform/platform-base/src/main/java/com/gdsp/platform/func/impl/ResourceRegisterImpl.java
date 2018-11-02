package com.gdsp.platform.func.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.func.dao.IResourceRegisterDao;
import com.gdsp.platform.func.model.ResourceRegisterVO;
import com.gdsp.platform.func.service.IResourceRegisterService;

@Transactional(readOnly = true)
@Service
public class ResourceRegisterImpl implements IResourceRegisterService {

    @Autowired
    private IResourceRegisterDao dao;

    @Override
    public Page<ResourceRegisterVO> getResourceByMumeid(Condition condition,
            Pageable page) {
        
        return dao.getResourceByMumeid(condition, page);
    }

    @Override
    public ResourceRegisterVO getResourceByID(String id) {
        
        return dao.getResourceByID(id);
    }

    @Transactional
    @Override
    public void updateResRegister(ResourceRegisterVO vo) {
        
        dao.updateResRegister(vo);
    }

    @Transactional
    @Override
    public void insertResRegister(ResourceRegisterVO vo) {
        
        dao.insertResRegister(vo);
    }

    @Transactional
    @Override
    public void delete(String... id) {
        
        dao.delete(id);
    }

    @Override
    public boolean synchroCheck(ResourceRegisterVO vo) {
        return dao.existSameURL(vo) == 0;
    }
}
