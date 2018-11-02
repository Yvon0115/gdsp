package com.gdsp.platform.log.utils;

import java.util.List;

/**
 * 单句sql解析器制造工厂
 * 
 */
public class SqlParserUtil {
	/**
	 * 解析sql
	 * 
	 * @param sql
	 * @return
	 */
	public static String getParsedSql(String sql) {
		sql = sql.trim();
		sql = sql.toLowerCase();
		sql = sql.replaceAll("\\s{1,}", " ");
		sql = "" + sql + " ENDOFSQL";
		return SingleSqlParserFactory.generateParser(sql).getParsedSql();
	}

	/**
	 * 解析sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public List<SqlSegment> getParsedSqlList(String sql) {
		sql = sql.trim();
		sql = sql.toLowerCase();
		sql = sql.replaceAll("\\s{1,}", " ");
		sql = "" + sql + " ENDOFSQL";
		return SingleSqlParserFactory.generateParser(sql).retrunSqlSegments();
	}
}
