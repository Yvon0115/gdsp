package com.gdsp.platform.log.model;

/**
 * 访问记录VO类
 * @author songxiang
 * @date 2015年10月28日 上午11:36:31
 */
public class ResAccessTopVO extends ResAccessLogVO {

    private static final long serialVersionUID = 4275308557881587272L;
    /**
     * 频度统计
     */
    private String            cnt;
    /**
     * 报表上线时间
     */
    private String            onlineTime;

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

}
