package com.gdsp.integration.framework.reportentry.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class ReportVO extends AuditableEntity {

    private static final long serialVersionUID = 1L;
    private String            id;
    private String            report_name;
    private String            parent_path;
    private String            report_path;
    private String            report_type;
    private String            leaf;
    private String            cube_flag;
    private String            data_source;
    private String            param_type;
    private String            param_template_path;
    private String            comments;
    //数据源名称
    private String            data_source_name;
    //数据源类型
    private String            data_source_type;
    //资源ID,smartbi报表访问主要用资源ID
    private String            resource_id;

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public String getParent_path() {
        return parent_path;
    }

    public void setParent_path(String parent_path) {
        this.parent_path = parent_path;
    }

    public String getReport_path() {
        return report_path;
    }

    public void setReport_path(String report_path) {
        this.report_path = report_path;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getCube_flag() {
        return cube_flag;
    }

    public void setCube_flag(String cube_flag) {
        this.cube_flag = cube_flag;
    }

    public String getData_source() {
        return data_source;
    }

    public void setData_source(String data_source) {
        this.data_source = data_source;
    }

    public String getParam_type() {
        return param_type;
    }

    public void setParam_type(String param_type) {
        this.param_type = param_type;
    }

    public String getParam_template_path() {
        return param_template_path;
    }

    public void setParam_template_path(String param_template_path) {
        this.param_template_path = param_template_path;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getData_source_type() {
        return data_source_type;
    }

    public void setData_source_type(String data_source_type) {
        this.data_source_type = data_source_type;
    }

    public String getData_source_name() {
        return data_source_name;
    }

    public void setData_source_name(String data_source_name) {
        this.data_source_name = data_source_name;
    }
}
