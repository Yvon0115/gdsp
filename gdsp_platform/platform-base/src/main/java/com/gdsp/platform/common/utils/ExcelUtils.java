package com.gdsp.platform.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.platform.common.model.ExcelAttributeVO;

/**
 * @version 
 * <p> Excel导出工具类 </p>
 * <p> 使用: <br>调用静态方法createExcel(), 传入封装好的ExcelAttributeVO对象集合与OutputStream对象</p>
 * <p style="font-style:oblique;"> 注: 目前表头列与数据列无对应关系, 封装数据时请注意表头与数据的顺序.</p>
 * @author wqh 
 * 2016年3月23日 上午10:14:05
 */
public class ExcelUtils {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 这是一个通用的方法，可以将放置在JAVA集合中并且符合一定条件的数据 以EXCEL 的形式输出。
     * 
     * @param dataSet
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     */
    public static void createExcel(List<ExcelAttributeVO> dataSet, OutputStream out) {
        /** 声明一个工作薄 */
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 虽然创建了样式，但暂时没用到
        HSSFCellStyle titleCellStyle = getCellStyle(workbook, "title"); // 生成一个样式(表头样式)
        HSSFCellStyle headerCellStyle = getCellStyle(workbook, "header"); // 生成一个样式(表头样式)
        HSSFCellStyle valueCellStyle = getCellStyle(workbook, "value"); // 生成并设置另一个样式(数据样式)
        /** 遍历List, 里面是一个个的VO对象(sheet页，有序排列) */
        for (int sheetNum = 0; sheetNum < dataSet.size(); sheetNum++) {
            ExcelAttributeVO attributeVO = dataSet.get(sheetNum);
            /** 生成一个表格(sheet页) */
            if (attributeVO.getSheetName() == null) {
                attributeVO.setSheetName("sheet" + sheetNum);
            }
            HSSFSheet sheet = workbook.createSheet(attributeVO.getSheetName());
            //			 设置表格默认列宽度为15
            sheet.setDefaultColumnWidth(15);
            /** 生成标题(表名) */
            createTitle(sheet, attributeVO, titleCellStyle);
            /** 生成表头区域 */
            createHeader(sheet, attributeVO, headerCellStyle);
            /** 生成数据区域 */
            createValue(sheet, attributeVO, valueCellStyle);
        }
        try {
            workbook.write(out);
        } catch (SecurityException e) {
        	logger.error(e.getMessage(),e);
        } catch (IllegalArgumentException e) {
        	logger.error(e.getMessage(),e);
        } catch (IOException e) {
        	logger.error(e.getMessage(),e);
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        }
    }

    /**
     *  创建excel标题(表名)
     * @param sheet
     * @param attributeVO
     * @param cellStyle
     */
    private static void createTitle(HSSFSheet sheet, ExcelAttributeVO attributeVO, HSSFCellStyle cellStyle) {
        if (attributeVO.getTitle() != null) {
            HSSFRow titleRow = sheet.createRow(0); // 创建标题行(第一行)
            HSSFCell titleCell = titleRow.createCell(0); // 创建单元格(第一列)
            HSSFRichTextString titleText = new HSSFRichTextString(attributeVO.getTitle()); // 标题(表名)
            titleCell.setCellValue(titleText); // 设值
            titleCell.setCellStyle(cellStyle); // 设样式
            // 合并单元格 - 标题
            if (attributeVO.getHeader().size() > 0) {
                CellRangeAddress region = new CellRangeAddress(0, 1, 0, attributeVO.getHeader().get(0).length - 1); // 开始行，结束行，开始列，结束列
                sheet.addMergedRegion(region);
            }
        }
    }

