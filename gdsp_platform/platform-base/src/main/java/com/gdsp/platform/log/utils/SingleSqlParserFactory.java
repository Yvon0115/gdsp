package com.gdsp.platform.log.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 单句sql解析工厂
 */
public class SingleSqlParserFactory {
	public static BaseSingleSqlParser generateParser(String sql) {
			return new UpdateSqlParser(sql);
	}

	/**
	 * 正则表达式解析sql语句
	 * 
	 * @param sql
	 * @param regExp
	 * @return
	 */
	private static boolean contains(String sql, String regExp) {
		Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sql);
		return matcher.find();
	}
}
