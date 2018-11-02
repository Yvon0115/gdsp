package com.gdsp.platform.workflow.dao;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.BussinessFormVO;

/**
 * 表单Dao
 * @author wxl
 *
 */
@MBDao
public interface IBussinessFormDao {

	/**
	 * 添加表单
	 * @param bussinessFormVO
	 */
	void insert(BussinessFormVO bussinessFormVO);

	BussinessFormVO queryFormVariableValueByFormId(@Param("formId")String formId);

//	int queryFormVariableValueByDepIdAndFormId(@Param("depId")String deploymentId, @Param("formId")String formid);

	BussinessFormVO queryFormVariableValuerByDepidAndFormid(@Param("depId")String deploymentId, @Param("formid")String formid);

	/**
	 * 更新下载地址
	 * @param id
	 * @param url
	 */
	void updateDownloadUrl(@Param("id")String id,@Param("downloadurl")String url);

	/**
	 * 更新状态
	 * @param id
	 * @param status
	 */
	void updateBusinessStatus(@Param("formId")String formId, @Param("status")int status);

	/**
	 * 更新
	 * @param bussinessFormVO
	 */
	void update(@Param("bussinessFormVO")BussinessFormVO bussinessFormVO);

	/**
	 * 根据proInstid查询单据
	 * @param processInstanceId
	 * @return
	 */
	BussinessFormVO queryFormIdByInstId(@Param("proceInstId")String processInstanceId);

}