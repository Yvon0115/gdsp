/**
 * 
 */
package com.gdsp.ptbase.appcfg.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.data.json.Json2ObjectMapper;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.common.model.SortDataVO;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.platform.func.helper.FuncConst;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.model.PageRegisterVO;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.func.service.IPageRegisterService;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.grant.org.web.OrgController;
import com.gdsp.platform.grant.utils.GrantUtils;
import com.gdsp.ptbase.appcfg.helper.AppConfigConst;
import com.gdsp.ptbase.appcfg.model.CommonDirVO;
import com.gdsp.ptbase.appcfg.model.CommonDirsVO;
import com.gdsp.ptbase.appcfg.model.LayoutColumnVO;
import com.gdsp.ptbase.appcfg.model.PageVO;
import com.gdsp.ptbase.appcfg.model.PageWidgetVO;
import com.gdsp.ptbase.appcfg.model.WidgetActionVO;
import com.gdsp.ptbase.appcfg.service.ICommonDirService;
import com.gdsp.ptbase.appcfg.service.ILayoutColumnService;
import com.gdsp.ptbase.appcfg.service.IPageService;
import com.gdsp.ptbase.appcfg.service.IPageWidgetService;
import com.gdsp.ptbase.appcfg.service.IWidgetActionService;
import com.gdsp.ptbase.appcfg.service.IWidgetQueryService;

/**
 * @author lian.yf
 *
 */
@RequestMapping("/appcfg/pagecfg")
@Controller
public class PageConfgController {
	private static final Logger logger = LoggerFactory.getLogger(OrgController.class);
	@Resource
    private ICommonDirService    pdService;
    @Resource
    private IPageService         pService;
    @Resource
    private IWidgetQueryService  wqService;
    @Resource
    private IDefDocService       ddService;
    @Resource
    private IPageWidgetService   pwSeervice;
    @Resource
    private IPowerMgtQueryPubService   powerMgtQueryPubService;
    @Autowired
    private IMenuRegisterService munuRegisterService;
    @Autowired
    private IPageRegisterService pageRegisterService;
    @Autowired
    private IWidgetActionService widgetActionService;
    @Autowired
    private ILayoutColumnService layoutColumnService;
    
    
    @RequestMapping("/list.d")
    public String list(Model model){
    	return "appcfg/pagecfg/page_list";
    }
    
    //加载目录树
    @RequestMapping("/toDirTree.d")
    @ResponseBody
    @ViewWrapper(wrapped=false,onlyAjax=true)
    public Object loadDirTree(){
    	Map<String, List<CommonDirsVO>> dirs = pdService.queryDirs();
    	Map<String,String> nodeAttr = new HashMap<String,String>();
    	nodeAttr.put("text", "name");
    	if(dirs.size()!=0){
    		return JsonUtils.formatMap2TreeViewJson(dirs, nodeAttr);
    	}else{
    		return "";
    	}
    }
    
    //返回到目录树所在页面
    @RequestMapping("/listDirsData.d")
    @ViewWrapper(wrapped=false)
    public String loadDirsData(){
    	return "appcfg/pagecfg/dirs_tree";
    }
    
    //重新加载全部页面
    @RequestMapping("/reloadList.d")
    @ViewWrapper(wrapped=false)
    public String reloadList(){
    	return "appcfg/pagecfg/page_list";
    }
    
    //局部加载页面数据
    @RequestMapping("/listPageData.d")
    @ViewWrapper(wrapped=false)
    public String loadPageData(Model model,Pageable pageable,Condition condition,String dir_id){
    	Sorter spage = new Sorter(Direction.ASC, "sortnum");
    	List<PageVO> listVOs = pService.queryPageVOListByDirid(dir_id,condition,spage);
    	Page<PageVO> pages = GrantUtils.convertListToPage(listVOs, pageable);
    	model.addAttribute("pageVOs",pages);
    	return "appcfg/pagecfg/page_list_data";
    }
    
