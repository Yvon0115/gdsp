package com.gdsp.platform.grant.supplier.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.grant.supplier.dao.ISupplierDao;
import com.gdsp.platform.grant.supplier.model.SupplierVO;
import com.gdsp.platform.grant.supplier.service.ISupplierQueryPubService;

@Service
@Transactional(readOnly = true)
public class SupplierQueryPubServiceImpl implements ISupplierQueryPubService {
	@Autowired
	private ISupplierDao supDao;

	@Override
	public SupplierVO load(String id) {
		SupplierVO supVO = supDao.load(id);
		return supVO;
	}

	@Override
	public List<SupplierVO> queryAllSupList() {
		return supDao.queryAllSupList();
	}

	@Override
	public List<SupplierVO> findSelfandChildrenById(String supId) {
		List<SupplierVO> SupplierVO =null;
		SupplierVO supplierVO=supDao.load(supId);
		if(supplierVO!=null){
			SupplierVO=supDao.querySelfAndChildSupListByInnercode(supplierVO.getInnercode());
		}
		return SupplierVO;
	}

}
