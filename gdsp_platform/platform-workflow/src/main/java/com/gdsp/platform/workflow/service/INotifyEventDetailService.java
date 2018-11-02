package com.gdsp.platform.workflow.service;

import java.util.List;

import com.gdsp.platform.workflow.model.NotifyEventDetailVO;

public interface INotifyEventDetailService {

	/**
	 * 注册事件通知url
	 * @param deploymentCode
	 * @param url
	 * @return
	 */
	Boolean registNotifyEvent(String deploymentCode, String url);

	/**
	 * 根据nodeInfoIdc查询NotifyEventDetailVO
	 * @param nfId
	 * @return
	 */
	List<NotifyEventDetailVO> queryNotifyEventDetailByNodeinfoId(String nfId);

}
