package com.gdsp.dev.base.utils.exp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 表达式编译器
 * @author paul.yang
 * @version 1.0 2009-9-20
 * @since 1.7
 */
public class ExpCompiler {

    /**
     * 日志对象
     */
    private static final Logger logger        = Logger.getLogger(ExpCompiler.class);
    /**转义关键字*/
    private static final char   ESCAPE_CHAR   = '\\';
    /**表达式识别关键字前缀*/
    private static final char   SPECIAL_CHAR  = '$';
    /**表达式识别关键字左括号表示表达式起点*/
    private static final char   BRACKET_BEGIN = '{';
    /**表达式识别关键字左括号表示表达式终点*/
    private static final char   BRACKET_END   = '}';
    /**表达式原始串编译结果片断*/
    private List<Object>        sections      = null;

    /**
     * 构造方法
     */
    public ExpCompiler() {
        sections = new ArrayList<Object>();
    }

    /**
     * 编译文本串，生成编译对象
     * @param text 需编译的文本
     * @return 编译对象
     */
    public static ExpCompiler compile(String text) {
        //空串判断
        if (StringUtils.isBlank(text)) {
            return null;
        }
        ExpCompiler compiler = new ExpCompiler();
        //逃逸标志
        boolean escaped = false;
        //表达式关键字开始标志
        boolean specialCharFound = false;
        //当前EL表达式是否正在收集
        boolean inBracket = false;
        //此次编译是否存在表达式
        boolean hasExpression = false;
        try {
            StringBuilder section = new StringBuilder();
            char cs[] = text.toCharArray();
            for (int i = 0; i < cs.length; i++) {
                char c = cs[i];
                //对$关键字后字符进行的处理
                if (specialCharFound) {
                    specialCharFound = false;
                    if (c == BRACKET_BEGIN) {
                        inBracket = true;
                        if (section.length() > 0) {
                            compiler.addSection(section.toString());
                            section = new StringBuilder();
                        }
                        continue;
                    }
                    section.append(SPECIAL_CHAR);
                }
                //逃逸状态下的处理
                if (escaped) {
                    escaped = false;
                    if (c != SPECIAL_CHAR && c != BRACKET_BEGIN && c != BRACKET_END) {
                        section.append(ESCAPE_CHAR);
                    } else {
                        //此处为了使原来的\${expression}最终转换为${expression}形式
                        hasExpression = true;
                    }
                } else {
                    //是否需要逃逸
                    if (c == ESCAPE_CHAR) {
                        escaped = true;
                        continue;
                    }
                    //是否可能存在表达式
                    if (c == SPECIAL_CHAR) {
                        specialCharFound = true;
                        continue;
                    }
                    //表达式结束处理
                    if (c == BRACKET_END && inBracket) {
                        inBracket = false;
                        hasExpression = true;
                        Expression exp = ExpUtils.getEvaluator().createExpression(section.toString());
                        compiler.addSection(exp);
                        section = new StringBuilder();
                        continue;
                    }
                }
                section.append(c);
            }
            //存在表达式，最后的buffer不为空将最后的串补齐
            if (hasExpression && section.length() > 0) {
                compiler.addSection(section.toString());
            }
        } catch (Exception e) {
            logger.error("expression error", e);
        }
        //存在表达式则返回编译器对象，否则返回空
        if (hasExpression) {
            return compiler;
        } else {
            return null;
        }
    }

    /**
     * 添加非表达式字符串
     * @param s 非表达式字符串
     */
    public void addSection(String s) {
        sections.add(s);
    }

    /**
     * 添加表达式对象
     * @param exp 表达式对象
     */
    public void addSection(Expression exp) {
        sections.add(exp);
    }

    /**
     * 根据应用上下文进行执行表达式
     * @param context 应用上下文
     * @return 表达式结果
     */
    public String translate(Map<String, Object> context) {
        //单表达式的执行，返回执行结果
        if (sections.size() == 1 && sections.get(0) instanceof JexlExpression) {
            try {
                return ((Expression) sections.get(0)).evaluate(context).toString();
            } catch (Exception e) {
                logger.debug(e.getMessage(),e);
                return null;
            }
        }
        //表达式混串时的执行，返回结果为字符串
        StringBuilder sb = new StringBuilder();
        for (Object section : sections) {
            //根据片断类型进行处理
            if (!(section instanceof JexlExpression)) {
                sb.append(section);
                continue;
            }
            try {
                //执行表达式并获取结果
                Object value = ((JexlExpression) section).evaluate(context);
                if (value != null) {
                    sb.append(value);
                }
            } catch (Exception ex) {
                logger.debug(ex.getMessage(),ex);
            }
        }
        return sb.toString();
    }

    /**
     * 取得表达式原始串编译结果片断集
     * @return 片断集
     */
    public List<Object> getSections() {
        return sections;
    }
}
