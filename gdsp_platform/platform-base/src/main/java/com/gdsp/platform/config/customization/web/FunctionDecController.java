package com.gdsp.platform.config.customization.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.PageSerImpl;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.ChainLogicExpression;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.model.query.ValueExpression;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.common.utils.DateStyle;
import com.gdsp.platform.common.utils.DateUtils;
import com.gdsp.platform.config.customization.model.FuncDecVO;
import com.gdsp.platform.config.customization.model.FunctionDecVO;
import com.gdsp.platform.config.customization.service.IFuncDecService;
import com.gdsp.platform.config.customization.service.IFunctionDecService;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * 功能说明
 * @author songxiang
 * @date 2015年10月28日 上午11:18:11
 */
@Controller
@RequestMapping("portal/functionDec")
public class FunctionDecController {

	@Autowired
	private IFunctionDecService functionService;
	@Autowired
	private IFuncDecService funcDecService;
	@Autowired
	private IPowerMgtQueryPubService powerMgtQueryPubService;
	
//	private List<String> powerMenuPks = new ArrayList<String>();
//	private String pk_user;

	/** 日志记录 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping("/adminList.d")
	public String adminList(Model model, Condition condition, Pageable pageable) {
		Sorter sort = new Sorter(Direction.ASC, "innercode", "dispcode");
		Map voTreeMap = functionService.queryMenuRegisterVOsByCondReturnMap(
				condition, sort);
		// 初始化page对象
		List list = new ArrayList();
		Page<FunctionDecVO> functionPages = new PageSerImpl<FunctionDecVO>(list,pageable,list.size());
		
		initPowerMenuPks(functionPages.getContent());
		model.addAttribute("functionPages", functionPages);
		model.addAttribute("voTreeMap", voTreeMap);
		UserVO user = (UserVO) AppContext.getContext().getContextUser();
		model.addAttribute("usertype", user.getUsertype());
		return "funcdesc/adminList";
	}
	
	@RequestMapping("/getTree.d")
	@ResponseBody
	public String getTree(Condition condition){
	    Sorter sort = new Sorter(Direction.ASC, "innercode", "dispcode");
        Map voTreeMap = functionService.queryMenuRegisterVOsByCondReturnMap(
                condition, sort);
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "funname");
        nodeAttr.put("checked", "isChecked");
        if(voTreeMap.size()!=0){
            return   JsonUtils.formatMap2TreeViewJson(voTreeMap, nodeAttr);
        } else {
            return "";
        }
	}

	@RequestMapping("/decideFunType.d")
	@ResponseBody
	public Object decideFunType(String id){
		FunctionDecVO functionDecVO =functionService.loadFunctionDecVOById(id);
		int funtype = functionDecVO.getFuntype();
		if(funtype ==2 || funtype ==3 || funtype ==4){
			return AjaxResult.STATUS_SUCCESS;
		}
		else{
			return AjaxResult.STATUS_ERROR;
		}
	}

	/**
	 * 
	 * @Title: queryMenuDetail
	 * @Description: (展示节点类型为2,3,4的数据)
	 * @param model
	 * @param id
	 * @param condition
	 * @param pageable
	 * @return 参数说明
	 * @return String 返回值说明
	 * 	 */
	@RequestMapping("/queryMenuAdminDetail.d")
	@ViewWrapper(wrapped = false)
	public String queryMenuAdminDetail(Model model, String id,Condition condition, Pageable pageable) {
		ChainLogicExpression ch = new ChainLogicExpression();
		ch.setOr(true);
		ValueExpression a = new ValueExpression("funtype", 2);
		ValueExpression b = new ValueExpression("funtype", 3);
		ValueExpression c = new ValueExpression("funtype", 4);
		ch.addExpression(a, b, c);
		condition.addExpressions(ch);
		if (id != null) {
			condition.addExpression("parentid", id);
		}
		Page<FunctionDecVO> functionPages = functionService.queryFunctionDecVOsPages(condition, pageable);
		initPowerMenuPks(functionPages.getContent());
		model.addAttribute("functionPages", functionPages);
		UserVO user = (UserVO) AppContext.getContext().getContextUser();
		model.addAttribute("usertype", user.getUsertype());
		model.addAttribute("parentid", id);
		return "funcdesc/adminList-data";

	}

