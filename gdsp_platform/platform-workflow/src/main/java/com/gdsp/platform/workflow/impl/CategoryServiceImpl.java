package com.gdsp.platform.workflow.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.tree.helper.TreeCodeHelper;
import com.gdsp.platform.workflow.dao.ICategoryDao;
import com.gdsp.platform.workflow.model.CategoryVO;
import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.model.FormTypeVO;
import com.gdsp.platform.workflow.service.ICategoryService;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.IFormTypeService;

/**
 * 
 * @author sun
 *
 */
@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryDao categoryDao;
    @Autowired
    private IFormTypeService formTypeService;
    @Autowired
    private IDeploymentService    deploymentService;

    @Transactional
    @Override
    public boolean saveCategory(CategoryVO vo) {
        if (StringUtils.isNotEmpty(vo.getId())) {
            return categoryDao.updateCategory(vo);
        } else {
            vo = (CategoryVO) TreeCodeHelper.generateTreeCode(vo, null);
            return categoryDao.saveCategory(vo);
        }
    }

    @Transactional
    @Override
    public boolean deleteCategory(String... ids) {
      //如果该分类下查有关联的单据或流程则不允许删除
        Condition condition = new Condition();
        condition.addExpression("c.id", ids, "in");
        Condition cond = new Condition();
        cond.addExpression("categoryid", ids, "in");
        List<FormTypeVO> formTypeListByCondition = formTypeService.queryFormTypeListByCondition(condition, null);
        List<DeploymentVO> deploymentListByCondition = deploymentService.queryDeploymentListByCondition(cond, null);
        if((null != formTypeListByCondition && formTypeListByCondition.size()>0) && 
                (null!=deploymentListByCondition && deploymentListByCondition.size()>0)){
            throw new BusinessException("该分类下含有已注册单据和已定义流程，请先删除相应单据和流程！");
        } else if ((null != formTypeListByCondition && formTypeListByCondition.size()>0)){
            throw new BusinessException("该分类下含有已注册的单据，请先删除相应单据！");
        } else {
            return categoryDao.deleteCategory(ids);
        }
    }

    @Override
    public CategoryVO findCategoryById(String id) {
        return categoryDao.findCategoryById(id);
    }

    @Override
    public Page<CategoryVO> queryCategoryByCondition(Condition condition,
            Pageable pageable, Sorter sort) {
        return categoryDao.queryCategoryByCondition(condition, pageable, sort);
    }

    @Override
    public List<CategoryVO> queryCategoryListByCondition(Condition condition, Sorter sort) {
        return categoryDao.queryCategoryListByCondition(condition, sort);
    }

	@Override
	public boolean checkCode(String id, String categoryCode) {
		int flag=0;
		flag=categoryDao.countCategoryByCode(id,categoryCode);
		if(flag>0){
			return false;
		}
		return true;
	}

	@Override
	public boolean checkName(String id, String categoryName) {
		int flag=0;
		flag=categoryDao.countCategoryByName(id,categoryName);
		if(flag>0){
			return false;
		}
		return true;
	}
}
