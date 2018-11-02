package com.gdsp.platform.comm.message.model;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.BaseEntity;

/**
* @ClassName: MessageVO
* (站内信VO)
* @author wwb
* @date 2015年10月28日 下午2:42:56
*
*/
public class MessageVO extends BaseEntity {

    private static final long serialVersionUID = -8675112756570432859L;
    private String            touserid;                                //   varchar(32) not null comment '接受者ID',,
    private String            fromuserid;                              //    varchar(32) not null comment '发送者ID',
    private String            touseraccout;                            //      varchar(32) not null comment '接受者账号',
    private String            fromuseraccount;                         //varchar(32) default comment '发送者账号',
    private String            subject;                                 //  varchar(128) comment '主题',
    private String            content;                                 //  longtext comment '内容',,
    private String            iflook;                                  //  varchar(10) default null COMMENT '是否查看',
    private DDateTime         transtime;                               // bigint comment '发送时间'，
    private Integer           msgtype;                                 //  tinyint comment '信息 1 mail 2 回复信息',
    private String            reply_by;                                //varchar(32)  comment '回复';
    private String            attachments;                             //varchar(128) comment '附件',

    public MessageVO() {
        super();
    }

    public MessageVO(String touserid, String touseraccout, String fromuserid, String fromuseraccount, String subject, String content) {
        super();
        this.touserid = touserid;
        this.touseraccout = touseraccout;
        this.fromuserid = fromuserid;
        this.fromuseraccount = fromuseraccount;
        this.subject = subject;
        this.content = content;
    }

    public String getTouserid() {
        return touserid;
    }

    public void setTouserid(String touserid) {
        this.touserid = touserid;
    }

    public String getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid;
    }

    public String getTouseraccout() {
        return touseraccout;
    }

    public void setTouseraccout(String touseraccout) {
        this.touseraccout = touseraccout;
    }

    public String getFromuseraccount() {
        return fromuseraccount;
    }

    public void setFromuseraccount(String fromuseraccount) {
        this.fromuseraccount = fromuseraccount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIflook() {
        return iflook;
    }

    public void setIflook(String iflook) {
        this.iflook = iflook;
    }

    public DDateTime getTranstime() {
        return transtime;
    }

    public void setTranstime(DDateTime transtime) {
        this.transtime = transtime;
    }

    public Integer getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(Integer msgtype) {
        this.msgtype = msgtype;
    }

    public String getReply_by() {
        return reply_by;
    }

    public void setReply_by(String reply_by) {
        this.reply_by = reply_by;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }
}