package com.gdsp.platform.systools.notice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.systools.notice.model.SysNoticeVO;

public interface ISysNoticeService {

    Page<SysNoticeVO> queryNoticeVosPageByCondition(Condition condition, Pageable pageable);
/*
 * 权限拆分--------------------------------------2016.12.28
 * 此接口没有被调用
 * Page<SysNoticeVO> querySimpleNoticeVosPageByCondition(Condition condition, Pageable pageable, String UserId);
*/
    Page<SysNoticeVO> querySimpleNoticeVoPageByCondition(Condition condition, Pageable pageable);

    public List<SysNoticeVO> querySysNoticeVosByCondition(Condition condition);

    public void insert(SysNoticeVO sysNoticeVo);

    SysNoticeVO loadById(String id);

    void update(SysNoticeVO sysNoticeVo);

    void delete(String id);

    boolean deleteSysNoticeBatch(String[] id);

    /**
    * @Title: queryPublishInfCount
    * (有效公告数量)
    * @return    参数说明
    * @return int    数量
    *      */
    public int queryPublishInfCount(String user_id);

    List<SysNoticeVO> loadByIds(String... id);

    public Page<SysNoticeVO> querySimpleNoticeVoDlgPage(Condition condition, Pageable pageable, String user_id);
}
