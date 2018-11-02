package com.gdsp.platform.grant.supplier.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.grant.supplier.model.SupplierVO;

@MBDao
public interface ISupplierDao extends ICrudDao<SupplierVO> {
	int insert(SupplierVO supVO);

	int update(SupplierVO supVO);

	int delete(String id);

	SupplierVO load(String id);

	List<SupplierVO> findAllSupList();

	List<SupplierVO> queryAllSupList();

	public int existSameNameSup(SupplierVO supVO);

	public int existSameSup(SupplierVO supVO);
	
	public List<SupplierVO>querySelfAndChildSupListByInnercode(@Param("innercode")String innercode);
}
