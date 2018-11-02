package com.gdsp.platform.grant.org.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgOptPubService;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.org.service.IOrgService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;

@Controller
@RequestMapping("grant/org")
public class OrgController {
	private static final Logger logger = LoggerFactory.getLogger(OrgController.class);
    @Autowired
    private IOrgService              orgService;
    @Autowired
    private IOrgOptPubService        orgOptPubService;
    @Autowired
    private IOrgQueryPubService      orgQueryPubService;
    @Autowired
    private IUserQueryPubService     userPubService;
    @Autowired
    private IOrgPowerQueryPubService orgPowerPubService;

    @RequestMapping("/list.d")
    public String list(Model model, String pk_org) {
        String loginUserID = AppContext.getContext().getContextUserId();
        List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(loginUserID);// 获取机构
        if(orgVOs.size() > 0){
            OrgVO orgVO = orgVOs.get(0);
            pk_org = orgVO.getId();
            model.addAttribute("orgVO", orgQueryPubService.load(pk_org));
        }
        return "grant/org/list";
    }
    
    @RequestMapping("/toOrgTreePage.d")
    @ViewWrapper(wrapped = false)
    public String  toOrgTreePage(){
    	return "grant/org/org-tree";
    }
    
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, String pk_org) {
        model.addAttribute("orgVO", orgQueryPubService.load(pk_org));
        return "grant/org/form";
    }

    @RequestMapping("/listOrg.d")
    @ResponseBody
    public String listOrg() {
        String loginUserID = AppContext.getContext().getContextUserId();
        List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(loginUserID);//获取机构
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "orgname");
        if(orgVOs.size() != 0){
        	return JsonUtils.formatList2TreeViewJson(orgVOs, nodeAttr);
        }else{
        	return "";
        }
    }

    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public Object add(Model model, String pk_org) {
        if (StringUtils.isEmpty(pk_org)) {
            String loginUserID = AppContext.getContext().getContextUserId();
            UserVO user = userPubService.load(loginUserID);
            if (user.getUsertype() == GrantConst.USERTYPE_ADMIN) {
                OrgVO orgvo = new OrgVO();
                model.addAttribute("orgVO", orgvo);
            } else {
                throw new BusinessException("请选择上级机构！");
            }
        } else {
            OrgVO org = orgQueryPubService.load(pk_org);
            OrgVO orgvo = new OrgVO();
            orgvo.setPk_fatherorg(pk_org);
            orgvo.setPk_fatherName(org.getOrgname());
            model.addAttribute("orgVO", orgvo);
        }
        model.addAttribute("editType", "add");
        return "grant/org/org-add";
    }

    @RequestMapping("/queryParentOrg.d")
    @ViewWrapper(wrapped = false)
    public Object queryParentOrg(Model model,String flag) {
        //flag是一个标志，有这个标志是给数据授权用
        String loginUserID = AppContext.getContext().getContextUserId();
        List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(loginUserID);
        model.addAttribute("nodes", orgVOs);
        if(StringUtils.isEmpty(flag)){
            return "grant/org/queryparentorg";
        }else{
            return "datalicense/dataauth/org-tree";  
        }
    }

    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String pk_org) {
        model.addAttribute("orgVO", orgQueryPubService.load(pk_org));
        model.addAttribute("editType", "edit");
        return "grant/org/org-add";
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(OrgVO org) {
        try {
            if (StringUtils.isEmpty(org.getId())) {
                orgOptPubService.insert(org);
            } else {
                orgOptPubService.update(org);
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            return new AjaxResult(AjaxResult.STATUS_ERROR, "同一机构的下级机构名称不能重复，请确认！", null, false);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String id) {
        try {
            orgOptPubService.deleteOrg(new String[] { id });
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            return new AjaxResult(AjaxResult.STATUS_ERROR, e.getMessage(), null, false);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/uniqueCheck.d")
    @ResponseBody
    public Object uniqueCheck(OrgVO org) {
        return orgService.uniqueCheck(org);
    }
}
