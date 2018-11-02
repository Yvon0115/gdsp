package com.gdsp.platform.systools.datasource.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.EncryptAndDecodeUtils;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.config.global.helper.DefDocConst;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
import com.gdsp.platform.systools.datasource.model.DatasourceConnection;
import com.gdsp.platform.systools.datasource.model.DsLibraryVO;
import com.gdsp.platform.systools.datasource.model.DsRegisterVO;
import com.gdsp.platform.systools.datasource.service.IDataSourceQueryPubService;
import com.gdsp.platform.systools.datasource.service.IDataSourceService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 数据源管理控制器
 * @author songxiang
 * @since 2015年10月28日 下午1:34:28
 */
@Controller
@RequestMapping("/systools/ds")
public class DataSourceController {

    @Autowired
    private IDataSourceService         dataSourceService;
    @Autowired
    private IDataSourceQueryPubService dataSourceQueryPubService;
    @Autowired
    private IDefDocService             defDocService;

    private String       encryptKey = FileUtils.getFileIO("reportKey", true);
    /**
     * 页面加载
     * @param model 模型
     * @param pageable 分页
     * @param condition 条件
     */
    @RequestMapping("/dslist.d")
    public String dslist(Model model, Pageable pageable, Condition condition, Sorter sort) {
    	sort = new Sorter(Direction.DESC, "createTime");
        Page<DataSourceVO> dateSourceVO = dataSourceService.queryDataSourceByCondition(condition, pageable, sort);
        handleIsDefault(dateSourceVO);
        model.addAttribute("dateSourceVO", dateSourceVO);
        return "systools/datasource/dlist";
    }

    /**
     * 数据加载
     * @param model
     * @param pageable
     * @param condition
     * @return 参数说明
     */
    @RequestMapping("/reloadList.d")
    @ViewWrapper(wrapped = false)
    public String reloadList(Model model, Pageable pageable, Condition condition, Sorter sort, String docId, String doc_code) {
        if (StringUtils.isNotEmpty(doc_code)) {
            // 设置条件
            if (doc_code.equals(DefDocConst.DATASOURCE_TYPE_DB) || doc_code.equals(DefDocConst.DATASOURCE_TYPE_BI)) {
                List<String> dsTypeList = new ArrayList<>();
                List<DefDocVO> list = defDocService.findSubLevelDocsByCode(DefDocConst.DATASOURCE_TYPE, doc_code);
                for (DefDocVO vo : list) {
                    dsTypeList.add(vo.getDoc_code());
                }
                condition.addExpression("type", dsTypeList, "in");
            } else {
                condition.addExpression("type", doc_code);
            }
        }
        sort = new Sorter(Direction.DESC, "createTime");
        Page<DataSourceVO> dateSourceVO = dataSourceService.queryDataSourceByCondition(condition, pageable, sort);
        handleIsDefault(dateSourceVO);
        model.addAttribute("doc_code", doc_code);
        model.addAttribute("doc_id", docId);
        model.addAttribute("dateSourceVO", dateSourceVO);
        return "systools/datasource/dlist-data";
    }

    @RequestMapping("/loadTypeTree.d")
    @ResponseBody
    public Object loadTypeTree() {
        Map docMap = defDocService.findDefDocMapByTypeCode(DefDocConst.DATASOURCE_TYPE);
        Map<String, String> nodeAttr = new HashMap<String, String>();
        nodeAttr.put("text", "doc_name");
        nodeAttr.put("customAttrs", "doc_code");
        if (docMap.size() != 0) {
            return JsonUtils.formatMap2TreeViewJson(docMap, nodeAttr);
        } else {
            return "";
        }
    }

