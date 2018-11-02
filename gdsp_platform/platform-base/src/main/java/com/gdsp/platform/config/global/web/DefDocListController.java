package com.gdsp.platform.config.global.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.config.global.model.DefDocListVO;
import com.gdsp.platform.config.global.service.IDefDocListService;
import com.gdsp.platform.config.global.service.IDefDocService;

/**
 * 
* @ClassName: DefDocListController
* (数据字典控制类)
* @author songxiang
* @date 2015年10月28日 下午2:05:56
*
 */
@Controller
@RequestMapping("conf/defdoclist")
public class DefDocListController {

    @Resource
    private IDefDocListService defDocListSercice;
    @Resource
    private IDefDocService     defDocService;

    /**
     * 
    * @Title: listDocDef
    * (页面加载)
    * @param model
    * @param pageable
    * @param condition
    * @return
    * @throws DevException    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/list.d")
    public String listDocDef(Model model, Pageable pageable, Condition condition) throws DevException {
        Page<DefDocListVO> defDocListVO = defDocListSercice.queryDefDocListVOByCondition(condition, pageable, null);
        model.addAttribute("defDocListVO", defDocListVO);
        return "conf/defdoclist/list";
    }

    /**
     * 
    * @Title: reloadList
    * (重新加载)
    * @param model
    * @param pageable
    * @param condition
    * @return
    * @throws DevException    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/reloadList.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String reloadList(Model model, Pageable pageable, Condition condition) throws DevException {
        Page<DefDocListVO> defDocListVO = defDocListSercice.queryDefDocListVOByCondition(condition, pageable, null);
        model.addAttribute("defDocListVO", defDocListVO);
        return "conf/defdoclist/list-data";
    }

    /**
     * 
    * @Title: toSava
    * (去添加页面)
    * @param model
    * @return
    * @throws DevException    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/toSava.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toSava(Model model) throws DevException {
        return "conf/defdoclist/form";
    }

    /**
     * 
    * @Title: save
    * (保存更新)
    * @param defDocListVO
    * @param model
    * @return    参数说明
    * @return Object    返回值说明
    *      */
    @RequestMapping("/save.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    @ResponseBody
    public Object save(DefDocListVO defDocListVO, Model model) {
        defDocListSercice.saveDefDocList(defDocListVO);
        return AjaxResult.SUCCESS;
    }

    /**
     * 
     * 
    * @Title: toEdit
    * (去更新页面)
    * @param model
    * @param id
    * @return
    * @throws DevException    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/toEdit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toEdit(Model model, String id) throws DevException {
        model.addAttribute("defDocListVO", defDocListSercice.load(id));
        model.addAttribute("editType", "edit");
        return "conf/defdoclist/form";
    }

    /**
    * 删除
    * 删除原因：不允许从页面删除码表
    * wqh 2017-9-18 14:18:13
     */
    /*
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) throws DevException {
        List<String> list = defDocListSercice.isPreset(id);
        if (list.contains("Y")) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "删除的选项包含系统预置数据，不能删除。");
        }
        if (id != null && id.length > 0) {
            defDocListSercice.delete(id);
        }
        return AjaxResult.SUCCESS;
    }
*/
    @RequestMapping("/synchroCheck.d")
    @ResponseBody
    public Object synchroCheck(DefDocListVO defDocListVO) {
        return defDocListSercice.synchroCheck(defDocListVO);
    }
}
