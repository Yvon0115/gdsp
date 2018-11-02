/**
 * 
 */
/**
 * @author lian.yf
 *
 */
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
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.platform.demo.model.ZtreeOrgVO;
import com.gdsp.platform.demo.service.ZtreeService;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;

@Controller
@RequestMapping("grant/ztree")
public class ZtreeController {
	@Autowired
    private ZtreeService ztreeService;

    @RequestMapping("/list.d")
    public String list() {
        return "demo/ztree/list";
    }
    @RequestMapping("/openTree.d")
    @ViewWrapper(wrapped=false)
    public String openTree(){
		return "demo/ztree/demo-tree";
    }
    @RequestMapping("/listDemo.d")
    @ResponseBody
    public String listOrg(String orgId,String name,String test) {
    	List<ZtreeOrgVO> ztreeOrgVOs = new ArrayList<ZtreeOrgVO>();
        String loginUserID = AppContext.getContext().getContextUserId();
        //////////异步加载/////////////
        if(orgId == null){
        	ztreeOrgVOs = ztreeService.queryRootOrgOnZtree();
        }else{
        	ztreeOrgVOs = ztreeService.queryOrgsByIdOnZtree(orgId);
        }
        /////////////////////////////////////
        ///////////非异步加载//////////////////
//        ztreeOrgVOs = ztreeService.queryOrgListByUserOnZtree(loginUserID);//获取机构
        ///////////////////////////////////
        for (ZtreeOrgVO orgVO : ztreeOrgVOs) {
        	if(orgVO!=null&&orgVO.getOrgname().contains("null")){
        		orgVO.setIsParent("false");
        	}
			if(orgVO!=null&&orgVO.getOrgname().contains("北京")){
				orgVO.setNodeDisable("true");
				orgVO.setChkEnabled("false");
				orgVO.setChecked("false");
				orgVO.setOpenUrl("/gdsp/img/diy/1_open.png");
				orgVO.setCloseUrl("/gdsp/img/diy/1_close.png");
				orgVO.setIconUrl("/gdsp/img/diy/5.png");
			}
			if(orgVO!=null&&orgVO.getOrgname().contains("bbb")){
				orgVO.setNodeDisable("false");
				orgVO.setChkEnabled("true");
				orgVO.setChecked("true");
			}
		}
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("name", "orgname");
        //设置自定义属性，格式如下：diyAttrs为固定值，代表自定义属性，map的value值存放需要添加的自定义属性，以逗号隔开
        nodeAttr.put("diyAttrs","innercode,shortname");
        if(ztreeOrgVOs.size() != 0){
        	//异步加载转json数据
        	return JsonUtils.zTreeViewJsonAsync(ztreeOrgVOs, nodeAttr);
        	//非异步加载list<VO>转json数据
//        	return JsonUtils.formatList2ZTreeViewJsonNoAsync(ztreeOrgVOs, nodeAttr);
        	//非异步加载map转json数据
//        	return JsonUtils.formatMap2ZTreeViewJsonNoAsync(ztreeMaps, nodeAttr);
        }else{
        	return "";
        }
    }
    
    @RequestMapping("/listDemoB.d")
    @ResponseBody
    public String listOrgB(String orgId,String name,String test) {
    	List<ZtreeOrgVO> ztreeOrgVOs = new ArrayList<ZtreeOrgVO>();
        String loginUserID = AppContext.getContext().getContextUserId();
        //////////异步加载/////////////
//        if(orgId == null){
//        	ztreeOrgVOs = ztreeService.queryRootOrgOnZtree();
//        }else{
//        	ztreeOrgVOs = ztreeService.queryOrgsByIdOnZtree(orgId);
//        }
        /////////////////////////////////////
        ///////////非异步加载//////////////////
        ztreeOrgVOs = ztreeService.queryOrgListByUserOnZtree(loginUserID);//获取机构
        ///////////////////////////////////
        for (ZtreeOrgVO orgVO : ztreeOrgVOs) {
        	orgVO.setOpenUrl("/gdsp/img/diy/1_open.png");
			orgVO.setCloseUrl("/gdsp/img/diy/1_close.png");
			orgVO.setIconUrl("/gdsp/img/diy/5.png");
			if(orgVO!=null&&orgVO.getOrgname().contains("北京")){
				orgVO.setNodeDisable("true");
				orgVO.setChkEnabled("false");
				orgVO.setChecked("false");
				orgVO.setOpenUrl("/gdsp/img/diy/1_open.png");
				orgVO.setCloseUrl("/gdsp/img/diy/1_close.png");
				orgVO.setIconUrl("/gdsp/img/diy/5.png");
			}
			if(orgVO!=null&&orgVO.getOrgname().contains("bbb")){
				orgVO.setNodeDisable("false");
				orgVO.setChkEnabled("true");
				orgVO.setChecked("true");
			}
		}
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("name", "orgname");
        //设置自定义属性，格式如下：diyAttrs为固定值，代表自定义属性，map的value值存放需要添加的自定义属性，以逗号隔开
        nodeAttr.put("diyAttrs","innercode,shortname");
        if(ztreeOrgVOs.size() != 0){
        	//异步加载转json数据
//        	return JsonUtils.zTreeViewJsonAsync(ztreeOrgVOs, nodeAttr);
        	//非异步加载list<VO>转json数据
        	return JsonUtils.formatList2ZTreeViewJsonNoAsync(ztreeOrgVOs, nodeAttr);
        	//非异步加载map转json数据
//        	return JsonUtils.formatMap2ZTreeViewJsonNoAsync(ztreeMaps, nodeAttr);
        }else{
        	return "";
        }
    }
}
