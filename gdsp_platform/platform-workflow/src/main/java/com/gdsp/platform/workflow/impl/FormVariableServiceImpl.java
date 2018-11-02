package com.gdsp.platform.workflow.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.dao.IFormVariableDao;
import com.gdsp.platform.workflow.model.FormVariableVO;
import com.gdsp.platform.workflow.service.IFormVariableService;

/**
 * 
 * @author sun
 *
 */
@Service
@Transactional(readOnly = true)
public class FormVariableServiceImpl implements IFormVariableService {

    @Autowired
    private IFormVariableDao formVariableDao;

    @Override
    public List<FormVariableVO> queryFormVariableListByCondition(Condition condition, Sorter sort) {
        return formVariableDao.queryFormVariableListByCondition(condition, sort);
    }
}
