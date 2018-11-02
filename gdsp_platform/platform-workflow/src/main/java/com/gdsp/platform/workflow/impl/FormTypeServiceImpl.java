package com.gdsp.platform.workflow.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.dao.IFormTypeDao;
import com.gdsp.platform.workflow.dao.IFormVariableDao;
import com.gdsp.platform.workflow.model.FormTypeVO;
import com.gdsp.platform.workflow.model.FormVariableVO;
import com.gdsp.platform.workflow.service.IFormTypeService;

/**
 * 
 * @author sun
 *
 */
@Service
@Transactional(readOnly = true)
public class FormTypeServiceImpl implements IFormTypeService {

    @Autowired
    private IFormTypeDao     formTypeDao;
    @Autowired
    private IFormVariableDao formVariableDao;

    @Override
    public Page<FormTypeVO> queryFormTypePageByCondition(Condition condition, Sorter sort, Pageable pageable) {
        return formTypeDao.queryFormTypePageByCondition(condition, sort, pageable);
    }

    @Override
    @Transactional
    public boolean saveFormType(FormTypeVO formTypeVO) {
        //validParas是过滤formTypeVO.parameters之后的list，剔除空值。
        List<FormVariableVO> validParas = new ArrayList<FormVariableVO>();
        List<FormVariableVO> parameters = formTypeVO.getParameters();
        if (StringUtils.isNotEmpty(formTypeVO.getId())) {
            //更新
            //单据类型表直接更新即可
            formTypeDao.updateFormType(formTypeVO);
            //单据对应的变量表先删除然后再插入
            String[] ids = new String[1];
            ids[0] = formTypeVO.getId();
            formVariableDao.deleteFormVariable(ids);
            //过滤单据变量集合
            for (FormVariableVO item : parameters) {
                if (StringUtils.isEmpty(item.getVariableName()))
                    continue;
                validParas.add(item);
            }
            for (FormVariableVO formVar : validParas) {
                formVar.setFromTypeId((formTypeVO.getId()));
                ;
                formVariableDao.insertFormVariable(formVar);
            }
            return true;
        } else {
            //插入
            //首先插入单据类型表信息
            formTypeDao.insertFormType(formTypeVO);
            //过滤单据变量集合
            for (FormVariableVO item : parameters) {
                if (StringUtils.isEmpty(item.getVariableName()))
                    continue;
                validParas.add(item);
            }
            //插入单据变量集合
            for (FormVariableVO formVar : validParas) {
                formVar.setFromTypeId((formTypeVO.getId()));
                ;
                formVariableDao.insertFormVariable(formVar);
            }
            return true;
        }
    }

    @Override
    @Transactional
    public boolean deleteFormType(String... ids) {
        formTypeDao.deleteFormType(ids);
        formVariableDao.deleteFormVariable(ids);
        return true;
    }

    @Override
    public FormTypeVO findTypeAndVariableById(String Id) {
        Condition condition1 = new Condition();
        Condition condition2 = new Condition();
        condition1.addExpression("formtypeid", Id);
        condition2.addExpression("f.id", Id);
        List<FormVariableVO> formVariableVOs = formVariableDao.queryFormVariableListByCondition(condition1, null);
        FormTypeVO formTypeVO = formTypeDao.queryFormTypeListByCondition(condition2, null).get(0);
        formTypeVO.setParameters(formVariableVOs);
        return formTypeVO;
    }

    @Override
    public List<FormTypeVO> queryFormTypeListByCondition(Condition condition,
            Sorter sort) {
        return formTypeDao.queryFormTypeListByCondition(condition, sort);
    }

    @Override
    public String queryFormURLByFormId(String formId) {
        return formTypeDao.queryFormURLByFormId(formId);
    }

	@Override
	public boolean checkCode(String formCode) {
		int flag=0;
		flag=formTypeDao.countFormDefByCode(formCode);
		if(flag>0){
			return false;
		}
		return true;
	}

	@Override
	public boolean checkName(String id, String formName) {
		int flag=0;
		flag=formTypeDao.countFormDefByName(id,formName);
		if(flag>0){
			return false;
		}
		return true;
	}
}
