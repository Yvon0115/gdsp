package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.FormProcinstRltVO;

/**
 * 表单与流程实例关联Dao
 * @author wxl
 *
 */
@MBDao
public interface IFormProcinstRltDao {

	/**
	 * 查询最大版本号
	 * @return
	 */
	int queryMaxVersion();

	/**
	 * 添加业务key与procinstId关联关系
	 * @param fp
	 */
	void insert(FormProcinstRltVO fp);

	/**
	 * 根据业务主键查询FormProcinstRltVO
	 * @param id
	 * @return List<FormProcinstRltVO>
	 */
	List<FormProcinstRltVO> queryFormProcinstRltByBusiFormId(@Param("busFormId")String busFormId);

	/**
	 * 根据流程id查询关联关系
	 * @param proceinstId
	 * @return
	 */
	FormProcinstRltVO queryFormProceRltByProceinstID(@Param("proceinstId")String proceinstId);

}