    /**
     * 去编辑页面
     * @param model
     * @param id
     * @return String 返回值说明
     */
    @RequestMapping("/toEdit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    @ResponseBody
    public Object toEdits(String... id) {
        if (null != id && id.length > 0) {
            List<DsRegisterVO> dsRegisterVOs = dataSourceService.queryDatasourceRef(id);
            if (null != dsRegisterVOs && dsRegisterVOs.size() > 0) {
                String res_names = "";
                for (DsRegisterVO VO : dsRegisterVOs) {
                    res_names += (VO.getRes_name() + ",");
                }
                return new AjaxResult(AjaxResult.STATUS_ERROR, "数据源下有" + dsRegisterVOs.size() + "个注册服务,名称为:" + res_names + "确认继续修改该数据源吗？");
            } else {
                return AjaxResult.SUCCESS;
            }
        }
        return AjaxResult.ERROR;
    }

    /**
     * 加载数据源新增/修改表单
     * @param model
     * @param id
     * @return
     * @throws DevException
     */
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edits(Model model, String id) throws DevException {
        DataSourceVO dsVO = dataSourceQueryPubService.load(id);
        String docCode = dsVO.getType();
        DefDocVO doc = defDocService.findDocByTypeAndCode(DefDocConst.DATASOURCE_TYPE, docCode);
        String[] comboType = getComboType(doc);
        List<DsLibraryVO> dsMetaList = getProInfoList(docCode, comboType);
        model.addAttribute("products", dsMetaList);
        model.addAttribute("dateSourceVO", dsVO);
        model.addAttribute("comboTypes", comboType);
        model.addAttribute("modelList",IDataSourceService.AUTHENTICATION_MODEL);
        //        model.addAttribute("types", defDocService.findListByType(DefDocConst.WIDGET_AC_RES_TYPE));
        return "systools/datasource/form";
    }

    /**
     * 去添加页面
     * @param model
     * @return 参数说明
     * @return String 返回值说明
     */
    @RequestMapping("/toSava.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toSava(Model model, String docCode, String docId) {
        //        model.addAttribute("types", defDocService.findListByType(DefDocConst.WIDGET_AC_RES_TYPE));
        DefDocVO doc = defDocService.load(docId);
        String[] type = getComboType(doc);
        String curtype = null;
        if (doc != null && doc.getParentCode() != null) {
            curtype = doc.getDoc_code();
        }
        List<DsLibraryVO> dsMetaList = getProInfoList(docCode, type);
        model.addAttribute("modelList",IDataSourceService.AUTHENTICATION_MODEL);
        model.addAttribute("products", dsMetaList);
        model.addAttribute("datasourceType", curtype); // 当前数据源类型
        model.addAttribute("comboTypes", type); // 下拉框内对应的数据项
        return "systools/datasource/form";
    }

    private String[] getComboType(DefDocVO doc) {
        // TODO 给出默认类型，不过应该按照排序来选择，而不是直接写在代码中。
        String fatherCode = null;
        if (doc == null) {
            fatherCode = DefDocConst.DATASOURCE_TYPE_BI; // 初始化加载
        } else if (doc.getParentCode() == null) {
            fatherCode = doc.getDoc_code(); // 选择的是父节点
        } else {
            fatherCode = doc.getParentCode(); // 选择的是子节点
        }
        String[] type = new String[] { DefDocConst.DATASOURCE_TYPE, fatherCode };
        return type;
    }

    /**
     * 获取产品信息列表
     * @param condition
     * @param docCode
     * @param comboType
     * @return
     */
    private List<DsLibraryVO> getProInfoList(String docCode, String[] comboType) {
        List<DsLibraryVO> dsMetaList = null;
        if (StringUtils.isNotEmpty(docCode) && (comboType[1].equals(DefDocConst.DATASOURCE_TYPE_DB)
        		|| comboType[1].equals(DefDocConst.DATASOURCE_TYPE_BGDB))
                && (!docCode.equals(DefDocConst.DATASOURCE_TYPE_DB) 
                		|| !docCode.equals(DefDocConst.DATASOURCE_TYPE_BGDB))) {
            dsMetaList = dataSourceService.queryDSProListByTypeAndVersion(docCode,null);
        }
        return dsMetaList;
    }

    /**
     * 
     * 远程动态添加下拉产品号选项
     * @param condition
     * @param model
     * @param ds_type
     * @return jsonString
     */
    @RequestMapping("loadProductNo.d")
    @ResponseBody
    public String loadProductNo(Condition condition, Model model, String ds_type) {
        List<DsLibraryVO> dsMetaList = null;
        if (StringUtils.isNotEmpty(ds_type)) {
            dsMetaList = dataSourceService.queryDSProListByTypeAndVersion(ds_type,null);
        }
        return JSONArray.fromObject(dsMetaList).toString();
    }

    /**
     * 
     * 远程获取驱动名称
     * @param condition
     * @param model
     * @param ds_type
     * @param ds_version
     * @return
     */
    @RequestMapping("loadProductDriver.d")
    @ResponseBody
    public String loadProductDriver(Model model, String ds_type, String ds_version) {
        List<DsLibraryVO> dsMetaList = null;
        if (StringUtils.isNotEmpty(ds_type) && StringUtils.isNotEmpty(ds_version)) {
            dsMetaList = dataSourceService.queryDSProListByTypeAndVersion(ds_type,ds_version);
        }
        return JSONObject.fromObject(dsMetaList.get(0)).toString();
    }

    /**
     * 
    * @Title: save
    * @Description: 更新数据源
    * @param dataSoureVO
    * @return    参数说明
    * @return Object    返回值说明
    *      */
    @RequestMapping("/save.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    @ResponseBody
    public Object save(DataSourceVO dataSourceVO) {
        //        if (dataSourceService.checkDatasourceTypeUnique(dataSourceVO)) {
        if (dataSourceVO.getIsDefault() == null) {
            dataSourceVO.setIsDefault(null);
        } else {
            dataSourceVO.setIsDefault("Y");
        }
        if (!dataSourceService.passwordCheck(dataSourceVO)) {
			throw new BusinessException("操作出错，密码异常！");
		}
        dataSourceService.saveDataSource(dataSourceVO);
        
        return AjaxResult.SUCCESS;
        //        } else {
        //            return new AjaxResult(AjaxResult.STATUS_ERROR, "已经存在的数据源类型，请选择其他数据源类型");
        //        }
    }

    /**
     * 
    * @Title: delete
    * @Description: 删除数据源
    * @param id
    * @return    参数说明
    * @return Object    返回值说明
    *      */
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (null != id && id.length > 0) {
            String[] ids = id[0].replace("[", "").replace("]", "").replace("\"", "").split(",");
            List<DsRegisterVO> dsRegisterVOs = dataSourceService.queryDatasourceRef(ids);
            if (dsRegisterVOs != null && dsRegisterVOs.size() > 0) {
                return new AjaxResult(AjaxResult.STATUS_ERROR, "删除失败！数据源下有" + dsRegisterVOs.size() + "个注册服务信息!");
            }
            dataSourceService.deleteDataSource(ids);
            return new AjaxResult(AjaxResult.STATUS_SUCCESS, "删除成功！");
        }
        return AjaxResult.ERROR;
    }

    @RequestMapping("/dsSqllist.d")
    public String dsSqllist(Model model, Pageable pageable, Condition condition, Sorter sort) {
        if (condition == null)
            condition = new Condition();
        condition.addExpression("type", "SQL");
        Page<DataSourceVO> dateSourceVO = dataSourceService.queryDataSourceByCondition(condition, pageable, sort);
        model.addAttribute("dateSourceVO", dateSourceVO);
        return "systools/datasource/sqllist";
    }

    @RequestMapping("/reloadSqlList.d")
    @ViewWrapper(wrapped = false)
    public String reloadSqlList(Model model, Pageable pageable, Condition condition, Sorter sort) {
        if (condition == null)
            condition = new Condition();
        condition.addExpression("type", "SQL");
        Page<DataSourceVO> dateSourceVO = dataSourceService.queryDataSourceByCondition(condition, pageable, sort);
        model.addAttribute("dateSourceVO", dateSourceVO);
        return "systools/datasource/sqllist-data";
    }

    @RequestMapping("/sqlEdit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String sqlEdit(Model model, String id) throws DevException {
        model.addAttribute("dateSourceVO", dataSourceQueryPubService.load(id));
        return "systools/datasource/sqlform";
    }

    @RequestMapping("/sqlAdd.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String sqlAdd(Model model) {
        return "systools/datasource/sqlform";
    }

    @RequestMapping("/synchroCheck.d")
    @ResponseBody
    public Object synchroCheck(DataSourceVO dateSourceVO) {
        return dataSourceService.synchroCheck(dateSourceVO);
    }

    
    @RequestMapping("/passwordCheck.d")
    @ResponseBody
    public Object passwordCheck(DataSourceVO dateSourceVO) {
    	
        return dataSourceService.passwordCheck(dateSourceVO);
    }

    
    /**
     * 设置是否默认为
     * 数据源显示方式
     */
    public void handleIsDefault(Page<DataSourceVO> dateSourceVO) {
        List<DataSourceVO> tempVO = dateSourceVO.getContent();
        for (DataSourceVO VO : tempVO) {
            if ("Y".equalsIgnoreCase(VO.getIsDefault())) {
                VO.setIsDefault("是");
            } else {
                VO.setIsDefault("否");
            }
        }
    }
    
    /**
    * @Title: testConn
    * @Description: 表单页面的测试连接方法
    * @param datasourceVO 表单数据
    * @return    参数说明
    */ 
    @RequestMapping("/testConn.d")
    @ResponseBody
    public Object testConn(DataSourceVO datasourceVO){
    	String encryptPassword = EncryptAndDecodeUtils.getEncryptString(datasourceVO.getPassword(), encryptKey);
    	datasourceVO.setPassword(encryptPassword);
    	return privateTestConn(datasourceVO);
    }
    
    /**
    * @Title: testConnection
    * @Description: 表格数据的测试连接方法
    * @param id 数据源id
    * @return    参数说明
    */ 
    @RequestMapping("/testConnection.d")
    @ResponseBody
    public Object testConnection(String id){
    	DataSourceVO datasourceVO = dataSourceQueryPubService.load(id);
    	String encryptPassword = EncryptAndDecodeUtils.getEncryptString(datasourceVO.getPassword(), encryptKey);
    	datasourceVO.setPassword(encryptPassword);
    	return privateTestConn(datasourceVO);
    }
    
    private Object privateTestConn(DataSourceVO datasourceVO){
    	String message = "";
    	try {
			dataSourceQueryPubService.getDataSourceConn(datasourceVO);
		} catch (ClassNotFoundException e) {
			message = e.getMessage();
		} catch (MalformedURLException e) {
			message = e.getMessage();
		} catch (SQLException e) {
			message = e.getMessage();
		} catch (IOException e) {
			message = e.getMessage();
		} catch (BusinessException e){
			message = e.getMessage();
		} catch (Exception e){
			message = e.getMessage();
		}
    	if(StringUtils.isBlank(message)){
    		message = "连接成功！";
    		return new AjaxResult(AjaxResult.STATUS_SUCCESS,message);
    	}
		return new AjaxResult(AjaxResult.STATUS_ERROR,message);
    }
}