	/**
	 * @Title: view
	 * @Description: (功能图片展示)
	 * @param model
	 * @param id
	 * @return 参数说明
	 * @return String 返回值说明
	 */
	@RequestMapping("/adminView.d")
	@ViewWrapper(wrapped = false)
	public String adminView(Model model, String id) {
		
		model.addAttribute("functionDecVO",isMenuPower(functionService.loadFunctionDecVOById(id)));
		
		List<FuncDecVO> funcDecVO = funcDecService
				.queryFuncDecListByCondition(id);
		if(funcDecVO != null && funcDecVO.size() == 1){
		    FuncDecVO funcDecVo = funcDecVO.get(0);
		    model.addAttribute("funcDecVOs",funcDecVo);
		}
		UserVO user = (UserVO) AppContext.getContext().getContextUser();
		model.addAttribute("usertype", user.getUsertype());
		model.addAttribute("upload",
				AppConfig.getInstance().getString("portal.functiondec.images.addr"));
		return "funcdesc/adminList-view";
	}

	/**
	 * 
	 * @Title: list
	 * @Description: (页面加载)
	 * @param model
	 * @param condition
	 * @return 参数说明
	 * @return String 返回值说明
	 * 	 */
	@RequestMapping("/list.d")
	public String list(Model model, Condition condition, Pageable pageable) {
//		Sorter sort = new Sorter(Direction.ASC, "innercode", "dispcode");
//		Map voTreeMap = functionService.queryMenuRegisterVOsByCondReturnMap(
//				condition, sort);
//		// 初始化page对象
//		List list = new ArrayList();
//		Page<FunctionDecVO> functionPages = new PageSerImpl<FunctionDecVO>(list,pageable,list.size());
//		initPowerMenuPks(functionPages.getContent());
//		model.addAttribute("functionPages", functionPages);
//		model.addAttribute("voTreeMap", voTreeMap);
		UserVO user = (UserVO) AppContext.getContext().getContextUser();
		model.addAttribute("usertype", user.getUsertype());
		return "funcdesc/list";
	}

	/**
	 * @Title: queryMenuDetail
	 * @Description: (展示节点类型为2,3,4的数据)
	 * @param id
	 * @param condition
	 * @param pageable
	 */
	@RequestMapping("/queryMenuDetail.d")
	@ViewWrapper(wrapped = false)
	public String queryMenuDetail(Model model, String id, Condition condition,Pageable pageable) {
		ChainLogicExpression ch = new ChainLogicExpression();
		ch.setOr(true);
		ValueExpression a = new ValueExpression("funtype", 2);
		ValueExpression b = new ValueExpression("funtype", 3);
		ValueExpression c = new ValueExpression("funtype", 4);
		ch.addExpression(a, b, c);
		condition.addExpressions(ch);
		if (id != null) {
			condition.addExpression("parentid", id);
		}
		Page<FunctionDecVO> functionPages = functionService
				.queryFunctionDecVOsPages(condition, pageable);
		initPowerMenuPks(functionPages.getContent());
		model.addAttribute("functionPages", functionPages);
		UserVO user = (UserVO) AppContext.getContext().getContextUser();
		model.addAttribute("usertype", user.getUsertype());
		model.addAttribute("parentid", id);
		return "funcdesc/list-data";

	}

	/**
	 *跳转到上传页面
	 * @param model
	 * @param id
	 * @return 参数说明
	 */
	@RequestMapping("/upload.d")
	@ViewWrapper(wrapped = false)
	public String upload(Model model, String id) {
		List<FuncDecVO> funcDecVO = funcDecService
				.queryFuncDecListByCondition(id);
		model.addAttribute("funcDecVO", funcDecVO);
		model.addAttribute("menuid", id);
		return "funcdesc/upload";
	}

	@RequestMapping("/listtagrid.d")
	@ViewWrapper(wrapped = false)
	public String listtagrid(Model model, String id) {
		List<FuncDecVO> funcDecVO = funcDecService
				.queryFuncDecListByCondition(id);
		model.addAttribute("funcDecVO", funcDecVO);
		return "funcdesc/list-tagrid";
	}

