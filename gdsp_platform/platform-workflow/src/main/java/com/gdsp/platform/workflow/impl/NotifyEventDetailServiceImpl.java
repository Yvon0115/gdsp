package com.gdsp.platform.workflow.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.dao.INotifyEventDetailDao;
import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.model.EventTypeVO;
import com.gdsp.platform.workflow.model.NodeInfoVO;
import com.gdsp.platform.workflow.model.NotifyEventDetailVO;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.IEventTypeService;
import com.gdsp.platform.workflow.service.INodeInfoService;
import com.gdsp.platform.workflow.service.INotifyEventDetailService;

@Service
public class NotifyEventDetailServiceImpl implements INotifyEventDetailService{

	@Autowired
	private INotifyEventDetailDao notifyEventDetailDao ;
	@Autowired
	private IDeploymentService deploymentService;
	@Autowired
	private INodeInfoService nodeInfoService;
	@Autowired
	private IEventTypeService eventTypeService;
	

	@Override
	public Boolean registNotifyEvent(String deploymentCode, String url) {
		//获取通知事件com.gdsp.platform.workflow.helper.listener.NodifyTaskListener对应的id
		Condition cond1=new Condition();
        cond1.addExpression("EVENTINTERFACE", "com.gdsp.platform.workflow.helper.listener.NodifyTaskListener");
        List<EventTypeVO> register = eventTypeService.queryEventTypeListByCondition(cond1, null);
        EventTypeVO event=register.get(0);
        
		String deploymentId=deploymentService.findByDeploymentCode(deploymentCode).getId();
		//根据deploymentId查询节点信息
		List<NodeInfoVO> nodeInfos = nodeInfoService.findNodeInfoByDeploymentId(deploymentId);
		
		try {
       
        //遍历节点信息，如果节点的事件类型id==eventTypeVO.getId()，将事件类型id放到通知信息VO里
		
		for (NodeInfoVO nodeInfoVO : nodeInfos) {
			if(event.getId().equals(nodeInfoVO.getEventTypeId())){
				List<String> regUrlList = getRegisterUrlList(nodeInfoVO);
				if (!regUrlList.contains(url)) {
					NotifyEventDetailVO notifyEventDetailVO=new NotifyEventDetailVO();
					notifyEventDetailVO.setDeploymentId(deploymentId);
					notifyEventDetailVO.setNodeinfoId(nodeInfoVO.getId());
					notifyEventDetailVO.setNotifyUrl(url);
					notifyEventDetailVO.setEventTypeId(event.getId());
					notifyEventDetailVO.setId(UUIDUtils.getRandomUUID());
					notifyEventDetailDao.insertVO(notifyEventDetailVO);
				}
				
			}
		}
//		notifyEventDetailDao.batchInsert(notifyEventDetailVOs);
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	private List<String> getRegisterUrlList(NodeInfoVO nodeInfoVO) {
		List<NotifyEventDetailVO> nedVOs = notifyEventDetailDao.queryNotifyEventDetailByNodeinfoId(nodeInfoVO.getId());
		List<String> regUrlList = new ArrayList<String>();
		for (NotifyEventDetailVO notifyEventDetailVO : nedVOs) {
			regUrlList.add(notifyEventDetailVO.getNotifyUrl());
		}
		return regUrlList;
	}

	@Override
	public List<NotifyEventDetailVO> queryNotifyEventDetailByNodeinfoId(String nfId) {
		return notifyEventDetailDao.queryNotifyEventDetailByNodeinfoId(nfId);
	}

}
