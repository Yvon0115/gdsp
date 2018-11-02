package com.gdsp.platform.systools.datalicense.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.role.service.IRoleQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.systools.datadic.service.IDataDicService;
import com.gdsp.platform.systools.datalicense.model.DataLicenseVO;
import com.gdsp.platform.systools.datalicense.service.IDataDicPowerService;
import com.gdsp.platform.systools.datalicense.service.IDataLicenseService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**数据授权
 * @author wangliyuan
 *
 */
@Controller
@RequestMapping("power/datalicense")
public class DataLicenseController {
    
   @Autowired
   private  IRoleQueryPubService roleQueryPubService;
   @Autowired
   private  IUserQueryPubService userQueryPubService;
   @Autowired
   private  IOrgQueryPubService  orgQueryPubService;
   @Autowired
   private  IUserRoleQueryPubService  userRoleQueryPubService;
   @Autowired
   private  IDataLicenseService      dataLicenseService;
   @Autowired
   private  IDataDicPowerService    dataDicPowerService;
   @Autowired
   private IDataDicService         dataDicService;
   @Autowired
   private IOrgPowerQueryPubService orgPowerQueryPubService;
   /**
    * 查询机构及角色
    * @param model
    * @param con
    * @return
    */
   @RequestMapping("/list.d")
   public String list(Model model,Condition con){
       List<RoleVO> list = new ArrayList<RoleVO>();
       OrgVO orgVO = new OrgVO();
       RoleVO roleVO = new RoleVO();
       String userId=AppContext.getContext().getContextUserId();
       UserVO userVO = userQueryPubService.load(userId);
       if(!(userVO.getUsertype() == GrantConst.USERTYPE_ADMIN)){
    	   //查询当前登录用户有权限的机构
    	   List<OrgVO> queryOrgListByUser = orgPowerQueryPubService.queryOrgListByUser(userId);
    	   //当前登录用户只有一个机构权限时显示出来，拥有多个机构权限时不显示
    	   if(queryOrgListByUser!=null && queryOrgListByUser.size()==1){
    		   orgVO = queryOrgListByUser.get(0);
    		   roleVO.setPk_org(orgVO.getId());
    		   list = roleQueryPubService.queryRoleListByOrgId(roleVO, false);
    	   }
       }
    /*       List<OrgVO> orgLists = orgQueryPubService.queryAllOrgList();
           List<OrgVO> orgList = new ArrayList<OrgVO>();
           for (OrgVO orgvo : orgLists) {
                if(orgvo.getPk_fatherorg().isEmpty()){
                    orgList.add(orgvo);
                }
            }
           con.addExpression("pk_fatherorg", "", "=");
           Sorter sort = new Sorter(Direction.ASC, "orgcode");
           List<OrgVO> orgList = orgService.queryOrgListByCondition(con, sort);
           if(orgList!=null && orgList.size()>0){
               orgVO=orgList.get(0);
               roleVO.setPk_org(orgVO.getId());
           }
           list = roleQueryPubService.queryRoleListByOrgId(roleVO, false);
       }else{
    	  
           orgVO = orgQueryPubService.load(userVO.getPk_org());
           roleVO.setPk_org(orgVO.getId());    
           list = roleQueryPubService.queryRoleListByOrgId(roleVO, false);
       }*/
//       model.addAttribute("orgVO", orgVO);
       model.addAttribute("roleList", list);
       return "datalicense/dataauth/list";   
   }
   
   @RequestMapping("/toOrgTreePage.d")
   public String toOrgTreePage(){
	  return "datalicense/dataauth/org-tree" ;
   }
   @RequestMapping("/queryOrgNode.d")
   @ViewWrapper(wrapped = false)
   @ResponseBody
   public Object queryOrgNode(Model model) {
	   String loginUserID = AppContext.getContext().getContextUserId();
       List<OrgVO> orgVOs = orgPowerQueryPubService.queryOrgListByUser(loginUserID);
       Map<String,String> nodeAttr = new HashMap<String,String>();
	   nodeAttr.put("text", "orgname");
	   if(orgVOs.size()!=0)
		   return   JsonUtils.formatList2TreeViewJson(orgVOs, nodeAttr);
	   else
		   return "";
       
   }
    /**
     * 通过机构id查询角色列表
     * @param orgId
     * @return
     */
   @RequestMapping("/queryRoles.d")
   @ResponseBody
   
