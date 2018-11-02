package com.gdsp.platform.workflow.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdsp.dev.core.model.param.PageSerImpl;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.auth.service.IUserGroupRltService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.role.service.IRoleQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.usergroup.model.UserGroupVO;
import com.gdsp.platform.grant.usergroup.service.IUserGroupService;
import com.gdsp.platform.workflow.model.ApprAuthorityVO;
import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.model.EventTypeVO;
import com.gdsp.platform.workflow.model.EventVO;
import com.gdsp.platform.workflow.model.FormTypeVO;
import com.gdsp.platform.workflow.model.FormVariableVO;
import com.gdsp.platform.workflow.model.NodeInfoVO;
import com.gdsp.platform.workflow.model.TimerTaskVO;
import com.gdsp.platform.workflow.service.IApprAuthorityService;
import com.gdsp.platform.workflow.service.ICategoryService;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.IEventService;
import com.gdsp.platform.workflow.service.IEventTypeService;
import com.gdsp.platform.workflow.service.IFormTypeService;
import com.gdsp.platform.workflow.service.IFormVariableService;
import com.gdsp.platform.workflow.service.IModeService;
import com.gdsp.platform.workflow.service.INodeInfoService;
import com.gdsp.platform.workflow.service.ITimerTaskService;
import com.gdsp.platform.workflow.utils.WorkflowConst;
import com.gdsp.platform.workflow.utils.WorkflowConst.WorkFlowStatus;

/**
 * 流程定义相关 <br/>
 * 流程模型控制器
 * @author sun
 */
@Controller
@RequestMapping("/workflow/model")
public class ModelController {

    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);
    @Autowired
    RepositoryService     repositoryService;
    @Autowired
    IApprAuthorityService apprAuthorityService;
    @Autowired
    ITimerTaskService     timerTaskService;
    @Autowired
    IDeploymentService    deploymentService;
    @Autowired
    INodeInfoService      nodeInfoService;
    @Autowired
    ICategoryService      categoryService;
    @Autowired
    IUserQueryPubService  userPubService;
    @Autowired
    IUserGroupService     userGroupService;
