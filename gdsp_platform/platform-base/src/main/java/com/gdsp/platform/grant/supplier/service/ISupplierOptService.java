package com.gdsp.platform.grant.supplier.service;

import java.util.List;

import com.gdsp.platform.grant.supplier.model.SupplierVO;

public interface ISupplierOptService {
	public void insert(SupplierVO supVO);

	public int delete(String id);
	
	public void update(SupplierVO supVO);

//	public SupplierVO load(String id);
//
//	public List<SupplierVO> findAll();
//	
//	public boolean uniqueCheck(SupplierVO supVO);
}