    //更改页面所在目录
    @RequestMapping("/changeFolder.d")
    @ViewWrapper(wrapped = false)
    public String changeFolder(Model model, String page_id) throws JSONException   {
        model.addAttribute("page_id", page_id);
        PageVO page = pService.load(page_id);
        model.addAttribute("dir_id", page.getDir_id());
        return "/appcfg/pagecfg/dialogs/change_fold";
    }
    
    //加载目录的树结构
    @RequestMapping("/toFolderTree.d")
    @ResponseBody
    @ViewWrapper(wrapped=false,onlyAjax=true)
    public Object loadFolderTree(){
    	Map<String, List<CommonDirsVO>> dirs = pdService.queryDirs();
    	Map<String,String> nodeAttr = new HashMap<String,String>();
    	nodeAttr.put("text", "name");
    	if(dirs.size()!=0){
    		return JsonUtils.formatMap2TreeViewJson(dirs, nodeAttr);
    	}else{
    		return "";
    	}
    }

    //保存页面对所在目录更改的结果
    @RequestMapping("/saveChgFolder.d")
    @ResponseBody
    public Object saveChgFolder(Model model, String dir_id, String page_id){
        PageVO page = pService.load(page_id);
        page.setDir_id(dir_id);
        int sortnum = pService.findMaxSortNum(dir_id);
        page.setSortnum(sortnum + 1);
        pService.update(page);
        return AjaxResult.SUCCESS;
    }
    
    //对页面进行排序
    @RequestMapping("/sortPage.d")
    @ViewWrapper(wrapped = false)
    public String sortPage(Model model, String dir_id){
    	Sorter spage = new Sorter(Direction.ASC, "sortnum");
        List<PageVO> list = pService.queryPageVOListByDirid(dir_id,null,spage);
        List<SortDataVO> sortdats = new ArrayList<SortDataVO>();
        if (list != null && list.size() > 0) {
            Iterator<PageVO> it = list.iterator();
            while (it.hasNext()) {
                PageVO type = (PageVO) it.next();
                SortDataVO sort = new SortDataVO();
                sort.setId(type.getId());
                sort.setSortNum(type.getSortnum());
                sort.setItemDesc(type.getPage_name());
                sortdats.add(sort);
            }
        }
        model.addAttribute("datas", sortdats);
        return "/appcfg/pagecfg/dialogs/sort";
    }
    
    //保存排序的结果
    @RequestMapping("/saveSort.d")
    @ResponseBody
    public Object saveSort(Model model, SortDataVO sortvo){
        //  pService.sort(page_id);
        String ids = sortvo.getId();
        List<PageVO> page = new ArrayList<PageVO>();
        if (!StringUtils.isEmpty(ids)) {
            String[] idarray = ids.split(",");
            PageVO parametervo = null;
            for (int i = 0; i < idarray.length; i++) {
                parametervo = new PageVO();
                parametervo.setId(idarray[i]);
                parametervo.setSortnum(i);
                String loginName = AppContext.getContext().getContextUserId();
                DDateTime current = new DDateTime();
                //创建新对象
                parametervo.setLastModifyTime(current);
                parametervo.setLastModifyBy(loginName);
                page.add(parametervo);
            }
            pService.batchUpdate(page);
        }
        return AjaxResult.SUCCESS;
    }
    
    //新建页面
    @RequestMapping("/newPage.d")
    @ViewWrapper(wrapped = false)
    public String newPage(Model model, String dir_id) {
        model.addAttribute("dir_id", dir_id);
        return "/appcfg/pagecfg/dialogs/new_page";
    }

    //保存新建的页面
    @RequestMapping("/savePage.d")
    @ResponseBody
    public Object savePage(Model model, PageVO page) {
        int sortnum = pService.findMaxSortNum(page.getDir_id());
        page.setSortnum(sortnum + 1);
        pService.insert(page);
        return AjaxResult.SUCCESS;
    }

    //新建目录
    @RequestMapping("/newFolder.d")
    @ViewWrapper(wrapped = false)
    public String newFolder(Model model, String parent_id){
        model.addAttribute("parent_id", parent_id);
        return "/appcfg/pagecfg/dialogs/new_folder";
    }

