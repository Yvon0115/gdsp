package com.gdsp.platform.systools.notice.model;

import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.usergroup.model.UserGroupVO;

public class NoticePowerVO extends NoticeRangeVO {

    private static final long serialVersionUID = 1L;
    private UserVO            userVO;
    private OrgVO             orgVO;
    private UserGroupVO       userGroupVO;

    public String getTableName() {
        return "rms_notice_range";
    }

    public UserGroupVO getUserGroupVO() {
        return userGroupVO;
    }

    public void setUserGroupVO(UserGroupVO userGroupVO) {
        this.userGroupVO = userGroupVO;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public OrgVO getOrgVO() {
        return orgVO;
    }

    public void setOrgVO(OrgVO orgVO) {
        this.orgVO = orgVO;
    }
}
