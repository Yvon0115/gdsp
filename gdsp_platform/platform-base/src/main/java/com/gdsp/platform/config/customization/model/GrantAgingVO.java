package com.gdsp.platform.config.customization.model;

/**
 * 权限时效配置
 * @author wqh
 * 2016年12月8日 下午5:26:48
 */
public class GrantAgingVO {
	
	private String  status;               // 权限时效开启状态（Y：开启；N：关闭）
	private Integer leadTime;             // 权限时效提醒前置时间
	private Integer defaultAgingTime;     //权限时效默认值

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(Integer leadTime) {
		this.leadTime = leadTime;
	}
    public Integer getDefaultAgingTime() {
        return defaultAgingTime;
    }
    public void setDefaultAgingTime(Integer defaultAgingTime) {
        this.defaultAgingTime = defaultAgingTime;
    }
	
}
