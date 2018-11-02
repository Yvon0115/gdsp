package com.gdsp.platform.workflow.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.user.service.IUserService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.workflow.model.ApprAuthorityVO;
import com.gdsp.platform.workflow.model.BussinessFormVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.model.TaskVO;
import com.gdsp.platform.workflow.service.IApprAuthorityService;
import com.gdsp.platform.workflow.service.IBussinessFormService;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.IFormTypeService;
import com.gdsp.platform.workflow.service.INodeInfoService;
import com.gdsp.platform.workflow.service.IProcessHistoryService;
import com.gdsp.platform.workflow.utils.UserUtils;

@Controller
@RequestMapping("workflow/monitor")
public class MonitorController {

    @Autowired
    private IDeploymentService     deploymentService;
    @Autowired
    private IProcessHistoryService processHistoryService;
    @Autowired
    private INodeInfoService       nodeInfoService;
    @Autowired
    private IApprAuthorityService  apprAuthorityService;
    @Autowired
    IUserService                   userService;
    @Autowired
    IUserQueryPubService           userQueryPubService;
    @Autowired
    private IBussinessFormService       bussinessFormService;
    @Autowired
    private IFormTypeService       formTypeService;

    // Initialize loading all processes
    @RequestMapping("/list.d")
    public String list(String userId, Condition condition, Model model,
            Pageable pageable) {
        String user = UserUtils.getCurrentUserID();
        UserVO userVO = userQueryPubService.load(user);
        if(null != userVO && GrantConst.USERTYPE_ADMIN == userVO.getUsertype()){
            model.addAttribute("userType", "admin");
        }
        Sorter sort = new Sorter(Direction.DESC, "createtime");
        model.addAttribute("monitorList",
                deploymentService.queryProcForAdmin(condition, pageable, sort));
        return "monitor/list";
    }

