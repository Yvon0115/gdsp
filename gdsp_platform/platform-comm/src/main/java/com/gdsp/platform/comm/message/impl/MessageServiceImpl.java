package com.gdsp.platform.comm.message.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.comm.message.dao.IMessageDao;
import com.gdsp.platform.comm.message.model.MessageVO;
import com.gdsp.platform.comm.message.service.IMessageService;
import com.gdsp.platform.comm.message.utils.MessageConst;

@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private IMessageDao messageDao;

    @Transactional
    @Override
    public boolean saveMessage(MessageVO vo) {
        messageDao.insertMessage(vo);
        return true;
    }

    @Transactional
    @Override
    public boolean senderMessage(MessageVO vo) {
        vo.setIflook("N");
        vo.setMsgtype(MessageConst.MESSAGE_TYPE_SEND);
        vo.setTranstime(new DDateTime());
        messageDao.insertMessage(vo);
        return true;
    }

    @Transactional
    @Override
    public boolean senderMessage(String touserid, String touserAccout, String fromuserid, String fromuserAccount, String subject, String content) {
        MessageVO vo = new MessageVO(touserid, touserAccout, fromuserid, fromuserAccount, subject, content);
        return senderMessage(vo);
    }

    @Transactional
    @Override
    public boolean deleteMessage(String... ids) {
        messageDao.deleteMessage(ids);
        return true;
    }

    @Transactional
    @Override
    public boolean signlook(String... ids) {
        for (String id : ids) {
            messageDao.signlook(id);
        }
        return true;
    }

    @Transactional
    @Override
    public MessageVO lookInf(String id) {
        messageDao.signlook(id);
        return messageDao.load(id);
    }

    @Override
    public int queryUnlookInfCount(String userId) {
        return messageDao.queryUnlookInfCount(userId);
    }

    @Override
    public Page<MessageVO> findMessageByCondition(Condition condition, Pageable page, Sorter sort) {
        return messageDao.findMessageByCondition(condition, sort, page);
    }
}
