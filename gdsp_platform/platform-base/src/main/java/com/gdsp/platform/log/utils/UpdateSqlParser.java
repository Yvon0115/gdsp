package com.gdsp.platform.log.utils;

/**
 *
 * 单句更新语句的解析器
 * 
 * @version 1.00
 */
public class UpdateSqlParser extends BaseSingleSqlParser {
	public UpdateSqlParser(String originalSql) {
		super(originalSql);
	}

	@Override
	protected void initializeSegments() {
		segments.add(new SqlSegment("(update)(.+)(set)", "[,]"));
		segments.add(new SqlSegment("(set)(.+)( where | ENDOFSQL)", "[,]"));
		segments.add(new SqlSegment("(where)(.+)( ENDOFSQL)", "(and|or)"));
	}
}
