package com.gdsp.platform.workflow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.workflow.service.IDeploymentService;

/**
 * 流程部署Controller
 * @author sun
 *
 */
@Controller
@RequestMapping("workflow/deployment")
public class DeploymentController {

    @Autowired
    private IDeploymentService deploymentService;

    @RequestMapping("list.d")
    public String list(Model model, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "deploymentname");
        model.addAttribute("deployments", deploymentService.queryDeploymentByCondition(condition, pageable, sort));
        return "deployment/list";
    }

    @RequestMapping("listData.d")
    public String listData(Model model, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "deploymentname");
        model.addAttribute("deployments", deploymentService.queryDeploymentByCondition(condition, pageable, sort));
        return "deployment/list-data";
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id != null && id.length > 0) {
            deploymentService.deleteDeployment(id);
        }
        return AjaxResult.SUCCESS;
    }
}
