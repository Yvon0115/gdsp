package com.gdsp.platform.demo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.auth.service.IPowerMgtOptPubService;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;

/**
 * Web层控件 
 * @author xiangguo
 */
@Controller
@RequestMapping("plugins/easyTree")
public class EasyTreeController {
	@Autowired
    private IPowerMgtOptPubService   powerMgtOptPubService;
    @Autowired
    private IPowerMgtQueryPubService powerMgtQueryPubService;
    @Autowired
    private IOrgPowerQueryPubService orgPowerPubService;


    @RequestMapping("/ajaxShow.d")
    public String ajaxShow(Model model) {
        return "demo/easytree/runqianResTree";
    }

    @RequestMapping("/ajaxPost.d")
    @ResponseBody
    public Object ajaxPost(Model model, String reportPaths) {
        return AjaxResult.SUCCESS;
    }
    
    @RequestMapping("/easyTreeByListData.d")
    public String easyTreeByListData() {
        return "demo/easytree/easytreebylistdata";
    }
    
    @ResponseBody
    @RequestMapping("/getEasyTreeByListData.d")
    public String getEasyTreeByListData(String roleId, String orgID) {
    	String loginUserID = AppContext.getContext().getContextUserId();
        List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(loginUserID);//获取机构
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "orgname");
        return JsonUtils.formatList2TreeViewJson(orgVOs,nodeAttr);
    }
    
    @RequestMapping("/easyTreeByMapData.d")
    public String checkboxTreeByMap() {
        return "demo/easytree/easytreebymapdata";
    }
    
    @SuppressWarnings("rawtypes")
	@ResponseBody
    @RequestMapping("/getEasyTreeByMapData.d")
    public String getTreeByMap(String roleId, String orgID) {
    	String loginUserID = AppContext.getContext().getContextUserId();
        //遍历设置是否选中。
        //查询登陆用户有权限的菜单
        Map menuMap = powerMgtQueryPubService.queryMenuMapByUser(loginUserID, true);
        //角色有权限操作的菜单
        List<PowerMgtVO> queryPowerMgtByRoleId = powerMgtQueryPubService.queryPowerMgtByRoleId(roleId);
        List<String> menuIdList = null;
        if (queryPowerMgtByRoleId != null && queryPowerMgtByRoleId.size() > 0) {
            menuIdList = new ArrayList<String>();
            for (PowerMgtVO powerMgtVO : queryPowerMgtByRoleId) {
                menuIdList.add(powerMgtVO.getResource_id());
            }
        }
        Map roleMenuMaps = powerMgtQueryPubService.queryMenuRecursive(menuIdList);
        //设置是否选中。
        powerMgtOptPubService.setMenuIsChecked(menuMap, roleMenuMaps);
    	
        //查询登陆用户有权限的菜单
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "funname_safelevel");
        nodeAttr.put("checked", "isChecked");
        String formatMap2TreeViewJson = JsonUtils.formatMap2TreeViewJson(menuMap,nodeAttr);
        return formatMap2TreeViewJson;
    }
}
