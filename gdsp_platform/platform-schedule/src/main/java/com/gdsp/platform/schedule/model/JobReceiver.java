package com.gdsp.platform.schedule.model;

import java.io.Serializable;

/*
 *
 */
public class JobReceiver implements Serializable {

    private static final long serialVersionUID = 1L;
    private String            receiverids;
    private int               sendtype;             // '0:站内消息,1:邮件,2:短信' null default '0',
    private String            isset;                // 是否设置

    public String getReceiverids() {
        return receiverids;
    }

    public void setReceiverids(String receiverids) {
        this.receiverids = receiverids;
    }

    public int getSendtype() {
        return sendtype;
    }

    public void setSendtype(int sendtype) {
        this.sendtype = sendtype;
    }

    public String getIsset() {
        return isset;
    }

    public void setIsset(String isset) {
        this.isset = isset;
    }
}