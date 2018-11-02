package com.gdsp.platform.grant.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.product.model.ProductVO;

public interface IProductOptPubService {

	public int insert(ProductVO productVO);

	public int delete(String[] ids);
	
	public int update(ProductVO productVO);

	public ProductVO load(String id);

	public Page<ProductVO> queryProductByCondition(Condition condition, Pageable page, Sorter sort);
	
	public boolean uniqueCheck(ProductVO productVO);
}