	/**
	 * 上传图片
	 * @param model
	 * @param request
	 * @param functionDecVO
	 * @param funcDecVO
	 * @param menuid
	 */
	@RequestMapping("/doUpload.d")
	public String doUpload(Model model, HttpServletRequest request,
			FunctionDecVO functionDecVO, FuncDecVO funcDecVO, String menuid) throws IOException {
		MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = mulRequest.getFile("fileName");
		String path = AppConfig.getInstance().getString(
				"portal.functiondec.images.addr");
		// String path=
		// request.getServletContext().getRealPath(AppConfig.getInstance().getString(AppConfigConst.PORTAL_FUNCTIONDEC_IMAGES_ADDR));
		int begin = file.getOriginalFilename().lastIndexOf(".");
		String str = file.getOriginalFilename().substring(0, begin);
		String str1 = file.getOriginalFilename().substring(
				file.getOriginalFilename().lastIndexOf("."));
		String strName = file.getOriginalFilename().substring(
				file.getOriginalFilename().lastIndexOf(".") + 1);
		if (StringUtils.isNotBlank(strName) && !strName.equals("jpg")
				&& !strName.equals("gif") && !strName.equals("png")
				&& !strName.equals("jpeg")) {
			throw new BusinessException("请上传正确的图片格式");
		}
		functionDecVO = functionService.loadFunctionDecVOById(menuid);
		String funcode = functionDecVO.getFuncode();
		String time = DateUtils.DateToString(new Date(),
				DateStyle.YYYYMMDDHHMMSS);
		String fileName = str + "_" + funcode + "_" + time + str1;
		InputStream inputStream = null;
		OutputStream outputStream = null;
	
		inputStream = file.getInputStream();
		outputStream = new FileOutputStream(new File(path,fileName));
		byte[] b = new byte[1024];
		int i = -1;
		while ((i = inputStream.read(b)) > -1) {
			outputStream.write(b, 0, i);
		}
		outputStream.close();
		inputStream.close();
		
		funcDecVO.setName(fileName);
		funcDecVO.setFileUrl(fileName);
		funcDecService.save(funcDecVO);
		
		return "redirect:../../portal/functionDec/adminList.d";
	}

	@RequestMapping("/readFile.d")
	@ViewWrapper(wrapped = false)
	public String downLoad(Model model, HttpServletResponse response,
			HttpServletRequest request, String fileUrl) throws IOException {
		String path = AppConfig.getInstance().getString(
				"portal.functiondec.images.addr");
		File file = new File(path + fileUrl);
		
		OutputStream os = response.getOutputStream();
		FileInputStream fs = new FileInputStream(file);
		byte bytes[] = new byte[1024];
		int len = 0;
		while ((len = fs.read(bytes)) != -1) {
			os.write(bytes, 0, len);
		}
		os.close();
		fs.close();
		response.getOutputStream();
		
		return null;
	}

	/**
	 * 功能图片展示
	 * @param model
	 * @param id
	 * @return String 返回值说明
	 */
	@RequestMapping("/view.d")
	@ViewWrapper(wrapped = false)
	public String view(Model model, String id) {
		model.addAttribute("functionDecVO",
				isMenuPower(functionService.loadFunctionDecVOById(id)));
		List<FuncDecVO> funcDecVO = funcDecService
				.queryFuncDecListByCondition(id);
		if(funcDecVO != null && funcDecVO.size() == 1){
            FuncDecVO funcDecVo = funcDecVO.get(0);
            model.addAttribute("funcDecVOs",funcDecVo);
        }
		UserVO user = (UserVO) AppContext.getContext().getContextUser();
		model.addAttribute("usertype", user.getUsertype());
		model.addAttribute(
				"upload",
				AppConfig.getInstance().getString(
						"portal.functiondec.images.addr"));
		// model.addAttribute("upload",
		// AppConfig.getInstance().getString(AppConfigConst.PORTAL_FUNCTIONDEC_IMAGES_ADDR));
		return "funcdesc/list-view";
	}

