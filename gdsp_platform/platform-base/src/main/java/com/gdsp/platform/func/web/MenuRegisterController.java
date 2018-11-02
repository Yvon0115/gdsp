package com.gdsp.platform.func.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.ChainLogicExpression;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.model.query.Operator;
import com.gdsp.dev.core.model.query.ValueExpression;
import com.gdsp.dev.core.utils.excel.ExcelHelper;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.config.global.helper.SafeLevelConst;
import com.gdsp.platform.config.global.service.IDefDocListService;
import com.gdsp.platform.func.helper.MenuConst;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.service.IButnRegisterService;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;

/**
 * 客户化 - 菜单管理
 * @author zhouyu
 *
 */
@Controller
@RequestMapping("func/menu")
public class MenuRegisterController {

    @Autowired
    IMenuRegisterService         munuRegisterService;
    @Autowired
    private IDefDocListService   docListService;
    @Autowired
    private IButnRegisterService butnRegisterService;
    @Resource
    private IUserQueryPubService userQueryPubService;
    @Resource
    private IPowerMgtQueryPubService powerMgtQueryPubService;
    
    private final Logger         log = LoggerFactory.getLogger(MenuRegisterController.class);

    /***
    * @Title: list
    * @Description: 菜单树
    * @param model
    * @param pageable
    * @return    参数说明
    * @return String    返回菜单树
    *      */
    @RequestMapping("/list.d")
    public String list(Model model, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "dispcode", "innercode");
        Map<?, ?> voTreeMap = munuRegisterService.queryMenuRegisterVOsByCondReturnMap(null, sort);
        model.addAttribute("voTreeMap", voTreeMap);
        model.addAttribute("safeLevel", docListService.getDefDocsByTypeID(SafeLevelConst.safelevel));//安全级别
        return "func/menuregister/list";
    }

    /**
    * @Title: listData
    * @Description: 加载菜单树所在页面
    * @param model
    * @param condition
    * @param pageable
    * @return    参数说明
    * @return String    返回树
    *      */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false) //包装外壳
    public String listData(Model model, Condition condition, Pageable pageable) {
       
        Sorter sort = new Sorter(Direction.ASC, "dispcode", "innercode");
        Map<?, ?> voTreeMap = munuRegisterService.queryMenuRegisterVOsByCondReturnMap(null, sort);
        model.addAttribute("voTreeMap", voTreeMap);
        model.addAttribute("safeLevel", docListService.getDefDocsByTypeID(SafeLevelConst.safelevel));//安全级别
        return "func/menuregister/menu-tree";
    }

    /**
     * 加载菜单管理树
     * @return
     * @author xue
     * @since 2017年3月7日
     */
    @RequestMapping("/listmenu.d")
    @ResponseBody
    public String listmenu() {
        Sorter sort = new Sorter(Direction.ASC, "dispcode", "innercode");
        Map<?, ?> voTreeMap = munuRegisterService.queryMenuRegisterVOsByCondReturnMap(null, sort);
        // 树形控件更改
        Map<String, String> nodeAttr = new HashMap<>();
        nodeAttr.put("text", "funname");
        return JsonUtils.formatMap2TreeViewJson(voTreeMap, nodeAttr);
    }

    
    
    
    /**
     * 父菜单树--修改父菜单
     * @return
     * @author xue
     * @since 2017年3月7日
     */
    @RequestMapping("/editparent.d")
    @ResponseBody
    public String parentTree(String id) {
        Sorter sort = new Sorter(Direction.ASC, "dispcode", "innercode");
        Condition condition = new Condition();
        condition.addExpression("funtype", 0 , Operator.EQUAL.getCode());
        condition.addExpression("id", id, "<>");
        Map<?, ?> voTreeMap = munuRegisterService.queryMenuRegisterVOsByCondReturnMap(condition, sort);
        // 树形控件更改
        Map<String, String> nodeAttr = new HashMap<>();
        nodeAttr.put("text", "funname");
        return JsonUtils.formatMap2TreeViewJson(voTreeMap, nodeAttr);
    }
    
    /**
     * 加载上级节点树的数据
     * 添加菜单时，选择上级节点，仅仅显示funtype=0的菜单（菜单目录）
     * @return 
     * @author xue
     * @since 2017年3月7日
     */
    @RequestMapping("/checkparentmenu.d")
    @ResponseBody
    public String checkparentmenu() {
        Sorter sort = new Sorter(Direction.ASC, "dispcode", "innercode");
        Condition condition = new Condition();
        condition.addExpression("funtype", 0 , "=");
        Map<?, ?> voTreeMap = munuRegisterService.queryMenuRegisterVOsByCondReturnMap(condition, sort);
        // 树形控件更改
        Map<String, String> nodeAttr = new HashMap<>();
        nodeAttr.put("text", "funname");
        return JsonUtils.formatMap2TreeViewJson(voTreeMap, nodeAttr);
    }

    /***
    * @Title: add
    * @Description: 添加菜单
    * @param model
    * @param parentMenuVo
    * @param optType
    * @return    参数说明
    * @return String    返回添加菜单界面
    *      */
    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add(Model model, MenuRegisterVO parentMenuVo, String optType) {
        model.addAttribute("parentMenuVo", munuRegisterService.loadMenuRegisterVOById(parentMenuVo.getId()));
        model.addAttribute("optType", "add");    // 操作类型：添加
        model.addAttribute("safeLevel", docListService.getDefDocsByTypeID(SafeLevelConst.safelevel));//安全级别
        return "func/menuregister/edit_form";
    }
    
    /**
     * 打开修改菜单编辑页面
     * xue 2017年3月14日
     * @param model
     * @param id(选取节点的ID)
     * @param optType
     * @return
     */
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id, String optType) {
        MenuRegisterVO menuRegisterVo = munuRegisterService.loadMenuRegisterVOById(id);
        String parentId = menuRegisterVo.getParentid();
        MenuRegisterVO parentMenuVo = munuRegisterService.loadMenuRegisterVOById(parentId);
        model.addAttribute("muneRegisterVo", menuRegisterVo);
        model.addAttribute("parentMenuVo", parentMenuVo);
        model.addAttribute("optType", "edit");    //操作类型:修改
        model.addAttribute("safeLevel", docListService.getDefDocsByTypeID(SafeLevelConst.safelevel));//安全级别
        return "func/menuregister/edit_form";
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(MenuRegisterVO muneRegisterVo) {
        //给默认值
        if (muneRegisterVo != null) {
            if (StringUtils.isBlank(muneRegisterVo.getDispcode())) {
                muneRegisterVo.setDispcode(muneRegisterVo.getFuncode());
            }
            if (StringUtils.isBlank(muneRegisterVo.getIspower())) {
                muneRegisterVo.setIspower("Y");
            }
            if (StringUtils.isBlank(muneRegisterVo.getIsenable())) {
                muneRegisterVo.setIsenable("Y");
            }
            if (StringUtils.isNotEmpty(muneRegisterVo.getId())) {
                munuRegisterService.updateMenuRegister(muneRegisterVo);
            } else {
                munuRegisterService.insertMenuRegister(muneRegisterVo);
            }
            return AjaxResult.SUCCESS;
        }
        return AjaxResult.ERROR;
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String id) {
        try {
            munuRegisterService.deleteMenuRegister(id);
        } catch (BusinessException e) {
            //业务信息提示，无其他异常，不作处理。
            log.error(e.getMessage(), e);
            return new AjaxResult(AjaxResult.STATUS_ERROR, e.getMessage(), null, false);
        }
        return AjaxResult.SUCCESS;
    }

    /**
     * 打开上级节点弹出层--》新增菜单
     * xue 2017年3月14日
     * @param model
     * @param cond
     * @param pid
     * @return
     */
    @RequestMapping("/queryParentMenu.d")
    @ViewWrapper(wrapped = false)
    public Object queryParentMenu(Model model, Condition cond, String pid) {
        //管理菜单，业务菜单，页面菜单，都是叶子节点，不能被选为父节点。
        ChainLogicExpression ch = new ChainLogicExpression();
        ch.setOr(true);
        ValueExpression topLevelMenu = new ValueExpression("funtype", MenuConst.MENUTYPE_DIR.intValue());
        ValueExpression secondLevelMenu = new ValueExpression("funtype", MenuConst.MENUTYPE_MULTILEVEL.intValue());
        ch.addExpression(topLevelMenu, secondLevelMenu);
        cond.addExpressions(ch); 
        Sorter sort = new Sorter(Direction.ASC, "innercode", "dispcode");
        List<MenuRegisterVO> menuVos = munuRegisterService.queryParentMenuRegisters(cond, sort);
        model.addAttribute("menuVos", menuVos);
        return "func/menuregister/menu_reference";
    }

    /***
    * 根据id查菜单
    * @Title: queryMenuDetail
    * @Description: 选中菜单细节页面
    * @param model
    * @param id 当前菜单ID
    * @return    参数说明
    * @return Object    返回详细信息
    *      */
    @RequestMapping("/queryMenuDetail.d")
    @ViewWrapper(wrapped = false)
    public Object queryMenuDetail(Model model, String id) {
        MenuRegisterVO menuRegisterVo = munuRegisterService.loadMenuRegisterVOById(id);
        String parentId = "";
        if (menuRegisterVo != null) {
            parentId = menuRegisterVo.getParentid();
        }
        MenuRegisterVO parentMenuVo = munuRegisterService.loadMenuRegisterVOById(parentId);
        model.addAttribute("safeLevel", docListService.getDefDocsByTypeID(SafeLevelConst.safelevel));//安全级别
        model.addAttribute("muneRegisterVo", menuRegisterVo);
        model.addAttribute("parentMenuVo", parentMenuVo);
        return "func/menuregister/form";
    }

    /**
     * 打开修改父菜单弹出层
     * xue 2017年3月13日
     * @param model
     * @param menuRegisterVO
     * @param id
     * @return
     */
    @RequestMapping("/toEditParentMenu.d")
    @ViewWrapper(wrapped = false)
    public String toEditParentMenu(Model model, MenuRegisterVO menuRegisterVO,String id) {
        Condition cond = new Condition();
        ChainLogicExpression ch = new ChainLogicExpression();
        ch.setOr(true);
        ValueExpression topLevelMenu = new ValueExpression("funtype",  MenuConst.MENUTYPE_DIR.intValue());
        ValueExpression secondLevelMenu = new ValueExpression("funtype", MenuConst.MENUTYPE_MULTILEVEL.intValue());
        ch.addExpression(topLevelMenu, secondLevelMenu);
        cond.addExpressions(ch);
        model.addAttribute("menuRegisterVO", menuRegisterVO);
        model.addAttribute("id",id);
        return "func/menuregister/edit_parent_menu";
    }

    /**
     * 修改父菜单操作
     * xue 2017年3月13日
     * @param model
     * @param menuRegisterVO
     * @return
     */
    @RequestMapping("/doEditParentMenu.d")
    @ResponseBody
    public int doEditParentMenu(Model model, MenuRegisterVO menuRegisterVO) {
        int result = 0;
        if (menuRegisterVO != null) {
            if (menuRegisterVO.getId().equals(menuRegisterVO.getParentid())) {
                result = 1;//父节点不能为自身
                return result;
            }
            if (menuRegisterVO.getParentid().equals(munuRegisterService.loadMenuRegisterVOById(menuRegisterVO.getId()).getParentid())) {
                result = 2;//父菜单没变化
                return result;
            }
            munuRegisterService.editParentMenu(menuRegisterVO.getId(), menuRegisterVO.getParentid());
        }
        model.addAttribute("menuRegisterVO", menuRegisterVO);
        return result;
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
    @RequestMapping("/toPublishMenu.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toPublish(Model model, HttpServletRequest request) {
        if (request.getAttribute("menu") == null)
            throw new DevRuntimeException("url只能被带有模型menu的controller forwarded，请提供menu模型！");
        String userid = AppContext.getContext().getContextUserId();
        Map<String, List<MenuRegisterVO>> mapNodes = powerMgtQueryPubService.queryLevelMenuMapByUser(userid);
        model.addAttribute("nodes", mapNodes);
        return "func/menuregister/publish_menu";
    }

    /**
     *
     * @Title: 发布菜单
     * (发布)
     * @param @param menu
     * @param @param page_id
     * @param @param type
     * @param @return    设定文件
     * @return Object    返回类型
     *      */
    @RequestMapping("/doPublishMenu.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public Object doPublishMenu(MenuRegisterVO menu) {
        if (StringUtils.isEmpty(menu.getFunname()) || StringUtils.isEmpty(menu.getPageid()) || menu.getFuntype() != MenuConst.MENUTYPE_BUSI.intValue())
            return new AjaxResult(AjaxResult.STATUS_ERROR, "发布失败");
        munuRegisterService.insertMenuRegister(menu);
        return new AjaxResult("发布成功");
    }

    /**
     *
     * @Title: 发布菜单
     * (发布)
     * @param @param menu
     * @param @param page_id
     * @param @param type
     * @param @return    设定文件
     * @return Object    返回类型
     *      */
    @RequestMapping("/revertPublishedMenu.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public Object revertPublishedMenu(String pageId) {
        munuRegisterService.deleteMenuRegisterByPageId(pageId);
        return new AjaxResult("取消发布成功！");
    }

    /**
    *
    * @Title: 发布菜单
    * (发布)
    * @param @param menu
    * @param @param page_id
    * @param @param type
    * @param @return    设定文件
    * @return Object    返回类型
    *     */
    @RequestMapping("/updateInnercode.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public Object doPublishMenu(String parentid) {
        munuRegisterService.updateInnercode("7f291bdf6f754a66a10f53d921504ab4");
        return new AjaxResult("发布成功");
    }

    /**
     * 导出菜单
     * 导出所有叶子节点菜单
     * @throws IOException 
     */
    @RequestMapping("/exportAllLeafMune.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public void exportAllLeafMune(Model model, HttpServletResponse response) throws IOException {
        Sorter sort = new Sorter(Direction.ASC, "dispcode", "innercode");
        Map<String, List<MenuRegisterVO>> voTreeMap = munuRegisterService.queryMenuRegisterVOsByCondReturnMap(null, sort);
        List<String> nameList = new ArrayList<>();
        getAllLeafNames(nameList, voTreeMap, "__null_key__", "");
        ExcelHelper<MenuRegisterVO> util = new ExcelHelper<>(MenuRegisterVO.class);
        List<MenuRegisterVO> datas = new ArrayList<>();
        for (int i = 0; i < nameList.size(); i++) {
            MenuRegisterVO menuVO = new MenuRegisterVO();
            menuVO.setSysName(AppConfig.getProperty("view.systemName"));
            menuVO.setPath(nameList.get(i));
            datas.add(menuVO);
        }
        String fileName = "菜单结构.xls";
        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        response.reset();
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream output = response.getOutputStream();
        util.exportExcel(datas, "菜单项", 60000, output);
    }

    /**
     * 递归获取叶子节点
     * @param nameList
     * @param voTreeMap
     * @param key
     * @param name
     */
    public void getAllLeafNames(List<String> nameList, Map<String, List<MenuRegisterVO>> voTreeMap, String key, String name) {
        List<MenuRegisterVO> list = voTreeMap.get(key);
/**        if (list == null || list.size() == 0) {*/
        if (CollectionUtils.isEmpty(list)) {
            nameList.add(name);
            return;
        }
        for (MenuRegisterVO menuVO : list) {
            String subKey = menuVO.getId();
            String subName = name + "/" + menuVO.getFunname();
            if ("".equals(name)) {
                subName = menuVO.getFunname();
            }
            getAllLeafNames(nameList, voTreeMap, subKey, subName);
        }
    }

    @RequestMapping("queryAllAvailableMenu.d")
    @ResponseBody
    public Object queryAllAvailableMenu() {
        Subject subject = SecurityUtils.getSubject();
        String account = (String) subject.getPrincipal();
        //根据用户名查询对应用户所具有权限的菜单
        UserVO userVO = userQueryPubService.queryUserByAccount(account);
        List<MenuRegisterVO> menuList = powerMgtQueryPubService.queryMenuListByUserVo(userVO);
        Map<String, Object> params = new HashMap<>();
        params.put("menus", menuList);
        AjaxResult result = new AjaxResult();
        result.setParams(params);
        return result;
    }
}
