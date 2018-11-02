package com.gdsp.ptbase.appcfg.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class LayoutColumnVO extends AuditableEntity implements IPortletLayoutMeta {

    private static final long serialVersionUID = -4657314495996966380L;
    private String            layout_id;                               // varchar(32),
    private String            column_id;                               // varchar(32),
    private int               colspan;                                 // tinyint,
    private int               sortnum;                                 // int,

    public String getLayout_id() {
        return layout_id;
    }

    public void setLayout_id(String layout_id) {
        this.layout_id = layout_id;
    }

    public String getColumn_id() {
        return column_id;
    }

    public void setColumn_id(String column_id) {
        this.column_id = column_id;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public int getSortnum() {
        return sortnum;
    }

    public void setSortnum(int sortnum) {
        this.sortnum = sortnum;
    }
}
