package com.gdsp.integration.framework.reportentry.model;

import java.util.HashMap;
import java.util.Map;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class BIReportDisplayMetaVO extends AuditableEntity {

    private static final long serialVersionUID = 5183646355110947698L;
    /**reportQuery url*/
    private String            queryUrl;
    /**reportQuery url*/
    private String            exportUrl;
    /**查询区域路径*/
    private String            paramFilePath;
    /**报表类型*/
    private String            reportPath;
    /**参数条件类型*/
    private String            param_type;
    /**跨列*/
    private Integer           cols;
    /**报表说明*/
    private String            comments;
    /**菜单类型*/
    private int               menuType;
    /**报表名称*/
    private String            name;

    /**
     * @return Returns the queryUrl.
     */
    public String getQueryUrl() {
        return queryUrl;
    }

    /**
     * @param queryUrl The queryUrl to set.
     */
    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    /**
     * @return Returns the exportUrl.
     */
    public String getExportUrl() {
        return exportUrl;
    }

    /**
     * @param exportUrl The exportUrl to set.
     */
    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    /**
     * @return Returns the paramFilePath.
     */
    public String getParamFilePath() {
        return paramFilePath;
    }

    /**
     * @param paramFilePath The paramFilePath to set.
     */
    public void setParamFilePath(String paramFilePath) {
        this.paramFilePath = paramFilePath;
    }

    /**
     * 存储默认日期Map
     */
    private Map<String, Object> dateMap = new HashMap<String, Object>();

    /**
     * @return Returns the defaultDateMap.
     */
    public Map<String, Object> getDateMap() {
        return dateMap;
    }

    /**
     * @param defaultDateMap The defaultDateMap to set.
     */
    public void setDefaultDateMap(Map<String, Object> dateMap) {
        this.dateMap = dateMap;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getParam_type() {
        return param_type;
    }

    public void setParam_type(String param_type) {
        this.param_type = param_type;
    }

    /**
     * @return Returns the comments.
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments The comments to set.
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return Returns the menuType.
     */
    public int getMenuType() {
        return menuType;
    }

    /**
     * @param menuType The menuType to set.
     */
    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
