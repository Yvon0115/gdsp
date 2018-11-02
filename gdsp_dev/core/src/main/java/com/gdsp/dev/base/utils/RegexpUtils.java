package com.gdsp.dev.base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.base.utils.web.URLUtils;

/**
 * 正则表达式工具类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class RegexpUtils {

    /**
     * 正常数据正则表达式
     */
    public static final Pattern normalPattern  = Pattern.compile("[_a-zA-Z0-9-]*");
    /**
     * 变量名正则表达式
     */
    public static final Pattern varnamePattern = Pattern.compile("^[_a-zA-Z][_a-zA-Z0-9]*");

    /**
     * 将字符串格式化成可用于替换的字符串
     * 主要是一些特殊字符的处理
     * @param s 原始串
     * @return
     */
    public static String formatReplace(String s) {
        if (StringUtils.isEmpty(s))
            return s;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '$':
                    sb.append("\\$");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '(':
                    sb.append("\\(");
                    break;
                case ')':
                    sb.append("\\)");
                    break;
                case '[':
                    sb.append("\\[");
                    break;
                case ']':
                    sb.append("\\]");
                    break;
                case '{':
                    sb.append("\\{");
                    break;
                case '}':
                    sb.append("\\}");
                    break;
                case '*':
                    sb.append("\\*");
                    break;
                case '+':
                    sb.append("\\+");
                    break;
                case '.':
                    sb.append("\\.");
                    break;
                case '?':
                    sb.append("\\?");
                    break;
                case '|':
                    sb.append("\\|");
                    break;
                case '^':
                    sb.append("\\^");
                    break;
                default:
                    sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否为
     * @param str
     * @return
     */
    public static boolean isNormalData(String str) {
        return normalPattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否为变量名
     * @param str 测试串
     * @return 布尔值
     */
    public static boolean isVarName(String str) {
        return varnamePattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否为指定类型的文件名
     * @param str 测试串
     * @param type 文件类型
     * @return 布尔值
     */
    public static boolean isFilePath(String str, String type) {
        if (type == null) {
            type = "(\\.[_a-zA-Z0-9-]{1,10})?";
        }
        String partten = "/?([_a-zA-Z0-9-]{1,32}/{1}){0,10}([_a-zA-Z0-9-]){1,32}" + type;
        return Pattern.matches(partten, str);
    }

    public static String nestReplaceAll(Pattern pattern1, Pattern parttern2, String source, String replace) {
        Matcher m = pattern1.matcher(source);
        boolean result = m.find();
        if (result) {
            StringBuffer sb = new StringBuffer();
            do {
                String r = parttern2.matcher(m.group()).replaceAll(replace);
                m.appendReplacement(sb, r);
                result = m.find();
            } while (result);
            m.appendTail(sb);
            return sb.toString();
        }
        return source;
    }

    public static void main(String[] agv) {
        System.out.println(URLUtils.validURL("/gu?ohai/adf?/sdf.dc"));
        System.out.println(RegexpUtils.isNormalData("<script>adsfasd</script>"));
        System.out.println(RegexpUtils.isNormalData("-sdfasf"));
        System.out.println(RegexpUtils.isVarName("<script>adsfasd</script>"));
        System.out.println(RegexpUtils.isVarName("_sdfasf"));
        System.out.println(RegexpUtils.isFilePath("_sdfasf.aa", ".ftl"));
        System.out.println(RegexpUtils.isFilePath("_sdf/asf", ".ftl"));
        System.out.println(RegexpUtils.isFilePath("/_sdf/asf", ".ftl"));
        System.out.println(RegexpUtils.isFilePath("/_sdf/asf/asdfasd.ftl", ".ftl"));
    }
}
