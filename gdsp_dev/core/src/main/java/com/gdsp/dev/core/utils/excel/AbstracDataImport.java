package com.gdsp.dev.core.utils.excel;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gdsp.dev.base.exceptions.BusinessException;

/**
 * AbstracDataImport
 * (这里用一句话描述这个类的作用)
 */
@SuppressWarnings("hiding")
public abstract class AbstracDataImport<AbstractDataIOVO> {

    Class<AbstractDataIOVO>        clazz;
    private List<AbstractDataIOVO> lisImportData;

    public AbstracDataImport(Class<AbstractDataIOVO> clazz) {
        this.clazz = clazz;
    }

    public List<AbstractDataIOVO> doImport(String sheetname, MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            if (filename == null || "".equals(filename)) {
                return null;
            }
            if (!filename.endsWith("xls") && !filename.endsWith("xlsx")) {
                throw new BusinessException("传入的文件不是excel");
            }
            ExcelHelper<AbstractDataIOVO> util = new ExcelHelper<AbstractDataIOVO>(clazz);
            List<AbstractDataIOVO> list = util.importExcel(sheetname, file);
            return convertImportVO2InserVO(list);
        } catch (Exception ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

    /**
     * (对导入数据进行显示处理)
     * @param  list
     * @return List<AbstractDataIOVO> 返回类型
     * @since 2014年12月12日 上午9:32:42
     */
    public abstract List<AbstractDataIOVO> convertImportVO2InserVO(List<AbstractDataIOVO> list);

    public List<AbstractDataIOVO> getLisImportData() {
        return lisImportData;
    }

    public void setLisImportData(List<AbstractDataIOVO> lisImportData) {
        this.lisImportData = lisImportData;
    }
}