//    @Autowired
//    IRoleService          roleService;
    @Autowired
    private IRoleQueryPubService roleQueryPubService;
    @Autowired
    IOrgQueryPubService   orgQueryPubService;
    @Autowired
    IUserGroupRltService  userGroupRltService;
    @Autowired
    IEventService         eventService;
    @Autowired
    IFormTypeService      formTypeService;
    @Autowired
    IFormVariableService  formVariableService;
    @Autowired
    IModeService          modeService;
    @Autowired
    private IEventTypeService eventTypeService;

    /**
     * 模型列表
     * @param model
     * @param pageable 分页参数
     * @return 流程列表界面
     * @throws ParseException 
     */
    @RequestMapping("/list.d")
    public String modelList(org.springframework.ui.Model model, Pageable pageable) throws ParseException {
        List<Model> list = repositoryService.createModelQuery().list();
        Page<Model> page = new PageSerImpl<>(list, pageable, 30);
        model.addAttribute("models", page);
        return "model/list";
    }

    /**
     * 模型列表数据
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping("/listData.d")
    public String modelListData(org.springframework.ui.Model model, Pageable pageable) {
        List<Model> list = repositoryService.createModelQuery().list();
        Page<Model> page = new PageSerImpl<>(list, pageable, 30);
        model.addAttribute("models", page);
        return "model/list-data";
    }

    /**
     * 创建流程定义
     * @return
     */
    @RequestMapping("/create.d")
    public String create(String categoryCode, RedirectAttributes redirectAttributes) {
        String categoryName = categoryService.findCategoryById(categoryCode).getCategoryName();
        redirectAttributes.addAttribute("category", "'" + categoryCode + "'");
        redirectAttributes.addAttribute("categoryName", categoryName);
        return "redirect:/gdsp/page/designer/index.jsp";
    }

    @RequestMapping("/importZip.d")
    @ViewWrapper(wrapped = false)
    public String importUser() {
        return "model/importZip";
    }

    /**
     * 通过zip压缩包的方式部署流程
     * @param file 流程文件
     * @return
     */
    @RequestMapping("/uploadDeploy.d")
    public String deploy(@RequestParam(value = "file", required = false) MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            InputStream fileInputStream = file.getInputStream();
            Deployment deployment = null;
            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            }
            System.out.println("流程部署成功，部署ID为：" + deployment.getId());
        } catch (Exception e) {
        	logger.error("部署失败",e);
        }
        return "redirect:model/list";
    }

    /**
     * 保存流程定义
     * @throws IOException 
     */
    @RequestMapping(value = "save.d", method = RequestMethod.POST)
    @Transactional
    public void save(@RequestParam("processName") String name,
            @RequestParam("processDescriptor") String descriptor,
            @RequestParam("key") String key,
            @RequestParam("jsonPeroperties") String jsonPeroperties,
            HttpServletResponse response) throws IOException {
        jsonPeroperties = jsonPeroperties.replaceAll("\"null\"", "null").replaceAll("'null'", "null");
        jsonPeroperties = "{" + jsonPeroperties + "}";
        logger.debug("save process : " + jsonPeroperties);
        DeploymentVO deployVo = getDeployVO(jsonPeroperties);
        //保存模板
        modeService.save(key, name, descriptor); 
        //保存流程部署信息
        if(null == deployVo.getId()){
        	deployVo.setVersion(0);
			deploymentService.saveDeployment(deployVo);
        }else{
//            Model model = repositoryService.createModelQuery().modelKey(key).modelName(name).latestVersion()
//					.singleResult();
            DeploymentVO deployment = deploymentService.findDeploymentById(deployVo.getId());
            deployVo.setId(null);
            deployVo.setVersion(deployment.getVersion() + 1);
            deploymentService.saveDeployment(deployVo);
        }
        response.getWriter().print(true);
    }

    private DeploymentVO getDeployVO(String processDef) {
        JSONObject jsonObject = JSONObject.fromObject(processDef);
        String processID = jsonObject.getString("processID");
        String processName = jsonObject.getString("processName");
        String flowcategory = jsonObject.getString("flowcategory");
        String form = jsonObject.getString("form");
        String documentation = jsonObject.getString("documentation");
        String id = jsonObject.getString("tableID");
        id = StringUtils.isBlank(id) || "null".equals(id) ? null : id;
        documentation = StringUtils.isBlank(documentation) || "null".equals(documentation) ? null : documentation;
        form = StringUtils.isBlank(form) || "null".equals(form) ? null : form;
        flowcategory = StringUtils.isBlank(flowcategory) || "null".equals(flowcategory) ? null : flowcategory;
        processName = StringUtils.isBlank(processName) || "null".equals(processName) ? null : processName;
        processID = StringUtils.isBlank(processID) || "null".equals(processID) ? null : processID;
        
        DeploymentVO deploymentVO = new DeploymentVO();
        deploymentVO.setId(id);
        deploymentVO.setDeploymentCode(processID);
        deploymentVO.setDeploymentName(processName);
        deploymentVO.setFormTypeId(form);
        deploymentVO.setState(WorkflowConst.WorkFlowStatus.DISABLED);
        deploymentVO.setCategoryId(flowcategory);
        deploymentVO.setMemo(documentation);
        deploymentVO.setMetaInfo(processDef);
        return deploymentVO;
    }

    /**
     * 根据流程定义部署流程
     */
    @RequestMapping("/deploy.d")
    @ResponseBody
    public Object deploy(String id, RedirectAttributes redirectAttributes) {
        try {
            String modelId = getModelIdByDeploymentId(id);
            Model modelData = repositoryService.getModel(modelId);
            String meta = modelData.getMetaInfo();
            JSONObject jsonObject = JSONObject.fromObject(meta);
            String xml = (String) jsonObject.get("description");
            //ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            //byte[] bpmnBytes = null;
            //BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            //bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, xml).deploy();
            ProcessDefinition singleResult = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
            String procDefId = singleResult.getId();
            //更新部署ID到自定义的部署表                     
            DeploymentVO deploymentVO = deploymentService.findDeploymentById(id);
            deploymentVO.setState(WorkflowConst.WorkFlowStatus.ENABLED);
            deploymentVO.setDeployId(deployment.getId());
            deploymentVO.setProcDefId(procDefId);
            deploymentVO.setVersion(deploymentVO.getVersion() - 1);
            deploymentService.updateDeployment(deploymentVO);
            // 流程模板数据类型改动 oracle数据库类型匹配
            String dataBase = FileUtils.getFileIO("jdbc.dbtype", false);
            String metaInfo = null;
            if("oracle".equals(dataBase)){
                CLOB clob =  (CLOB) deploymentVO.getMetaInfo();
                metaInfo = clob.getSubString(1, (int)clob.length());
            }else if("mysql".equals(dataBase)){
                metaInfo = (String) deploymentVO.getMetaInfo();
            }
            saveTaskProperty(metaInfo, id);
            redirectAttributes.addFlashAttribute("message", "部署成功，部署ID=" + deployment.getId());
            //查询老流程并停用
            Condition con = new Condition();
            con.addExpression("deploymentCode", deploymentVO.getDeploymentCode());
            con.addExpression("state", WorkFlowStatus.ENABLED);
            List<DeploymentVO> list = deploymentService.queryDeploymentListByCondition(con, null);
            if(list.size()>0){
            	for(DeploymentVO depVo: list){
            		if(!depVo.getId().equals(deploymentVO.getId())){
            			deploymentService.stopDeployment(depVo.getId());
            		}
            	}
            }
        } catch (Exception e) {
            logger.error("根据模型部署流程失败：modelId={}", id, e);
            redirectAttributes.addFlashAttribute("根据模型部署流程失败：modelId={}", e);
            return new AjaxResult(AjaxResult.STATUS_ERROR, "部署失败！");
        }
        return new AjaxResult(AjaxResult.STATUS_SUCCESS,"部署成功！");
    }

    private void queryDeploymentListByCondition(Condition con, Object object) {
		// TODO Auto-generated method stub
		
	}

	private void saveTaskProperty(String jsonPeroperties, String deploymentId) {
        JSONObject jsonObject = JSONObject.fromObject(jsonPeroperties);
        String processID = jsonObject.getString("processID");
        JSONArray taskArray = jsonObject.getJSONArray("task");
        Iterator<?> it = taskArray.iterator();
        while (it.hasNext()) {
            JSONObject task = (JSONObject) it.next();
            String taskName = task.getString("taskName");
            String taskId = task.getString("taskId");
            //存储定时任务表
            TimerTaskVO timerTaskVO = new TimerTaskVO();
            timerTaskVO.setActId(taskId);
            if ("informPerson".equals(task.getString("dealMethod"))) {
                timerTaskVO.setExecType(WorkflowConst.DealMethod.INFORM_PERSON);
            } else if ("autoPass".equals(task.getString("dealMethod"))) {
                timerTaskVO.setExecType(WorkflowConst.DealMethod.AUTO_PASS);
            } else if ("autoFail".equals(task.getString("dealMethod"))) {
                timerTaskVO.setExecType(WorkflowConst.DealMethod.AUTO_FAIL);
            } else if ("autoReturn".equals(task.getString("dealMethod"))) {
                timerTaskVO.setExecType(WorkflowConst.DealMethod.AUTO_RETURN_SUPERIOR);
            } else if ("autoReturnPerson".equals(task.getString("dealMethod"))) {
                timerTaskVO.setExecType(WorkflowConst.DealMethod.AUTO_RETURN_PERSON);
            }
            timerTaskVO.setFormTypeId(task.getString("form"));
            if (!"startuser".equals(taskId) && "true".equals(task.getString("workingday"))) {
                timerTaskVO.setIsWorkdays(1);
            }
            timerTaskVO.setUnit(task.getString("unit"));
            if (!"startuser".equals(taskId) && task.getString("overtime") != null && !"".equals(task.getString("overtime"))&& !"null".equals(task.getString("overtime"))) {
                timerTaskVO.setLength(Integer.parseInt(task.getString("overtime")));
            }
            timerTaskVO.setDeploymentId(deploymentId);
            timerTaskService.saveTimerTask(timerTaskVO);
            //存储流程节点信息表
            NodeInfoVO nodeInfoVO = new NodeInfoVO();
            nodeInfoVO.setActId(taskId);
            nodeInfoVO.setActName(taskName);
            if(!StringUtils.isEmpty(task.getString("extendEvent"))&&!"null".equals(task.getString("extendEvent"))){
            	Condition condition=new Condition();
            	condition.addExpression("EVENTINTERFACE", task.getString("extendEvent"));
            	List<EventTypeVO> eventTypeVOs = eventTypeService.queryEventTypeListByCondition(condition, null);
            	if(eventTypeVOs.size()==1){
            		nodeInfoVO.setEventTypeId(eventTypeVOs.get(0).getId());
            	}
            }
//            nodeInfoVO.setEventTypeId(task.getString("extendEvent"));
            nodeInfoVO.setFormTypeId(task.getString("form"));
            nodeInfoVO.setMultiInsType(task.getString("multiInsRule"));
            if (!"startuser".equals(taskId)) {
                nodeInfoVO.setMultiInsValue(task.getInt("multiInsVal"));
            }
            nodeInfoVO.setProcDefKey(processID);
            //1-系统通知，2-邮件通知，3-两种通知
            if (!"startuser".equals(taskId)) {
                if (Boolean.valueOf(task.getString(WorkflowConst.XMLDefinition.KEY_EMAIL_INFORM))
                		&&Boolean.valueOf(task.getString(WorkflowConst.XMLDefinition.KEY_SYSTEM_INFORM))) {
                    nodeInfoVO.setNotice(WorkflowConst.InformDealPerson.SYSTEM_EMAIL_INFORM);
                } else if (Boolean.valueOf(task.getString(WorkflowConst.XMLDefinition.KEY_SYSTEM_INFORM))) {
                    nodeInfoVO.setNotice(WorkflowConst.InformDealPerson.SYSTEM_INFORM);
                } else if (Boolean.valueOf(task.getString(WorkflowConst.XMLDefinition.KEY_EMAIL_INFORM))) {
                    nodeInfoVO.setNotice(WorkflowConst.InformDealPerson.EMAIL_INFORM);
                }
            }
            nodeInfoVO.setTimerTaskId(timerTaskVO.getId());
            nodeInfoVO.setDeploymentId(deploymentId);
            nodeInfoService.saveNodeInfo(nodeInfoVO);
            //存储审批权限表
            JSONArray candidateArray = task.getJSONArray("candidateUsers");
            Iterator<?> candidateIt = candidateArray.iterator();
            while (candidateIt.hasNext()) {
                JSONObject candidate = (JSONObject) candidateIt.next();
                ApprAuthorityVO apprAuthorityVO = new ApprAuthorityVO();
                apprAuthorityVO.setApprType(candidate.getString("type"));
                apprAuthorityVO.setApprTypeCode(candidate.getString("code"));
                apprAuthorityVO.setApprTypeId(candidate.getString("id"));
                apprAuthorityVO.setApprTypeName(candidate.getString("name"));
                apprAuthorityVO.setNodeInfoId(nodeInfoVO.getId());
                apprAuthorityService.saveApprAuthority(apprAuthorityVO);
            }
        }
    }

    /**
     * 通过流程扩展表的ID获取模型表的最后一个版本ID
     * @param deploymentId
     * @return
     */
    private String getModelIdByDeploymentId(String deploymentId) {
        DeploymentVO deployment = deploymentService.findDeploymentById(deploymentId);
        Model model = repositoryService.createModelQuery().modelName(deployment.getDeploymentName())
                .modelKey(deployment.getDeploymentCode()).latestVersion().singleResult();
        return model.getId();
    }

    /**
     * 修改该流程定义
     */
    @RequestMapping("/edit.d")
    public String editProcessDef() {
        return "redirect:/gdsp/page/designer/edit.jsp";
    }

    /**
     * 修改该流程定义,跳转到修改页面
     * @param modelId 流程定义id
     * @param redirectAttributes 跳转传递参数
     * @return
     */
    @RequestMapping("/getXml.d")
    public String getProcessXml(String id, RedirectAttributes redirectAttributes) {
        String modelId = getModelIdByDeploymentId(id);
        redirectAttributes.addFlashAttribute("modelId", modelId);
        redirectAttributes.addFlashAttribute("tableID", id);
        return "redirect:/workflow/model/edit.d";
    }

    /**
     * 通过jquery Ajax方式返回指定流程定义id的xml定义
     * @param modelId 流程定义id
     * @param response 将xml描述放入response中
     * @throws IOException 
     * @throws Exception
     */
    @RequestMapping(value = "getXml.d", method = RequestMethod.POST)
    public void getProcessXml2(@RequestParam("modelId") String modelId, HttpServletResponse response) throws IOException{
        //String modelId = getModelIdByDeploymentId(deploymentId);
        Model modelData = repositoryService.getModel(modelId);
        //        CLOB clob =  modelData.getMetaInfo();
        //        String metaInfo = clob.getSubString(1, (int)clob.length());
        String meta = modelData.getMetaInfo();
        JSONObject jsonObject = JSONObject.fromObject(meta);
        String xml = (String) jsonObject.get("description");
        response.getWriter().write(xml);
    }

    /**
     * 通过jquery Ajax方式返回指定流程定义id的json定义
     * @param modelId 流程定义id
     * @param response 将json描述放入response中
     * @throws IOException 
     * @throws Exception
     */
    @RequestMapping(value = "getJson.d", method = RequestMethod.POST)
    public void getProcessJson(@RequestParam("modelId") String modelId, HttpServletResponse response) throws IOException{
        Model model = repositoryService.createModelQuery().modelId(modelId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(model.getKey())
                .latestVersion().singleResult();
        String json = null;
        DeploymentVO deploymentVO =null;
        //未部署流程通过部署编码查询
        if("".equals(processDefinition)||processDefinition==null){
        	deploymentVO = deploymentService.findByDeploymentCode(model.getKey());
        }else{
        	deploymentVO = deploymentService.findByProcDefId(processDefinition.getId());
        }
        /** -- 流程模板数据类型改动--<wqh 2016-01-08>-----*/
        //    	CLOB clob3 = (CLOB) deploymentVO.getMetaInfo();
        //    	String json = clob3.getSubString(1, (int)clob3.length());
        json = (String) deploymentVO.getMetaInfo();
        //String json = "{\"processName\":\"porcess\",\"processID\":\"P01\",\"form\":\"table2\",\"flowcategory\":\"C01\",\"task\":[{\"taskName\":\"审批1\",\"taskId\":\"T01\",\"form\":\"table1\",\"systeminform\":\"true\",\"emailinform\":\"null\",\"overtime\":\"20\",\"unit\":\"hour\",\"workingday\":\"true\",\"dealMethod\":\"autoFail\",\"extendEvent\":\"Event1\",\"multiInsRule\":\"null\",\"multiInsVal\":\"null\",\"candidateUsers\":[{\"type\":\"user\",\"id\":\"user5\",\"code\":\"U005\",\"name\":\"用户5\"},{\"type\":\"group\",\"id\":\"group3\",\"code\":\"G003\",\"name\":\"用户组3\"}]},{\"taskName\":\"审批2\",\"taskId\":\"T02\",\"form\":\"table2\",\"systeminfrom\":\"true\",\"emailinform\":\"null\",\"overtime\":\"30\",\"unit\":\"hour\",\"workingday\":\"true\",\"dealMethod\":\"autoReturn\",\"extendEvent\":\"Event2\",\"multiInsRule\":\"percentageAgree\",\"multiInsVal\":\"60\",\"candidateUsers\":[{\"type\":\"role\",\"id\":\"role5\",\"code\":\"R005\",\"name\":\"角色5\"},{\"type\":\"role\",\"id\":\"org4\",\"code\":\"O004\",\"name\":\"机构4\"}]}]}";
        response.getWriter().write(json);
    }

    /**
     * 导出model的xml文件
     * @param modelId 流程定义id
     * @param response
     */
    @RequestMapping(value = "export.d")
    public void export(@RequestParam("id") String modelId, HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            String meta = modelData.getMetaInfo();
            JSONObject jsonObject = JSONObject.fromObject(meta);
            String xml = (String) jsonObject.get("description");
            String name = (String) jsonObject.get("name");
            byte[] bytes = xml.getBytes();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            IOUtils.copy(in, response.getOutputStream());
            String filename = name + ".bpmn20.xml";
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("导出model的xml文件失败：modelId={}", modelId, e);
        }
    }

    // 获取候选用户列表
    @RequestMapping(value = "searchCandidateUser.d", method = RequestMethod.POST)
    public void candidateUser(HttpServletResponse response, String userName) throws IOException {
    	//权限拆分---------------------------------------2016.12.26
       // Condition condition = new Condition();
        //if (userName != null && !"".equals(userName)) {
          //  condition.addExpression("u.username", userName, "like");
       // }
       // Sorter sort = new Sorter(Direction.ASC, "ACCOUNT");
       // List<UserVO> list = userService.queryUserListByCondition(condition, sort);
    	 List<UserVO> list = userPubService.queryUserList(null, userName);
        String jsonStr = "{\"rows\":";
        JSONArray json = new JSONArray();
        for (UserVO user : list) {
            JSONObject jo = new JSONObject();
            jo.put("userId", user.getId());
            jo.put("userCode", user.getAccount());
            jo.put("userName", user.getUsername());
            jo.put("memo", user.getMemo());
            json.add(jo);
        }
        jsonStr += json.toString() + "}";
        response.getWriter().print(jsonStr);
    }

    // 查询出所有用户组
    @RequestMapping(value = "getAllGroups.d", method = RequestMethod.POST)
    public void getAllGroups(HttpServletResponse response, String groupName) throws IOException {
        Condition condition = new Condition();
        if (groupName != null && !"".equals(groupName)) {
            condition.addExpression("GROUPNAME", groupName, "like");
        }
        Sorter sort = new Sorter(Direction.ASC, "GROUPNAME");
        List<UserGroupVO> list = userGroupService
                .queryUserGroupListByCondition(condition, sort);
        String jsonStr = "{\"rows\":";
        JSONArray json = new JSONArray();
        for (UserGroupVO userGroup : list) {
            JSONObject jo = new JSONObject();
            jo.put("groupId", userGroup.getId());
            jo.put("groupCode", "");
            jo.put("groupName", userGroup.getGroupname());
            jo.put("memo", userGroup.getMemo());
            json.add(jo);
        }
        jsonStr += json.toString() + "}";
        response.getWriter().print(jsonStr);
    }

    // 查询出所有角色
    @RequestMapping(value = "getAllRoles.d", method = RequestMethod.POST)// 原POST
    public void getAllRoles(HttpServletResponse response, String roleName) throws IOException {
        Condition condition = new Condition();
        if (roleName != null && !"".equals(roleName)) {
            condition.addExpression("ROLENAME", roleName, "like");
        }
        
        /** ---- 查询更改  wqh 2016/12/22  原因：权限拆分 -------------------------*/
        // 更改前
//      Sorter sort = new Sorter(Direction.ASC, "ROLENAME");
//      List<RoleVO> list = roleService.queryRoleListByCondition(condition,sort); 
        // 更改后
        List<RoleVO> list = roleQueryPubService.fuzzySearchRoleListByCondition(roleName);
        /**---------------------- 更改至此结束 ----------------------------------*/
        
        String jsonStr = "{\"rows\":";
        JSONArray json = new JSONArray();
        for (RoleVO role : list) {
            JSONObject jo = new JSONObject();
            jo.put("roleId", role.getId());
            jo.put("roleCode", "");
            jo.put("roleName", role.getRolename());
            jo.put("memo", role.getMemo());
            json.add(jo);
        }
        jsonStr += json.toString() + "}";
        response.getWriter().print(jsonStr);
    }

    // 查询出所有机构
    @RequestMapping(value = "getAllOrgs.d", method = RequestMethod.POST)
    public void getAllOrgs(HttpServletResponse response, String orgName) throws IOException {
        /**lyf 2016.12.23修改 权限拆分start**/
        List<OrgVO> list = new ArrayList<OrgVO>();
        if (orgName != null && !"".equals(orgName)) {
            list = orgQueryPubService.queryOrgListByCondition(orgName);
        }else{
            list = orgQueryPubService.queryAllOrgList();
        }
        /**end**/
        /*Condition condition = new Condition();
        if (orgName != null && !"".equals(orgName)) {
            condition.addExpression("ORGNAME", orgName, "like");
        }
        Sorter sort = new Sorter(Direction.ASC, "ORGNAME");
        List<OrgVO> list = orgService.queryOrgListByCondition(condition, sort);*/
        String jsonStr = "{\"rows\":";
        JSONArray json = new JSONArray();
        for (OrgVO org : list) {
            JSONObject jo = new JSONObject();
            jo.put("orgId", org.getId());
            jo.put("orgCode", org.getOrgcode());
            jo.put("orgName", org.getOrgname());
            jo.put("memo", org.getMemo());
            json.add(jo);
        }
        jsonStr += json.toString() + "}";
        response.getWriter().print(jsonStr);
    }

    //用户预览
    @RequestMapping(value = "candidateUserPreview.d", method = RequestMethod.POST)
    public void candidateUserPreview(HttpServletRequest request,
            HttpServletResponse response, Condition condition)
            throws IOException {
        // 从前台获取datagrid传过来的四个字符串参数，转化成对应的id数组
        String[] userId = request.getParameter("userIds").split(",");
        String[] groupId = request.getParameter("groupIds").split(",");
        String[] roleId = request.getParameter("roleIds").split(",");
        String[] orgId = request.getParameter("orgIds").split(",");
        List<UserVO> list = userPubService.queryUserPreByIds(userId, groupId, roleId, orgId);
        String jsonStr = "{\"rows\":";
        JSONArray json = new JSONArray();
        for (UserVO userVO : list) {
            //把查询到的有机构名、用户组名、角色名的userVO替换现在的userVO
            JSONObject jo = new JSONObject();
            jo.put("userId", userVO.getId());
            jo.put("userCode", userVO.getAccount());
            jo.put("userName", userVO.getUsername());
            jo.put("orgName", userVO.getOrgname());
            jo.put("roleName", userVO.getRolename());
            jo.put("groupName", userVO.getGroupname());
            jo.put("memo", userVO.getMemo());
            json.add(jo);
        }
        jsonStr += json.toString() + "}";
        response.getWriter().print(jsonStr);
    }

    // 查询出所有事件
    @RequestMapping(value = "getEvent.d", method = RequestMethod.POST)
    public void getEvent(HttpServletResponse response) throws IOException {
        List<EventVO> eventlist = eventService.queryEventListByCondition(null, null);
        String jsonStr = "{\"events\":";
        JSONArray json = new JSONArray();
        for (EventVO event : eventlist) {
            JSONObject jo = new JSONObject();
            jo.put("code", event.getEventCode());
            jo.put("name", event.getEventName());
            json.add(jo);
        }
        jsonStr += json.toString() + "}";
        response.getWriter().print(jsonStr);
    }

    // 查询出所有单据类型
    @RequestMapping(value = "getForm.d", method = RequestMethod.POST)
    public void getForm(String type, HttpServletResponse response) throws IOException {
        Condition condition = new Condition();
        List<FormTypeVO> formTypeList = formTypeService.queryFormTypeListByCondition(condition, null);
        String jsonStr = "{\"forms\":";
        JSONArray json = new JSONArray();
        for (FormTypeVO formType : formTypeList) {
            JSONObject jo = new JSONObject();
            jo.put("code", formType.getId());
            jo.put("name", formType.getFormName());
            json.add(jo);
        }
        jsonStr += json.toString() + "}";
        if (("datagrid").equals(type)) {
            jsonStr = jsonStr.substring(9, jsonStr.length() - 1);
        }
        response.getWriter().print(jsonStr);
    }

    // 返回默认的XML文件
    @RequestMapping(value = "getDefaultProcessXml.d", method = RequestMethod.POST)
    public void getDefaultProcessXml(HttpServletResponse response) throws IOException {
        InputStream resourceAsStream = this.getClass().getClassLoader()
                .getResourceAsStream("gdsp/conf/DefaultProcess.xml");
        BufferedInputStream bufferin = new BufferedInputStream(resourceAsStream);
        byte c[] = new byte[1000];
        int n = 0;
        StringBuilder bs = new StringBuilder();
        while ((n = bufferin.read(c)) != -1) {
            String temp = new String(c, 0, n);//
            bs.append(temp);
        }
        response.getWriter().print(bs.toString());
    }

    // 返回单据变量
    @RequestMapping(value = "getFormVariable.d", method = RequestMethod.POST)
    public void getFormVariable(String formId, HttpServletResponse response) throws IOException {
        Condition condition = new Condition();
        condition.addExpression("FORMTYPEID", formId, "=");
        Sorter sort = new Sorter(Direction.ASC, "id");
        List<FormVariableVO> formVariableList = formVariableService.queryFormVariableListByCondition(condition, sort);
        String jsonStr = "";
        JSONArray json = new JSONArray();
        for (FormVariableVO formVariable : formVariableList) {
            JSONObject jo = new JSONObject();
            jo.put("code", formVariable.getId());
            jo.put("name", formVariable.getVariableName());
            json.add(jo);
        }
        jsonStr += json.toString();
        response.getWriter().print(jsonStr);
    }

    // 编码唯一性校验及命名规则验证
    @RequestMapping(value = "validateCode.d", method = RequestMethod.POST)
    public void validateCode(String codeValue, HttpServletResponse response) throws IOException {
        int result = deploymentService.countByCode(codeValue);
        String mess = "";
        if (result == 0) {
        	String regEx = "^[a-zA-Z_][a-zA-Z0-9_\\.\\-\\']+$";
    	    Pattern pattern = Pattern.compile(regEx);
    	    Matcher matcher = pattern.matcher(codeValue);
    	    boolean rs = matcher.find();
        	if(rs){
        		mess = "<font color=\"green\">该编码可以使用！</font>";
        	}else{
        		mess = "<font color=\"red\">编码必须以字母或下划线字符开头，且不包含冒号 ！</font>";
        	}
        } else {
            mess = "<font color=\"red\">编码存在！</font>";
        }
        response.getWriter().print(mess);
    }
}
