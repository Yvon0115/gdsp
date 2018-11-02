package com.gdsp.platform.grant.supplier.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.grant.supplier.dao.ISupplierDao;
import com.gdsp.platform.grant.supplier.model.SupplierVO;
import com.gdsp.platform.grant.supplier.service.ISupplierService;

@Service
@Transactional(readOnly = true)
public class SupplierServiceImpl implements ISupplierService {
	@Autowired
	private ISupplierDao supDao;

	@Override
	public boolean uniqueCheck(SupplierVO supVO) {
		return supDao.existSameSup(supVO) == 0;
	}

	@Override
	public boolean uniqueNameCheck(SupplierVO supVO) {
		return supDao.existSameNameSup(supVO) == 0;
	}

}
