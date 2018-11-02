package com.gdsp.platform.grant.product.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.product.model.ProductVO;

@MBDao
public interface IProductDao extends ICrudDao<ProductVO> {

	int insert(ProductVO productVO);

	int update(ProductVO productVO);

	int delete(String[] ids);

	ProductVO load(String id);

	Page<ProductVO> queryProductByCondition(@Param("condition") Condition condition, Pageable page,
			@Param("sort") Sorter sort);

	public int existSameProduct(ProductVO productVO);
}
