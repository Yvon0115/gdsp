/*
 * 类名: ActivitiController
 * 创建人: sun    
 * 创建时间: 2015-5-26
 */
package com.gdsp.platform.workflow.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.workflow.model.BussinessFormVO;
import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.model.FormProcinstRltVO;
import com.gdsp.platform.workflow.model.NodeInfoVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.service.IApprAuthorityService;
import com.gdsp.platform.workflow.service.IBussinessFormService;
import com.gdsp.platform.workflow.service.ICategoryService;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.IFormProcinstRltService;
import com.gdsp.platform.workflow.service.INodeInfoService;
import com.gdsp.platform.workflow.service.IProcessHistoryService;
import com.gdsp.platform.workflow.service.IProcessService;
import com.gdsp.platform.workflow.service.ITimerTaskService;
import com.gdsp.platform.workflow.utils.WorkflowConst.WorkFlowStatus;

/**
 * 流程管理控制器
 * @author sun
 */
@Controller
@RequestMapping("workflow/process")
public class ProcessController {

    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);
    @Autowired
    protected RepositoryService   repositoryService;
    @Autowired
    protected RuntimeService      runtimeService;
    @Autowired
    protected TaskService         taskService;
    @Autowired
    protected HistoryService      historyService;
    @Autowired
    ProcessEngineConfiguration    processEngineConfiguration;
    @Autowired
    ProcessEngineFactoryBean      processEngine;
    @Autowired
    protected INodeInfoService    nodeInfoService;
    @Autowired
    protected ICategoryService    categoryService;
    @Autowired
    private IBussinessFormService       bussinessFormService;
    @Autowired
    private IDeploymentService    deploymentService;
    @Autowired
    private IApprAuthorityService apprAuthorityService;
    @Autowired
    ITimerTaskService             timerTaskService;
    @Autowired
    IProcessService               processService;
    @Autowired
    IProcessHistoryService        processHistoryService;
    @Autowired
    private IFormProcinstRltService  formProcinstRltService;
    /**
     * 流程列表
     * @return 跳转页面
     */
    @RequestMapping("/list.d")
    public String ProcessList(Model model, Condition condition, Pageable pageable) {
        Page<DeploymentVO> page = deploymentService.queryDeploymentByCondition(condition, pageable, null);
        model.addAttribute("pds", page);
//        Sorter sort = new Sorter(Direction.ASC, "innercode");
//        Condition categoryCondition = new Condition();
        //假设流程娄型的父结点的innnercode是0001
//        String innercode = "0";
//        categoryCondition.addExpression("innercode", innercode, "like");
//        model.addAttribute("nodes", categoryService.queryCategoryListByCondition(categoryCondition, sort));
        return "process/list";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String ProcessListData(Model model, Condition condition, Pageable pageable, String categoryCode) {
        if (StringUtils.isNotEmpty(categoryCode)) {
            condition.addExpression("c.id", categoryCode, "=");
        }
        Page<DeploymentVO> page = deploymentService.queryDeploymentByCondition(condition, pageable, null);
        model.addAttribute("pds", page);
        return "process/list-data";
    }

    /**
     * 启动流程实例
     */
    @RequestMapping(value = "start.d", method = RequestMethod.GET)
    @ResponseBody
    public Object startWorkflow(@RequestParam("id") String deploymentId, String formid, String leaveDay,
            RedirectAttributes redirectAttributes) {
        try {
            Map<String, Object> variableMap = new HashMap<String, Object>();
            variableMap.put("leaveDay", leaveDay);
            processService.start(deploymentId, formid, variableMap);
            return AjaxResult.SUCCESS;
        } catch (ActivitiException e) {
            if (e.getMessage().indexOf("no processes deployed with key") != -1) {
                logger.warn("没有部署流程!", e);
                return new AjaxResult(AjaxResult.STATUS_ERROR, "没有部署流程，请<重新部署流程>", null, false);
            } else {
                logger.error("启动流程失败：", e);
                return new AjaxResult(AjaxResult.STATUS_ERROR, "系统内部错误", null, false);
            }
        } catch (Exception e) {
            logger.error("启动流程失败：", e);
            return new AjaxResult(AjaxResult.STATUS_ERROR, "系统内部错误", null, false);
        }
    }
    
    
    
    //测试 开启流程
    @RequestMapping(value = "teststart.d", method = RequestMethod.POST)
    @ResponseBody
    public Object teststartWorkflow(Model model,String deploymentId,String formid,Map<String, Object> variableMap) {
    	try {
    
    		boolean flag=processService.startProcess(deploymentId, formid, variableMap);
    		if(flag){
    			return new AjaxResult(AjaxResult.STATUS_SUCCESS, "发起成功");
    		}
    		return new AjaxResult(AjaxResult.STATUS_ERROR, "参数缺失，请核对参数");
    		
    	} catch (ActivitiException e) {
    		if (e.getMessage().indexOf("no processes deployed with key") != -1) {
    			logger.warn("没有部署流程!", e);
    			return new AjaxResult(AjaxResult.STATUS_ERROR, "没有部署流程，请<重新部署流程>", null, false);
    		} else {
    			logger.error("启动流程失败：", e);
    			return new AjaxResult(AjaxResult.STATUS_ERROR, "系统内部错误", null, false);
    		}
    	} catch (Exception e) {
    		logger.error("启动流程失败：", e);
    		return new AjaxResult(AjaxResult.STATUS_ERROR, "系统内部错误", null, false);
    	}
    }

    /**
     * 完成任务-->任务办理
     * @param taskId 任务ID
     * @param result 审批意见
     * @param options 附加意见
     * @param formId 表单ID
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "complete.d")
    @ResponseBody
    public Object complete(@RequestParam("taskId") String taskId, @RequestParam("result") String result, String options, String formid, RedirectAttributes redirectAttributes){
        processService.complete(taskId, result, options, formid);
        return AjaxResult.SUCCESS;
    }

    /**
     * 签收任务
     */
    @RequestMapping(value = "task/claim/{id}")
    public String claim(@PathVariable("id") String taskId, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            taskService.claim(taskId, "张三");
            redirectAttributes.addFlashAttribute("message", "任务已签收,测试用例默认签收张三");
        } catch (ActivitiTaskAlreadyClaimedException e) {
        	logger.error("任务已经被认领！",e);
            redirectAttributes.addFlashAttribute("message", "任务已经被认领！");
        } catch (ActivitiObjectNotFoundException e) {
        	logger.error("被认领的任务不存在！",e);
            redirectAttributes.addFlashAttribute("message", "被认领的任务不存在！");
        }
        return "redirect:/process/list/task";
    }

    @RequestMapping(value = "picture.d")
    public String getPicture(String procInsId, HttpServletResponse response, Model model, Pageable pageable){
        model.addAttribute("actName", procInsId);
        Sorter sort = new Sorter(Direction.ASC, "createtime");
        Page<ProcessHistoryVO> hdetail = processHistoryService.queryProcessHistoryByInsId(procInsId, sort, pageable);
        model.addAttribute("hdetail", hdetail);
        return "monitor/picture";
    }
    
    /**
     * 获取带跟踪的图片(仅图片)
     * @param formId
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getPicture.d")
    public String getResourcePicture(String formId, HttpServletResponse response, Model model){
        //这里调用接口通过formid查询procInsId
        BussinessFormVO bussinessFormVO = bussinessFormService.queryFormVariableValueByFormId(formId);
        String procInsId = null;
        if(null != bussinessFormVO){
            List<FormProcinstRltVO> formProcinstRltVOs = formProcinstRltService.queryFormProcinstRltByBusiFormId(bussinessFormVO.getId());
            FormProcinstRltVO vo=formProcinstRltVOs.get(0);
            for (int i = 1; i < formProcinstRltVOs.size(); i++) {
                if(formProcinstRltVOs.get(i).getVersion()>vo.getVersion()){
                    vo=formProcinstRltVOs.get(i);
                }
            }
            procInsId = vo.getProceinstId();
        }
        model.addAttribute("actName", procInsId);
        return "monitor/resourcePicture";
    }

    /**
     * 读取带跟踪的图片
     * @throws IOException 
     */
    @RequestMapping(value = "image.d")
    public void readResource(String procInsId, HttpServletResponse response, Model model) throws IOException{
        //ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).singleResult();
        List<Task> list = taskService.createTaskQuery().processInstanceId(procInsId).list();
        List<String> activeActivityIds = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            activeActivityIds.addAll(runtimeService.getActiveActivityIds(list.get(i).getExecutionId()));
        }
        BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        InputStream is = null;
        if (activeActivityIds.size() > 0) {
            is = diagramGenerator.generateDiagram(bpmnModel, "png",
                    activeActivityIds, Collections.<String> emptyList(),
                    processEngineConfiguration.getActivityFontName(),
                    processEngineConfiguration.getLabelFontName(),
                    processEngineConfiguration.getClassLoader(), 1.0);
        } else {
            is = diagramGenerator.generateDiagram(bpmnModel, "png",
                    processEngineConfiguration.getActivityFontName(),
                    processEngineConfiguration.getLabelFontName(),
                    ProcessController.class.getClassLoader());
        }
        /*
         * Command<InputStream> cmd = new ProcessDefinitionDiagramCmd(
         * processInstance.getProcessDefinitionId(),activeActivityIds);
         * InputStream is =
         * processEngine.getProcessEngineConfiguration().getManagementService
         * ().executeCommand(cmd); response.setContentType("image/png");
         */
        response.setContentType("image/png");
        IOUtils.copy(is, response.getOutputStream());
    }

    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "processdefinition/update/{state}/{processDefinitionId}")
    public String updateState(@PathVariable("state") String state, @PathVariable("processDefinitionId") String processDefinitionId,
            RedirectAttributes redirectAttributes) {
        if (state.equals("active")) {
            redirectAttributes.addFlashAttribute("message", "已激活ID为["
                    + processDefinitionId + "]的流程定义。");
            repositoryService.activateProcessDefinitionById(
                    processDefinitionId, true, null);
        } else if (state.equals("suspend")) {
            repositoryService.suspendProcessDefinitionById(processDefinitionId,
                    true, null);
            redirectAttributes.addFlashAttribute("message", "已挂起ID为["
                    + processDefinitionId + "]的流程定义。");
        }
        return "redirect:/workflow/processList";
    }

    /**
     * 删除部署的流程，不级联删除 ---> 只能删除没有历史并且没有任务在执行的流程 
     * @author lt
     * @date 2018-2-27
     * @param deploymentId 流程部署ID
     */
    @RequestMapping("/delete.d")
    @ResponseBody
    @ViewWrapper(wrapped = false)
    public Object delete(RedirectAttributes redirectAttributes, String... id) {
        try {
            for (int i = 0; i < id.length; i++) {
                DeploymentVO deploymentVO = deploymentService.findDeploymentById(id[i]);
                if (deploymentVO != null) {
                	//查找历史
                	Condition condition = new Condition();
                	condition.addExpression("deploymentid", deploymentVO.getId());
                	List<ProcessHistoryVO> listprcVO = processHistoryService.queryProcessHistoryListByCondition(condition, null);
                	if(listprcVO.size()>0){
                		return new AjaxResult(AjaxResult.STATUS_ERROR, "该流程存在历史或正在执行，不能删除", null, false);
                	}else{
	                    String deploymentId = deploymentService.findDeploymentById(id[i]).getDeployId();
	                    if (StringUtils.isNotBlank(deploymentId)) {
	                        repositoryService.deleteDeployment(deploymentId);
	                        // 删除相关表信息
	                        List<NodeInfoVO> nodeinfoList = nodeInfoService.findNodeInfoByDeploymentId(id[i]);
	                        if (nodeinfoList != null && nodeinfoList.size() > 0) {
	                            String[] ids = new String[nodeinfoList.size()];
	                            for (int j = 0; j < nodeinfoList.size(); j++) {
	                                NodeInfoVO nodeInfoVO = (NodeInfoVO) nodeinfoList.get(j);
	                                ids[j] = nodeInfoVO.getId();
	                            }
	                            apprAuthorityService.deleteApprAuthorityByNodeInfoIDs(ids);
	                        }
	                        nodeInfoService.deleteNodeInfoByDeploymentId(id[i]);
	                        timerTaskService.deleteTimerTaskByDeploymentId(id[i]);
	                    }
	                    deploymentService.deleteDeployment(id);
	                }
	            }
            }
            return AjaxResult.SUCCESS;
        } catch (RuntimeException se) {
        	logger.error("该流程有正在执行的实例，不能删除",se);
            return new AjaxResult(AjaxResult.STATUS_ERROR, "该流程有正在执行的实例，不能删除", null, false);
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            return new AjaxResult(AjaxResult.STATUS_ERROR, "未知错误", null, false);
        }
    }

    /**
     * 启用流程
     * @param deploymentId
     *            流程部署ID
     */
    @RequestMapping("/setup.d")
    @ResponseBody
    @ViewWrapper(wrapped = false)
    public Object setUp(String... id) {
        this.deploymentService.setUpDeployment(id);
        return AjaxResult.SUCCESS;
    }

    /**
     * 停用流程
     * @param deploymentId
     *            流程部署ID
     */
    @RequestMapping("/stop.d")
    @ResponseBody
    @ViewWrapper(wrapped = false)
    public Object stop(String... id) {
        this.deploymentService.stopDeployment(id);
        return AjaxResult.SUCCESS;
    }

    /**************************************************************************
     * 测试用 流程查询
     * @param model
     * @param condition
     * @param pageable
     * @return
     */
    @RequestMapping("/listPro.d")
    public String ProcList(Model model, Condition condition, Pageable pageable) {
    	//员工发起流程的页面仅显示已部署的流程
    	condition.addExpression("state", WorkFlowStatus.ENABLED);
        Page<DeploymentVO> page = deploymentService.queryDeploymentByCondition(condition, pageable, null);
        model.addAttribute("pds", page);
//        Sorter sort = new Sorter(Direction.ASC, "innercode");
//        Condition categoryCondition = new Condition();
        //假设流程娄型的父结点的innnercode是0001
//        String innercode = "0001";
//        categoryCondition.addExpression("innercode", innercode, "like");
//        model.addAttribute("nodes", categoryService.queryCategoryListByCondition(categoryCondition, sort));
        return "process/showlist";
    }

    @RequestMapping("/listProData.d")
    @ViewWrapper(wrapped = false)
    public String ProcListData(Model model, Condition condition, Pageable pageable) {
    	//员工发起流程的页面仅显示已部署的流程
    	condition.addExpression("state", WorkFlowStatus.ENABLED);
        Page<DeploymentVO> page = deploymentService.queryDeploymentByCondition(condition, pageable, null);
        model.addAttribute("pds", page);
        return "process/showlist-data";
    }
    /*************************************************************************/
}
