package com.gdsp.platform.grant.product.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.product.dao.IProductDao;
import com.gdsp.platform.grant.product.model.ProductVO;
import com.gdsp.platform.grant.product.service.IProductOptPubService;

@Service
@Transactional(readOnly = false)
public class ProductOptPubServiceImpl implements IProductOptPubService {
	@Autowired
	private IProductDao productDao;

	@Override
	public int insert(ProductVO productVO) {
		return productDao.insert(productVO);
	}

	@Override
	public int delete(String[] ids) {
		return productDao.delete(ids);
	}

	@Override
	public ProductVO load(String id) {
		return productDao.load(id);
	}

	@Override
	public Page<ProductVO> queryProductByCondition(Condition condition, Pageable page, Sorter sort) {
		return productDao.queryProductByCondition(condition, page, sort);
	}

	@Override
	public boolean uniqueCheck(ProductVO productVO) {
		return productDao.existSameProduct(productVO) == 0;
	}

	@Override
	public int update(ProductVO productVO) {
		return productDao.update(productVO);
	}

}
