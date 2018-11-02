package com.gdsp.platform.workflow.web;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.workflow.model.LeaveVO;
import com.gdsp.platform.workflow.service.ILeaveTestService;
import com.gdsp.platform.workflow.service.IProcessService;

/**
 * 请假流程测试
 *
 */
@Controller
@RequestMapping("workflow/leave")
public class LeaveTestController {

    private static final Logger logger = LoggerFactory.getLogger(LeaveTestController.class);
    @Autowired
    protected ILeaveTestService        leaveService;
    @Autowired
    IProcessService               processService;

    @RequestMapping("getFormAndData.d")
    @ViewWrapper(wrapped = false)
    public String getFormAndData(String id, Model model) {
        model.addAttribute("leave", leaveService.load(id));
        return "leave/leave_c";
    }
    
    @RequestMapping("startTest.d")
    @ViewWrapper(wrapped = false)
    public String startTest(String id, Model model) {
        model.addAttribute("deploymentid", id);
        model.addAttribute("editable", "true");
        model.addAttribute("leave", leaveService.load(id));
        return "leave/list";
    }

    @RequestMapping("getForm.d")
    @ViewWrapper(wrapped = false)
    public String getForm(String id, String editable, Model model) {
        model.addAttribute("deploymentid", id);
        model.addAttribute("editable", editable);
        model.addAttribute("leave", leaveService.load(id));
        return "leave/leave";
    }

    @RequestMapping("save.d")
    @ResponseBody
    public Object save(LeaveVO leave, String deploymentid,
            RedirectAttributes redirectAttributes) {
        leaveService.saveLeave(leave);
        Map<String, Object> params = new HashMap<>();
        params.put("id", deploymentid);
        params.put("formid", leave.getId());
        params.put("leaveDay", leave.getLeaveDay());
//        redirectAttributes.addAttribute("id", deploymentid);
//        redirectAttributes.addAttribute("formid", leave.getId());
//        redirectAttributes.addAttribute("leaveDay", leave.getLeaveDay());
//        return "redirect:/workflow/process/start.d";
        return new AjaxResult(AjaxResult.STATUS_SUCCESS, "保存成功", params);
    }
    
    @RequestMapping("startProcess.d")
    @ResponseBody
    public Object startPro(String deploymentId, String formid, String leaveDay){
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
}
