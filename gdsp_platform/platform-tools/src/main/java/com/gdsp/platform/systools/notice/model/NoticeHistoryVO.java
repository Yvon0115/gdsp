package com.gdsp.platform.systools.notice.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class NoticeHistoryVO extends AuditableEntity {

    private static final long serialVersionUID = 1L;
    private String            notice_id;
    private String            user_id;
    private String            access_date;

    public String getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccess_date() {
        return access_date;
    }

    public void setAccess_date(String access_date) {
        this.access_date = access_date;
    }
}
