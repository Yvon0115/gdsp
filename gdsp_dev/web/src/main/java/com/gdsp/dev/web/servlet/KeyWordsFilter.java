package com.gdsp.dev.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gdsp.dev.base.utils.RegexpUtils;
import com.gdsp.dev.base.utils.web.URLUtils;
import com.gdsp.dev.core.common.AppConfig;

/**
 * 关键字过滤器
 */
public class KeyWordsFilter extends OncePerRequestFilter {

    private String              EMPTY               = "";
    private Map<String, String> htmlTags            = null;
    private Map<String, String> htmlSingleTags      = null;
    private Map<String, String> htmlInlineTags      = null;
    private Map<String, String> htmlBlockTags       = null;
    private Map<String, String> htmlAttrs           = null;
    private Pattern             htmlPattern         = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
    private Pattern             metaPattern         = Pattern.compile("<meta[\\s\\S]*?>", Pattern.CASE_INSENSITIVE);
    private Pattern             sPattern            = Pattern.compile("<![\\s\\S]*?>", Pattern.CASE_INSENSITIVE);
    private Pattern             stylePattern        = Pattern.compile("<style[^>]*>[\\s\\S]*?<\\/style>", Pattern.CASE_INSENSITIVE);
    private Pattern             scriptPattern       = Pattern.compile("<script[^>]*>[\\s\\S]*?<\\/script>", Pattern.CASE_INSENSITIVE);
    private Pattern             wPattern            = Pattern.compile("<w:[^>]+>[\\s\\S]*?<\\/w:[^>]+>", Pattern.CASE_INSENSITIVE);
    private Pattern             oPattern            = Pattern.compile("<o:[^>]+>[\\s\\S]*?<\\/o:[^>]+>", Pattern.CASE_INSENSITIVE);
    private Pattern             xmlPattern          = Pattern.compile("<xml>[\\s\\S]*?<\\/xml>", Pattern.CASE_INSENSITIVE);
    private Pattern             tablePattern        = Pattern.compile("<(?:table|td)[^>]*>", Pattern.CASE_INSENSITIVE);
    private Pattern             bottomBorderPattern = Pattern.compile("border-bottom:([#\\w\\s]+)", Pattern.CASE_INSENSITIVE);
    private Pattern             prePattern          = Pattern.compile("(<(?:pre|pre\\s[^>]*)>)([\\s\\S]*?)(<\\/pre>)", Pattern.CASE_INSENSITIVE);
    private Pattern             brPattern           = Pattern.compile("<(?:br|br\\s[^>]*)>", Pattern.CASE_INSENSITIVE);
    private Pattern             brpPattern          = Pattern.compile("<(?:br|br\\s[^>]*)\\s*\\/?>\\s*<\\/p>", Pattern.CASE_INSENSITIVE);
    private Pattern             pPattern            = Pattern.compile("(<(?:p|p\\s[^>]*)>)\\s*(<\\/p>)", Pattern.CASE_INSENSITIVE);
    private Pattern             u00a9Pattern        = Pattern.compile("\\u00A9");
    private Pattern             u200bPattern        = Pattern.compile("\\u200B");
    private Pattern             rePattern           = Pattern.compile(
            "([ \\t\\n\\r]*)<(\\/)?([\\w\\-:]+)((?:\\s+|(?:\\s+[\\w\\-:]+)|(?:\\s+[\\w\\-:]+=[^\\s\"'<>]+)|(?:\\s+[\\w\\-:\"]+=\"[^\"]*\")|(?:\\s+[\\w\\-:\"]+='[^']*'))*)(/)?>([ \\t\\n\\r]*)");
    private Pattern             attrPattern         = Pattern
            .compile("\\s+(?:([\\w\\-:]+)|(?:([\\w\\-:]+)=([^\\s\"'<>]+))|(?:([\\w\\-:\"]+)=\"([^\"]*)\")|(?:([\\w\\-:\"]+)='([^']*)'))(?=(?:\\s|\\/|>)+)");
    private Pattern             styleAttrPattern    = Pattern.compile("expression", Pattern.CASE_INSENSITIVE);
    private Pattern             spacePattern        = Pattern.compile("\\s");
    private Pattern             nnPattern           = Pattern.compile("\\n\\s*\\n");
    private List<Pattern>       referPatterns       = null;
    private boolean             referFilter         = false;

