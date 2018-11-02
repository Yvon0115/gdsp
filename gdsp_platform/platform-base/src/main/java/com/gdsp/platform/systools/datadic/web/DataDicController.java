package com.gdsp.platform.systools.datadic.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.systools.datadic.model.DataDicVO;
import com.gdsp.platform.systools.datadic.model.DataDicValueVO;
import com.gdsp.platform.systools.datadic.service.IDataDicService;
import com.gdsp.platform.systools.datalicense.model.DataDicPowerVO;
import com.gdsp.platform.systools.datalicense.model.DataLicenseVO;
/**
 * @ClassName: DataDicController
 * (数据字典维度及维值管理)
 * @author qishuo
 * @date 2016年12月9日 
 */
@Controller
@RequestMapping("conf/datadic")
public class DataDicController {
    @Autowired
    private IDataDicService dataDicService;
    /**
     * 加载数据字典列表
     * @param model
     * @param con 条件
     * @param page 分页
     * @return
     */
    @RequestMapping("/list.d")
    public String listDataDic(Model model,Condition con,Pageable page){
        Sorter sorter=new Sorter(Direction.DESC,"createTime");
        Page<DataDicVO> dataDicVO=dataDicService.queryDataDicPageByCon(con,page,sorter);
        model.addAttribute("dataDicVO", dataDicVO);
        return "datadic/list";
    }
    /**
     * 重新加载数据字典列表
     * @param model
     * @param con 条件
     * @param page 分页
     * @return
     */
    @RequestMapping("/reloadList.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String reloadList(Model model,Condition con,Pageable page){
        Sorter sorter=new Sorter(Direction.DESC,"createTime");
        Page<DataDicVO> dataDicVO=dataDicService.queryDataDicPageByCon(con,page,sorter);
        model.addAttribute("dataDicVO", dataDicVO);
        return "datadic/list-data";
    }
    /**
     * 加载数据字典值列表
     * @param model
     * @param page 分页
     * @param pk_dicId 数据字典ID
     * @return
     */
    @RequestMapping("/valueList.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String valueList(Model model,Pageable page,String pk_dicId){
        Page<DataDicValueVO> dataDicValueVO=dataDicService.findDaDicValPageByDicId(page,pk_dicId);
        model.addAttribute("dataDicValueVO", dataDicValueVO);
        model.addAttribute("pk_dicId", pk_dicId);
        return "datadic/valueList";
    }
    /**
     * 重新加载数据字典值列表
     * @param model
     * @param page 分页
     * @param pk_dicId 数据字典ID
     * @return
     */
    @RequestMapping("/reloadValueList.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String reloadValueList(Model model,Pageable page,String pk_dicId){
        Page<DataDicValueVO> dataDicValueVO=dataDicService.findDaDicValPageByDicId(page,pk_dicId);
        model.addAttribute("dataDicValueVO", dataDicValueVO);
        model.addAttribute("pk_dicId", pk_dicId);
        return "datadic/valueList-data";
    }
    /**
     * 前往添加数据字典值
     * @param model
     * @param pk_dicId 数据字典ID
     * @return
     */
    @RequestMapping("/addValue.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String addValue(Model model,String pk_dicId){
        model.addAttribute("pk_dicId", pk_dicId);
        return "datadic/value-form";
        
    }
    /**
     * 选择上级数据字典值
     * @param model
     * @param pk_dicId 数据字典ID
     * @return
     */
    @RequestMapping("/toParentPage.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toParentPage(Model model,String pk_dicId){
//        Map voTreeMap=dataDicService.queryDataDicValTree(pk_dicId);
//        Map<String,String> nodeAttr = new HashMap<String,String>();
//        nodeAttr.put("text", "dimvl_name");
//        if(voTreeMap.size()!=0) {
//			String formatMap2TreeViewJson = JsonUtils.formatMap2TreeViewJson(voTreeMap, nodeAttr);
//		}
//		else {
		//	return "";
//        model.addAttribute("voTreeMap", voTreeMap);
//		}
    	model.addAttribute("pk_dicId",pk_dicId);
        return "datadic/parent-tree";
    }
    @RequestMapping("/parentValueCode.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    @ResponseBody
    public String parentValueCode(Model model,String pk_dicId){
    	  Map voTreeMap=dataDicService.queryDataDicValTree(pk_dicId);
          Map<String,String> nodeAttr = new HashMap<String,String>();
          nodeAttr.put("text", "dimvl_name");
          if(voTreeMap.size()!=0) {
  			return JsonUtils.formatMap2TreeViewJson(voTreeMap, nodeAttr);
  		}
  		else {
  			return "";
  		}
    }
    /**
     * 保存数据字典值
     * @param vo
     * @return
     */
    @RequestMapping("/saveValue.d")
    @ResponseBody
    public Object saveValue(DataDicValueVO vo){
        dataDicService.saveDataDicValue(vo);
        return AjaxResult.SUCCESS;
    }
    /**
     * 前往新增数据字典页面
     * @return
     */
    @RequestMapping("/addDim.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String addDim(){
        return "datadic/dim-form";
    }
    /**
     * 前往修改数据字典页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/editDim.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edditDim(Model model,String id){
        model.addAttribute("dataDicVO", dataDicService.loadDataDic(id));
        return "datadic/dim-form";
    }
    /**
     * 保存数据字典
     * @param vo
     * @return
     */
    @RequestMapping("/saveDim.d")
    @ResponseBody
    public Object saveDim(DataDicVO vo){
        dataDicService.saveDataDic(vo);
        return AjaxResult.SUCCESS;
    }
    /**
     * 删除数据字典
     * @param id
     * @return
     */
    @RequestMapping("/deleteDim.d")
    @ResponseBody
    public Object deleteDim(String [] id){
        List list=dataDicService.deleteDataDic(id);
        if(list!=null){
            if(list.get(0).getClass()==DataDicValueVO.class){
                return new AjaxResult(AjaxResult.STATUS_ERROR, "存在维值,请先清空维值");
            }else if(list.get(0).getClass()==DataDicPowerVO.class){
            	  return new AjaxResult(AjaxResult.STATUS_ERROR, "数据已被权限控制维度关联,不能删除");
            }
            else if(list.get(0).getClass()==DataLicenseVO.class){
                return new AjaxResult(AjaxResult.STATUS_ERROR, "数据已被授权,不能删除");
            }else{
                return AjaxResult.ERROR;
            }
        }else{
            return AjaxResult.SUCCESS;
        }
    }
    /**
     * 删除数据字典值
     * @param id
     * @return
     */
    @RequestMapping("/deleteDimValue.d")
    @ResponseBody
    public Object deleteDimValue(String [] id){
        List list=dataDicService.deleteDataDicVal(id);
        if(list!=null){
            if(list.get(0).getClass()==DataDicValueVO.class){
                return new AjaxResult(AjaxResult.STATUS_ERROR, "数据存在下级,请先删除下级");
            }else if(list.get(0).getClass()==DataLicenseVO.class){
                return new AjaxResult(AjaxResult.STATUS_ERROR, "数据已授权,不能删除");
            }else{
                return AjaxResult.ERROR;
            }
        }else{
            return AjaxResult.SUCCESS;
        }
    }
    /**
     * 修改数据字典值
     * @param model
     * @param id
     * @param pk_dicId 数据字典ID
     * @return
     */
    @RequestMapping("/editDimValue.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String editDimValue(Model model,String id,String pk_dicId){
        DataDicValueVO dataDicValueVO=dataDicService.loadDataDicValue(id);
        model.addAttribute("dataDicValueVO", dataDicValueVO);
        model.addAttribute("pk_dicId", pk_dicId);
        return "datadic/value-form";
    }
    /**
     * 验证数据字典的编码和名称的唯一性
     * @param dataDicVO
     * @return
     */
    @RequestMapping("/synchroCheck.d")
    @ResponseBody()
    public Object synchroCheck(DataDicVO dataDicVO) {
        return dataDicService.checkDataDicCode(dataDicVO);
    }
    /**
     * 验证数据字典值的编码和名称的唯一性
     * @param dataDicValueVO
     * @return
     */
    @RequestMapping("/synchroCheckVal.d")
    @ResponseBody()
    public Object synchroCheckVal(DataDicValueVO dataDicValueVO) {
        return dataDicService.checkDataDicValCode(dataDicValueVO);
    }
}
