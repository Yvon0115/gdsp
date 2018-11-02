package com.gdsp.platform.func.helper;

import java.util.Arrays;
import java.util.List;

import com.gdsp.dev.core.model.enums.EnumItem;
import com.gdsp.dev.core.model.enums.EnumType;

/**
 * 菜单常量
 * @author blank
 * @version 1.0 2018年7月10日 下午5:30:54
 * @since 1.7
 */
public enum MenuConst implements EnumItem,EnumType{
	
	/** 目录(仅用放置菜单)  */          MENUTYPE_DIR("0","目录"),
	/** 管理菜单  (一般是系统菜单) */    MENUTYPE_ADMIN("2","管理菜单"),
	/** 业务菜单  */                    MENUTYPE_BUSI("3","业务菜单"), 
	/** 页面 */                        MENUTYPE_PAGE("4","页面菜单"),
	
	/** 原意不明 TODO 考虑删除 */       MENUTYPE_MULTILEVEL("1","下级菜单"),
	/** 原意不明  TODO 考虑删除  */      HOME_FUNCODE("0001","含义不明的编码");
	
	
	private String typeCode;
	private String typeName;
	
	
	MenuConst(String typeCode,String typeName){
		this.typeCode = typeCode;
		this.typeName = typeName;
	}
	
	
	@Override
	public List<EnumItem> getEnumItems() {
		EnumItem[] values = values();
		return Arrays.asList(values);
	}

	/** 常量标签（说明） */
	@Override
	public String getLabel() {
		return typeName;
	}

	/** 值 */
	@Override
	public String getValue() {
		return typeCode;
	}

	/** int值 */
	public int intValue(){
		return Integer.parseInt(typeCode);
	}
}
