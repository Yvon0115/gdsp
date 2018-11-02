package com.gdsp.platform.workflow.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.model.param.PageSerRequest;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.workflow.model.BussinessFormVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.model.TaskVO;
import com.gdsp.platform.workflow.service.IBussinessFormService;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.IFormTypeService;
import com.gdsp.platform.workflow.service.IProcessHistoryService;
import com.gdsp.platform.workflow.utils.TemplateEmail;
import com.gdsp.platform.workflow.utils.UserUtils;


/**
 * 流程任务控制器
 */
@Controller
@RequestMapping("workflow/task")
public class TaskController {
    
    /** 
     * 日志 
     */  
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    
    @Autowired
    protected TaskService          taskService;
    @Autowired
    protected HistoryService       historyService;
    @Autowired
    protected RuntimeService       runtimeService;
    @Autowired
    protected RepositoryService    repositoryService;
    @Autowired
    protected TemplateEmail        templateEmail;
    @Autowired
    private IDeploymentService     deploymentService;
    @Autowired
    private IProcessHistoryService processHistoryService;
    @Autowired
    private IFormTypeService       formTypeService;
    @Autowired
    private IBussinessFormService       bussinessFormService;
    @Autowired
    private IUserQueryPubService     userQueryPubService;
    
    /**
     * 待办列表
     * @param condition
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping("/myList.d")
    public String taskList(Condition condition, Model model, Pageable pageable) {
        /*	查询逻辑:任务ID,流程实例ID,流程定义ID,节点ID,部署编码,部署名称,流程类别名称,流程发起人,任务创建时间,持续时间
        	SELECT T.ID_,T.PROC_INST_ID_,T.PROC_DEF_ID_,T.TASK_DEF_KEY_,D.DEPLOYMENTCODE,D.DEPLOYMENTNAME,C.CATEGORYNAME,H.USERID,T.CREATE_TIME_,T.DUE_DATE_ FROM ACT_RU_TASK T
        	LEFT JOIN FLOW_DEPLOYMENT D ON T.PROC_DEF_ID_=D.PROCDEFID
        	LEFT JOIN FLOW_CATEGORY C ON D.CATEGORYID = C.ID
        	LEFT JOIN FLOW_PROCESSHISTORY H ON T.PROC_INST_ID_=H.PROCESSINSID
        	按照个人过滤:user1为当前用户ID
        	WHERE H.ACTID='startuser' AND T.ASSIGNEE_='user1' OR T.ID_ IN (
        	SELECT TASK_ID_ FROM ACT_RU_IDENTITYLINK
        	WHERE TYPE_='candidate' AND USER_ID_='user1'
        	)
        	页面查询条件:
        	and D.DEPLOYMENTNAME='' and T.CREATE_TIME_ BETWEEN '' AND ''
         */
    	
//    	try {
//			String url = "http://192.168.100.135:8888/ssm/request/updateRequests.d";
//			Map<String, Object> queryParams = new LinkedMap();
//			queryParams.put("1", "1");
//			Map<String, Object> parameters= new LinkedMap();
//			String reqResult = com.gdsp.dev.base.utils.web.HttpClientUtils.httpGetText(url, null);
//			System.out.println(reqResult);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
    	
