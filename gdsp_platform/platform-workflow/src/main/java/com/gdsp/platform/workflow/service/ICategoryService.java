package com.gdsp.platform.workflow.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.CategoryVO;

/**
 * 流程类别服务
 * @author sun
 */
public interface ICategoryService {

    /**
     * 保存流程类别
     * @param vo 流程部署
     * @return 是否成功
     */
    public boolean saveCategory(CategoryVO categoryVO);

    /**
     * 删除流程类别
     * @param ids 流程类别ids
     */
    public boolean deleteCategory(String... ids);

    /**
     * 根据主键查询流程类别
     * @return 流程类别对象
     */
    public CategoryVO findCategoryById(String id);

    /**
     * 通用分页查询方法
     * @param condition 查询条件
     * @param page 分页请求
     * @param sort 排序规则
     * @return 分页结果
     */
    public Page<CategoryVO> queryCategoryByCondition(Condition condition, Pageable pageable, Sorter sort);

    /**
     * 通用集合结果查询方法
     * @param condition 查询条件
     * @param sort 排序规则
     * @return List
     */
    public List<CategoryVO> queryCategoryListByCondition(Condition condition, Sorter sort);

    /**
     * 校验分类code
     * @param id
     * @param categoryCode
     * @return
     */
	public boolean checkCode(String id, String categoryCode);

	/**
	 * 校验分类名称
	 * @param id
	 * @param categoryName
	 * @return
	 */
	public boolean checkName(String id, String categoryName);
}
