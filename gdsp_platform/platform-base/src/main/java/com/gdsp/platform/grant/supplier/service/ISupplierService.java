package com.gdsp.platform.grant.supplier.service;

import com.gdsp.platform.grant.supplier.model.SupplierVO;

public interface ISupplierService {
	public boolean uniqueCheck(SupplierVO supVO);

	public boolean uniqueNameCheck(SupplierVO supVO);
}