    public KeyWordsFilter() {
        String referers = AppConfig.getInstance().getString("security.filter.referers");
        if (StringUtils.isNotEmpty(referers)) {
            referPatterns = new ArrayList<>();
            String[] rs = referers.split(",");
            for (String r : rs) {
                referPatterns.add(Pattern.compile(r, Pattern.CASE_INSENSITIVE));
            }
        }
        htmlTags = new HashMap<>();
        htmlTags.put("font", EMPTY);
        htmlTags.put("span", EMPTY);
        htmlTags.put("div", EMPTY);
        htmlTags.put("label", EMPTY);
        htmlTags.put("table", EMPTY);
        htmlTags.put("td", EMPTY);
        htmlTags.put("th", EMPTY);
        htmlTags.put("a", EMPTY);
        htmlTags.put("embed", EMPTY);
        htmlTags.put("img", EMPTY);
        htmlTags.put("p", EMPTY);
        htmlTags.put("ol", EMPTY);
        htmlTags.put("ul", EMPTY);
        htmlTags.put("li", EMPTY);
        htmlTags.put("blockquote", EMPTY);
        htmlTags.put("h1", EMPTY);
        htmlTags.put("h2", EMPTY);
        htmlTags.put("h3", EMPTY);
        htmlTags.put("h4", EMPTY);
        htmlTags.put("h5", EMPTY);
        htmlTags.put("h6", EMPTY);
        htmlTags.put("pre", EMPTY);
        htmlTags.put("hr", EMPTY);
        htmlTags.put("br", EMPTY);
        htmlTags.put("tbody", EMPTY);
        htmlTags.put("tfoot", EMPTY);
        htmlTags.put("thead", EMPTY);
        htmlTags.put("tr", EMPTY);
        htmlTags.put("strong", EMPTY);
        htmlTags.put("b", EMPTY);
        htmlTags.put("sub", EMPTY);
        htmlTags.put("sup", EMPTY);
        htmlTags.put("em", EMPTY);
        htmlTags.put("i", EMPTY);
        htmlTags.put("u", EMPTY);
        htmlTags.put("strike", EMPTY);
        htmlTags.put("s", EMPTY);
        htmlTags.put("del", EMPTY);
        htmlTags.put("dl", EMPTY);
        htmlTags.put("dt", EMPTY);
        htmlTags.put("dd", EMPTY);
        htmlAttrs = new HashMap<>();
        htmlAttrs.put("id", EMPTY);
        htmlAttrs.put("class", EMPTY);
        htmlAttrs.put("color", EMPTY);
        htmlAttrs.put("size", EMPTY);
        htmlAttrs.put("face", EMPTY);
        htmlAttrs.put(".background-color", EMPTY);
        htmlAttrs.put(".font-size", EMPTY);
        htmlAttrs.put(".font-family", EMPTY);
        htmlAttrs.put(".background", EMPTY);
        htmlAttrs.put(".font-weight", EMPTY);
        htmlAttrs.put(".font-style", EMPTY);
        htmlAttrs.put(".text-decoration", EMPTY);
        htmlAttrs.put(".vertical-align", EMPTY);
        htmlAttrs.put(".line-height", EMPTY);
        htmlAttrs.put("align", EMPTY);
        htmlAttrs.put(".border", EMPTY);
        htmlAttrs.put(".margin", EMPTY);
        htmlAttrs.put(".padding", EMPTY);
        htmlAttrs.put(".text-align", EMPTY);
        htmlAttrs.put(".margin-left", EMPTY);
        htmlAttrs.put("border", EMPTY);
        htmlAttrs.put("cellspacing", EMPTY);
        htmlAttrs.put("cellpadding", EMPTY);
        htmlAttrs.put("width", EMPTY);
        htmlAttrs.put("height", EMPTY);
        htmlAttrs.put("bordercolor", EMPTY);
        htmlAttrs.put("bgcolor", EMPTY);
        htmlAttrs.put(".width", EMPTY);
        htmlAttrs.put(".height", EMPTY);
        htmlAttrs.put(".border-collapse", EMPTY);
        htmlAttrs.put("valign", EMPTY);
        htmlAttrs.put("colspan", EMPTY);
        htmlAttrs.put("rowspan", EMPTY);
        htmlAttrs.put("href", EMPTY);
        htmlAttrs.put("target", EMPTY);
        htmlAttrs.put("name", EMPTY);
        htmlAttrs.put("src", EMPTY);
        htmlAttrs.put("type", EMPTY);
        htmlAttrs.put("loop", EMPTY);
        htmlAttrs.put("autostart", EMPTY);
        htmlAttrs.put("quality", EMPTY);
        htmlAttrs.put("allowscriptaccess", EMPTY);
        htmlAttrs.put("alt", EMPTY);
        htmlAttrs.put("title", EMPTY);
        htmlAttrs.put(".margin-left", EMPTY);
        htmlAttrs.put(".text-indent", EMPTY);
        htmlAttrs.put(".page-break-after", EMPTY);
        htmlSingleTags = new HashMap<>();
        htmlSingleTags.put("br", EMPTY);
        htmlSingleTags.put("hr", EMPTY);
        htmlSingleTags.put("img", EMPTY);
        htmlSingleTags.put("input", EMPTY);
        htmlSingleTags.put("embed", EMPTY);
        htmlInlineTags = new HashMap<>();
        htmlInlineTags.put("a", EMPTY);
        htmlInlineTags.put("b", EMPTY);
        htmlInlineTags.put("del", EMPTY);
        htmlInlineTags.put("em", EMPTY);
        htmlInlineTags.put("font", EMPTY);
        htmlInlineTags.put("i", EMPTY);
        htmlInlineTags.put("img", EMPTY);
        htmlInlineTags.put("s", EMPTY);
        htmlInlineTags.put("strike", EMPTY);
        htmlInlineTags.put("strong", EMPTY);
        htmlInlineTags.put("sub", EMPTY);
        htmlInlineTags.put("sup", EMPTY);
        htmlInlineTags.put("u", EMPTY);
        htmlBlockTags = new HashMap<>();
        htmlBlockTags.put("blockquote", EMPTY);
        htmlBlockTags.put("div", EMPTY);
        htmlBlockTags.put("span", EMPTY);
        htmlBlockTags.put("label", EMPTY);
        htmlBlockTags.put("dl", EMPTY);
        htmlBlockTags.put("dd", EMPTY);
        htmlBlockTags.put("dt", EMPTY);
        htmlBlockTags.put("h1", EMPTY);
        htmlBlockTags.put("h2", EMPTY);
        htmlBlockTags.put("h3", EMPTY);
        htmlBlockTags.put("h4", EMPTY);
        htmlBlockTags.put("h5", EMPTY);
        htmlBlockTags.put("h6", EMPTY);
        htmlBlockTags.put("hr", EMPTY);
        htmlBlockTags.put("li", EMPTY);
        htmlBlockTags.put("ol", EMPTY);
        htmlBlockTags.put("p", EMPTY);
        htmlBlockTags.put("pre", EMPTY);
        htmlBlockTags.put("table", EMPTY);
        htmlBlockTags.put("tbody", EMPTY);
        htmlBlockTags.put("tr", EMPTY);
        htmlBlockTags.put("td", EMPTY);
        htmlBlockTags.put("th", EMPTY);
        htmlBlockTags.put("tfoot", EMPTY);
        htmlBlockTags.put("thead", EMPTY);
        htmlBlockTags.put("ul", EMPTY);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        request = new Request((HttpServletRequest) request);
        response.setHeader("Set-Cookie", "name=value; HttpOnly");
        if (!referFilter) {
            chain.doFilter(request, response);
            return;
        }
        String referer = request.getHeader("Referer");
        if (referer != null) {
            boolean find = false;
            for (Pattern p : referPatterns) {
                if (p.matcher(referer).find()) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                response.sendRedirect(request.getContextPath() + "/404.d");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public String filterDangerString(String value) {
        if (value == null) {
            return null;
        }
        if (!htmlPattern.matcher(value).find())
            return value;
        return clearMsWord(value);
    }

    public String clearMsWord(String data) {
        data = metaPattern.matcher(data).replaceAll(EMPTY);
        data = sPattern.matcher(data).replaceAll(EMPTY);
        data = stylePattern.matcher(data).replaceAll(EMPTY);
        data = scriptPattern.matcher(data).replaceAll(EMPTY);
        data = wPattern.matcher(data).replaceAll(EMPTY);
        data = oPattern.matcher(data).replaceAll(EMPTY);
        data = xmlPattern.matcher(data).replaceAll(EMPTY);
        data = RegexpUtils.nestReplaceAll(tablePattern, bottomBorderPattern, data, "border:$1");
        return formatHTML(data);
    }

    /**
     * 处理html元素pre
     * @param data html串
     * @return html结果
     */
    private String handPre(String data) {
        Matcher m = prePattern.matcher(data);
        boolean result = m.find();
        if (result) {
            StringBuffer sb = new StringBuffer();
            do {
                m.appendReplacement(sb, m.group(1) + brPattern.matcher(m.group(2)).replaceAll("\n") + m.group(3));
                result = m.find();
            } while (result);
            m.appendTail(sb);
            return sb.toString();
        }
        return data;
    }

    /**
     * 处理html属性
     * @param html 标签串
     * @param tagName 标签名
     * @return 属性串
     */
    private String getAttrs(String html, String tagName) {
        Matcher m = attrPattern.matcher(html);
        HashMap<String, String> attrs = new HashMap<>();
        while (m.find()) {
            String attrName = m.group(1);
            if (StringUtils.isEmpty(attrName)) {
                attrName = m.group(2);
            }
            if (StringUtils.isEmpty(attrName)) {
                attrName = m.group(4);
            }
            if (StringUtils.isEmpty(attrName)) {
                attrName = m.group(6);
            }
            attrName = attrName.toLowerCase();
            String value = StringUtils.isNotEmpty(m.group(2)) ? m.group(3) : (StringUtils.isNotEmpty(m.group(4)) ? m.group(5) : m.group(7));
            if (StringUtils.isNotEmpty(value))
                attrs.put(attrName, value);
        }
        if (tagName.equals("font")) {
            HashMap<String, String> styles = new HashMap<>();
            String v = attrs.remove("color");
            if (v != null) {
                styles.put("color", v);
            }
            v = attrs.remove("size");
            if (v != null) {
                if (StringUtils.isNumeric(v)) {
                    styles.put("font-size", v + "px");
                } else if ((v.endsWith("px") || v.endsWith("em") && StringUtils.isNumeric(v.substring(0, v.length() - 2).trim()))) {
                    styles.put("font-size", v);
                }
            }
            v = attrs.remove("face");
            if (v != null) {
                styles.put("font-family", v);
            }
            String fontStyle = attrs.get("style");
            if (fontStyle != null && !fontStyle.endsWith(";")) {
                fontStyle += ";";
            }
            for (Map.Entry<String, String> s : styles.entrySet()) {
                String key = s.getKey();
                String sv = s.getValue();
                if (spacePattern.matcher(sv).matches()) {
                    sv = "'" + sv + "'";
                }
                fontStyle += key + ":" + sv + ";";
            }
            if (fontStyle != null)
                attrs.put("style", fontStyle);
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> s : attrs.entrySet()) {
            String key = s.getKey();
            if (!htmlAttrs.containsKey(key)) {
                continue;
            }
            String v = s.getValue();
            if ((key.equals("src") || key.equals("href")) && !URLUtils.validURL(v)) {
                continue;
            }
            if (key.equals("style")) {
                v = styleAttrPattern.matcher(v).replaceAll(EMPTY);
            }
            v = v.replaceAll("\"", "&quot;");
            builder.append(" ").append(key).append("=\"").append(v).append("\"");
        }
        return builder.toString();
    }

    /**
     * 处理标签
     * @param data 数据
     * @return 标签数据
     */
    private String handTag(String data) {
        Matcher m = rePattern.matcher(data);
        boolean result = m.find();
        if (!result)
            return data;
        StringBuffer sb = new StringBuffer();
        do {
            String full = m.group();
            String startNewline = m.group(1);
            String startSlash = m.group(2);
            String tagName = m.group(3).toLowerCase();
            String attr = m.group(4);
            String endSlash = m.group(5);
            endSlash = endSlash != null ? ' ' + endSlash : EMPTY;
            String endNewline = m.group(6);
            if (!htmlTags.containsKey(tagName)) {
                result = m.find();
                continue;
            }
            if (endSlash == EMPTY && htmlSingleTags.containsKey(tagName))
                endSlash = "/";
            if (htmlInlineTags.containsKey(tagName)) {
                if (StringUtils.isNotEmpty(startNewline)) {
                    startNewline = " ";
                }
                if (StringUtils.isNotEmpty(endNewline)) {
                    endNewline = " ";
                }
            }
            if ("pre".equals(tagName)) {
                if (StringUtils.isNotEmpty(startSlash)) {
                    endNewline = "\n";
                } else {
                    startNewline = "\n";
                }
            }
            if (htmlBlockTags.containsKey(tagName) && !"pre".equals(tagName)) {
                startNewline = endNewline = EMPTY;
            }
            if (StringUtils.isNotEmpty(attr)) {
                attr = getAttrs(full, tagName);
            } else {
                attr = EMPTY;
            }
            if (startSlash == null)
                startSlash = EMPTY;
            String rs = startNewline + '<' + startSlash + tagName + attr + endSlash + '>' + endNewline;
            m.appendReplacement(sb, rs);
            result = m.find();
        } while (result);
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 格式化html去掉不必要的字符
     * @param data 数据
     * @return html串
     */
    private String formatHTML(String data) {
        if (!htmlPattern.matcher(data).find()) {
            return data;
        }
        data = handPre(data);
        data = brpPattern.matcher(data).replaceAll("</p>");
        data = pPattern.matcher(data).replaceAll("$1<br />$2");
        data = u00a9Pattern.matcher(data).replaceAll(EMPTY);
        data = u200bPattern.matcher(data).replaceAll("&copy;");
        data = handTag(data);
        data = nnPattern.matcher(data).replaceAll("\n");
        return data;
    }

    /**
     * 扩展Request类过滤参数
     */
    class Request extends HttpServletRequestWrapper {

        public Request(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            // 返回值之前 先进行过滤
            return filterDangerString(super.getParameter(name));
        }

        @Override
        public String[] getParameterValues(String name) {
            // 返回值之前 先进行过滤
            String[] values = super.getParameterValues(name);
            if (values == null)
                return values;
            for (int i = 0; i < values.length; i++) {
                values[i] = filterDangerString(values[i]);
            }
            return values;
        }
    }
}