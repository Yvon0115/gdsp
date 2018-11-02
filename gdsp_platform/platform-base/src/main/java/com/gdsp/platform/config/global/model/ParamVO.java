package com.gdsp.platform.config.global.model;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class ParamVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -5750659396354113539L;
    public static final int   PARAM_INT        = 0;
    public static final int   PARAM_DOUBLE     = 1;
    public static final int   PARAM_BOOLEAN    = 2;
    public static final int   PARAM_DATE       = 3;
    public static final int   PARAM_CHAR       = 4;
    private String            parname;                                 //参数名称
    private String            parcode;                                 //参数编码
    private String            pargroupid;                              //分组主键
    private String            groupname;                               //分组名称
    private String            groupcode;                               //分组编码
    private int               valuetype;                               //参数类型   0:整型,1:Double型,2:逻辑型,3:日期型,4:字符
    private String            valuerange;                              //取值范围
    private String            defaultvalue;                            //默认值
    private String            parvalue;                                //参数值
    private String            memo;                                    //使用说明
    private String            valuetypestr;                            //参数类型名称
    private String            defaultvaluestr;                         //默认值显示
    private String            parvaluestr;                             //默认值显示

    public String getParname() {
        return parname;
    }

    public void setParname(String parname) {
        this.parname = parname;
    }

    public String getParcode() {
        return parcode;
    }

    public void setParcode(String parcode) {
        this.parcode = parcode;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public int getValuetype() {
        return valuetype;
    }

    public void setValuetype(int valuetype) {
        this.valuetype = valuetype;
    }

    public String getValuerange() {
        return valuerange;
    }

    public void setValuerange(String valuerange) {
        this.valuerange = valuerange;
    }

    public String getDefaultvalue() {
        if (StringUtils.isBlank(defaultvalue)) {
            defaultvalue = "N/A";
        }
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    public String getParvalue() {
        return parvalue;
    }

    public void setParvalue(String parvalue) {
        this.parvalue = parvalue;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPargroupid() {
        return pargroupid;
    }

    public void setPargroupid(String pargroupid) {
        this.pargroupid = pargroupid;
    }

    //参数类型   0:整型,1:Double型,2:逻辑型,3:日期型,4:字符
    public String getValuetypestr() {
        switch (valuetype) {
            case 0:
                valuetypestr = "整型";
                break;
            case 1:
                valuetypestr = "浮点型";
                break;
            case 2:
                valuetypestr = "逻辑型";
                break;
            case 3:
                valuetypestr = "日期型";
                break;
            default:
                valuetypestr = "字符";
                break;
        }
        return valuetypestr;
    }

    public String getDefaultvaluestr() {
        if (valuetype == 2) {
            if ("false".equalsIgnoreCase(defaultvalue) || "N".equalsIgnoreCase(defaultvalue))
                defaultvaluestr = "否";
            else if ("true".equalsIgnoreCase(defaultvalue) || "Y".equalsIgnoreCase(defaultvalue))
                defaultvaluestr = "是";
        } else {
            defaultvaluestr = defaultvalue;
        }
        return defaultvaluestr;
    }

    public String getParvaluestr() {
        if (valuetype == 2) {
            if ("false".equalsIgnoreCase(parvalue) || "N".equalsIgnoreCase(parvalue))
                parvaluestr = "否";
            else if ("true".equalsIgnoreCase(parvalue) || "Y".equalsIgnoreCase(parvalue))
                parvaluestr = "是";
        } else {
            parvaluestr = parvalue;
        }
        return parvaluestr;
    }
}
