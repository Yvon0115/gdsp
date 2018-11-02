package com.gdsp.platform.systools.indexanddim.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.common.model.ExcelAttributeVO;
import com.gdsp.platform.common.utils.ExcelUtils;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.config.global.service.IDefDocListService;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.systools.indexanddim.model.IndexInfoVO;
import com.gdsp.platform.systools.indexanddim.service.IIdxDimRelService;
import com.gdsp.platform.systools.indexanddim.service.IIndexManageService;
import com.gdsp.platform.systools.indexanddim.utils.ResolExcelUtils;

/**
 * 指标管理控制类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("index/indexmanager")
public class IndexManageController {

    @Autowired
    private IIndexManageService      indexManageservice;
    @Resource
    private IDefDocListService       defDocListService;
    @Resource
    private IDefDocService           defDocService;
    @Autowired
    private IOrgPowerQueryPubService orgPowerPubService;
    @Autowired
    private IIdxDimRelService        idxDimRelService;

    /**
     * 查询所有指标信息
     * @param model
     * @param condition
     * @param pageable
     * @return
     */
    @RequestMapping("/list.d")
    public String list(Model model, Condition condition, Pageable pageable) {
        String code = "bp_indexanddim";
        String type_id = defDocListService.findIdByTypeCode(code);
        Sorter sort = new Sorter(Direction.ASC, "indexCode");
        model.addAttribute("type_id", type_id);
        Page<IndexInfoVO> IndexInfoVO = indexManageservice.queryIndexInfoByCondition(condition, pageable, sort);
        // 设置灵活查询是否可用
        handleIsSelect(IndexInfoVO);
        model.addAttribute("indexlist", IndexInfoVO);
        return "indexanddim/idxmanage/list";
    }

    /**
     * 查询指标数据
     * @param model
     * @param condition
     * @param pageable
     * @param indexCode 指标编码
     * @param indexName 指标名称
     * @param indexTableName 指标表名称
     * @param comedepart 部门名称
     * @return
     */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, Pageable pageable, String indexCode, String indexName,
            String indexTableName, String comedepart) {
        Sorter sort = new Sorter(Direction.ASC, "indexCode");
        if (!("@$@".equals(indexCode) || indexCode == null)) {
            condition.addExpression("indexCode", indexCode, "like");
        }
        if (!("@$@".equals(indexName) || indexName == null)) {
            condition.addExpression("indexName", indexName, "like");
        }
        if (!("@$@".equals(indexTableName) || indexTableName == null)) {
            String[] st = indexTableName.split(",");
            condition.addExpression("indexTableName", st, "in");
        }
        // same as above
        if (!("@$@".equals(comedepart) || comedepart == null)) {
            String[] ap = comedepart.split(",");
            condition.addExpression("comedepart", ap, "in");
        }
        Page<IndexInfoVO> IndexInfoVO = indexManageservice.queryIndexInfoByCondition(condition, pageable, sort);
        handleIsSelect(IndexInfoVO);
        model.addAttribute("indexlist", IndexInfoVO);
        return "indexanddim/idxmanage/list-data";
    }

    /**
     * 获取指标档案表数据(数据字典中指标表信息) 页面
     * @return
     */
    @RequestMapping("/findIdxList.d")
    @ViewWrapper(wrapped = false)
    public String findIdxList(Model model, Pageable pageable) {
        String code = "bp_indexanddim";
        String type_id = defDocListService.findIdByTypeCode(code);
        model.addAttribute("defDocVO", defDocService.findPageByType(type_id, pageable));
        return "indexanddim/idxmanage/idxtablist";
    }

    /**
     * 
     * 指标档案数据表信息 <br/>
     * 
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping("/IdxListData.d")
    @ViewWrapper(wrapped = false)
    public String IdxListData(Model model, Pageable pageable) {
        String code = "bp_indexanddim";
        String type_id = defDocListService.findIdByTypeCode(code);
        model.addAttribute("defDocVO", defDocService.findPageByType(type_id, pageable));
        return "indexanddim/idxmanage/idxdatatab-data";
    }

    /**
     * 添加指标页面
     * @return
     */
    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String addIdxInfo(Model model) {
        String code = "bp_indexanddim";
        String type_id = defDocListService.findIdByTypeCode(code);
        String dateFreq = "frequency";
        List<DefDocVO> defDocList = defDocService.findDateFreq(dateFreq);
        model.addAttribute("type_id", type_id);
        model.addAttribute("defDocList", defDocList);
        return "indexanddim/idxmanage/form";
    }

    /**
     * 修改指标
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
        IndexInfoVO indexInfo = indexManageservice.findIdxInfoById(id);
        String code = "bp_indexanddim";
        String type_id = defDocListService.findIdByTypeCode(code);
        String dateFreq = "frequency";
        List<DefDocVO> defDocList = defDocService.findDateFreq(dateFreq);
        model.addAttribute("indexinfo", indexInfo);
        model.addAttribute("type_id", type_id);
        model.addAttribute("defDocList", defDocList);
        model.addAttribute("tags", indexInfo.getStatfreq());
        return "indexanddim/idxmanage/form";
    }

    /**
     * 查看指标详情
     * @return
     */
    @RequestMapping("/findDetailIdx.d")
    @ViewWrapper(wrapped = false)
    public String findDetailIdx(Model model, String id) {
        IndexInfoVO findIdxInfoById = indexManageservice.findIdxInfoById(id);
        String dateFreq = "frequency";
        List<DefDocVO> defDocList = defDocService.findDateFreq(dateFreq);
        model.addAttribute("idxdetail", indexManageservice.findIdxInfoById(id));
        model.addAttribute("defDocList", defDocList);
        model.addAttribute("tags", findIdxInfoById.getStatfreq());
        return "indexanddim/idxmanage/idxdetaillist";
    }

    /**
     * 保存指标
     * @param indexInfoVO
     * @return
     */
    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(IndexInfoVO indexInfoVO, HttpServletRequest request) {
        if (indexInfoVO.getOnlyflexiablequery() == null) {
            indexInfoVO.setOnlyflexiablequery("N");
        } else {
            indexInfoVO.setOnlyflexiablequery("Y");
        }
        indexManageservice.saveIdxInfo(indexInfoVO);
        return AjaxResult.SUCCESS;
    }

    /**
     * 删除指标
     * @param id
     * @return
     */
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id != null && id.length > 0) {
            int idxDimCount = idxDimRelService.findIdxDimCount(id);
            if (idxDimCount != 0 && idxDimCount > 0) {
                return new AjaxResult(AjaxResult.STATUS_ERROR, "当前指标被指标维度关联关系引用，先请删除指标维度关联关系!", null, false);
            }
            indexManageservice.deleteIdxInfo(id);
        }
        return AjaxResult.SUCCESS;
    }

    /**
     * 部门树界面
     * @return
     */
    @RequestMapping("/orgList.d")
    public String orgList(Model model) {
        String loginUserID = AppContext.getContext().getContextUserId();
        List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(loginUserID);// 获取机构
        model.addAttribute("nodes", orgVOs);
        return "indexanddim/idxmanage/org-list";
    }

    /**
     * 导入指标界面显示
     * @return
     */
    @RequestMapping("/importIdxInfo.d")
    @ViewWrapper(wrapped = false)
    public String importIdxInfo() {
        return "indexanddim/idxmanage/importidxinfo";
    }

    /**
     * 导入指标实现
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "static-access" })
    @RequestMapping("/execImportIdxInfo.d")
    public String execImportIdxInfo(MultipartHttpServletRequest request, Model model){
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mulRequest.getFile("excelFile");
        ResolExcelUtils fromExcel = new ResolExcelUtils();
//        int COL_COUNT = 14;// excel每行的列数
        int COL_COUNT = 3;
        ArrayList<String[]> excelList = fromExcel.generateParamSql(file,
                COL_COUNT);
        // 定义一个错误的集合
        List<String> errorList = new ArrayList<String>();
        indexManageservice.saveExcelIdxInfo(excelList, errorList);
        model.addAttribute("errorList", errorList);
        model.addAttribute("excelList", excelList);
        return "indexanddim/idxmanage/doimportidxtip";
    }

    /**
     * 导出模板实现
     * @param model
     * @param response
     * @return
     * @throws IOException 
     */
    @SuppressWarnings("resource")
    @RequestMapping("/doExportIdx.d")
    public void doExportIdx(Model model, HttpServletResponse response, HttpServletRequest request) throws IOException{
        List<String[]> list = new ArrayList<String[]>();
       /* String[] data = { "指标编码（必填）", "指标名称（必填）", "指标值字段名（必填）", "指标编码字段名（必填）",
                "指标表显示名称（必填）", "是否灵活查询可用（是/否）", "统计频度（可填日、周、月、季、年）", "数据来源",
                "归属部门", "业务口径描述", "技术口径描述", "精度", "计量单位", "备注" };*/
        //修改人：wangxiaolong
        //修改时间：2017-3-22
        //修改原因：导出模板只是对添加指标中的指标 进行导出
        String[] data = { "指标编码（必填）", "指标名称（必填）","业务口径描述" };
        list.add(data);
        ExcelAttributeVO excelAttributeVO = new ExcelAttributeVO();
        excelAttributeVO.setHeader(list);
        excelAttributeVO.setSheetName("指标信息表");
        List<ExcelAttributeVO> dataSet = new ArrayList<ExcelAttributeVO>();
        dataSet.add(excelAttributeVO);
        String fileName = "指标信息表.xls";
        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        response.reset();
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        OutputStream out = response.getOutputStream();
        ExcelUtils.createExcel(dataSet, out);
        out.flush();
    }

    /**
     * 唯一性检查
     * @param userGroup
     * @return
     */
    @RequestMapping("/uniqueCheck.d")
    @ResponseBody
    public Object uniqueCheck(IndexInfoVO indexInfoVO) {
        return indexManageservice.uniqueCheck(indexInfoVO);
    }

    /**
     * 
     * 设置灵活查询是否可以使用 <br/>
     * 
     * @param IndexInfoVO
     */
    public void handleIsSelect(Page<IndexInfoVO> IndexInfoVO) {
        List<IndexInfoVO> tempVO = IndexInfoVO.getContent();
        for (IndexInfoVO VO : tempVO) {
            if ("Y".equalsIgnoreCase(VO.getOnlyflexiablequery())) {
                VO.setOnlyflexiablequery("是");
            } else {
                VO.setOnlyflexiablequery("否");
            }
        }
    }
}
