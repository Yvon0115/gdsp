package com.gdsp.dev.persist.ext.merge.assistant;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.persist.utils.PersistUtils;
import com.gdsp.dev.persist.utils.XmlParserUtils;

/**
 * 解析待合并的xml,生成mybatis规范的sql
 * @author xiangguo
 * @version 1.0 2016年3月25日
 * @since 1.8
 */
public class ResourceMergeSqlParser {

    private final Log                        logger        = LogFactory.getLog(getClass());
    private Map<String, Map<String, String>> sqlMap        = new HashMap<String, Map<String, String>>();
    private static final String              IN_TEMPLATE   = " <foreach collection=\"param\" item=\"item\" index=\"index\" separator=\",\" open=\"(\" close=\")\">#{item}</foreach> ";
    private static final String              COND_TEMPLATE = " <trim prefix=\"where\" prefixOverrides=\"and |or \"><if test=\"param!= null\">${param._CONDITION_}</if></trim> ";
    private static final String              SORT_TEMPLATE = " <if test=\"param != null\"> order by	<foreach collection=\"param\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if> ";
    private static final Map<String, String> EXP_MAP       = new HashMap<String, String>();
    public static final String               GT            = "###GT2016###";
    public static final String               LT            = "###LT2016###";
    static {
        EXP_MAP.put("in", IN_TEMPLATE);
        EXP_MAP.put("cond", COND_TEMPLATE);
        EXP_MAP.put("sort", SORT_TEMPLATE);
    }

    /**
     * 转化mergenod to sql 
     * @param mergeNodeSql
     * @param keyMap
     * @return
     * @throws DevException 
     * @throws TransformerException 
     * @throws IOException 
     * @throws SAXException 
     */
    public String convertMergeNodeToSql(String mergeNodeSql, String keyMap) throws DevException, SAXException, IOException, TransformerException {
        //获取表达式 <sql[\\s\\S]*?</sql>
        List<String> expList = PersistUtils.getRegexpMathList("#\\{[\\s\\S]*?\\}", mergeNodeSql);
        //处理字符转义 否则 mybatis解析会报错
        //mergeNodeSql = mergeNodeSql.replace(">", GT).replace("<", LT);
        //.replace("&", "");
        //.replace("'", "&apos;").replace("\"", "&quot;");
        Map<String, String> paramMap = this.getParamMap(keyMap);
        //处理sql大于小于号及if表达式等参数映射
        if (mergeNodeSql.indexOf(">") != -1 || mergeNodeSql.indexOf("<") != -1) {
            mergeNodeSql = doProcessMybatisExp(mergeNodeSql, paramMap);
        }
        Map<String, Expression> expMap = new HashMap<String, Expression>();
        for (String exp : expList) {
            expMap.put(exp, this.createExp(exp));
        }
        //替换参数名
        for (String preParm : paramMap.keySet()) {
            for (Expression exp : expMap.values()) {
                if (preParm.equals(exp.getParam())) {
                    exp.setParam(paramMap.get(preParm));
                }
            }
        }
        //替换表达式模板的参数
        String template = null;
        for (Expression exp : expMap.values()) {
            if (exp.isJustParam()) {
                exp.setContent("#{" + exp.getParam() + "}");
            } else {
                template = EXP_MAP.get(exp.getExpName().toLowerCase());
                template = template.replace("param", exp.getParam());
                exp.setContent(template);
            }
        }
        //将表达式替换为表达式模板
        for (String expPattern : expList) {
            mergeNodeSql = mergeNodeSql.replace(expPattern, expMap.get(expPattern).getContent());
        }
        return mergeNodeSql;
    }

    /**
     * 处理 if 等mybatis表达式
     * @param sql
     * @param paramMap
     * @return
     * @throws IOException 
     * @throws SAXException 
     * @throws TransformerException 
     */
    private String doProcessMybatisExp(String sql, Map<String, String> paramMap) throws SAXException, IOException, TransformerException  {
        sql = "<sqlxxxsql>" + sql + "</sqlxxxsql>";
        Document doc = XmlParserUtils.getDocument(sql, false);
        Element root = doc != null ? doc.getDocumentElement() : null;
        NodeList nodeList = root != null && root.hasChildNodes() ? root.getChildNodes() : null;
        processMybatisExp(nodeList, paramMap);
        sql = XmlParserUtils.getDocumentFullContent(doc);
        sql = ResourceMergeXmlParser.removeXmlTitle(sql);
        return sql.replace("<sqlxxxsql>", "").replace("</sqlxxxsql>", "");
    }

