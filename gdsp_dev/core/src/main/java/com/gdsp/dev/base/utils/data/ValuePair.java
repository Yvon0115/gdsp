/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.base.utils.data;

import java.io.Serializable;



/**
 * 值对类，描述键值和值的数据类型
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class ValuePair implements Comparable<ValuePair>,Serializable{
	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -6889395925857698456L;

	/**
	 * 键值对象
	 */
	private String key;
	
	/**
	 * 值对象
	 */
	private String value;

	/**
	 * 构造方法
	 * @param key 键值
	 * @param value 值
	 */
	public ValuePair(String key, String value){
		this.key = key;
		this.value = value;
	}

	/**
	 * 取得键值
	 * @return 键值
	 */
	public String getKey(){
		return key;
	}
	
	/**
	 * 设置键值
	 * @param key 键值
	 */
	public void setKey(String key){
		this.key = key;
	}

	/**
	 * 取得值对中的值
	 * @return 值
	 */
	public String getValue(){
		return value;
	}

	

	/**
	 * 设置值对中的值
	 * @param value 值
	 */
	public void setValue(String value){
		this.value = value;
	}
	
	public String toString() {
		return key + ":" + value;
	}


	public int compareTo(ValuePair o) {
		if(o == this)
			return 0;
		return this.toString().compareTo(o.toString());
	}
	public boolean equals(Object obj)
	{
	    return super.equals(obj);
	}
	public int hashCode() {
	    return super.hashCode();
	}

}
