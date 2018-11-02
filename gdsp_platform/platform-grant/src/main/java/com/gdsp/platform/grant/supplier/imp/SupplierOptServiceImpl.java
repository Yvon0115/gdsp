package com.gdsp.platform.grant.supplier.imp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.utils.tree.helper.TreeCodeHelper;
import com.gdsp.platform.grant.supplier.dao.ISupplierDao;
import com.gdsp.platform.grant.supplier.model.SupplierVO;
import com.gdsp.platform.grant.supplier.service.ISupplierOptService;
import com.gdsp.platform.grant.supplier.service.ISupplierService;

@Service
@Transactional(readOnly = false)
public class SupplierOptServiceImpl implements ISupplierOptService {
	@Autowired
	private ISupplierService supService;
	@Autowired
	private ISupplierDao supDao;

	@Override
	public void insert(SupplierVO supVO) {
		if (!supService.uniqueNameCheck(supVO)) {
			throw new BusinessException("已经存在的供应商名称");
		}
		String parentId = supVO.getPk_fathersup();
		if (!StringUtils.isEmpty(parentId)) {
			SupplierVO parentVO = supDao.load(parentId);
			supVO = (SupplierVO) TreeCodeHelper.generateTreeCode(supVO, parentVO.getInnercode());
		} else {
			supVO = (SupplierVO) TreeCodeHelper.generateTreeCode(supVO, null);
		}
		supDao.insert(supVO);
	}

	@Override
	public int delete(String id) {
		return supDao.delete(id);
	}

	@Override
	public void update(SupplierVO supVO) {
		if (!supService.uniqueNameCheck(supVO)) {
			throw new BusinessException("已经存在的机构名称");
		}
		supDao.update(supVO);
	}

}
