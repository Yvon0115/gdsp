package com.gdsp.platform.config.customization.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
/**
 * 
* 
* @Description:系统配置扩展vo
* @author
* @date 2016年12月2日
 */
public class SystemConfExtVO extends AuditableEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 4591647392733772741L;
    private String            confName;                             // 系统配置名称
    private String            confCode;                             // 系统配置编码
    private String            confValue;                            // 报表配置值
    private Integer           valueType;                            // 配置值数据类型   0:Integer,1:Double,2:逻辑型,3:日期型,4:字符
    private String            categoryCode;                         // 分类编码
    private String            categoryName;                         // 分类名称
    
	public String getConfName() {
		return confName;
	}
	public void setConfName(String confName) {
		this.confName = confName;
	}
	public String getConfCode() {
		return confCode;
	}
	public void setConfCode(String confCode) {
		this.confCode = confCode;
	}
	public String getConfValue() {
		return confValue;
	}
	public void setConfValue(String confValue) {
		this.confValue = confValue;
	}
	public Integer getValueType() {
		return valueType;
	}
	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
    
    
}