	/**
	 * 删除功能
	 * @param id
	 * @return Object 返回值说明
	 */
	/*@RequestMapping("/delete.d")
	@ResponseBody
	public Object delete(String id) {
		funcDecService.delete(id);
		return AjaxResult.SUCCESS;
	}*/

	/**
	 * 导出功能说明配置
	 * @param model
	 * @param condition
	 * @return 参数说明
	 * @return String 返回值说明
	 * 	 */
	/*@RequestMapping("/exportFunDec.d")
	@ViewWrapper(wrapped = false)
	public String exportFunDec(Model model, Condition condition) {
		// 构建树
		Sorter sort = new Sorter(Direction.ASC, "innercode", "dispcode");
		Map voTreeMap = functionService.queryMenuRegisterVOsByCondReturnMap(
				condition, sort);
		model.addAttribute("voTreeMap", voTreeMap);
		return "funcdesc/exportFunDec";
	}*/

	/**
	 * 功能说明导出
	 * @param model
	 * @param response
	 * @param commonDirId
	 * @return String 返回值说明
	 */
	/*@RequestMapping("/doExportFunDec.d")
	public String doExportFunDec(Model model, HttpServletResponse response,
			String commonDirId) throws IOException {
		List<FuncDecDataIOVO> list = functionService
				.findExportList(commonDirId);
		// 文件下载（添加的配置）
		response.reset();
		OutputStream outputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		String filename = "功能说明信息（"
				+ DateUtils.DateToString(new Date(), DateStyle.YYYYMMDDHHMMSS)
				+ "）";
		filename = new String(filename.getBytes("gbk"), "iso-8859-1");
		response.setHeader("Content-disposition", "attachment;filename="+ filename + ".xls");
		response.setBufferSize(1024);
		// 导出
		ExcelHelper<FuncDecDataIOVO> e = new ExcelHelper<>(FuncDecDataIOVO.class);
		if (list != null && list.size() > 0)
			e.exportExcel(list, "功能说明信息", 0, outputStream);
		else
			outputStream.write("无数据".getBytes("GBK"));
		return null;
	}
*/
	/**
	 * 跳转到排序页面
	 * @param model
	 * @param menuid
	 * @param condition
	 * @return String 返回值说明
	 */
	/*@RequestMapping("/toSort.d")
	@ViewWrapper(wrapped = false)
	public String toSort(Model model, String menuid, Condition condition) {
		List<FuncDecVO> list = funcDecService.findListByMenuId(menuid,condition);
		if (list.size() == 0) {
			throw new BusinessException("排序内容为空。");
		}
		List<SortDataVO> sortdats = new ArrayList<SortDataVO>();
		Iterator<FuncDecVO> it = list.iterator();
		while (it.hasNext()) {
			FuncDecVO type = (FuncDecVO) it.next();
			SortDataVO sort = new SortDataVO();
			sort.setId(type.getId());
			sort.setSortNum(type.getSortnum());
			sort.setItemDesc(type.getName());
			sortdats.add(sort);
		}
		model.addAttribute("datas", sortdats);
		return "funcdesc/sort";
	}*/

	/**
	 * @Description: (排序)
	 * @param model
	 * @param sortvo
	 * @return
	 */
	/*@RequestMapping("/doSort.d")
	@ResponseBody
	public Object doSort(Model model, SortDataVO sortvo) {
		String ids = sortvo.getId();
		List<FuncDecVO> funcDecVO = new ArrayList<FuncDecVO>();
		if (!StringUtils.isEmpty(ids)) {
			String[] idarray = ids.split(",");
			FuncDecVO parametervo = null;
			for (int i = 0; i < idarray.length; i++) {
				parametervo = new FuncDecVO();
				parametervo.setId(idarray[i]);
				parametervo.setSortnum(i);
				String loginName = AppContext.getContext().getContextUserId();
				DDateTime current = new DDateTime();
				// 创建新对象
				parametervo.setLastModifyTime(current);
				parametervo.setLastModifyBy(loginName);
				funcDecVO.add(parametervo);
			}
			funcDecService.batchUpdate(funcDecVO);
		}
		return AjaxResult.SUCCESS;
	}*/

