package com.gdsp.platform.common.model;

/**
 * 
* @ClassName: ResExportInfoVO
* (cognos 报表导出VO)
* @author hongwei.xu
* @date 2015年9月24日 下午4:49:50
*
 */
public class ResExportInfoVO {

    //导出路径
    private String  exportPath;
    private String  fileType;      //导出文件类型
    private Object  exportCdnValue;//导出统一参数
    private boolean isFixCnd;      //是否统一导出参数
    private String  subsname;

    public boolean isFixCnd() {
        return isFixCnd;
    }

    public void setFixCnd(boolean isFixCnd) {
        this.isFixCnd = isFixCnd;
    }

    public Object getExportCdnValue() {
        return exportCdnValue;
    }

    public void setExportCdnValue(Object exportCdnValue) {
        this.exportCdnValue = exportCdnValue;
    }

    public String getExportPath() {
        return exportPath;
    }

    public void setExportPath(String exportPath) {
        this.exportPath = exportPath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSubsname() {
        return subsname;
    }

    public void setSubsname(String subsname) {
        this.subsname = subsname;
    }
}
