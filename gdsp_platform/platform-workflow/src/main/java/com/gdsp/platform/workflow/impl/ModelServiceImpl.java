package com.gdsp.platform.workflow.impl;

import java.util.List;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.platform.workflow.service.IApprAuthorityService;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.IModeService;
import com.gdsp.platform.workflow.service.INodeInfoService;
import com.gdsp.platform.workflow.service.ITimerTaskService;

@Service
@Transactional(readOnly = true)
public class ModelServiceImpl implements IModeService {

	private static final Logger logger = LoggerFactory.getLogger(ModelServiceImpl.class);
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	IDeploymentService deploymentService;
	@Autowired
	INodeInfoService nodeInfoService;
	@Autowired
	IApprAuthorityService apprAuthorityService;
	@Autowired
	ITimerTaskService timerTaskService;

	@Transactional
	@Override
	public void save(String key, String name, String descriptor) {
		try{
		List<Model> list = repositoryService.createModelQuery().modelKey(key).modelName(name).orderByModelVersion().asc().list();
        Integer version = 1;
        //设置model版本号
        for (Model model : list) {
            version = model.getVersion()+1;
        }
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.set("stencilset", stencilSetNode);
		Model modelData = repositoryService.newModel();
		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		descriptor = StringUtils.defaultString(descriptor);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, descriptor);
		modelData.setMetaInfo(modelObjectNode.toString());
		modelData.setName(name);
		modelData.setKey(StringUtils.defaultString(key));
		modelData.setVersion(version);
		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new BusinessException("流程编码异常！", e);
		}
	}


	// private void deleteNodeInfo(String id) {
	// DeploymentVO deploymentVO = deploymentService
	// .findDeploymentById(id);
	// if (deploymentVO != null) {
	// String deploymentId = deploymentService.findDeploymentById(
	// id).getDeployId();
	// if (StringUtils.isNotBlank(deploymentId)) {
	// repositoryService.deleteDeployment(deploymentId, true);
	// // 删除相关表信息
	// List<NodeInfoVO> nodeinfoList = nodeInfoService
	// .findNodeInfoByDeploymentId(id);
	// if (nodeinfoList != null && nodeinfoList.size() > 0) {
	// String[] ids = new String[nodeinfoList.size()];
	// for (int j = 0; j < nodeinfoList.size(); j++) {
	// NodeInfoVO nodeInfoVO = (NodeInfoVO) nodeinfoList
	// .get(j);
	// ids[j] = nodeInfoVO.getId();
	// }
	// apprAuthorityService
	// .deleteApprAuthorityByNodeInfoIDs(ids);
	// }
	// nodeInfoService.deleteNodeInfoByDeploymentId(id);
	// timerTaskService
	// .deleteTimerTaskByDeploymentId(id);
	// }
	// }
	// }
}
