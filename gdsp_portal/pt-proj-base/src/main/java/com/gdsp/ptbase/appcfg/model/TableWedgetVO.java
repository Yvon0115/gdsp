package com.gdsp.ptbase.appcfg.model;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.core.model.entity.AuditableEntity;

public class TableWedgetVO extends AuditableEntity {

    private static final long serialVersionUID = -4311719068194567722L;
    private String            table_name;                              // varchar(128),
    private String            dir_id;                                  // varchar(32),
    private String            ds_id;                                   // varchar(32),
    private String            icon_url;                                // varchar(32),
    private DBoolean          show_head;                               // char(1),
    private DBoolean          show_tail;                               // char(1),
    private DBoolean          show_arg;                                // char(1),
    private DBoolean          show_chart;                              // char(1),
    private DBoolean          show_pager;                              // char(1),
    private String            script;                                  // text,
    private int               page_size;                               // int,

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
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

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public DBoolean getShow_head() {
        return show_head;
    }

    public void setShow_head(DBoolean show_head) {
        this.show_head = show_head;
    }

    public DBoolean getShow_tail() {
        return show_tail;
    }

    public void setShow_tail(DBoolean show_tail) {
        this.show_tail = show_tail;
    }

    public DBoolean getShow_arg() {
        return show_arg;
    }

    public void setShow_arg(DBoolean show_arg) {
        this.show_arg = show_arg;
    }

    public DBoolean getShow_chart() {
        return show_chart;
    }

    public void setShow_chart(DBoolean show_chart) {
        this.show_chart = show_chart;
    }

    public DBoolean getShow_pager() {
        return show_pager;
    }

    public void setShow_pager(DBoolean show_pager) {
        this.show_pager = show_pager;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
}
