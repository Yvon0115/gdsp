package com.gdsp.platform.schedule.model;

import java.io.Serializable;

/*
 *
 */
public class JobParameter implements Serializable {

    private static final long serialVersionUID = 1L;
    private String            paraname;
    private String            description;
    private String            required;
    private String            inputMask;
    private String            value;

    public JobParameter() {
        this.paraname = "";
        this.description = "";
        this.required = "N";
        this.inputMask = "";
        this.value = "";
    }

    public String getParaname() {
        return paraname;
    }

    public void setParaname(String paraname) {
        this.paraname = paraname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getInputMask() {
        return inputMask;
    }

    public void setInputMask(String inputMask) {
        this.inputMask = inputMask;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}