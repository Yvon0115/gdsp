package com.gdsp.platform.grant.supplier.service;

import java.util.List;

import com.gdsp.platform.grant.supplier.model.SupplierVO;

public interface ISupplierQueryPubService {
	public SupplierVO load(String id);

	public List<SupplierVO> queryAllSupList();

	public List<SupplierVO> findSelfandChildrenById(String supId);

}