    //保存新建的目录
    @RequestMapping("/saveFolder.d")
    @ResponseBody
    public Object saveFolder(Model model, CommonDirVO dir){
        int sortnum = pdService.findMaxSortNum();
        dir.setSortnum(sortnum + 1);
        pdService.insert(dir);
        return AjaxResult.SUCCESS;
    }

    //修改所选目录
    @RequestMapping("/toEditFolder.d")
    @ViewWrapper(wrapped = false)
    public String toEditFolder(Model model, String id){
        model.addAttribute("dir", pdService.load(id));
        return "/appcfg/pagecfg/dialogs/edit_folder";
    }

    //保存修改目录的操作结果
    @RequestMapping("/editFolder.d")
    @ResponseBody
    public Object editFolder(Model model, CommonDirVO dir){
        pdService.updateName(dir);
        return AjaxResult.SUCCESS;
    }
    
    //删除目录
    @RequestMapping("/deleteDir.d")
    @ResponseBody
    public Object deleteDir(String dir_id) {
        if (AppConfigConst.PK_PUB_PAGEDIR_ROOT.equals(dir_id)) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "根目录不能删除.");
        }
        Condition c = new Condition();
        c.addExpression("parent_id", dir_id);
        int idir = pdService.findCountByCondition(c);
        Condition c2 = new Condition();
        c2.addExpression("dir_id", dir_id);
        int ipage = pService.findCountByCondition(c2);
        if (idir > 0 || ipage > 0) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "当前目录不为空,不能删除.");
        }
        pdService.delete(dir_id);
        return AjaxResult.SUCCESS;
    }

    //删除页面
    @RequestMapping("/deletePage.d")
    @ResponseBody
    public Object deletePage(String page_id){
        int count = pageRegisterService.loadPageID(page_id);
        if (count >= 1) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "当前页面已发布页面,不能删除");
        }
        int count_memu=munuRegisterService.isPublished(page_id);
        if(count_memu>0)
        {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "当前页面已发布菜单,不能删除");
        }
        pService.delete(page_id);
        return AjaxResult.SUCCESS;
    }
    
    /**
     * 
     * @Title: toPublish
     * (功能发布)
     * @param @param model
     * @param @param page_id
     * @param @return 设定文件
     * @return String 返回类型
     *      */
    //发布功能、页面
    @RequestMapping("/toPublish.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toPublish(Model model, String page_id, String type) {
        PageVO page = pService.load(page_id);
        String pagename = page.getPage_name();
        model.addAttribute("type", type);
        model.addAttribute("page_id", page_id);
        model.addAttribute("pagename1", pagename);
        return "/appcfg/pagecfg/dialogs/publish_fun";
    }
    
    @RequestMapping("/toPubTree.d")
    @ResponseBody
    @ViewWrapper(wrapped=false,onlyAjax=true)
    public Object loadPubTree(String type){
    	String userid = AppContext.getContext().getContextUserId();
    	Map<String, List<MenuRegisterVO>> mapNodes = null;
    	if ("2".equals(type)) {
            mapNodes = powerMgtQueryPubService.queryLevelMenuMapByUser(userid);
        } else {
            mapNodes = powerMgtQueryPubService.queryPageMenuMapByUser(userid);
        }
    	Map<String,String> nodeAttr = new HashMap<String,String>();
    	nodeAttr.put("text", "funname");
    	if(mapNodes != null&& mapNodes.size() > 0){
    		return JsonUtils.formatMap2TreeViewJson(mapNodes, nodeAttr);
    	}else{
    		return "";
    	}
    }
    
    //保存发布的操作结果
    @RequestMapping("/doPublisFun.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public Object doPublisFun(Model model, String page_id, String type, String folderid, String pagename) {
        PageVO page = pService.load(page_id);
        String parent_id = folderid;
        String url = AppConfig.getInstance().getString(AppConfigConst.PORTAL_PUBLISH_MAINURL) + page.getId() + ".d";
        String memo = page.getPage_desc();
        int sortnum = page.getSortnum();
        //业务功能发布
        if ("2".equals(type)) {
            MenuRegisterVO menuVo = new MenuRegisterVO();
            menuVo.setFunname(pagename);
            menuVo.setFuntype(FuncConst.MENUTYPE_BUSI);
            menuVo.setParentid(parent_id);
            menuVo.setUrl(url);
            menuVo.setMemo(memo);
            menuVo.setPageid(page_id);
            menuVo.setDispcode(String.format("%05d", sortnum));
            menuVo.setIsenable("Y");
            if (munuRegisterService.synchroCheck(pagename, folderid) == false) {
                return new AjaxResult(AjaxResult.STATUS_ERROR, "同一节点下面不允许发布同名功能!", null, false);
            }
            munuRegisterService.insertMenuRegister(menuVo);
        }
        //首页发布
        else {
            PageRegisterVO vo = new PageRegisterVO();
            //private String funname; // 菜单名
            //private String dispcode; // 显示编码
            //private String menuid; // 菜单ID
            //private String pageid; // 页面ID
            //private String url;// 访问URL
            //private String memo; // 描述
            vo.setFunname(pagename);
            vo.setUrl(url);
            vo.setMemo(memo);
            vo.setPageid(page_id);
            vo.setMenuid(parent_id);
            vo.setDispcode(String.format("%05d", sortnum));
            if (pageRegisterService.synchroCheck(pagename, folderid) == false) {
                return new AjaxResult(AjaxResult.STATUS_ERROR, "同一节点下面不允许发布同名页面!", null, false);
            }
            pageRegisterService.insertPageRegister(vo);
          /*page.setPage_name(pagename);
            pService.update(page);*/
        }
        return new AjaxResult();
    }
    
    //保存对页面组件操作结果
    @RequestMapping("/savePageConfig.d")
    @ResponseBody
    public Object savePageConfig(Model model, PageVO page, String del_comp, String col1, String col2, String col3) throws JsonParseException, JsonMappingException, IOException{
        ObjectMapper mapper = Json2ObjectMapper.getInstance();
        PageWidgetVO[] pw1 = null;
        PageWidgetVO[] pw2 = null;
        PageWidgetVO[] pw3 = null;
        if (StringUtils.isNotBlank(col1) && col1.indexOf("undefined") <= -1) {
            pw1 = mapper.readValue(col1, PageWidgetVO[].class);
        }
        if (StringUtils.isNotBlank(col2) && col2.indexOf("undefined") <= -1) {
            pw2 = mapper.readValue(col2, PageWidgetVO[].class);
        }
        if (StringUtils.isNotBlank(col3) && col3.indexOf("undefined") <= -1) {
            pw3 = mapper.readValue(col3, PageWidgetVO[].class);
        }
        pService.savePageConfig(page, del_comp, pw1, pw2, pw3);
        return AjaxResult.SUCCESS;
    }
    
    //点击一个页面，进入添加组件的大的页面（添加组件完毕后返回的执行的方法也是这个）
    @RequestMapping("/pageConfig.d")
    @ViewWrapper(wrapped = false)
    public String pageConfig(Model model, String page_id){
        PageVO page = pService.load(page_id);
        //查询已有的布局类型
        List<DefDocVO> layout = ddService.findListByType("np_layout");
        model.addAttribute("page", page);
        model.addAttribute("layout", layout);
        //查询该定制页面下的布局
        List<LayoutColumnVO> lc = wqService.getColumnInfoByLayout(page.getLayout_id());
        model.addAttribute("layoutcol", lc);
        //获取content_section组件容器的数据，包含具体的小部件信息，哪个组件容器含有哪些最内层DIV容器
        Map<String, List<PageWidgetVO>> sortResult = pwSeervice.findWidgetByPageId(page_id);
        Map<String, String> complist = new HashMap<String, String>();
        for (List<PageWidgetVO> list : sortResult.values()) {
            for (PageWidgetVO vo : list) {
                complist.put(vo.getId(), HtmlUtils.htmlEscape(vo.toString()));
            }
        }
        model.addAttribute("compmap", complist);
        for (LayoutColumnVO c : lc) {
            if (sortResult.containsKey(c.getColumn_id())) {
                continue;
            }
            sortResult.put(c.getColumn_id(), new ArrayList<PageWidgetVO>());
        }
        //jqueryui排序结果，用于放在content_section组件容器的填装数据
        model.addAttribute("coldata", sortResult);
        return "/appcfg/pagecfg/page_customizat";
    }
    
    //点击组件设置执行方法
    @RequestMapping("/setComp.d")
    @ViewWrapper(wrapped = false)
    public String setComp(Model model, String widget_id, String metaid, String widgettype){
        // 120120427154412chart
        PageWidgetVO widget = pwSeervice.load(widget_id);
        if (widget == null) {
            widget = new PageWidgetVO();
            widget.setId(widget_id);
        }
        model.addAttribute("widget", widget);
        List<WidgetActionVO> actions = widgetActionService.findActionsByWidgetId(metaid, widgettype);
        if (actions != null && actions.size() > 0) {
            model.addAttribute("actions", actions);
        }
        return "/appcfg/pagecfg/dialogs/camp_setting";
    }

    //组件设置完成后保存执行的方法
    @RequestMapping("/saveSetting.d")
    @ResponseBody
    public Object saveSetting(PageWidgetVO widget){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("widget", widget.toString());
        map.put("id", widget.getId());
        AjaxResult ar = new AjaxResult(AjaxResult.STATUS_SUCCESS, null, map);
        return ar;
    }
    
    //选择布局时执行的方法
    @RequestMapping("/getColInfo.d")
    @ResponseBody
    public Object getColInfo(String layout_id){
        List<LayoutColumnVO> list = wqService.getColumnInfoByLayout(layout_id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("colinfo", list);
        AjaxResult ar = new AjaxResult(AjaxResult.STATUS_SUCCESS, null, map);
        return ar;
    }
    
  //添加组件界面点击确认后执行的方法
    @RequestMapping("/createCompBox.d")
    @ViewWrapper(wrapped = false)
    public String createCompBox(Model model, String widget_type, String widget_id, String widget_name){
        String[] ids = widget_id.split(",");
        String[] names = widget_name.split(",");
        List<PageWidgetVO> list = new ArrayList<PageWidgetVO>();
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < ids.length; i++) {
            PageWidgetVO pw = new PageWidgetVO();
            pw.setWidget_type(widget_type);
            pw.setWidget_id(ids[i]);
            pw.setTitle(names[i]);
            pw.setWidth("100%");
            pw.setHeight(350);
            pw.setAuto_height(DBoolean.FALSE);
            pw.setTitle_show(DBoolean.TRUE);
            pw.setId("new_widget_" + i + new DDateTime().getMillis());
            list.add(pw);
            map.put(pw.getId(), HtmlUtils.htmlEscape(pw.toString()));
        }
        model.addAttribute("compList", list);
        model.addAttribute("compmap", map);
        return "/appcfg/pagecfg/dialogs/comp_box";
    }
    
    /**
     * @String
     */
    //检查目录名字是否重复
    @RequestMapping("/nameCheck.d")
    @ResponseBody
    public boolean nameCheck(CommonDirVO dir, String parent_id) {
        Condition condition = new Condition();
        condition.addExpression("parent_id", parent_id, "=");
        condition.addExpression("dir_name", dir.getDir_name(), "=");
        int number = pdService.findCountByCondition(condition);
        if (number == 0)
            return true;
        return false;
    }

    /**
     * @String
     */
    //页面名称检查
    @RequestMapping("/pageNameCheck.d")
    @ResponseBody
    public boolean pageNameCheck(PageVO page, String dir_id) {
        Condition condition = new Condition();
        condition.addExpression("dir_id", dir_id, "=");
        condition.addExpression("page_name", page.getPage_name(), "=");
        int number = pService.findCountByCondition(condition);
        if (number == 0)
            return true;
        return false;
    }
    

    
    /**
     * 新建自定义布局
     * @param model 
     * @param page_id 定制页面ID
     * @return 新建布局表单
     */
    @RequestMapping("/newLayout.d")
    @ViewWrapper(wrapped = false)
    public String newLayout(Model model, String page_id,LayoutColumnVO iLayoutColumnVO){
        model.addAttribute("page_id", page_id);
        model.addAttribute("iLayoutColumn", iLayoutColumnVO);
        return "/appcfg/pagecfg/dialogs/new_layout";
    }
    

    /**
     * 检查布局名字是否重复
     * @param 
     * @param page_id
     * @return
     */
    @RequestMapping("/layoutNameCheck.d")
    @ResponseBody
    public boolean layoutNameCheck(Model model, String page_id) {
    	
    	
       /* Condition condition = new Condition();
        condition.addExpression("parent_id", page_id, "=");
        condition.addExpression("dir_name", dir.getDir_name(), "=");
        int number = pdService.findCountByCondition(condition);
        if (number == 0)
            return true;*/
        return false;
    } 
    
    

    /**
     * 保存新建自定义布局
     * @param defDocVO 码表
     * @param page_id 页面
     * @param width_colspan_one    第一列
     * @param width_colspan_two	          第二列
     * @param width_colspan_three  第三列
     * @return
     */
    @RequestMapping("/saveLayout.d")
    @ResponseBody
    public Object saveLayout(DefDocVO defDocVO,String page_id,String width_colspan_one,String width_colspan_two,String width_colspan_three){
    	//判断布局是否重名
    	boolean nameUnique = layoutColumnService.docNameCheck(defDocVO.getType_id(),defDocVO.getDoc_name());
    	defDocVO.setDoc_code(defDocVO.getDoc_name());
	    if(nameUnique){
	    	//系统码表添加新的布局信息
		    List<String> columnIdArray=new ArrayList<>();
		    columnIdArray.add(0, width_colspan_one);
		    columnIdArray.add(1, width_colspan_two);
		    if (StringUtils.isNoneBlank(width_colspan_three)) {
		    	columnIdArray.add(2, width_colspan_three);
			}
		    try {
				layoutColumnService.save(columnIdArray,defDocVO);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
		        return new AjaxResult(AjaxResult.STATUS_ERROR, e.getMessage(), null, false);
			}
		    return AjaxResult.SUCCESS;
		}
	    return new AjaxResult(AjaxResult.STATUS_ERROR, "自定义布局名称重复.");
    } 
    
    /**
     * 删除自定义布局
     * @param layout_id 当前选中的布局id
     * @return
     */
    @RequestMapping("/delLayout.d")
    @ResponseBody
    public Object delLayout(String layout_id){
    	//预置的不能删除
    	
    	if ( StringUtils.isNoneBlank(layout_id)&&layout_id.indexOf("np_layout")!=-1) {
    		String layoutSubstring = layout_id.substring(9);
    		
    		if (!layoutSubstring.equalsIgnoreCase("_tabs")) {
				int layoutInt = Integer.parseInt(layoutSubstring);
				if (layoutInt < 7) {
					return new AjaxResult(AjaxResult.STATUS_ERROR, "预置布局不能删除.");
				} 
			}else {
					return new AjaxResult(AjaxResult.STATUS_ERROR, "预置布局不能删除.");
			}
        }
    	//已发布的不能删除
    	 Condition c = new Condition();
         c.addExpression("layout_id", layout_id);
    	 Sorter sorter = new Sorter(Direction.ASC, "page_name");
    	 List<PageVO> pageVOList = pService.findListByCondition(c,sorter);
    	 List<String> pageids=new ArrayList<>();
    	 for (PageVO pageVO : pageVOList) {
    		 pageids.add(pageVO.getId());
		 }
    	 for (int i = 0; i < pageids.size(); i++) {
			
    		 int count = pageRegisterService.loadPageID(pageids.get(i));
    		 if (count >= 1) {
    			 return new AjaxResult(AjaxResult.STATUS_ERROR, "当前布局已应用到发布页面,取消发布后的页面才能删除当前布局");
    		 }
    		 int count_memu=munuRegisterService.isPublished(pageids.get(i));
    	     if(count_memu>0){
    	         return new AjaxResult(AjaxResult.STATUS_ERROR, "当前布局已应用到发布菜单,取消发布后的页面才能删除当前布局");
    	      }
    		 
		}
         layoutColumnService.delete(layout_id,pageids);
    	 return AjaxResult.SUCCESS;
    	
    }
    
