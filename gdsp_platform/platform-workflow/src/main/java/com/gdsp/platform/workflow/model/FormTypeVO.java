package com.gdsp.platform.workflow.model;

import java.util.ArrayList;
import java.util.List;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 预置单据类型表
 * @author gdsp
 *
 */
public class FormTypeVO extends AuditableEntity {

    /**
     * 
     */
    private static final long    serialVersionUID = 1L;
    private String               pk_categoryid;                                     // 流程类型id
    private String               categoryType;                                      // 流程类型
    private String               formCode;                                          // 单据类型编码
    private String               formName;                                          // 单据类型名称
    private String               formURL;                                           // 表单URL
    //单据对应的参数集合
    private List<FormVariableVO> parameters       = new ArrayList<FormVariableVO>();

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormURL() {
        return formURL;
    }

    public void setFormURL(String formURL) {
        this.formURL = formURL;
    }

    public List<FormVariableVO> getParameters() {
        return parameters;
    }

    public void setParameters(List<FormVariableVO> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(FormVariableVO param) {
        this.parameters.add(param);
    }

    /**
     * @return the pk_categoryid
     */
    public String getPk_categoryid() {
        return pk_categoryid;
    }

    /**
     * @param pk_categoryid the pk_categoryid to set
     */
    public void setPk_categoryid(String pk_categoryid) {
        this.pk_categoryid = pk_categoryid;
    }

    /**
     * @return the categoryType
     */
    public String getCategoryType() {
        return categoryType;
    }

    /**
     * @param categoryType the categoryType to set
     */
    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
