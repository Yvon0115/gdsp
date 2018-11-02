package com.gdsp.ptbase.appcfg.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.ChainLogicExpression;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.model.query.ValueExpression;
import com.gdsp.dev.web.mvc.ext.ViewDecorate;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.ptbase.appcfg.helper.AppConfigConst;
import com.gdsp.ptbase.appcfg.model.CommonDirVO;
import com.gdsp.ptbase.appcfg.model.WidgetMetaVO;
import com.gdsp.ptbase.appcfg.service.ICommonDirService;
import com.gdsp.ptbase.appcfg.service.IResourceManageService;
import com.gdsp.ptbase.appcfg.service.IWidgetMetaService;

@Controller
@RequestMapping("appcfg/resourceManage")
public class ResourceManangeController {

    @Autowired
    private IResourceManageService resourceManageService;
    @Autowired
    private ICommonDirService      commonDirService;
    @Autowired
    private IWidgetMetaService     widgetMetaService;
    @Autowired
    private IDefDocService         defService;

    @RequestMapping("/list.d")
    public String list(Model model, Pageable pageable) {
        return "appcfg/resManage/list";
    }
    @RequestMapping("/dirTree.d")
    @ResponseBody
    public String DirTree(){
        Map commonDirTree = commonDirService.findAllDirTree();
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "dir_name");
        if(commonDirTree.size()!=0){
            return JsonUtils.formatMap2TreeViewJson(commonDirTree, nodeAttr);
        }else{
            return "";
        }   
    }
    @RequestMapping("/addDir.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String addDir(Model model) {
        model.addAttribute("editType", "add");
        return "appcfg/resManage/dir_form";
    }

    @RequestMapping("/addWidgetmeta.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String addWidgetmeta(Model model, String dirId,String type) {
        WidgetMetaVO widgetMetaVO = new WidgetMetaVO();
        widgetMetaVO.setDirId(dirId);
        model.addAttribute("widgetMetaVO", widgetMetaVO);
        model.addAttribute("type", type);
//        model.addAttribute("res_type", defService.findListByType(AppConfigConst.WIDGET_AC_RES_TYPE));
        return "appcfg/resManage/widgetmeta_form";
    }

    @RequestMapping("/queryParentResDir.d")
    @ViewWrapper(wrapped = false)
    public Object queryParentResDir(Model model, String parent_id) {
        Map commonDirTree = commonDirService.findAllDirTree();
        model.addAttribute("commonDirTree", commonDirTree);
        //		Condition condition = new Condition();
        //    	if (StringUtils.isEmpty(parent_id))
        //    	{
        //    		ChainLogicExpression ch = new ChainLogicExpression();
        //			ch.setOr(false);
        //			ValueExpression category = new ValueExpression("category", "widgetmeta");
        //			ValueExpression def1 = new ValueExpression("def1", "");
        //			ch.addExpression(category, def1);
        //			condition.addExpressions(ch);
        //    	}
        //    	else
        //    	{
        //    		condition.addExpression("parent_id", parent_id);
        //    	}
        //		Sort spage = new Sort(Direction.ASC, "sortnum");
        //		model.addAttribute("nodes", commonDirService.findListByCondition(condition,spage));
        return "appcfg/resManage/resDirReference";
    }

    @RequestMapping("/saveCommonDirVO.d")
    @ResponseBody
    public Object saveCommonDirVO(CommonDirVO commonDirVO) {
        if (StringUtils.isNotEmpty(commonDirVO.getId())) {
            if (commonDirVO.getId().equals(commonDirVO.getParent_id())) {
                return AjaxResult.STATUS_SUCCESS;
            }
            commonDirService.update(commonDirVO);
        } else {
            commonDirVO.setDef1("");
            commonDirVO.setCategory("widgetmeta");
            commonDirService.insert(commonDirVO);
        }
        return AjaxResult.STATUS_SUCCESS;
    }

    @RequestMapping("/saveWidgetMetaVO.d")
    @ResponseBody
    public Object saveWidgetMetaVO(WidgetMetaVO widgetMetaVO) {
        if (StringUtils.isNotEmpty(widgetMetaVO.getId())) {
            widgetMetaService.update(widgetMetaVO);
        } else {
            widgetMetaService.insert(widgetMetaVO);
        }
        return "redirect:list.d";
    }

    @RequestMapping("/queryWidgetmeta.d")
    @ViewWrapper(wrapped = false)
    public String queryWidgetmeta(Model model, String id, Pageable pageable) {
        Page<WidgetMetaVO> widgetmetaPage = widgetMetaService.findWidgetMetasByDirIdWithPage(id, pageable);
//        Map<String, String> map = new HashMap<String, String>();
//        List<DefDocVO> defList = defService.findListByType(AppConfigConst.WIDGET_AC_RES_TYPE);
//        for (int i = 0; i < defList.size(); i++) {
//            map.put(defList.get(i).getDoc_code(), defList.get(i).getDoc_name());
//        }
//        List<WidgetMetaVO> list = widgetmetaPage.getContent();
//        for (int i = 0; i < list.size(); i++) {
//            String newType = map.get(list.get(i).getType());
//            list.get(i).setType(newType);
//        }
        model.addAttribute("widgetmetaPage", widgetmetaPage);
        model.addAttribute("dirId", id);
        return "appcfg/resManage/list-data";
    }

    @RequestMapping("/deleteWidgetmeta.d")
    @ResponseBody
    public Object deleteWidgetmeta(String... id) {
        if (id == null) {
            return AjaxResult.ERROR;
        }
        widgetMetaService.delete(id);
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/deleteCommonDir.d")
    @ResponseBody
    public String deleteCommonDir(String id) {
        if (StringUtils.isBlank(id)) {
            return "N";
        }
        String isDelete = commonDirService.deleteById(id);
        return isDelete;
    }

    @RequestMapping("/editWidgetmeta.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String editWidgetmeta(Model model, String id) {
        WidgetMetaVO widgetMetaVO = widgetMetaService.load(id);
        model.addAttribute("widgetMetaVO", widgetMetaVO);
        model.addAttribute("editType", "edit");
        model.addAttribute("res_type", defService.findListByType(AppConfigConst.WIDGET_AC_RES_TYPE));
        return "appcfg/resManage/widgetmeta_form";
    }

    @RequestMapping("/editCommonDir.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String editCommonDir(Model model, String id) {
        CommonDirVO commonDirVO = commonDirService.load(id);
        model.addAttribute("commonDirVO", commonDirVO);
        if (commonDirVO != null && StringUtils.isNotBlank(commonDirVO.getParent_id())) {
            CommonDirVO parentCommonDirVO = commonDirService.load(commonDirVO.getParent_id());
            model.addAttribute("parentCommonDirVO", parentCommonDirVO);
        }
        return "appcfg/resManage/dir_form";
    }

    @RequestMapping("/loaddirtreenode.d")
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    public String loaddirtreenode(Model model, String parent_id){
        Condition condition = new Condition();
        if (StringUtils.isEmpty(parent_id)) {
            ChainLogicExpression ch = new ChainLogicExpression();
            ChainLogicExpression eh = new ChainLogicExpression();
            ValueExpression category = new ValueExpression("category", "widgetmeta");
            eh.setOr(true);
            ch.setOr(true);
            ValueExpression def1 = new ValueExpression("def1", "");
            ValueExpression def2 = new ValueExpression("def1", null, "is null");
            ch.addExpression(def1, def2);
            ValueExpression pid1 = new ValueExpression("parent_id", "");
            ValueExpression pid2 = new ValueExpression("parent_id", null, "is null");
            eh.addExpression(pid1, pid2);
            condition.addExpressions(ch);
            condition.addExpressions(eh);
            condition.addExpressions(category);
        } else {
            condition.addExpression("parent_id", parent_id);
        }
        Sorter spage = new Sorter(Direction.ASC, "sortnum");
//        Map dimTree=commonDirService.findListByCondition(condition, spage);
        model.addAttribute("nodes", commonDirService.findListByCondition(condition, spage));
        return "/appcfg/resManage/cdir_treenode";
    }

    @RequestMapping("/synchroCheck.d")
    @ResponseBody
    public Object synchroCheck(WidgetMetaVO widgetMetaVO) {
        return widgetMetaService.synchroCheck(widgetMetaVO);
    }

    @RequestMapping("/synchroCheckDirName.d")
    @ResponseBody
    public Object synchroCheckDirName(CommonDirVO commonDirVO) {
        return commonDirService.synchroCheckDirName(commonDirVO);
    }
}
