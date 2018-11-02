package com.gdsp.ptbase.appcfg.model;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.core.model.entity.AuditableEntity;

public class ChartWedgetVO extends AuditableEntity {

    private static final long serialVersionUID = 8887916626478950823L;
    private String            chart_name;                             // varchar(128),
    private String            dir_id;                                 // varchar(32),
    private String            ds_id;                                  // varchar(32),
    private String            chart_type_id;                          // varchar(32),
    private String            chart_style_id;                         // varchar(32),
    private DBoolean          drill_flag;                             // char(1),

    public String getChart_name() {
        return chart_name;
    }

    public void setChart_name(String chart_name) {
        this.chart_name = chart_name;
    }

    public String getDir_id() {
        return dir_id;
    }

    public void setDir_id(String dir_id) {
        this.dir_id = dir_id;
    }

    public String getDs_id() {
        return ds_id;
    }

    public void setDs_id(String ds_id) {
        this.ds_id = ds_id;
    }

    public String getChart_type_id() {
        return chart_type_id;
    }

    public void setChart_type_id(String chart_type_id) {
        this.chart_type_id = chart_type_id;
    }

    public String getChart_style_id() {
        return chart_style_id;
    }

    public void setChart_style_id(String chart_style_id) {
        this.chart_style_id = chart_style_id;
    }

    public DBoolean getDrill_flag() {
        return drill_flag;
    }

    public void setDrill_flag(DBoolean drill_flag) {
        this.drill_flag = drill_flag;
    }
}