    /**
     *  创建excel表头
     * @param sheet
     * @param attributeVO
     * @param headerStartRowIndex
     * @param cellStyle
     */
    private static void createHeader(HSSFSheet sheet, ExcelAttributeVO attributeVO, HSSFCellStyle cellStyle) {
        int startRow; // 表头开始行
        if (attributeVO.getTitle() == null) {
            startRow = 0;
        } else {
            startRow = 2;
        }
        List<String[]> header = attributeVO.getHeader(); // 获取表头
        if (header.size() > 0) {
            for (int i = 0; i < header.size(); i++) {
                HSSFRow headerRow = sheet.createRow(startRow); // 从第三行开始写
                startRow++;
                String[] headerContent = header.get(i);
                for (int j = 0; j < headerContent.length; j++) {
                    HSSFCell headerCell = headerRow.createCell(j);
                    headerCell.setCellValue(new HSSFRichTextString(headerContent[j]));
                    headerCell.setCellStyle(cellStyle);
                }
            }
        }
    }

    /**
     *  创建excel数据区域
     * @param workbook
     * @param sheet
     * @param attributeVO
     * @param headerStartRowIndex
     * @param cellStyle
     * @param pattern
     */
    private static void createValue(HSSFSheet sheet, ExcelAttributeVO attributeVO, HSSFCellStyle cellStyle) {
        int startRow;
        // 数据从表头结束行下一行开始
        if (attributeVO.getTitle() == null) {
            if (attributeVO.getHeader() == null) {
                startRow = 0;
            } else {
                startRow = attributeVO.getHeader().size();
            }
        } else {
            if (attributeVO.getHeader() == null) {
                startRow = 2;
            } else {
                startRow = attributeVO.getHeader().size() + 2;
            }
        }
        List<ArrayList> values = attributeVO.getValue();
        if (values != null) {
            Iterator<ArrayList> vit = values.iterator();
            // 值域循环(有多少行)
            while (vit.hasNext()) {
                HSSFRow row = sheet.createRow(startRow);
                startRow++;
                List rowVals = vit.next();
                // 行内循环(一行有多少元素/单元格)
                for (int i = 0; i < rowVals.size(); i++) {
                    HSSFCell cell = row.createCell(i);
                    Object value = rowVals.get(i);
                    String valueText = null;
                    // 如果只为日期类型, 根据pattern转换为string
                    /*if (value instanceof Date) {
                    	Date date = (Date) value;
                    	if ("".equals(pattern) || pattern == null) {
                    		pattern = "yyyy-MM-dd";
                    	}
                    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    	valueText = sdf.format(date);
                    } else {
                    	// 其它数据类型都当作字符串简单处理
                    	valueText = value.toString();
                    }*/
                    valueText = value.toString();
                    if (valueText != null) {
                        HSSFRichTextString richStr = new HSSFRichTextString(valueText);
                        // HSSFFont vfont = workbook.createFont();
                        //						vfont.setColor(HSSFColor.BLACK.index);
                        //						richStr.applyFont(vfont);
                        cell.setCellValue(richStr);
                        cell.setCellStyle(cellStyle);
                    }
                } // 行内循环结束
            } // 值域循环结束
        }
    }

    /**
     *  获取表格单元格样式, <br> region参数: "header" 或其他, "header"返回表头样式, 其他返回内容样式.
     * @param workbook 工作表
     * @param region 
     * @return 
     */
    public static HSSFCellStyle getCellStyle(HSSFWorkbook workbook, String region) {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        /** 标题样式 */
        if ("title".equals(region)) {
            //			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            //			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);   
            //		    style.setWrapText(true);    // 设置自动换行
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直也居中
            style.setBorderTop(HSSFCellStyle.BORDER_THIN); // 上、右、下、左边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            //			font.setColor(HSSFColor.VIOLET.index);
            font.setFontHeightInPoints((short) 16); // 16号字体 - 标题
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗体
            style.setFont(font); // 把字体应用到当前的样式
        }
        /** 表头样式 */
        else if ("header".equals(region)) {
            //			style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
            //			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style.setWrapText(true); // 表头自动换行
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 表头粗体
            style.setFont(font);
            /** 内容样式 */
        } else {
            style.setWrapText(true); // 内容自动换行
            //		    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            style.setFont(font);
        }
        return style;
    }
}
