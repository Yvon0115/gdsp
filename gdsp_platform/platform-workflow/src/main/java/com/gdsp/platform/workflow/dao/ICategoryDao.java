package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.CategoryVO;

/**
 * 
 * @author sun
 *
 */
@MBDao
public interface ICategoryDao {

    public boolean saveCategory(CategoryVO categoryVO);

    public boolean updateCategory(CategoryVO categoryVO);

    public boolean deleteCategory(String[] ids);

    public CategoryVO findCategoryById(String id);

    public Page<CategoryVO> queryCategoryByCondition(@Param("condition") Condition condition, Pageable pageable, @Param("sort") Sorter sort);

    public List<CategoryVO> queryCategoryListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    /**
     * 分类编码校验
     * @param id
     * @param categoryCode
     * @return
     */
	public int countCategoryByCode(@Param("id")String id, @Param("categoryCode")String categoryCode);

	/**
	 * 分类名称校验
	 * @param id
	 * @param categoryName
	 * @return
	 */
	public int countCategoryByName(@Param("id")String id, @Param("categoryName")String categoryName);
}
