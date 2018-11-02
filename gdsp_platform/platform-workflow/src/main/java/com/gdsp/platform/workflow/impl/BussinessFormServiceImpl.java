package com.gdsp.platform.workflow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.platform.workflow.dao.IBussinessFormDao;
import com.gdsp.platform.workflow.model.BussinessFormVO;
import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.service.IBussinessFormService;
import com.gdsp.platform.workflow.service.IDeploymentService;

/**
 * 表单接口实现
 * @author wxl
 *
 */
@Service
public class BussinessFormServiceImpl implements IBussinessFormService{

	@Autowired
	private IBussinessFormDao bussinessFormDao;
	@Autowired
	private IDeploymentService deploymentService;
	
	/**
	 * 添加表单
	 */
	@Override
	public void save(BussinessFormVO bussinessFormVO) {

		bussinessFormDao.insert(bussinessFormVO);
	}

	/**
	 * 根据formid查询表单
	 */
	@Override
	public BussinessFormVO queryFormVariableValueByFormId(String formId) {
		
		return bussinessFormDao.queryFormVariableValueByFormId(formId);
	}

	/**
	 * 保存表单
	 */
	@Override
	public void saveBusinessForm(String deploymentCode, String formId) {
		
		DeploymentVO deploymentVO = deploymentService.findByDeploymentCode(deploymentCode);
		BussinessFormVO fvbVO = bussinessFormDao.queryFormVariableValuerByDepidAndFormid(deploymentVO.getId(), formId);
		if(null ==fvbVO){
			fvbVO=new BussinessFormVO();
			fvbVO.setDeploymentId(deploymentVO.getId());
			fvbVO.setFormId(formId);
			fvbVO.setStatus(IBussinessFormService.PROCESS_STATUS_NOSTARTED);
			bussinessFormDao.insert(fvbVO);
		}else {
			fvbVO.setStatus(IBussinessFormService.PROCESS_STATUS_NOSTARTED);
			bussinessFormDao.updateBusinessStatus(formId,fvbVO.getStatus());
		}
		
	}

//	@Override
//	public boolean isExistFormId(String deploymentId, String formid) {
//		int count=formVariableValueDao.queryFormVariableValueByDepIdAndFormId(deploymentId,formid);
//		if(count>0){
//			return true;
//		}else{
//			return false;
//		}
//	}

	/**
	 * 更新表单
	 */
	@Override
	public void update(BussinessFormVO bussinessFormVO) {

		bussinessFormDao.update(bussinessFormVO);
	}

	/**
	 * 根据formi与deploymentid查询表单
	 */
	@Override
	public BussinessFormVO queryFormVariableValuerByDepidAndFormid(String deploymentId, String formid) {
		// TODO Auto-generated method stub
		return bussinessFormDao.queryFormVariableValuerByDepidAndFormid(deploymentId,formid);
	}

	/**
	 * 保存附件url
	 */
	@Override
	public void saveAttachmentUrl(String deploymentCode, String formid,String url) {
		DeploymentVO deploymentVO = deploymentService.findByDeploymentCode(deploymentCode);
		BussinessFormVO fvbVO = bussinessFormDao.queryFormVariableValuerByDepidAndFormid(deploymentVO.getId(), formid);
		if(null==fvbVO){
			BussinessFormVO bfVO=new BussinessFormVO();
			bfVO.setDeploymentId(deploymentVO.getId());
			bfVO.setFormId(formid);
			bfVO.setDownloadurl(url);
			bfVO.setId(UUIDUtils.getRandomUUID());
			bussinessFormDao.insert(bfVO);
		}else{
			fvbVO.setDownloadurl(url);
			bussinessFormDao.updateDownloadUrl(fvbVO.getId(),url);
		}
		
	}


	/**
	 *更新表单状态
	 */
	@Override
	public void updateBusinessStatus(String formId, int status) {
		bussinessFormDao.updateBusinessStatus(formId,status);
		
	}

	/**
	 * 根据proInstid查询单据
	 */
	@Override
	public BussinessFormVO queryFormIdByInstId(String processInstanceId) {
		// TODO Auto-generated method stub
		return bussinessFormDao.queryFormIdByInstId(processInstanceId);
	}
	
	

}
