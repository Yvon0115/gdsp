package com.gdsp.platform.workflow.service;

import java.util.List;

import com.gdsp.platform.workflow.model.FormProcinstRltVO;

public interface IFormProcinstRltService {

	/**
	 * 查询最大版办号
	 * @return
	 */
	int queryMaxVersion();

	/**
	 * 添加业务key与procinstId关联关系
	 * @param fp
	 */
	void save(FormProcinstRltVO fp);

	/**
	 * 根据业务主键查询FormProcinstRltVO
	 * @param id
	 * @return
	 */
	List<FormProcinstRltVO> queryFormProcinstRltByBusiFormId(String busformid);

	/**
	 * 根据流程id查询关联关系
	 * @param proceinstId
	 * @return
	 */
	FormProcinstRltVO queryFormProceRltByProceinstID(String proceinstId);


}
