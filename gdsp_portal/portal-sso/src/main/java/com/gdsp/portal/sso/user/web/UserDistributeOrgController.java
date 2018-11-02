package com.gdsp.portal.sso.user.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.portal.sso.user.model.UserSyncDataVO;
import com.gdsp.portal.sso.user.service.IUserSyncTmpService;

/**
* @ClassName: UserDistributeOrgController
* @Description: 用户分配机构
* @author lianyanfei,yucl
* @date 2018年3月16日 下午3:37:21
*
*/

@Controller
@RequestMapping("user/distributeOrg")
public class UserDistributeOrgController {
	
	@Autowired
    private IOrgPowerQueryPubService orgPowerPubService;
	
	@Resource
	private IUserSyncTmpService userSyncTmpService;
	
	@RequestMapping("/list.d")
	public String list(Model model,Pageable pageable){
		Page<UserSyncDataVO> tmpUsers2Allocated = userSyncTmpService.queryTmpUserPage2Allocated(pageable);
		model.addAttribute("tmpUsers2Allocated", tmpUsers2Allocated);
		return "orgDistribute/list";
	}
	
	@RequestMapping("/listData.d")
	@ViewWrapper(wrapped=false)
	public String listData(Model model,Pageable pageable){
		Page<UserSyncDataVO> tmpUsers2Allocated = userSyncTmpService.queryTmpUserPage2Allocated(pageable);
		model.addAttribute("tmpUsers2Allocated", tmpUsers2Allocated);
		return "orgDistribute/list-data";
	}
	
	@RequestMapping("/distributeOrg.d")
	public String distributeOrg(Model model,String[] ids,String pk_org){
		String userIds = "";
		for(String id : ids){
			userIds = userIds + "," + id;
		}
		userIds = userIds.substring(1);
        model.addAttribute("ids", userIds);
		return "orgDistribute/list-orgs";
	}
	@RequestMapping("/saveAllocated.d")
	@ResponseBody
	public Object saveAllocated(Model model, String ids, String pk_org){
		String[] userIds = ids.split(",");
		List<String> allocatingIds = Arrays.asList(userIds);
		List<UserSyncDataVO> tmpUsersByIds = userSyncTmpService.queryTmpUsersByIds(allocatingIds);
		userSyncTmpService.saveTmpSync2Users(tmpUsersByIds, pk_org);
		return AjaxResult.SUCCESS;
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
}
