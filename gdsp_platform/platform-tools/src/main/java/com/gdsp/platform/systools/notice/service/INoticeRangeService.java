package com.gdsp.platform.systools.notice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.usergroup.model.UserGroupVO;

public interface INoticeRangeService {
/*权限拆分----------------------------------------2016.12.28
 * 未被调用(SysNoticeController中将调用改service的代码全部注释)
    public Page<UserVO> queryUsersByAddCond(String noticeID, String userID, Condition cond, Pageable page);
*/
    public Page<OrgVO> queryOrgByConditions(Condition condition, Pageable page, String noticeID, String userID, Sorter sort);

    public Page<UserGroupVO> queryUserGroupByConditions(Condition condition, Pageable page, String noticeID, String userID, Sorter sort);

    void insertNoticeRange(String noticeID, int type, String... rangeIDs);

    public boolean deleteNoticeRange(String... ids);
}
