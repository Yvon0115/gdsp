package com.gdsp.platform.systools.notice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.systools.notice.dao.INoticeHistoryDao;
import com.gdsp.platform.systools.notice.model.NoticeHistoryVO;
import com.gdsp.platform.systools.notice.service.INoticeHistoryService;

@Service
@Transactional(readOnly = true)
public class NoticeHistoryServiceImpl implements INoticeHistoryService {

    @Autowired
    private INoticeHistoryDao dao;

    @Override
    @Transactional
    public void insert(NoticeHistoryVO notHistoryVO) {
        dao.insert(notHistoryVO);
    }

    @Override
    public int queryNoticeHistory(String notice_id, String user_id) {
        int count = dao.queryNoticeHistory(notice_id, user_id);
        return count;
    }

	@Override
	public void delete(String notice_id) {
		dao.delete(notice_id);
	}
    
}