        String user = UserUtils.getCurrentUserID();
        Page<TaskVO> tasks = deploymentService.queryTaskByCondition(condition, user, pageable);
//        Page<TaskVO> alltasks = integratedBacklogTask(pageable, user, tasks);
        model.addAttribute("tasks", tasks);
        return "task/list";
    }

    /**
     * 这里用一句话描述这个方法的作用
     * @param pageable
     * @param user
     * @param tasks
     * @return
     */ 
    Page<TaskVO> integratedBacklogTask(Pageable pageable, String user, Page<TaskVO> tasks) {
        UserVO userVO = userQueryPubService.load(user);
        String userName = null;
        if(null != userVO){
            userName = userVO.getUsername();
        }
        List<TaskVO> rejectedTasks = deploymentService.queryRejectedTasks(user);
        for (TaskVO taskVO : rejectedTasks) {
            taskVO.setUserId(userName);
        }
        tasks.getContent().addAll(rejectedTasks);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int startRowNum = pageNumber * pageSize;
        int endRowNum = pageNumber * pageSize + pageSize;
        if(tasks.getContent().size() < startRowNum){
            pageable = new PageSerRequest();
            pageNumber = pageable.getPageNumber();
            pageSize = pageable.getPageSize();
            startRowNum = pageNumber * pageSize;
            endRowNum = pageNumber * pageSize + pageSize;
        }
        List<TaskVO> resultList = tasks.getContent().subList(startRowNum, endRowNum > tasks.getContent().size() ? tasks.getContent().size() : endRowNum);
        Page<TaskVO> alltasks = new PageImpl<TaskVO>(resultList, pageable, tasks.getContent().size());
        return alltasks;
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String taskListData(Condition condition, Model model, Pageable pageable) {
        String user = UserUtils.getCurrentUserID();
        Page<TaskVO> tasks = deploymentService.queryTaskByCondition(condition, user, pageable);
//        Page<TaskVO> alltasks = integratedBacklogTask(pageable, user, tasks);
        model.addAttribute("tasks", tasks);
        return "task/list-data";
    }

    /**
     * 所有流程
     * @param condition
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping("/listAll.d")
    public String myTaskList(Condition condition, Model model, Pageable pageable) {
        Sorter sort = new Sorter(Direction.DESC, "starttime");
        String user = UserUtils.getCurrentUserID();
        Page<ProcessHistoryVO> tasks = deploymentService.queryTaskAllByCondition(user, condition, pageable, sort);
        //查询个人已被审批流程实例id集合
        List<String> irrevocableProcInstIds = deploymentService.queryIrrevocableProcInstIds(user);
        model.addAttribute("irrevocableProcInstIds", irrevocableProcInstIds);
        model.addAttribute("tasks", tasks);
        return "task/listAll";
    }

    @RequestMapping("/listAllData.d")
    @ViewWrapper(wrapped = false)
    public String myTaskListData(Condition condition, Model model, Pageable pageable) {
        Sorter sort = new Sorter(Direction.DESC, "starttime");
        String user = UserUtils.getCurrentUserID();
        Page<ProcessHistoryVO> tasks = deploymentService.queryTaskAllByCondition(user, condition, pageable, sort);
        //查询个人已被审批流程实例id集合
        List<String> irrevocableProcInstIds = deploymentService.queryIrrevocableProcInstIds(user);
        model.addAttribute("irrevocableProcInstIds", irrevocableProcInstIds);
        model.addAttribute("tasks", tasks);
        return "task/listAll-data";
    }

    /**
     * 办理任务
     * @param taskId
     * @param procInsId
     * @param model
     * @param sort
     * @param pageable
     * @param condition
     * @return
     */
    @RequestMapping("preComplete.d")
    @ViewWrapper(wrapped = false)
    public String preComplete(String taskId, String procInsId, String formId, Model model, Pageable pageable, Condition condition) {
        model.addAttribute("taskId", taskId);
        model.addAttribute("formid", formId);
        model.addAttribute("procInsId", procInsId);
        Sorter sort = new Sorter(Direction.ASC, "createtime");
        Page<ProcessHistoryVO> hdetail = processHistoryService.queryProcessHistoryByInsId(procInsId, sort, pageable);
        model.addAttribute("hdetail", hdetail);
        
        /*--------------=====判断下载附件功能显示=====-------------*/
        BussinessFormVO bussinessFormVO = bussinessFormService.queryFormVariableValueByFormId(formId);
        String downloadUrl = null;
        if(null != bussinessFormVO){
            downloadUrl = bussinessFormVO.getDownloadurl();
        }
        model.addAttribute("downloadUrl", downloadUrl);
        //传递单据的url地址到前端，然后通过这个url加载到对应的单据界面
        //办理人肯定是当前用户。
        String urlStr = formTypeService.queryFormURLByFormId(formId) + "?id=" + formId+"&flag="+1;
        model.addAttribute("formURL", urlStr);
        return "task/preComp";
    }
    
    /**
     * 办理任务
     * @param taskId
     * @param procInsId
     * @param model
     * @param sort
     * @param pageable
     * @param condition
     * @return
     */
    @RequestMapping("preCompleteListData.d")
    @ViewWrapper(wrapped = false)
    public String preComplete(String procInsId, Model model, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "createtime");
        Page<ProcessHistoryVO> hdetail = processHistoryService.queryProcessHistoryByInsId(procInsId, sort, pageable);
        model.addAttribute("hdetail", hdetail);
        return "task/preCompListData";
    }
    
    @RequestMapping("testpreComplete.d")
    @ViewWrapper(wrapped = false)
    public String testpreComplete(String taskId, String procInsId, String formId, Model model, Pageable pageable, Condition condition) {
    	model.addAttribute("taskId", taskId);
    	model.addAttribute("formid", formId);
    	Sorter sort = new Sorter(Direction.ASC, "createtime");
    	Page<ProcessHistoryVO> hdetail = processHistoryService.queryProcessHistoryByInsId(procInsId, sort, pageable);
    	model.addAttribute("hdetail", hdetail);
    	//传递单据的url地址到前端，然后通过这个url加载到对应的单据界面
    	//办理人肯定是当前用户。
    	String urlStr = formTypeService.queryFormURLByFormId(formId) + "?id=" + formId;
        model.addAttribute("formURL", urlStr);
    	return "task/preComp";
    }

    /**
     *  查看当前用户代办任务
     * @param procInsId
     * @param model
     * @param pageable
     * @param condition
     * @return
     */
    @RequestMapping("taskDetail.d")
    @ViewWrapper(wrapped = false)
    public String taskDetail(String taskId, String procInsId, String formId, String nodeId, Model model, Pageable pageable, Condition condition) {
        Sorter sort = new Sorter(Direction.ASC, "createtime");
        Page<ProcessHistoryVO> hdetail = processHistoryService.queryProcessHistoryByInsId(procInsId, sort, pageable);
        model.addAttribute("hdetail", hdetail);
        //传递单据的url地址到前端，然后通过这个url加载到对应的单据界面
        String urlStr = formTypeService.queryFormURLByFormId(formId) + "?id=" + formId;
        if("startuser".equals(nodeId)){
            urlStr = urlStr + "&editable=true";
        }
        model.addAttribute("procInsId", procInsId);
        model.addAttribute("nodeId", nodeId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("formid", formId);
        model.addAttribute("formURL", urlStr);
        return "task/taskDetail";
    }
    
    /**
     *  查看当前用户代办任务
     * @param procInsId
     * @param model
     * @param pageable
     * @param condition
     * @return
     */
    @RequestMapping("taskDetailListData.d")
    @ViewWrapper(wrapped = false)
    public String taskDetail(String procInsId, Model model, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "createtime");
		Page<ProcessHistoryVO> hdetail = processHistoryService.queryProcessHistoryByInsId(procInsId, sort, pageable);
        model.addAttribute("hdetail", hdetail);
        return "task/taskDetailListData";
    }
    
    /**
     * 撤回流程
     * @param taskId
     * @return
     */
    @RequestMapping("retract.d")
    @ResponseBody
    public Object withdrawTask(Condition condition,Sorter sort,String procInsId){
        processHistoryService.withdrawProcessHistoryByInsId(procInsId);
        return AjaxResult.SUCCESS;
    }
    
    /**
     * 下载附件LOGGER
     */
    @RequestMapping("downloadAttachments.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public void downloadAttachments(HttpServletResponse response ,@RequestParam("fileUrl")String fileUrl){
        
        if(null == response || StringUtils.isEmpty(fileUrl)){
            return;
        }
        
        //下载附件名称
        String filename = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        // 执行下载  
        BufferedOutputStream bos = null;  
        BufferedInputStream bis = null;  
        InputStream inputStream = null;  
        HttpURLConnection httpUrlConn = null;  
        try {  
            LOGGER.info("执行下载开始：fileUrl=" + fileUrl);  
            // 清空response  
            response.reset();  
            // 设置响应头  
            response.setContentType("application/x-msdownload");  
            // 重命名为原文件名称  
            // 解决中文名称乱码  
            filename = new String(filename.getBytes("GBK"), "iso-8859-1");  
            response.addHeader("Content-Disposition", "attachment; filename=" + filename);  
            // 获取输出流  
            bos = new BufferedOutputStream(response.getOutputStream());  
  
            // 获取网络路径连接  
            URL url = new URL(fileUrl);  
            httpUrlConn = (HttpURLConnection) url.openConnection();  
            // 设置输入开启  
            httpUrlConn.setDoInput(true);  
            // 显性设置缓存  
            httpUrlConn.setUseCaches(true);  
            httpUrlConn.setRequestMethod("GET");  
            // 设置网络请求头  
            httpUrlConn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");  
            httpUrlConn.connect();  
  
            // 通过连接获取输入流  
            inputStream = httpUrlConn.getInputStream();  
            bis = new BufferedInputStream(httpUrlConn.getInputStream());  
  
            // 设置下载缓冲  
            int cache = 10*1024;
            int byteread = 0;
            byte[] buffer = new byte[cache];  
            while ((byteread = bis.read(buffer)) != -1) {  
                bos.write(buffer, 0, byteread);  
            }  
  
            bos.flush();  
  
            LOGGER.info("执行下载正常结束！");  
  
        } catch (UnsupportedEncodingException e) {  
            printLog(fileUrl, filename);  
            e.printStackTrace();  
  
        } catch (ProtocolException e) {  
            printLog(fileUrl, filename);  
            e.printStackTrace();  
  
        } catch (MalformedURLException e) {  
            printLog(fileUrl, filename);  
            e.printStackTrace();  
  
        } catch (IOException e) {  
            printLog(fileUrl, filename);  
            e.printStackTrace();  
            // 关闭流  
        } finally {  
            closeStream(bos, bis, inputStream, httpUrlConn);  
        } 
        
    }
    
    /** 
     * 输出日志 
     * 
     * @param fileUrl 
     * @param fileName 
     */  
    private void printLog(String fileUrl, String fileName) {  
        LOGGER.info("执行下载失败：fileUrl=" + fileUrl + ";fileName=" + fileName);  
    }
    
    /** 
     * 关闭流 
     * 
     * @param bos 
     * @param bis 
     * @param inputStream 
     * @param httpUrlConn 
     */  
    private void closeStream(BufferedOutputStream bos, BufferedInputStream bis  
            , InputStream inputStream, HttpURLConnection httpUrlConn) {  
        try {  
            // 释放资源  
            if (bos != null) {  
                bos.close();  
            }  
            if (bis != null) {  
                bis.close();  
            }  
            if (inputStream != null) {  
                inputStream.close();  
            }  
            // 断开连接  
            if (httpUrlConn != null) {  
                httpUrlConn.disconnect();  
            }  
        } catch (IOException ex) {  
            LOGGER.info("断开连接出错！");  
            ex.printStackTrace();  
        }  
    } 
    
}