	/**
	 * 
	 * @Title: importFunDec
	 * @Description: (跳转到功能导入页面)
	 * @param model
	 * @return 参数说明
	 * @return String 返回值说明
	 */
	/*@RequestMapping("/importFunDec.d")
	@ViewWrapper(wrapped = false)
	public String importFunDec(Model model) {
		return "funcdesc/importFunDec";
	}
*/
	/**
	 * 导入功能
	 * @param request
	 * @param model
	 */
	/*@RequestMapping("/doImportFunDec.d")
	public String doImportFunDec(HttpServletRequest request, Model model) {
		MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = mulRequest.getFile("excelFile");
		// 导入校验
		String originalFilename = file.getOriginalFilename();
		String suffixName = originalFilename.substring(originalFilename
				.lastIndexOf(".") + 1);
		if (StringUtils.isNotBlank(suffixName) && !suffixName.equals("xls")
				&& !suffixName.equals("xlsx")) {
			throw new BusinessException("请上传正确的excel文件");
		}
		KpiImport imp = new KpiImport(FuncDecDataIOVO.class);
		List<FuncDecDataIOVO> vos = imp.doImport("功能说明导入", file);
		if (vos == null || vos.size() == 0) {
			throw new BusinessException("导入的数据为空");
		}
		funcDecService.importFuncDec(vos);
		return "redirect:../../portal/functionDec/adminList.d";
	}*/
	
	@RequestMapping("/saveText.d")
	@ResponseBody
	public Object saveText(String menuId,String menuName,String content){
		//对oracle数据库的不同编码集分别进行了长度效验
		if(funcDecService.findDataSourceNlslang(content)){
			return new AjaxResult(AjaxResult.STATUS_ERROR, "您保存内容过长,最大支持500个字节");
		}
		List<FuncDecVO> funcDecVO = funcDecService.findListByMenuId(menuId, null);
	    if(funcDecVO != null && funcDecVO.size() == 1){
	        //维护更新
	        funcDecVO.get(0).setMemo(content);
            funcDecService.save(funcDecVO.get(0));
	    }else{
	        //添加
	        FuncDecVO newFuncDecVO = new FuncDecVO();
	        newFuncDecVO.setMemo(content);
	        newFuncDecVO.setMenuid(menuId);
	        newFuncDecVO.setName(menuName+"功能说明");
	        funcDecService.save(newFuncDecVO);
	    }
	    return new AjaxResult(AjaxResult.STATUS_SUCCESS,"保存成功！");
	}

	/**
	 * 初始化功能说明配置的权限（有/无权限）
	 */
	private void initPowerMenuPks(List<FunctionDecVO> lisvo) {
		
		List<String> powerMenuCodes = queryPowerMenuCodes();
		if (lisvo != null && lisvo.size() > 0) {
			Iterator<FunctionDecVO> it = lisvo.iterator();
			while (it.hasNext()) {
				FunctionDecVO vo = it.next();
				vo.setIspower(powerMenuCodes.contains(vo.getFuncode()) ? "Y" : "N");
			}
		}
	}

	/***
	 * 查询当前登录用户有权限的菜单编码集合
	 * @return
	 * @author wqh
	 * @since 2017年3月31日
	 */
	private List<String> queryPowerMenuCodes() {
		String userID = AppContext.getContext().getContextUserId();
		// 查询用户有权限的菜单
		List<MenuRegisterVO> list = powerMgtQueryPubService.queryMenuListByUser(userID);
		// 有权限的菜单编码
		List<String> powerMenuCodes = new ArrayList<String>();
		if (list != null && list.size() > 0) {
			Iterator<MenuRegisterVO> it = list.iterator();
			while (it.hasNext()) {
				MenuRegisterVO vo = it.next();
				powerMenuCodes.add(vo.getFuncode());
			}
		}
		return powerMenuCodes;
	}

	/**
	 * 点击每个管理菜单的时候判断是否有菜单权限
	 * @Description: (判断是否有权限)
	 * @param @param funcode
	 * @return boolean 返回类型
	 */
	private FunctionDecVO isMenuPower(FunctionDecVO decvo) {
		List powerMenuCodes = queryPowerMenuCodes();
		decvo.setIspower(powerMenuCodes.contains(decvo.getFuncode()) ? "Y" : "N");
		return decvo;
	}
}
