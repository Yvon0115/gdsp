package com.gdsp.platform.grant.supplier.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.dev.core.utils.tree.model.ITreeEntity;

public class SupplierVO extends AuditableEntity implements ITreeEntity {
	private static final long serialVersionUID = 1L;
	private static String tableName = "rms_supplier";
	private String 				innercode; 					// 内部码
	private String 				pk_fathersup; 				// 上级组织
	private String 				supcode; 					// 供应商编码
	private String 				supname; 					// 供应商名
	private String 				isChecked; 					// 是否选中 Y选中N未选中
	private String 				pk_fathername; 				// 上级组织名称
	private String            	shortname;                  //供应商简称

	public String getInnercode() {
		return innercode;
	}

	public void setInnercode(String innercode) {
		this.innercode = innercode;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	public String getPk_fathersup() {
		return pk_fathersup;
	}

	public void setPk_fathersup(String pk_fathersup) {
		this.pk_fathersup = pk_fathersup;
	}

	public String getSupcode() {
		return supcode;
	}

	public void setSupcode(String supcode) {
		this.supcode = supcode;
	}

	public String getSupname() {
		return supname;
	}

	public void setSupname(String supname) {
		this.supname = supname;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getPk_fathername() {
		return pk_fathername;
	}

	public void setPk_fathername(String pk_fathername) {
		this.pk_fathername = pk_fathername;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

}