    public JSONObject queryRoles(Model model,String orgId){
       List<Map<String, String>> queryRoleList = roleQueryPubService.queryRoleList(orgId);
        JSONObject json = new JSONObject();
        JSONArray fromObject = JSONArray.fromObject(queryRoleList);
        json.put("roleList", fromObject);
        return json;
    } 
   /**
    * 通过角色查询有权限的数据字典
    * @param model
    * @param roleId
    * @return
    */
   @RequestMapping("/queryDatadic.d")
   @SuppressWarnings("unused")
   @ViewWrapper(wrapped = false)
   public String queryDatadic(Model model,String roleId){
       if(StringUtils.isNotEmpty(roleId)){
        String userId = AppContext.getContext().getContextUserId();
        UserVO userVO = userQueryPubService.load(userId);
        //当前系统登录用户有权限的维度
        List<DataLicenseVO> queryDataDicList = new ArrayList<DataLicenseVO>();
        //维度信息，tab页签
        List<Map<String, String>> dataDicList = new ArrayList<>();
        List<String> dicIdsList = new ArrayList<String>();
        //是超级管理员
        if (userVO.getUsertype() == GrantConst.USERTYPE_ADMIN) {
            //查询所有已被数据源关联的维度
            dicIdsList = dataDicPowerService.queryAllDataDic();
            if(!(dicIdsList!=null && dicIdsList.size()>0)){
                model.addAttribute("dicList", null);
                return "datalicense/dataauth/dic_main";
//                throw new BusinessException("当前角色无有权限的数据");
            }
        } else {
            List<RoleVO> queryRoleList = userRoleQueryPubService.queryRoleListByUserId(userId);
            List<String> roleIdList = new ArrayList<String>();
            if (queryRoleList != null && queryRoleList.size() > 0) {
                for (RoleVO roleVO : queryRoleList) {
                    roleIdList.add(roleVO.getId());
                }
                //查询当前系统登录用户有权限的数据字典
                queryDataDicList = dataLicenseService.queryDataDicByRoleIds(roleIdList);
                if (queryDataDicList != null && queryDataDicList.size() > 0) {
                    Set<String> dataDicIdSet = new HashSet<String>();
                    for (DataLicenseVO dataVO : queryDataDicList) {
                        dataDicIdSet.add(dataVO.getPk_dic());
                    }
                    //将set转成list
                    Iterator it = dataDicIdSet.iterator();
                    while (it.hasNext()) {
                        dicIdsList.add((String) it.next());
                    }
                }else{
                    model.addAttribute("dicList", null);
                    return "datalicense/dataauth/dic_main";
//                	throw new BusinessException("当前角色无有权限的数据");
                }
            }
        }
        //根据维度id查询维度的具体信息
        dataDicList = dataDicService.queryDataDicDetail(dicIdsList);
        //根据维度查询维值，所有可以展现的
//        List<Map<String, Map>>  dimvalList = dataDicService.queryDataDicVal(dicIdsList); 
//        List<String> roleIdsList = new ArrayList<String>();
//        roleIdsList.add(roleId);
        //根据当前点击角色的id查询有权限的维值
//        List<DataLicenseVO> dataLicenseRltList = dataLicenseService.queryDataDicByRoleId(roleIdsList);
        //对点击角色有权限的数据打勾
//        List<Map<String, Map>> powerChecked = dataLicenseService.powerChecked(dimvalList, dataLicenseRltList);
        model.addAttribute("dicList", dataDicList);
//        model.addAttribute("dicTree", powerChecked);
        model.addAttribute("roleId",roleId);
       }else{
           model.addAttribute("dicList", null);
         //  model.addAttribute("dicTree", null);
       }
       return "datalicense/dataauth/dic_main";
   }
   @ResponseBody
   @RequestMapping("/getDimTree.d")
   public String getDimTree(String dicId,String roleId){
	   Map dimValueTree = dataLicenseService.getDimValueTree(dicId,roleId);
	   Map<String,String> nodeAttr = new HashMap<String,String>();
	   nodeAttr.put("text", "dimvl_name");
	   nodeAttr.put("disabled", "true");
	   nodeAttr.put("checked", "isChecked");
	   if(dimValueTree.size()!=0)
		   return   JsonUtils.formatMap2TreeViewJson(dimValueTree, nodeAttr);
	   else
		   return "";
	   
   }
   /**
    * 保存数据字典和角色的关联关系
    * @param dicId
    * @param dicvalId
    * @param roleId
    * @return
    */
   @RequestMapping("/saveDicValToRole.d")
   @ResponseBody
   @SuppressWarnings("deprecation")
   public Object saveDicValToRole(String dataValue, String roleId){
	   
		JSONObject jsonobject = JSONObject.fromObject(dataValue);
		if (jsonobject.size() == 0) {
			return new AjaxResult(AjaxResult.STATUS_ERROR, "未选中任何角色，没有可供保存的数据！");
		}
		// 将获取到的json串处理成map  Map<树ID,List<数据项ID>>
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (Object key : jsonobject.keySet()) {
			JSONArray valueArray = jsonobject.getJSONArray(key.toString());
			if (valueArray != null && valueArray.size() > 0) {
				List list = JSONArray.toList(valueArray);
				map.put(key.toString(), list);
			}
		}
		dataLicenseService.addDicValToRole(map, roleId);
		return AjaxResult.SUCCESS;
   }
   
}
