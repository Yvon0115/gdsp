package com.gdsp.platform.systools.notice.model;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.core.model.entity.AuditableEntity;

public class SysNoticeVO extends AuditableEntity {

    private static final long serialVersionUID = 1L;
    private String            title;
    private String            content;
    private DDate             publish_date;
    private DDate             start_date;
    private DDate             end_date;
    private DBoolean          valid_flag;
    private int               readflag;

    public int getReadflag() {
        return readflag;
    }

    public void setReadflag(int readflag) {
        this.readflag = readflag;
    }

    public DDate getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(DDate publish_date) {
        this.publish_date = publish_date;
    }

    public DDate getStart_date() {
        return start_date;
    }

    public void setStart_date(DDate start_date) {
        this.start_date = start_date;
    }

    public DDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(DDate end_date) {
        this.end_date = end_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DBoolean getValid_flag() {
        return valid_flag;
    }

    public void setValid_flag(DBoolean valid_flag) {
        this.valid_flag = valid_flag;
    }
}
