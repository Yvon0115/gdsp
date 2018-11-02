package com.gdsp.platform.systools.notice.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.systools.notice.dao.ISysNoticeDao;
import com.gdsp.platform.systools.notice.model.SysNoticeVO;
import com.gdsp.platform.systools.notice.service.INoticeHistoryService;
import com.gdsp.platform.systools.notice.service.ISysNoticeService;

@Transactional(readOnly = false)
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {

    @Autowired
    private ISysNoticeDao dao;
    @Autowired
    private INoticeHistoryService noticeHistoryService;
    

    @Override
    public List<SysNoticeVO> querySysNoticeVosByCondition(Condition condition) {
        return dao.querySysNoticeVosByCondition(condition);
    }

    @Override
    public void insert(SysNoticeVO sysNoticeVo) {
        if (sysNoticeVo.getValid_flag() != null && sysNoticeVo.getValid_flag().toString().equals("Y")) {
            sysNoticeVo.setPublish_date(new DDate(new Date()));
        }
        dao.insert(sysNoticeVo);
    }

    @Override
    public Page<SysNoticeVO> queryNoticeVosPageByCondition(Condition condition,
            Pageable pageable) {
    	List<String> properties =new ArrayList<>();
    	properties.add(0, "publish_date");
    	properties.add(1, "lastmodifytime");
    	Sorter sort = new Sorter(Direction.DESC, properties);
        return dao.queryNoticeVosPageByCondition(condition, pageable, sort);
    }

    @Override
    public SysNoticeVO loadById(String id) {
        return dao.load(id);
    }

    @Override
    public void update(SysNoticeVO sysNoticeVo) {
        //当“是否有效”为Y，并且发布时间为空时，设置发布时间。
        if (sysNoticeVo.getValid_flag() != null && sysNoticeVo.getValid_flag().toString().equals("Y")
                && (sysNoticeVo.getPublish_date() == null || sysNoticeVo.getPublish_date().toString().equals(""))) {
            sysNoticeVo.setPublish_date(new DDateTime());
        }
        dao.update(sysNoticeVo);
        String notice_id = sysNoticeVo.getId();
        noticeHistoryService.delete(notice_id);
    }

    @Override
    public void delete(String id) {
        dao.delete(id);
    }

    @Override
    public boolean deleteSysNoticeBatch(String[] ids) {
        dao.deleteSysNotices(ids);
        return true;
    }
/*权限拆分--------------------------------------2016.12.28
 * 此方法没有被调用
    @Override
    public Page<SysNoticeVO> querySimpleNoticeVosPageByCondition(Condition condition, Pageable pageable, String UserId) {
        Sorter sort = new Sorter(Direction.DESC, "publish_date");
        return dao.querySimpleNoticeVosPage(condition, pageable, sort, UserId);
    }
*/
    @Override
    public Page<SysNoticeVO> querySimpleNoticeVoPageByCondition(Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.DESC, "publish_date");
        return dao.querySimpleNoticeVoPage(condition, pageable, sort);
    }

    @Override
    public int queryPublishInfCount(String user_id) {
        user_id = AppContext.getContext().getContextUserId();
        return dao.queryPublishInfCount(user_id);
    }

    @Override
    public List<SysNoticeVO> loadByIds(String... id) {
        
        return dao.loads(id);
    }

    @Override
    public Page<SysNoticeVO> querySimpleNoticeVoDlgPage(Condition condition,
            Pageable pageable, String user_id) {
        return dao.querySimpleNoticeVoDlgPage(condition, pageable, user_id);
    }
}