    // Query by conditions
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Condition condition, Model model, Pageable pageable) {
        Sorter sort = new Sorter(Direction.DESC, "createtime");
        String user = UserUtils.getCurrentUserID();
        UserVO userVO = userQueryPubService.load(user);
        if(null != userVO && GrantConst.USERTYPE_ADMIN == userVO.getUsertype()){
            model.addAttribute("userType", "admin");
        }
        model.addAttribute("monitorList",
                deploymentService.queryProcForAdmin(condition, pageable, sort));
        return "monitor/list-data";
    }

    // 监控流程详情 传参
    @RequestMapping("/detail.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String detail(String deploymentId, String actId, String procInsId, Model model, Condition condition,
            Sorter sort, Pageable pageable) {
        
    	/** 流程信息*/
    	Condition cd2=new Condition();
    	cd2.addExpression("T.PROC_INST_ID_", procInsId);
    	TaskVO taskVO = deploymentService.queryProcForAdmin(cd2, pageable, sort).getContent().get(0);
    	
    	long time = System.currentTimeMillis()-taskVO.getCreateTime().getMillis(); // 转换long类型
    	long d = time / 86400000; // 天数
    	long hms = time % 86400000; // 除去天数的毫秒数
    	long h = hms / 3600000; // 时
    	long hh = hms % 3600000;
    	long m = hh / 60000; // 分
    	long mm = hh % 60000;
    	long s = mm / 1000; // 秒
    	// Date date = new Date(hms);
    	// DateFormat dateFormat = new SimpleDateFormat("HH时mm分ss秒");
    	// String hour = dateFormat.format(date);
    	String day = Long.toString(d);
    	String hour = Long.toString(h);
    	String min = Long.toString(m);
    	String sec = Long.toString(s);
    	String dueTime = day + "天" + hour + "时" + min + "分" + sec + "秒";
    	model.addAttribute("dueTime", dueTime);
    	
    	
    	
    	sort = new Sorter(Direction.ASC, "createtime");
    	model.addAttribute("taskVO", taskVO);
        model.addAttribute("procInsId", procInsId);
        Page<ProcessHistoryVO> phdetail = processHistoryService
                .queryProcessHistoryByInsId(procInsId, sort,
                        pageable);
        model.addAttribute("phdetail", phdetail);
        model.addAttribute("deploymentId", deploymentId);
        model.addAttribute("actId", actId);
        
        /** 代办人信息*/
        model.addAttribute("users",
                loadAssigneeFunc(deploymentId, actId, model, pageable));
        return "monitor/detail";
    }

    // 监控流程详情
    @RequestMapping("/detailData.d")
    @ViewWrapper(wrapped = false)
    public String detailData(String deploymentId, String actId, String procInsId, Model model,
            Condition condition, Sorter sort, Pageable pageable) {
        sort = new Sorter(Direction.ASC, "createtime");
        Page<ProcessHistoryVO> phdetail = processHistoryService
                .queryProcessHistoryByInsId(procInsId, sort,
                        pageable);
        model.addAttribute("phdetail", phdetail);
        model.addAttribute("deploymentId", deploymentId);
        model.addAttribute("actId", actId);
        return "monitor/detail-data";
    }

    @RequestMapping("loadAssignee.d")
    public String loadAssignee(String deploymentId, String actId, Model model,
            Pageable pageable) {
        model.addAttribute("users",
                loadAssigneeFunc(deploymentId, actId, model, pageable));
        model.addAttribute("deploymentId", deploymentId);
        model.addAttribute("actId", actId);
        return "monitor/assigneeList";
    }

    @RequestMapping("loadAssigneeData.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String loadAssigneeData(String deploymentId, String actId,
            Model model, Pageable pageable) {
        model.addAttribute("users",
                loadAssigneeFunc(deploymentId, actId, model, pageable));
        return "monitor/assigneeList-data";
    }

    public Page<UserVO> loadAssigneeFunc(String deploymentId, String actId,
            Model model, Pageable pageable) {
        Condition condition1 = new Condition();
        condition1.addExpression("DEPLOYMENTID", deploymentId);
        condition1.addExpression("ACTID", actId);
        // nodeName 返回到前台的节点名
        model.addAttribute("nodeName", nodeInfoService
                .queryNodeInfoListByCondition(condition1, null).get(0)
                .getActName());
        String nodeId = nodeInfoService
                .queryNodeInfoListByCondition(condition1, null).get(0).getId();
        Condition condition2 = new Condition();
        condition2.addExpression("nodeInfoId", nodeId);
        List<ApprAuthorityVO> apprAuthorityList = apprAuthorityService
                .queryApprAuthorityListByCondition(condition2, null);
        // 定义四个ArrayList用来存放对应类型的id值
        ArrayList<String> userIdList = new ArrayList<String>();
        ArrayList<String> roleIdList = new ArrayList<String>();
        ArrayList<String> groupIdList = new ArrayList<String>();
        ArrayList<String> orgIdList = new ArrayList<String>();
        // 循环遍历apprAuthorityList将不同类型的apprTypeId放入到相应的ArrayList里
        for (int i = 0; i < apprAuthorityList.size(); i++) {
            ApprAuthorityVO apprAuthorityVO = apprAuthorityList.get(i);
            String apprType = apprAuthorityVO.getApprType();
            String apprTypeId = apprAuthorityVO.getApprTypeId();
            if ("user".equals(apprType)) {
                userIdList.add(apprTypeId);
            } else if ("role".equals(apprType)) {
                roleIdList.add(apprTypeId);
            } else if ("group".equals(apprType)) {
                groupIdList.add(apprTypeId);
            } else if ("org".equals(apprType)) {
                orgIdList.add(apprTypeId);
            }
        }
        // 将ArrayList转化成 字符串数组
        String[] userIds = new String[userIdList.size()];
        userIds = userIdList.toArray(userIds);
        String[] roleIds = new String[roleIdList.size()];
        roleIds = roleIdList.toArray(roleIds);
        String[] userGroupIds = new String[groupIdList.size()];
        userGroupIds = groupIdList.toArray(userGroupIds);
        String[] orgIds = new String[orgIdList.size()];
        orgIds = orgIdList.toArray(orgIds);
        return userService.queryUserPreByIds(userIds, userGroupIds,
                roleIds, orgIds, pageable);
    }
    
    /**
     * 管理员办理
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
        return "monitor/preComp";
    }
}
