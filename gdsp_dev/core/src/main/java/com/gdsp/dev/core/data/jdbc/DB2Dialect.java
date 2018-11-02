package com.gdsp.dev.core.data.jdbc;

import org.apache.commons.lang3.StringUtils;
/**
 * DB2 SQL方言类
 * @author yangzh
 * @version 1.0 2018年1月17日
 * @since 1.6
 */
public class DB2Dialect extends DefaultDialect {
	@Override
	public String getPaginationSql(String sql, int offset, int limit){
		 return "SELECT * FROM (SELECT ROW_NUMBER () OVER () RN, T.* FROM (" 
				 + sql
	             + ") T ) T1 WHERE T1.RN > " 
				 + offset 
				 + " AND T1.RN <= " 
				 + (offset + limit);
	}

	@Override
	public String getDateColumnCondition(String columnName, String format){
		 return "to_char(" + columnName + ",'" + format + "')";
	}

	@Override
	public String substr(String field, String pos){
		 return "substr(" + field + "," + pos + ")";
	}

   
	@Override
	public String substr(String field, String pos, String len){
		 return "substr(" + field + "," + pos + "," + len + ")";
	}

	@Override
    public String concat(String... fields){
		return StringUtils.join(fields, "||");
	}
}
