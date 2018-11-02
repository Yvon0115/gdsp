package com.gdsp.platform.log.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 
 * 单句sql解析器，没有嵌套
 *
 * 
 */
public abstract class BaseSingleSqlParser {
	/**
	 * 原始sql语句
	 */
	protected String originalSql;
	/** 
	* sql语句片段
	 */
	protected List<SqlSegment> segments;

	/** 
	 *传入原始sql，进行劈分
	 */
	public BaseSingleSqlParser(String originalSql) {
		this.originalSql = originalSql;
		segments = new ArrayList<>();
		initializeSegments();
		splitSql2Segment();
	}

	/**
	*初始化Segments，强制子类实现
	 */
	protected abstract void initializeSegments();

	/** 
	 * 将原始sql语句劈成小片段
	 */
	protected void splitSql2Segment() {
		for (SqlSegment sqlSegment : segments) {
			sqlSegment.parse(originalSql);
		}
	}

	/** 
	* 得到解析完毕的sql语句
	 */
	public String getParsedSql() {

		StringBuilder sb = new StringBuilder();
		for (SqlSegment sqlSegment : segments) {
			sb.append(sqlSegment.getParsedSqlSegment());
		}
		return sb.toString().replaceAll("@+", "\n");
	}

	/** 
	 * 得到解析的sql片段
	 * 
	 * @return
	 */
	public List<SqlSegment> retrunSqlSegments() {
		int segmentLength = this.segments.size();
		if (segmentLength != 0) {
			return this.segments;
		} else {
			return Collections.emptyList();
		}
	}
}