////////////////////////////////////////////以下为未知功能的代码块////////////////////////////////////////////////////////////////////////    
    /*@RequestMapping("/expandDlg.d")
    @ViewWrapper(wrapped = false)
    public String expandDlg(Model model) {
        return "/appcfg/pagecfg/dialog/param_setting";
    }*/

    /*@RequestMapping("/pageView.d")
    @ViewWrapper(wrapped = false)
    public String pageView(Model model, String page_id, String cognosparams, String mstrparams){
        PageVO page = pService.load(page_id);
        model.addAttribute("page", page);
        Map<String, List<PageWidgetVO>> sortResult = pwSeervice.findWidgetByPageId(page_id);
        model.addAttribute("coldata", sortResult);
        return "/appcfg/pagecfg/page_view";
    }*/
    
    /*@RequestMapping("/filterByName.d")
    @ResponseBody
    public Object filterByName(String page_name) throws JSONException{
        if (StringUtils.isBlank(page_name)) {
            CommonDirVO rootdir = pdService.load(AppConfigConst.PK_PUB_PAGEDIR_ROOT);
            rootdir.setSubdir(getSubDir(rootdir.getId(), true, true));
            rootdir.setSubpage(getSubPage(rootdir.getId()));
            JSONArray tree = new JSONArray();
            JSONObject root = createTreeNode(rootdir, true, false);
            tree.put(root);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tree", tree.toString());
            AjaxResult ar = new AjaxResult(AjaxResult.STATUS_SUCCESS, null, map);
            return ar;
        }
        CommonDirVO rootdir = pdService.load(AppConfigConst.PK_PUB_PAGEDIR_ROOT);
        Condition c2 = new Condition();
        page_name = page_name.replace("%", "\\%").replace("_", "\\_");//特殊字符处理
        c2.addExpression("page_name", page_name, "like");
        Sorter spage = new Sorter(Direction.ASC, "page_name");
        List<PageVO> subpage = pService.findListByCondition(c2, spage);
        rootdir.setSubpage(subpage);
        JSONArray tree = new JSONArray();
        JSONObject root = createTreeNode(rootdir, true, false);
        tree.put(root);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tree", tree.toString());
        AjaxResult ar = new AjaxResult(AjaxResult.STATUS_SUCCESS, null, map);
        return ar;
    }
    
    private List<CommonDirVO> getSubDir(String pid, boolean includeSubPage, boolean isPage){
        Condition c = new Condition();
        c.addExpression("parent_id", pid);
        Sorter sdir = new Sorter(Direction.ASC, "sortnum");
        sdir.and(new Sorter(Direction.ASC, "dir_name"));
        List<CommonDirVO> subdir = pdService.findListByCondition(c, sdir);
        if (subdir == null || subdir.size() == 0)
            return subdir;
        for (int i = 0; i < subdir.size(); i++) {
            subdir.get(i).setSubdir(getSubDir(subdir.get(i).getId(), includeSubPage, isPage));
            if (!includeSubPage)
                continue;
            if (isPage) {
                subdir.get(i).setSubpage(getSubPage(subdir.get(i).getId()));
            } else {
                subdir.get(i).setSubpage(getSubOutRes(subdir.get(i).getId()));
            }
        }
        return subdir;
    }
    
    *//**
     * 查询目录下的的外部资源,将OutResourceVO转为PageVO
     * 
     * @param pid
     * @return
     * @ 
     *//*
    private List<PageVO> getSubOutRes(String pid)   {
        Condition c2 = new Condition();
        c2.addExpression("parent_id", pid);
        Sorter spage = new Sorter(Direction.ASC, "res_name");
        List<PageVO> subpage = wqService.queryOutResByCondition(c2, spage);
        return subpage;
    }*/
}
