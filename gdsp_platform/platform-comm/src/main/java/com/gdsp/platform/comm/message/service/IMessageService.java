package com.gdsp.platform.comm.message.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.comm.message.model.MessageVO;

/**
* @ClassName: IMessageService
* (消息服务)
* @author wwb
* @date 2015年10月29日 下午2:27:44
*
*/
public interface IMessageService {

    /**
    * @Title: saveMessage
    * (保存消息)
    * @param vo 消息对象
    * @return boolean 是否成功
    *     */
    public boolean saveMessage(MessageVO vo);

    /**
    * @Title: senderMessage
    * (发送消息)
    * @param vo 消息对象
    * @return boolean 是否成功
    *     */
    public boolean senderMessage(MessageVO vo);

    /**
    * @Title: senderMessage
    * (发送消息)
    * @param touserid 接受者ID
    * @param touserAccout 接受者名称
    * @param fromuserid 发送者ID
    * @param fromuserAccount 发送者名称
    * @param subject 主题
    * @param content 内容
    * @return boolean  是否成功
    *     */
    public boolean senderMessage(String touserid, String touserAccout, String fromuserid, String fromuserAccount, String subject, String content);

    /**
    * @Title: deleteMessage
    * (删除消息)
    * @param ids 消息IDs
    * @return boolean 是否成功
    *     */
    public boolean deleteMessage(String... ids);

    /**
    * @Title: signlook
    * (标记为已读)
    * @param ids 消息IDs
    * @return boolean  是否成功
    *     */
    public boolean signlook(String... ids);

    /**
    * @Title: lookInf
    * (查询消息详情)
    * @param id 消息主键
    * @return MessageVO   消息对象
    *     */
    public MessageVO lookInf(String id);

    /**
    * @Title: queryUnlookInfCount
    * (查询用户未读信息数量)
    * @param userId
    * @return int  数量
    *     */
    public int queryUnlookInfCount(String userId);

    /**
    * @Title: findMessageByCondition
    * (根据条件查询消息)
    * @param condition 查询条件
    * @param page 分页请求
    * @param sort 排序规则
    * @return Page<MessageVO>  消息分页数据
    *     */
    public Page<MessageVO> findMessageByCondition(Condition condition, Pageable page, Sorter sort);
}
