package com.gdsp.integration.cognos.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.integration.framework.kpi.model.KpisVO;
import com.gdsp.integration.framework.param.model.AcParam;

public class BICognosDisplayMetaVO extends AuditableEntity {

    private static final long   serialVersionUID = 5183646355110947698L;
    /**report url*/
    private String              url;
    /**默认值MAP(参数key，默认值)*/
    private Map<String, Object> dvMap;
    /**参数内容Map(参数key, 内容)*/
    private Map<String, Object> ctMap;
    /**参数*/
    private List<AcParam>       params;

    public List<AcParam> getParams() {
        return params;
    }

    public void setParams(List<AcParam> params) {
        this.params = params;
    }

    /**cube报表参数固定格式，转移到前段*/
    private Map<String, Object> cubeValueMap;
    /**报表类型*/
    private String              report_type;
    /**参数条件类型*/
    private String              param_type;
    /**跨列*/
    private Integer             cols;
    /**指标信息*/
    private List<KpisVO>        kpivo;
    private String              file_url;
    /***/
    private String              cube_flag;
    private String              comments;

    public String getCube_flag() {
        return cube_flag;
    }

    public void setCube_flag(String cube_flag) {
        this.cube_flag = cube_flag;
    }

    public Map<String, Object> getCubeValueMap() {
        return cubeValueMap;
    }

    public void setCubeValueMap(Map<String, Object> cubeValueMap) {
        this.cubeValueMap = cubeValueMap;
    }

    public Integer getCols() {
        return cols;
    }

    public List<KpisVO> getKpivo() {
        return kpivo;
    }

    public void setKpivo(List<KpisVO> kpivo) {
        this.kpivo = kpivo;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public Map<String, Object> getDvMap() {
        return dvMap;
    }

    public void setDvMap(Map<String, Object> dvMap) {
        this.dvMap = dvMap;
    }

    public Map<String, Object> getCtMap() {
        return ctMap;
    }

    public void setCtMap(Map<String, Object> ctMap) {
        this.ctMap = ctMap;
    }

    /**
     * 其他变量存储Map
     */
    private Map<String, Object> otherVarMap = new HashMap<String, Object>();

    public Map<String, Object> getOtherVarMap() {
        return otherVarMap;
    }

    public void setOtherVarMap(Map<String, Object> otherVarMap) {
        this.otherVarMap = otherVarMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public String getParam_type() {
        return param_type;
    }

    public void setParam_type(String param_type) {
        this.param_type = param_type;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
