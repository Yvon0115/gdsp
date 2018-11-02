package com.gdsp.platform.systools.notice.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class NoticeRangeVO extends AuditableEntity {

    private static final long serialVersionUID = 1L;
    private String            range_id;
    private String            notice_id;
    private int               type;                 //0为用户,1位机构,2为用户组
    private String            createby;
    private String            createtime;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRange_id() {
        return range_id;
    }

    public void setRange_id(String range_id) {
        this.range_id = range_id;
    }

    public String getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
