/*
 * Copyright (c)  Business Intelligence Unified Portal. All rights reserved.
 * 
 */ 
package com.gdsp.platform.systools.datadic.service;

import java.util.List;

import com.gdsp.platform.systools.datadic.model.DataDicVO;
import com.gdsp.platform.systools.datadic.model.DataDicValueVO;

/**
 * 数据字典查询公共接口
 * @author wqh
 * @since 2017年10月24日 下午3:10:17 
 */
public interface IDataDicQueryPubService {
	
	/**
	 * 获取数据字典类型信息
	 * @param typeCode  基础档案编码（类型编码）
	 * @return 数据字典类型实体类
	 */
	public DataDicVO getDataDicTypeInfo(String typeCode);
	
	/**
	 * 获取指定类型下数据字典详细信息列表
	 * @param typeCode 基础档案编码（类型编码）
	 * @return 数据字典数据项集合
	 */
	public List<DataDicValueVO> getDataDicValueList(String typeCode);
	
	/**
	 * 根据编码获取数据字典数据项内容
	 * @param typeCode 基础档案类型编码（类型编码）
	 * @param dataCode 数据项编码
	 * @return 字典数据项
	 */
	public DataDicValueVO getDataDicValueByCode(String typeCode,String dataCode);
	
	
}
