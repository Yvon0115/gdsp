package com.gdsp.platform.systools.indexanddim.utils;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.gdsp.dev.base.exceptions.BusinessException;

public class ResolExcelUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResolExcelUtils.class);
    //	private static final int COL_COUNT = 13; //Excel每行列数

    //	private static final int COL_NUMBER=3;
    /**
     * 解析Excel
     * @param formFile
     * @return
     * @throws Exception
     */
    public static ArrayList<String[]> generateParamSql(MultipartFile formFile, Integer COL_COUNT){
        InputStream in = null;
        Workbook wb = null; //jxl的核心对象
        ArrayList<String[]> list = new ArrayList<String[]>();
        String filename = "";
        try {
            if (formFile == null) {
                throw new BusinessException("传入的文件为空");
            }
            in = formFile.getInputStream();//将文件读入到输入流中
            filename = formFile.getOriginalFilename();
            if (filename.endsWith("xlsx")) {
                wb = new XSSFWorkbook(in);
            } else {
                wb = new HSSFWorkbook(in);
            }
            int sheetNum = wb.getNumberOfSheets();//通过workbook对象获取sheet对象，此时sheet对象是一个数组
            if (sheetNum >= 0) {
                for (int i = 0; i < sheetNum; i++) {
                    Sheet sheet = wb.getSheetAt(i);
                    for (int j = 1; j <= sheet.getLastRowNum(); j++) {//使用sheet对象用来获取每1行，从1开始表示要去掉excel的标题
                        Row row = sheet.getRow(j);
                        String[] valStr = new String[COL_COUNT];//用数组来存放每一行的数据，COL_COUNT表示每一行的数据不能超过COL_COUNT，可以<=COL_COUNT
                        if (row != null) {
                            for (int k = 0; k < COL_COUNT; k++) {//使用sheet对象用来获取每1列，从0开始表示从第1列开始
                                Cell cell = row.getCell(k);//k表示列的号，j表示行的号
                                String tempcontent = "";
                                if (cell != null) {
                                    if ((cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)) {
                                   /*   String num = cell.getNumericCellValue() + "";
                                        String num1 = num.substring(num.lastIndexOf(".") + 1);
                                        if (Integer.valueOf(num1) == 0) {
                                            num = num.substring(0, num.lastIndexOf("."));
                                        }*/
                                   //此段原有逻辑是处理含有小数时进行取整，为提高代码健壮性，改为java自带的 DecimalFormat进行处理
										DecimalFormat df = new DecimalFormat("0");
										String num = df.format(cell.getNumericCellValue());
                                        tempcontent = num;
                                    } else {
                                        tempcontent = (cell.getStringCellValue() == null ? "" : cell.getStringCellValue());
                                    }
                                }
                                valStr[k] = tempcontent.trim();//将excel获取到的值赋值给String类型的数组
                            }
                        }
                        list.add(valStr);
                    }
                }
            }
            return list;
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(),e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                	LOGGER.error(e.getMessage(),e);
                }
            }
        }
        return null;
    }
}
