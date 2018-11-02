package com.gdsp.platform.log.web;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.excel.ExcelHelper;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.platform.common.utils.DateUtils;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.utils.GrantUtils;
import com.gdsp.platform.log.helper.PortalQueryConst;
import com.gdsp.platform.log.model.OperationLogVO;
import com.gdsp.platform.log.service.IAuthorityLogService;
import com.gdsp.platform.log.utils.CsvUtils;
import com.gdsp.platform.systools.datadic.model.RoleAuthorityVO;

@Controller
@RequestMapping("systools/authoritylog")
public class AuthorityLogController {
	@Autowired
    private IAuthorityLogService authorityLogService;

    /**
     * 加载权限记录列表
     * @param model
     * @return 加载页面
     */
    @RequestMapping("/urlist.d")
    public String list(Model model, Condition condition, Pageable page) {
//    	//查询用户角色列表
//    	List<UserVO> userRoleList = authorityLogService.getUserRoleList();
//    	if(userRoleList != null && userRoleList.size()>0){
//    		Page<UserVO> userRolePages = GrantUtils.convertListToPage(userRoleList, page);
//	    	model.addAttribute("UserRolePages", userRolePages);
//    	}else{
//    		model.addAttribute("UserRolePages", new PageImpl<UserVO>(new ArrayList<UserVO>(), page,0));
//    	}
//    	//查询角色功能列表
//    	List<RoleVO>  roleFuncList = authorityLogService.getRoleFuncList();
//    	if(roleFuncList != null && roleFuncList.size()>0){
//    		Page<RoleVO> roleFuncPages = GrantUtils.convertListToPage(roleFuncList, page);
//	    	model.addAttribute("RoleFuncPages", roleFuncPages);
//    	}else{
//    		model.addAttribute("RoleFuncPages", new PageImpl<RoleVO>(new ArrayList<RoleVO>(), page,0));
//    	}
//    	//查询用户功能列表
//    	List<UserVO>  userFuncList = new ArrayList<UserVO>();
//    	if(userRoleList != null && userRoleList.size() > 0){
//    		for (UserVO userVO : userRoleList) {
//    			if(roleFuncList != null && roleFuncList.size() > 0){
//    				for (RoleVO roleVO : roleFuncList) {
//            			UserVO userFuncVO = new UserVO();
//        				userFuncVO.setUsername(userVO.getUsername());
//        				userFuncVO.setAccount(userVO.getAccount());
//        				userFuncVO.setOrgname(userVO.getOrgname());
//        				userFuncVO.setFunname(roleVO.getFunname());
//            			if(roleVO.getRolename()!=null && roleVO.getRolename().equals(userVO.getRolename())){
//            				userFuncList.add(userFuncVO);
//            			}
//        			}
//    			}
//    		}
//    	}
//    	if(userFuncList != null && userFuncList.size()>0){
//    		Page<UserVO> userFuncPages = GrantUtils.convertListToPage(userFuncList, page);
//	       	model.addAttribute("UserFuncPages", userFuncPages);
//    	}else{
//    		model.addAttribute("UserFuncPages", new PageImpl<UserVO>(new ArrayList<UserVO>(), page,0));
//    	}
//    	//查询角色数据权限列表
//    	List<RoleAuthorityVO> roleDataLists = authorityLogService.getRoleDataLists();
//    	if(roleDataLists != null && roleDataLists.size()>0){
//    		Page<RoleAuthorityVO> roleDataLimitPages = GrantUtils.convertListToPage(roleDataLists, page);
//	       	model.addAttribute("RoleDataLimitPages", roleDataLimitPages);
//    	}else{
//    		model.addAttribute("RoleDataLimitPages", new PageImpl<RoleAuthorityVO>(new ArrayList<RoleAuthorityVO>(), page,0));
//    	}
//    	//查询用户数据权限列表
//    	List<RoleAuthorityVO>  userDataList = new ArrayList<RoleAuthorityVO>();
//    	if(userRoleList != null && userRoleList.size() > 0){
//    		for (UserVO userVO : userRoleList) {
//    			if(roleDataLists != null && roleDataLists.size() > 0){
//    				for (RoleAuthorityVO roleAuthorityVO : roleDataLists) {
//            			RoleAuthorityVO userDataVO = new RoleAuthorityVO();
//        				userDataVO.setUsername(userVO.getUsername());
//        				userDataVO.setAccount(userVO.getAccount());
//        				userDataVO.setOrgname(userVO.getOrgname());
//        				userDataVO.setDic_name(roleAuthorityVO.getDic_name());
//        				userDataVO.setDimvl_name(roleAuthorityVO.getDimvl_name());
//        				if(userVO.getRolename()!=null&& userVO.getRolename().equals(roleAuthorityVO.getRolename())){
//        					userDataList.add(userDataVO);
//        				}
//            		}
//    			}
//    		}
//    	}
//    	if(userDataList != null && userDataList.size()>0){
//    		Page<RoleAuthorityVO> userDataLimitPages = GrantUtils.convertListToPage(userDataList, page);
//	       	model.addAttribute("UserDataLimitPages", userDataLimitPages);
//    	}else{
//    		model.addAttribute("UserDataLimitPages",new PageImpl<RoleAuthorityVO>(new ArrayList<RoleAuthorityVO>(), page, 0));
//    	}
    	return "systools/authoritylog/user-role-list";
    }
    @RequestMapping("/urlistData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model,Condition condition, Pageable page,String uaoParam,String rname){
    	//条件查询用户角色列表
    	List<UserVO>  selectUserList = new ArrayList<UserVO>();
    	selectUserList = authorityLogService.queryUserRoleList(uaoParam,rname);
    	if(selectUserList != null && selectUserList.size()>0){
    		Page<UserVO> userRolePages = GrantUtils.convertListToPage(selectUserList, page);
	    	model.addAttribute("UserRolePages", userRolePages);
    	}
    	return "systools/authoritylog/list-data";
    }
    
