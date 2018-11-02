package com.gdsp.dev.core.utils.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.exceptions.DevException;

/**
 * @ClassName: AbstracDataExport
 * (这里用一句话描述这个类的作用)
 * 
 */
public abstract class AbstracDataExport<T> {
    /**
     * 日志变量
     */
    protected static final Logger                    logger             = LoggerFactory.getLogger(AbstracDataExport.class);

    Class<AbstractDataIOVO>        clazz;
    private List<AbstractDataIOVO> lisExportData;

    public AbstracDataExport(Class<AbstractDataIOVO> clazz) {
        this.clazz = clazz;
    }

    
	/**
	 * doExport
	 * @param request
	 * @param sheetName
	 * @param filename
	 * @param isOnlyTemplet
	 * @return filePath
	 * @throws DevException
	 * @date 2014年12月11日 下午2:43:30
	 */
    public String doExport(HttpServletRequest request, String sheetName, String filename, boolean isOnlyTemplet) throws DevException {
        FileOutputStream out = null;
        String filePath = null;
        try {
            filePath = makeExportFileName(request, filename);
            out = new FileOutputStream(filePath);
            ExcelHelper<AbstractDataIOVO> util = new ExcelHelper<AbstractDataIOVO>(clazz);
            if (!isOnlyTemplet) {
                List<AbstractDataIOVO> lis = getLisExportData();
                util.exportExcel(lis, sheetName, 60000, out);
            } else {
                util.exportExcel(new ArrayList<AbstractDataIOVO>(), sheetName, 60000, out);
            }
        } catch (Exception ex) {
            logger.debug(ex.getMessage(), ex);
            throw new DevException(ex.getMessage(), ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }
        return filePath;
    }

	/**
	 * 创建文件名
	 * @param request
	 * @param filename
	 * @return String
	 * @date 2014年12月12日 上午10:09:46
	 */
    public abstract String makeExportFileName(HttpServletRequest request, String filename);

    public List<AbstractDataIOVO> getLisExportData() {
        return lisExportData;
    }

    public void setLisExportData(List<AbstractDataIOVO> lisExportData) {
        this.lisExportData = lisExportData;
    }
}
