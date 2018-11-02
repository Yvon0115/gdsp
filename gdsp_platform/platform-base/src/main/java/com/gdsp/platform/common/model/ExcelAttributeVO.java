package com.gdsp.platform.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @version
 * <p> Excel导出 Bean </p>
 * <p> 封装属性：1.sheet页(工作表)名称 2.标题 3.表头 4.数据  </p>
 * <p style="font-style:oblique;"> 注: 目前表头列与数据列无对应关系, 封装数据时请对应表头与数据的顺序. </p>
 * @author wqh
 * 2016年3月25日
 * 下午4:12:07
 */
public class ExcelAttributeVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //	private List headSet;    // 表头列表(行数为list长度)
    //	private List<int[]> mergeSet;    // 合并单元格(int[]内4个参数)
    //	private HSSFCellStyle titleStyle;    // 标题样式
    //	private HSSFCellStyle headerStyle;    // 表头样式
    //	private HSSFCellStyle valueStyle;    // 数据样式
    private String            sheetName;            // sheet页名称
    private String            title;                // 标题(表名)
    private List<String[]>    header;               // 表头
    private List<ArrayList>   value;                // 数据

    public ExcelAttributeVO() {}

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String[]> getHeader() {
        return header;
    }

    public void setHeader(List<String[]> header) {
        this.header = header;
    }

    public List<ArrayList> getValue() {
        return value;
    }

    public void setValue(List<ArrayList> value) {
        this.value = value;
    }
}