    @RequestMapping("/rflistData.d")
    @ViewWrapper(wrapped = false)
    public String rflistData(Model model,Condition condition, Pageable page,String rolename,String funcname){
    	//条件查询角色功能列表
    	List<RoleVO>  selectRoleFuncList = new ArrayList<RoleVO>();
    	selectRoleFuncList = authorityLogService.queryRoleFuncList(rolename,funcname);
    	if(selectRoleFuncList != null && selectRoleFuncList.size()>0){
    		Page<RoleVO> roleFuncPages = GrantUtils.convertListToPage(selectRoleFuncList, page);
	    	model.addAttribute("RoleFuncPages", roleFuncPages);
    	}
    	return "systools/authoritylog/role-func-data";
    }
    @RequestMapping("/uflistData.d")
    @ViewWrapper(wrapped = false)
    public String uflistData(Model model,Condition condition, Pageable page,String uaoParameter,String functionname){
    	//条件查询用户功能列表
    	List<UserVO>  selectUserFuncList = new ArrayList<UserVO>();
    	selectUserFuncList = authorityLogService.queryUserFuncList(uaoParameter,functionname);
    	if(selectUserFuncList != null && selectUserFuncList.size()>0){
    		Page<UserVO> userFuncPages = GrantUtils.convertListToPage(selectUserFuncList, page);
	       	model.addAttribute("UserFuncPages", userFuncPages);
    	}
    	return "systools/authoritylog/user-func-data";
    }
    
