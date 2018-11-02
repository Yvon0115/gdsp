package com.gdsp.integration.framework.param.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.ChainLogicExpression;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.model.query.ValueExpression;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.dev.web.mvc.ext.ViewDecorate;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.integration.framework.param.model.AcParam;
import com.gdsp.integration.framework.param.model.AcParamLibrary;
import com.gdsp.integration.framework.param.service.IParamLibraryService;
import com.gdsp.integration.framework.param.service.IParamService;
import com.gdsp.platform.systools.indexanddim.model.IndTreeVO;

@RequestMapping("param/paramLibrary")
@Controller
public class ParamLibraryController {

    @Autowired
    private IParamLibraryService service;
    @Autowired
    private IParamService        paramService;

    @RequestMapping("/main.d")
    public String list(Model model, Pageable pageable) {
        List<AcParamLibrary> list = service.queryAllParamLibrary();
        model.addAttribute("nodes", list);
        return "integration/framework/param/main";
    }

    @RequestMapping("/toAdd.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toAdd(Model model, AcParamLibrary parentParamLibrary) {
        model.addAttribute("parentParamLibrary", parentParamLibrary);
        return "integration/framework/param/paramlibrary-form";
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(AcParamLibrary vo) {
        if (StringUtils.isNotEmpty(vo.getId())) {
            service.updateParamLibrary(vo);
        } else {
            service.addParamLbrary(vo);
        }
        return AjaxResult.STATUS_SUCCESS;
    }

    @RequestMapping("/checkParamLibraryName.d")
    @ResponseBody
    public Object checkParamLibraryName(AcParamLibrary vo) {
        return service.checkParamLibraryName(vo);
    }

    @RequestMapping("/loaddirtreenode.d")
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    @ResponseBody
    public String loaddirtreenode(Model model, String pid){
        Condition condition = new Condition();
//        ChainLogicExpression ch = new ChainLogicExpression();
//        if (!StringUtils.isEmpty(pid)) {
//            ValueExpression category = new ValueExpression("pid", pid);
//            ch.addExpression(category);
//        } else {
//            ch.setOr(true);
//            ValueExpression category = new ValueExpression("pid", "");
//            ValueExpression c2 = new ValueExpression("pid", null, "is null");
//            ch.addExpression(category);
//            ch.addExpression(c2);
//        }
//        condition.addExpressions(ch);
        Sorter sort = null;
        Map dimTree=service.queryByCondition(condition, sort);
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "name");
        if(dimTree.size()!=0)
            return   JsonUtils.formatMap2TreeViewJson(dimTree, nodeAttr);
        else{
            return "";
            }
        }
//        model.addAttribute("nodes", service.queryByCondition(condition, sort));
//        return "integration/framework/param/treenode";
//    }

//    @RequestMapping("/queryParentDir.d")
//    @ViewWrapper(wrapped = false)
//    public Object queryParentResDir(Model model, String id) {
//        Map dirTree = service.queryParamLibraryTreeMap(id);
//        model.addAttribute("dirTree", dirTree);
//        return "integration/framework/param/resDirReference";
//    }
    @RequestMapping("/queryParentDir.d")
    public Object queryParentResDir(Model model, String id){
        model.addAttribute("id", id);
        return "integration/framework/param/resDirReference";
    }
    @RequestMapping("/queryParentTree.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public Object queryParentTree(Model model, String id) {
        Map dirTree = service.queryParamLibraryTreeMap(id);
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "name");
        if(dirTree.size()!=0){
            return JsonUtils.formatMap2TreeViewJson(dirTree, nodeAttr);
        }else{
            return "";
        }
    }

    @RequestMapping("/editDir.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String editDir(Model model, String id) {
        AcParamLibrary paramLibraryVO = service.load(id);
        //查询父节点
        if (paramLibraryVO != null) {
            String parentId = paramLibraryVO.getPid();
            AcParamLibrary parentParamLibrary = service.load(parentId);
            model.addAttribute("parentParamLibrary", parentParamLibrary);
        }
        model.addAttribute("paramLib", paramLibraryVO);
        return "integration/framework/param/paramlibrary-form";
    }

    @RequestMapping("/deleteDir.d")
    @ResponseBody
    public Object deleteDir(String id, Pageable pageable) {
        Condition condition = new Condition();
        condition.addExpression("pid", id);
        Page<AcParam> filevos = paramService.queryByCondition(condition, pageable, null);
        Map dimTree=service.queryByCondition(condition, null);
//        List<AcParamLibrary> folderVos = service.queryByCondition(condition, null);
        if (filevos != null && filevos.getContent() != null && filevos.getContent().size() > 0 ||
                dimTree != null && dimTree.size() > 0) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "该分类下有数据不允许删除！");
        }
        service.deleteParamLibrary(id);
        return AjaxResult.SUCCESS;
    }
}
