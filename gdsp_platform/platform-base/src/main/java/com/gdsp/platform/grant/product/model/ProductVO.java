package com.gdsp.platform.grant.product.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class ProductVO extends AuditableEntity {
	private static final long serialVersionUID = 1L;
	private String        	name; 					// 商品名称
	private String        	number; 				// 年龄
	private String 			price; 					// 价格
	private String 			pk_sup; 				// 供应商编号

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPk_sup() {
		return pk_sup;
	}

	public void setPk_sup(String pk_sup) {
		this.pk_sup = pk_sup;
	}

}