    @RequestMapping("/ralistData.d")
    @ViewWrapper(wrapped = false)
    public String ralistData(Model model,Condition condition, Pageable page,String rlname,String datalimited){
    	//条件查询角色数据权限列表
    	List<RoleAuthorityVO>  selectRoleDataList = new ArrayList<RoleAuthorityVO>();
    	selectRoleDataList = authorityLogService.queryRoleDataList(rlname,datalimited);
    	if(selectRoleDataList != null && selectRoleDataList.size()>0){
    		Page<RoleAuthorityVO> roleDataLimitPages = GrantUtils.convertListToPage(selectRoleDataList, page);
	       	model.addAttribute("RoleDataLimitPages", roleDataLimitPages);
    	}
    	return "systools/authoritylog/role-limit-data";
    }
    
    @RequestMapping("/ualistData.d")
    @ViewWrapper(wrapped = false)
    public String ualistData(Model model,Condition condition, Pageable page,String usaoParam,String dataauthority){
    	//条件查询用户数据权限列表
    	List<RoleAuthorityVO>  selectUserDataList = new ArrayList<RoleAuthorityVO>();
    	selectUserDataList = authorityLogService.queryUserDataList(usaoParam,dataauthority);
    	if(selectUserDataList != null && selectUserDataList.size()>0){
    		Page<RoleAuthorityVO> userDataLimitPages = GrantUtils.convertListToPage(selectUserDataList, page);
	       	model.addAttribute("UserDataLimitPages", userDataLimitPages);
    	}
    	return "systools/authoritylog/user-limit-data";
    }
    
