package com.gdsp.platform.systools.indexanddim.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.common.model.ExcelAttributeVO;
import com.gdsp.platform.common.utils.ExcelUtils;
import com.gdsp.platform.systools.indexanddim.service.IIdxDimRelService;
import com.gdsp.platform.systools.indexanddim.utils.ResolExcelUtils;

/**
 * 指标维度关联关系控制类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("indexanddim/idxdimrel")
public class IndexDimRelController {

    @Autowired
    private IIdxDimRelService idxDimRelService;

    /**
     * 查询指标树
     * @param model
     * @return
     */
    @RequestMapping("/list.d")
    @SuppressWarnings("rawtypes")
    public String list(Model model) {
        Map idxTreeMap = idxDimRelService.queryIdxList();
        model.addAttribute("idxTreeMap", idxTreeMap);
        return "indexanddim/idxdimrel/list";
    }

    /**
     * 选择指标 查询关联的维度
     * indexid  指标id
     * @return
     */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, String indexid, Pageable pageable) {
        model.addAttribute("indexid", indexid);
        model.addAttribute("dimList", idxDimRelService.queryDimListByIdxId(condition, indexid, pageable));
        return "indexanddim/idxdimrel/list-data";
    }

    /**
     * 添加维度  维度为当前指标未关联的维度
     * indexId 指标id
     * @return
     */
    @RequestMapping("/addDim.d")
    @ViewWrapper(wrapped = false)
    public String addDims(Model model, String indexId, Condition condition, Pageable pageable) {
        model.addAttribute("indexId", indexId);
        model.addAttribute("noDimList", idxDimRelService.queryNoDimListByIdxId(indexId, pageable, condition));
        return "indexanddim/idxdimrel/dimlist-add";
    }

    /**
     * 
     * 当前指标未关联的维度数据data <br/>
     * 
     * @param model
     * @param indexId
     * @param condition
     * @param pageable
     * @return
     */
    @RequestMapping("/dimListData.d")
    @ViewWrapper(wrapped = false)
    public String dimListData(Model model, String indexId, Condition condition, Pageable pageable) {
        model.addAttribute("indexId", indexId);
        model.addAttribute("noDimList", idxDimRelService.queryNoDimListByIdxId(indexId, pageable, condition));
        return "indexanddim/idxdimrel/dimlist-data";
    }

    /**
     * 保存指标关联的维度
     * @return
     */
    @RequestMapping("/saveDim.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public Object saveDim(String indexId, String... id) {
        idxDimRelService.saveDim(indexId, id);
        return AjaxResult.SUCCESS;
    }

    /**
     * 删除指标维度关联关系
     * @return
     */
    @RequestMapping("/deleteDim.d")
    @ResponseBody
    public Object deleteDim(String... id) {
        idxDimRelService.deleteDim(id);
        return AjaxResult.SUCCESS;
    }

    /**
     * 
     * 导出模板 <br/>
     * 
     * @throws IOException 
     */
    @RequestMapping("/emportMould.d")
    public void emportMould(HttpServletResponse response) throws IOException {
        List<String[]> list = new ArrayList<String[]>();
        String[] data = { "指标编码（必填）", "指标名称（必填）", "维度名称（必填）" };
        list.add(data);
        ExcelAttributeVO excelAttributeVO = new ExcelAttributeVO();
        excelAttributeVO.setHeader(list);
        excelAttributeVO.setSheetName("指标维度关联关系导入模板");
        List<ExcelAttributeVO> dataSet = new ArrayList<ExcelAttributeVO>();
        dataSet.add(excelAttributeVO);
        String fileName = "指标维度关联关系导入模板.xls";
        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        response.reset();
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename="
                + fileName);
        OutputStream out = response.getOutputStream();
        ExcelUtils.createExcel(dataSet, out);
        out.flush();
    }

    /**
     * 
     * 导入excel弹窗 <br/>
     * 
     * @return
     */
    @RequestMapping("/importIdxDim.d")
    public String importIdxDim() {
        return "indexanddim/idxdimrel/importidxdim";
    }

    /**
     * 
     * 导入指标维度关联关系 <br/>
     * 
     * @throws Exception 
     */
    @RequestMapping("/execImportIdxDim.d")
    @SuppressWarnings("static-access")
    public String execImportIdxDim(MultipartHttpServletRequest request, Model model){
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mulRequest.getFile("excelFile");
        ResolExcelUtils fromExcel = new ResolExcelUtils();
        int COL_COUNT = 3;// excel 每行的列数
        ArrayList<String[]> excelList = fromExcel.generateParamSql(file,
                COL_COUNT);
        // 定义一个错误的集合
        List<String> errorList = new ArrayList<String>();
        idxDimRelService.saveExcelIdxDim(excelList, errorList);
        model.addAttribute("errorList", errorList);
        model.addAttribute("excelList", excelList);
        return "indexanddim/idxdimrel/importidxtip";
    }
}
