package com.gdsp.platform.workflow.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdsp.platform.workflow.dao.IFormProcinstRltDao;
import com.gdsp.platform.workflow.model.FormProcinstRltVO;
import com.gdsp.platform.workflow.service.IFormProcinstRltService;

/**
 * 业务与流程关联接口实现
 * @author g
 *
 */
@Service
public class FormProcinstRltServiceImpl implements IFormProcinstRltService{
	
	@Autowired
	private IFormProcinstRltDao formProcinstRltDao;

	/**
	 * 查询当前最大版本号
	 */
	@Override
	public int queryMaxVersion() {
		
		return formProcinstRltDao.queryMaxVersion();
	}

	/**添加关联关系
	 * 
	 */
	@Override
	public void save(FormProcinstRltVO fp) {
		formProcinstRltDao.insert(fp);
		
	}

	/**
	 * 根据业务主键查询关联关系
	 */
	@Override
	public List<FormProcinstRltVO> queryFormProcinstRltByBusiFormId(String busformid) {
		return formProcinstRltDao.queryFormProcinstRltByBusiFormId(busformid);
	}

	/**
	 * 根据procInstId查询管理关系
	 */
	@Override
	public FormProcinstRltVO queryFormProceRltByProceinstID(String proceinstId) {
		// TODO Auto-generated method stub
		return formProcinstRltDao.queryFormProceRltByProceinstID(proceinstId);
	}

}
