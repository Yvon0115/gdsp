package com.gdsp.platform.grant.auth.model;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.BaseEntity;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.usergroup.model.UserGroupVO;

/**
 * 用户关联用户组表
 * @author wwb 
 * @version 1.0 2015/6/15
 */
public class UserGroupRltVO extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String            pk_user;              // 用户
    private String            pk_usergroup;         // 用户组	
    private UserVO            userVO;
    private UserGroupVO       groupVO;
    /**
     * 创建时间
     */
    protected DDateTime       createTime;
    /**
     * 创建人
     */
    protected String          createBy;

    public String getPk_user() {
        return pk_user;
    }

    public void setPk_user(String pk_user) {
        this.pk_user = pk_user;
    }

    public String getPk_usergroup() {
        return pk_usergroup;
    }

    public void setPk_usergroup(String pk_usergroup) {
        this.pk_usergroup = pk_usergroup;
    }

    public DDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public UserGroupVO getGroupVO() {
        return groupVO;
    }

    public void setGroupVO(UserGroupVO groupVO) {
        this.groupVO = groupVO;
    }
}
