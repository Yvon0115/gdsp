package com.gdsp.platform.systools.notice.service;

import com.gdsp.platform.systools.notice.model.NoticeHistoryVO;

public interface INoticeHistoryService {

    public void insert(NoticeHistoryVO notHistoryVO);

    public int queryNoticeHistory(String notice_id, String user_id);
    
    public void delete(String notice_id);
}
