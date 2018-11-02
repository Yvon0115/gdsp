package com.gdsp.platform.config.global.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.config.global.service.IDefDocListService;
import com.gdsp.platform.config.global.service.IDefDocService;

/**
 * 
* @ClassName: DefDocController
* (数据字典类别详细管理控制类)
* @author songxiang
* @date 2015年10月28日 下午2:03:50
*
 */
@Controller
@RequestMapping("conf/defdoc")
public class DefDocController {

    @Resource
    private IDefDocService     defDocService;
    @Resource
    private IDefDocListService defDocListSercice;

    /**
     * 
    * @Title: listDoc
    * (页面加载)
    * @param model
    * @param type_id
    * @param pageable
    * @return
    * @throws DevException    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/dlist.d")
    @ViewWrapper(wrapped = false)
    public String listDoc(Model model, String type_id, Pageable pageable) throws DevException {
        Page<DefDocVO> defDocVO=defDocService.findPageByType(type_id, pageable);
        model.addAttribute("defDocVO", defDocVO);
        model.addAttribute("type_id", type_id);
        return "conf/defdoc/list";
    }

    /**
     * 
    * @Title: defList
    * (重新加载)
    * @param model
    * @param type_id
    * @param pageable
    * @return
    * @throws DevException    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/docList")
    @ViewWrapper(wrapped = false)
    public String defList(Model model, String type_id, Pageable pageable) throws DevException {
        Page<DefDocVO> defDocVO=defDocService.findPageByType(type_id, pageable);
        model.addAttribute("defDocVO", defDocVO);
        model.addAttribute("type_id", type_id);
        return "conf/defdoc/list-data";
    }

    /**
     * 
    * @Title: toSava
    * (去添加页面)
    * @param model
    * @param type_id
    * @return
    * @throws DevException    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/toSava.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toSava(Model model, String type_id) throws DevException {
        model.addAttribute("type_id", defDocService.findListByType(type_id));
        model.addAttribute("type_id", type_id);
        return "conf/defdoc/form";
    }

    /**
     * 
    * @Title: save
    * (保存更新)
    * @param defDocVO
    * @param type_id
    * @param model
    * @return
    * @throws DevException    参数说明
    * @return Object    返回值说明
    *      */
    @RequestMapping("/save.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    @ResponseBody
    public Object save(DefDocVO defDocVO, String type_id, Model model) throws DevException {
        defDocService.saveDefDoc(defDocVO);
        return AjaxResult.SUCCESS;
    }

    /**
     * 
    * @Title: toEdit
    * (去更新页面)
    * @param model
    * @param id
    * @param type_id
    * @return
    * @throws DevException    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/toEdit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toEdit(Model model, String id, String type_id) throws DevException {
        DefDocVO defDocVO=defDocService.load(id);
        model.addAttribute("defDocVO", defDocVO);
        model.addAttribute("type_id", defDocService.findListByType(type_id));
        model.addAttribute("type_id", type_id);
        model.addAttribute("editType", "edit");
        return "conf/defdoc/form";
    }

    /**
    * 删除
    * @param id
    * 删除原因：不允许从页面删除码表
    * wqh 2017-9-18 14:17:19
    */
    /* 
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        List<String> list = defDocService.isPreset(id);
        if (list.contains("Y")) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "删除的选项包含系统预置数据，不能删除。");
        }
        if (id != null && id.length > 0) {
            defDocService.delete(id);
        }
        return AjaxResult.SUCCESS;
    }
    */
    
    @RequestMapping("/synchroCheck.d")
    @ResponseBody
    public Object synchroCheck(DefDocVO defDocVO) {
        return defDocService.synchroCheck(defDocVO);
    }
    @RequestMapping("/toSelectParentPage.d")
    public String toSelectParentPage(Model model,String type_id){
    	 model.addAttribute("type_id", type_id);
         return "conf/defdoc/selctParent-tree";
    }
    
    /**
     * 选择上级
     * @param model
     * @param type_id 类型ID
     * @return
     */
    @RequestMapping("/selectParent.d")
    @ResponseBody
    @ViewWrapper(wrapped = false)
    public String selectParent(Model model,String type_id){
        Map voTreeMap=defDocService.selectParent(type_id);
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "doc_name");
        if(voTreeMap.size()!=0) {
			return JsonUtils.formatMap2TreeViewJson(voTreeMap, nodeAttr);
		}
		else {
			return "";
		}
    }
}
