package com.gdsp.platform.systools.notice.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.usergroup.model.UserGroupVO;
import com.gdsp.platform.grant.utils.GrantUtils;
import com.gdsp.platform.systools.notice.dao.INoticeRangeDao;
import com.gdsp.platform.systools.notice.model.NoticePowerVO;
import com.gdsp.platform.systools.notice.model.NoticeRangeVO;
import com.gdsp.platform.systools.notice.service.INoticeRangeService;

@Service
@Transactional(readOnly = true)
public class NoticeRangeServiceImpl implements INoticeRangeService {

    @Autowired
    private INoticeRangeDao dao;
    @Autowired
    private IOrgQueryPubService orgQueryPubService;
/*权限拆分----------------------------------------2016.12.28
 *  未被调用
=======
    @Override
    public Page<UserVO> queryUsersByAddCond(String noticeID,
            String userID, Condition cond, Pageable page) {
        if (StringUtils.isEmpty(noticeID))
            return null;
        // 登录用户有权限的机构的角色，不含待分配用户已经关联的角色
        String addCond = " (n.notice_id ='" + noticeID + "') ";
        return dao.queryUsersByAddCond(addCond, page);
    }
*/
    @Override
    public Page<OrgVO> queryOrgByConditions(Condition condition, Pageable page, String noticeID, String userID,
            Sorter sort) {
        
        if (StringUtils.isEmpty(noticeID))
            return null;
        String addCond = " (n.notice_id ='" + noticeID + "') ";
        /**-------------lyf 2016.12.28修改 原因：权限拆分 去掉rms_orgs有关的联表查询--------------**/
        Page<NoticeRangeVO> nrvos = dao.queryNoticeRangeByCondition(addCond,page,sort);
        List<NoticeRangeVO> content = nrvos.getContent();
        Set<String> set = new HashSet<>();
        List<String> list = new ArrayList<>();
        for (NoticeRangeVO nrvo : content) {
            set.add(nrvo.getRange_id());
        }
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            list.add(it.next());
        }
        List<OrgVO> queryOrgListByIDs = orgQueryPubService.queryOrgListByIDs(list);
        List<OrgVO> orgVOs = new ArrayList<OrgVO>();
        for (NoticeRangeVO nrvo : nrvos) {
            for (OrgVO orgvo : queryOrgListByIDs) {
                if(nrvo.getRange_id().equalsIgnoreCase(orgvo.getId())){
                    orgVOs.add(orgvo);
                }
            }
        }
        Page<OrgVO> pageOrgVO = GrantUtils.convertListToPage(orgVOs, page);
        return pageOrgVO;
        /**--此次修改结束---**/
    }

    @Override
    @Transactional
    public void insertNoticeRange(String noticeID, int type, String... rangeIDs) {
        NoticePowerVO powerVO;
        for (String rangeID : rangeIDs) {
            powerVO = new NoticePowerVO();
            powerVO.setRange_id(rangeID);
            powerVO.setNotice_id(noticeID);
            powerVO.setType(type);
            dao.insertNoticeRange(powerVO);
        }
    }

    @Override
    public Page<UserGroupVO> queryUserGroupByConditions(Condition condition,
            Pageable page, String noticeID, String userID, Sorter sort) {
        if (StringUtils.isEmpty(noticeID))
            return null;
        String addCond = " (n.notice_id ='" + noticeID + "') ";
        return dao.queryUserGroupByConditions(addCond, page, sort);
    }

    //String addCond = " u.id not in (select range_id from rms_notice_range where notice_id ='" + noticeID + "') ";
    //String addCond = " o.id not in (select range_id from rms_notice_range where notice_id ='" + noticeID + "') ";
    //String addCond = " s.id not in (select range_id from rms_notice_range where notice_id ='" + noticeID + "') ";
    @Transactional
    @Override
    public boolean deleteNoticeRange(String... ids) {
        dao.deleteNoticeRange(ids);
        return true;
    }
}
