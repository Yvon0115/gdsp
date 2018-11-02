package com.gdsp.platform.log.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * sql语句片段
 */
public class SqlSegment {
	private static final String CRLF = "@";
	private static final String FOURSPACE = "    ";
	/**
	 * sql语句片段开头片段
	 */
	private String start;
	/**
	 * sql语句中间部分
	 */
	private String body;
	/**
	 * sql语句结束片段
	 */
	private String end;
	/**
	 * 分割中间部分的正则表达式
	 */
	private String bodySplitPattern;
	/**
	 * 表示片段的正则表达式
	 */
	private String segmentRegExp;
	/**
	 * 分割后的body小片段
	 */
	private List<String> bodyPieces;

	/**
	 * 构造函数
	 * 
	 * @param segmentRegExp
	 *            表示这个sql片段的正则表达式
	 * @param bodySplitPattern
	 *            用于分割body的正则表达式
	 */
	public SqlSegment(String segmentRegExp, String bodySplitPattern) {
		start = "";
		body = "";
		end = "";
		this.segmentRegExp = segmentRegExp;
		this.bodySplitPattern = bodySplitPattern;
		this.bodyPieces = new ArrayList<>();

	}

	/**
	 * 从sql中查找符合segmentRegExp的部分，并赋值到start，body，end三个属性中
	 * 
	 * @param sql
	 */
	public void parse(String sql) {
		Pattern pattern = Pattern.compile(segmentRegExp,
				Pattern.CASE_INSENSITIVE);
		for (int i = 0; i <= sql.length(); i++) {
			String shortSql = sql.substring(0, i);
			Matcher matcher = pattern.matcher(shortSql);
			while (matcher.find()) {
				start = matcher.group(1);
				body = matcher.group(2);
				end = matcher.group(3);
				parseBody();
				return;
			}
		}
	}

	/**
	 * 解析body部分
	 */
	private void parseBody() {

		List<String> ls = new ArrayList<>();
		Pattern p = Pattern.compile(bodySplitPattern, Pattern.CASE_INSENSITIVE);
		// 清除前后空格
		body = body.trim();
		Matcher m = p.matcher(body);
		//由于Matcher类appendReplacement()方法需要StringBuffer类的参数，因此未替换成StringBuilder
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		while (result) {
			m.appendReplacement(sb, m.group(0) + CRLF);
			result = m.find();
		}
		m.appendTail(sb);
		// 按空格断行
		String[] arr = sb.toString().split(" ");
		int arrLength = arr.length;
		for (int i = 0; i < arrLength; i++) {
			String temp = FOURSPACE + arr[i];
			if (i != arrLength - 1) {
			}
			ls.add(temp);
		}
		bodyPieces = ls;
	}

	/**
	 * 取得解析好的sql片段
	 * 
	 * @return
	 */
	public String getParsedSqlSegment() {
		StringBuilder sb = new StringBuilder();
		sb.append(start + CRLF);
		for (String piece : bodyPieces) {
			sb.append(piece + CRLF);
		}
		return sb.toString();
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

}