    /**
     * 导出用户角色权限详情
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/doExportUserRoleModel.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public void doExportUserRoleModel(HttpServletResponse response, HttpServletRequest request,Condition condition,String accountQueryParam,String roleParam) throws IOException{
    	List<String> headList = new ArrayList<String>();
    	headList.add("用户名");
    	headList.add("账号");
    	headList.add("所属机构");
    	headList.add("拥有角色");
    	List<UserVO> userRoleList = new ArrayList<UserVO>();
    	userRoleList = authorityLogService.queryUserRoleList(accountQueryParam,roleParam);
    	List<List<Object>> selectUserRoleList = new ArrayList<List<Object>>();
    	if(userRoleList != null && userRoleList.size()>0){
	    	for (UserVO userVO : userRoleList) {
	    		List<Object> arg = new ArrayList<Object>();
	    		arg.add(userVO.getUsername());
	    		arg.add(userVO.getAccount());
	    		arg.add(userVO.getOrgname());
	    		arg.add(userVO.getRolename());
	    		selectUserRoleList.add(arg);
			}
    	}
    	String fileName = "用户角色权限.xls";
    	String sheetName = 	"用户角色权限信息";
    	this.exportExcelFile(fileName, sheetName, selectUserRoleList, headList, response);
    }
    
    /**
     * 导出角色功能权限详情
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/doExportRoleFuncModel.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public void doExportRoleFuncModel(HttpServletResponse response, HttpServletRequest request,Condition condition,String roleParam,String funcParam) throws IOException{
    	List<String> headList = new ArrayList<String>();
    	headList.add("角色名");
    	headList.add("拥有功能");
    	List<RoleVO>  roleFuncList = new ArrayList<RoleVO>();
    	roleFuncList = authorityLogService.queryRoleFuncList(roleParam,funcParam);
    	List<List<Object>> selectRoleFuncList = new ArrayList<List<Object>>();
    	if(roleFuncList != null && roleFuncList.size()>0){
    		for (RoleVO roleVO : roleFuncList) {
    			List<Object> arg = new ArrayList<Object>();
    			arg.add(roleVO.getRolename());
    			arg.add(roleVO.getFunname());
    			selectRoleFuncList.add(arg);
			}
    	}
    	String fileName = "角色功能权限.xls";
    	String sheetName = 	"角色功能权限信息";
    	this.exportExcelFile(fileName, sheetName, selectRoleFuncList, headList, response);
    }
    
    /**
     * 导出用户角色权限详情
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/doExportUserFuncModel.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public void doExportUserFuncModel(HttpServletResponse response, HttpServletRequest request,Condition condition,String accountQueryParam,String funcParam) throws IOException{
    	List<String> headList = new ArrayList<String>();
    	headList.add("用户名");
    	headList.add("账号");
    	headList.add("所属机构");
    	headList.add("拥有功能");
    	List<UserVO> userFuncList = authorityLogService.queryUserFuncList(accountQueryParam,funcParam);
    	List<List<Object>> selectUserFuncList = new ArrayList<List<Object>>();
    	if(userFuncList != null && userFuncList.size()>0){
    		for (UserVO userVO : userFuncList) {
    			List<Object> arg = new ArrayList<Object>();
    			arg.add(userVO.getUsername());
    			arg.add(userVO.getAccount());
    			arg.add(userVO.getOrgname());
    			arg.add(userVO.getFunname());
    			selectUserFuncList.add(arg);
			}
    	}
    	String fileName = "用户功能权限.xls";
    	String sheetName = 	"用户功能权限信息";
    	this.exportExcelFile(fileName, sheetName, selectUserFuncList, headList, response);
    }
    
    /**
     * 导出角色数据权限详情
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/doExportRoleDataModel.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public void doExportRoleDataModel(HttpServletResponse response, HttpServletRequest request,Condition condition,String roleParam,String dataParam) throws IOException{
    	List<String> headList = new ArrayList<String>();
    	headList.add("角色名");
    	headList.add("数据维度");
    	headList.add("数据维度值");
    	List<RoleAuthorityVO> roleDataList = authorityLogService.queryRoleDataList(roleParam,dataParam);
    	List<List<Object>> selectRoleDataList = new ArrayList<List<Object>>();
    	if(roleDataList != null && roleDataList.size()>0){
    		for (RoleAuthorityVO roleAuthorityVO : roleDataList) {
    			List<Object> arg = new ArrayList<Object>();
    			arg.add(roleAuthorityVO.getRolename());
    			arg.add(roleAuthorityVO.getDic_name());
    			arg.add(roleAuthorityVO.getDimvl_name());
    			selectRoleDataList.add(arg);
			}
    	}
    	String fileName = "角色数据权限.xls";
    	String sheetName = 	"角色数据权限信息";
    	this.exportExcelFile(fileName, sheetName, selectRoleDataList, headList, response);
    }
    
    /**
     * 导出用户数据权限详情
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/doExportUserDataModel.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public void doExportUserDataModel(HttpServletResponse response, HttpServletRequest request,Condition condition,String accountQueryParam,String dataParam) throws IOException{
    	List<String> headList = new ArrayList<String>();
    	headList.add("用户名");
    	headList.add("账号");
    	headList.add("所属机构");
    	headList.add("数据维度");
    	headList.add("数据维度值");
    	List<RoleAuthorityVO> userDataList = authorityLogService.queryUserDataList(accountQueryParam,dataParam);
    	List<List<Object>>  selectUserDataList = new ArrayList<List<Object>> ();
    	if(userDataList != null && userDataList.size()>0){
    		for (RoleAuthorityVO roleAuthorityVO :userDataList) {
    			List<Object> arg = new ArrayList<Object>();
    			arg.add(roleAuthorityVO.getUsername());
    			arg.add(roleAuthorityVO.getAccount());
    			arg.add(roleAuthorityVO.getOrgname());
    			arg.add(roleAuthorityVO.getDic_name());
    			arg.add(roleAuthorityVO.getDimvl_name());
    			selectUserDataList.add(arg);
			}
    	}
    	String fileName = "用户数据权限.xls";
    	String sheetName = 	"用户数据权限信息";
    	this.exportExcelFile(fileName, sheetName, selectUserDataList, headList, response);
    }
    
    public void exportExcelFile(String fileName,String sheetName,List<List<Object>> list,List<String> columnHeaderList,HttpServletResponse response) throws IOException{
    	ExcelHelper util = new ExcelHelper();
        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        response.reset();
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream out = response.getOutputStream();
        util.exportExcel(list, sheetName,columnHeaderList,60000,out);
        out.flush();
    }
    
}

