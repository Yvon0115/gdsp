package com.gdsp.platform.config.global.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.config.global.dao.IParamDao;
import com.gdsp.platform.config.global.model.ParamVO;
import com.gdsp.platform.config.global.service.IParamService;
import com.gdsp.platform.config.global.utils.ParamValueUtils;

@Service
@Transactional
public class ParamImpl implements IParamService {

    @Resource
    IParamDao dao;

    @Override
    public void insertParamDef(ParamVO param) {
        
        dao.insertParamDef(param);
    }

    @Override
    public void deleteParamDef(String id) {
        
        dao.deleteParamDef(id);
    }

    @Override
    public void updateParamDef(ParamVO param) {
        
        dao.updateParamDef(param);
    }

    @Override
    public Object queryParamValue(String paramcode) {
        
        ParamVO vo = dao.queryParamVOByCode(paramcode);
        if (vo == null)
            return null;
        int valuetype = vo.getValuetype();
        String strValue = vo.getParvalue();
        Object value = ParamValueUtils.getParamValue(valuetype, strValue);
        if (value == null) {
            value = ParamValueUtils.getParamValue(valuetype, vo.getDefaultvalue());
        }
        return value;
    }

    @Override
    public void setParamValue(String paramCode, Object paramValue) {
        
        String strValue = null;
        if (paramValue != null) {
            strValue = paramValue.toString();
        }
        String msg = null;
        ParamVO vo = dao.queryParamVOByCode(paramCode);
        if (StringUtils.isBlank(strValue)) {//如果参数值为空，则设置为默认值
            strValue = vo.getDefaultvalue();
        } else {//检查参数合法性
            msg = ParamValueUtils.checkParamValue(vo.getValuetype(), strValue);
        }
        if (!StringUtils.isBlank(msg)) {
            throw new BusinessException(msg);
        }
        dao.setParamValue(paramCode, strValue);
    }

    @Override
    public Page<ParamVO> queryParamDefList(Condition condition, Pageable page,
            Sorter sort) {
        return dao.queryParamDefList(condition, page, sort);
    }

    @Override
    public void restoreDefault(String id) {
        dao.restoreDefault(id);
    }

    @Override
    public ParamVO loadParam(String id) {
        return dao.load(id);
    }
}