    /**
     * 递归处理大于小于号
     * @param itemList
     * @param paramMap
     */
    private void processMybatisExp(NodeList nodeList, Map<String, String> paramMap) {
        Node item = null;
        String test = null;
        for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
            item = nodeList.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                //处理参数名替换
                test = ResourceMergeXmlParser.getAttributeValue("test", item);
                if (test != null) {
                    for (String preParam : paramMap.keySet()) {
                        test = test.replace(preParam, paramMap.get(preParam));
                    }
                    ResourceMergeXmlParser.setAttrbuteValue("test", test, item);
                }
                //递归处理
                if (item.hasChildNodes()) {
                    processMybatisExp(item.getChildNodes(), paramMap);
                }
            }
            if (item.getNodeType() == Node.TEXT_NODE) {
                if (item.getTextContent() != null) {
                    item.setTextContent(item.getTextContent().replace(">", ResourceMergeSqlParser.GT).replace("<", ResourceMergeSqlParser.LT));
                }
            }
        }
    }

    private Map<String, String> getParamMap(String keyMap) throws DevException {
        Map<String, String> paramMap = new HashMap<String, String>();
        if (StringUtils.isNotBlank(keyMap)) {
            String[] keyAttr = keyMap.split(",");
            String[] keyItemAttr = null;
            String preParam = null;
            String postParam = null;
            for (String keyItem : keyAttr) {
                if (StringUtils.isNotBlank(keyItem) && keyItem.trim().length() > 0) {
                    boolean paramPass = false;
                    keyItemAttr = keyItem.split(":");
                    if (keyItemAttr != null && keyItemAttr.length == 2) {
                        preParam = keyItemAttr[0] != null ? keyItemAttr[0].trim() : null;
                        postParam = keyItemAttr[1] != null ? keyItemAttr[1].trim() : null;
                        if (StringUtils.isNotBlank(preParam) && StringUtils.isNotBlank(postParam)) {
                            paramMap.put(preParam, postParam);
                            paramPass = true;
                        }
                    }
                    if (!paramPass) {
                        String errMsg = "merge文件paramMap异常" + keyMap;
                        logger.error(errMsg);
                        throw new DevException(errMsg);
                    }
                }
            }
        }
        return paramMap;
    }

    public String getSqlFlag(String sqlNodeId) {
        return "##{" + sqlNodeId + "}";
    }

    /**
     * 
     * @param key
     * @param xml
     * @return
     */
    public String reaplaceAllSqlFlag(String key, String xml) {
        Map<String, String> mergeSqlMap = this.getSqlMap().get(key);
        if (mergeSqlMap != null) {
            for (String mapKey : mergeSqlMap.keySet()) {
                xml = xml.replace(this.getSqlFlag(mapKey), mergeSqlMap.get(mapKey));
            }
        }
        return xml;
    }

    public Map<String, Map<String, String>> getSqlMap() {
        return sqlMap;
    }

    public void setSqlMap(Map<String, Map<String, String>> sqlMap) {
        this.sqlMap = sqlMap;
    }

    /**
     * 生成表达式
     * @param expStr
     * @return
     * @throws DevException 
     */
    private Expression createExp(String expStr) throws DevException {
        Expression exp = null;
        if (StringUtils.isNotBlank(expStr)) {
            boolean expPass = true;
            String errMsg = null;
            exp = new Expression();
            expStr = expStr.replace("#{", "").replace("}", "").trim();
            int idx = expStr.indexOf(":");
            if (idx == -1) {
                //无表达式仅参数
                //exp = this.createExp(expStr);
                exp.setParam(expStr);
                exp.setJustParam(true);
            } else if (idx == 0 || idx == expStr.length() - 1) {
                expPass = false;
                errMsg = "merge文件表达式不合法" + expStr;
            } else {
                String[] expArry = expStr.split(":");
                String expName = expArry != null && expArry.length == 2 && expArry[0] != null ? expArry[0].trim() : null;
                String param = expArry != null && expArry.length == 2 && expArry[1] != null ? expArry[1].trim() : null;
                if (StringUtils.isBlank(expName) || StringUtils.isBlank(param)) {
                    expPass = false;
                    errMsg = "DevException:mergexml 表达式不合法" + expStr;
                } else {
                    if (EXP_MAP.get(expName == null ?null:expName.toLowerCase()) != null) {
                        exp = new Expression();
                        exp.setExpName(expName);
                        exp.setParam(param);
                    } else {
                        expPass = false;
                        errMsg = "DevException:merge文件无此表达式" + expStr;
                    }
                }
            }
            if (!expPass) {
                logger.error(errMsg);
                throw new DevException(errMsg);
            }
        }
        return exp;
    }

    /**
     * 表达式内部类
     *
     */
    class Expression {

        private String  content;
        private String  expName;
        private String  param;
        private boolean justParam = false;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExpName() {
            return expName;
        }

        public void setExpName(String expName) {
            this.expName = expName;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public boolean isJustParam() {
            return justParam;
        }

        public void setJustParam(boolean justParam) {
            this.justParam = justParam;
        }
    }
}
