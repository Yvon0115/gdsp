package com.gdsp.dev.persist.ext.merge.assistant;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.persist.ext.merge.info.ResourceMergeBaseInfo;
import com.gdsp.dev.persist.ext.merge.info.ResourceMergeDaoInfo;
import com.gdsp.dev.persist.ext.merge.info.ResourceMergeInfo;
import com.gdsp.dev.persist.utils.PersistUtils;
import com.gdsp.dev.persist.utils.XmlParserUtils;

/**
 * 解析处理xml文档
 * @author xiangguo
 * @version 1.0 2015年3月25日
 * @since 1.8
 */
public class ResourceMergeXmlParser {

    private final Log           logger         = LogFactory.getLog(getClass());
    public static final String  ROOT_NODE_NAME = "$root$";
    /**
     * merge xml <mapper >节点上的 merge属性
     */
    public static final String  MERGE_FLAG     = "to";
    /**
     * 处理dtd
     */
    private static final String PATTERN_DTD    = "<!DOCTYPE[\\s\\S]*?>";
    private static final String PATTERN_SQL    = "<sql[\\s\\S]*?</sql>";
    private static final String PATTERN_TITLE  = "<\\?xml[\\s\\S]*?\\?>";
    private static final String REPLACE_DTD    = "<!--##{ dtd replace }-->";
    private Map<String, String> dtdMap         = new HashMap<String, String>();

    /**
     * 拼装mergexml
     * @param origXml
     * @return
     * @throws DevException 
     */
    private String prepareMergeInfoXml(String xml) throws DevException {
        List<String> titleMatchList = PersistUtils.getRegexpMathList(PATTERN_TITLE, xml);
        if (titleMatchList == null || titleMatchList.size() != 1) {
            String errMsg = "merge文件解析异常：xml头不规范" + xml;
            logger.error(errMsg);
            throw new DevException(errMsg);
        } else {
            String titleMatch = titleMatchList.get(0);
            int idx = xml.indexOf(titleMatch);
            idx = idx + titleMatch.length();
            StringBuilder xmlBuffer = new StringBuilder();
            xmlBuffer.append(xml.substring(0, idx));
            xmlBuffer.append("<merge>");
            xmlBuffer.append(xml.substring(idx));
            xmlBuffer.append("</merge>");
            xml = xmlBuffer.toString();
        }
        List<String> sqlMatchList = PersistUtils.getRegexpMathList(PATTERN_SQL, xml);
        String match = null;
        int idxBegin = -1;
        int idxEnd = -1;
        StringBuilder cdataBuff = null;
        for (int i = 0; i < sqlMatchList.size(); i++) {
            match = sqlMatchList.get(i);
            idxBegin = match.indexOf(">");
            idxEnd = match.lastIndexOf("</sql>");
            cdataBuff = new StringBuilder();
            cdataBuff.append(match.substring(0, idxBegin + 1));
            if (idxBegin == idxEnd - 1) {
                cdataBuff.append("<![CDATA[]]>");
            } else {
                cdataBuff.append("<![CDATA[" + match.substring(idxBegin + 1, idxEnd) + "]]>");
            }
            cdataBuff.append(match.substring(idxEnd));
            xml = xml.replace(match, cdataBuff.toString());
        }
        return xml;
    }

    public static String removeXmlTitle(String xml) {
        List<String> titleMatchList = PersistUtils.getRegexpMathList(PATTERN_TITLE, xml);
        if (titleMatchList != null) {
            for (String m : titleMatchList) {
                xml = xml.replace(m, "");
            }
        }
        return xml;
    }

    /**
     * 去除DTD属性域，否则解析xml报错
     * @return
     */
    public String removeDTDElement(StringBuilder dtdBuffer, String xml) {
        List<String> matchList = PersistUtils.getRegexpMathList(PATTERN_DTD, xml);
        String match = null;
        for (int i = 0; i < matchList.size(); i++) {
            match = matchList.get(i);
            //会有多个dtd??? 兼容多个dtd
            if (i == matchList.size() - 1) {
                xml = xml.replace(match, REPLACE_DTD);
            } else {
                xml = xml.replace(match, "");
            }
            dtdBuffer.append(match);
        }
        return xml;
    }

    /**
     * 增加DTD
     * @param key
     * @param xml
     * @return
     */
    public String addDTDElement(String key, String xml) {
        if (StringUtils.isNotBlank(xml)) {
            xml = xml.replace(REPLACE_DTD, dtdMap.get(key));
        }
        return xml;
    }

    /**
     * 输入流转化为string
     * @param stream
     * @return
     * @throws IOException
     */
    public String convertStreamToString(InputStream stream) throws IOException {
        return IOUtils.toString(stream, "UTF-8");
    }

    /**
     * string 转化为 inputstream
     * @param resource
     * @return
     * @throws UnsupportedEncodingException
     */
    public InputStream convertStringToStream(String context) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(context)) {
            return new ByteArrayInputStream(context.getBytes("UTF-8"));
        }
        return null;
    }

    /**
     * 解析xml注入bean
     * @param resource 资源文件
     * @param baseInfo 
     * @param keyAttribute 
     * @throws IOException 
     * @throws DevException 
     * @throws SAXException 
     */
    public void parse(Resource resource, ResourceMergeBaseInfo baseInfo) throws IOException, DevException, SAXException  {
        if (resource == null) {
            return;
        }
        long lastModify = resource.lastModified();
        baseInfo.setSource(resource);
        baseInfo.setLastModify(lastModify);
        //设置原始xml信息
        String xml = this.convertStreamToString(resource.getInputStream());
        if (baseInfo instanceof ResourceMergeInfo) {
            xml = this.prepareMergeInfoXml(xml);
        }
        baseInfo.setOriginalContext(xml);
        //解析xml
        StringBuilder dtdBuffer = new StringBuilder();
        String filtedXml = this.removeDTDElement(dtdBuffer, baseInfo.getOriginalContext());
        Map<String, Node> nodeMap = new HashMap<String, Node>();
        Map<String, Node> mergeNodeMap = new HashMap<String, Node>();
        Document doc = XmlParserUtils.getDocument(filtedXml, false);
        Element root = doc != null ? doc.getDocumentElement() : null;
        if (root == null) {
            String errMsg = "merge文件不规范:" + filtedXml;
            logger.error(errMsg);
            throw new DevException(errMsg);
        }
        baseInfo.setDoc(doc);
        baseInfo.setRoot(root);
        NodeList nodeList = root.getChildNodes();
        Node item = null;
        String nodeKey = null;
        String daoNameScape = null;
        String mergeTo = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            item = nodeList.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                if (baseInfo instanceof ResourceMergeDaoInfo) {
                    //如果dao xml有mergeTo属性则以此为nodeKey 若无则以namescape+id为key
                    mergeTo = getAttributeValue("mergeTo", item);
                    if (StringUtils.isNotBlank(mergeTo)) {
                        nodeKey = mergeTo;
                        //如果发现合并节点则进行标记
                        mergeNodeMap.put(nodeKey, item);
                    } else {
                        daoNameScape = getAttributeValue("namescape", root);
                        nodeKey += StringUtils.isNotBlank(daoNameScape) ? daoNameScape : "" + getAttributeValue("id", item);
                    }
                } else {
                    //如果为merge xml节点则以id为nodekey
                    nodeKey = getAttributeValue("id", item);
                }
                nodeMap.put(nodeKey, item);
            }
        }
        if (baseInfo instanceof ResourceMergeDaoInfo && mergeNodeMap.size() > 0) {
            ResourceMergeDaoInfo daoInfo = (ResourceMergeDaoInfo) baseInfo;
            daoInfo.setToMerge(true);
            daoInfo.setMergeElementMap(mergeNodeMap);
        }
        baseInfo.setElementMap(nodeMap);
        baseInfo.setKey(resource.getDescription());
        //设置dtdmap 便于后续恢复dtd
        this.getDtdMap().put(baseInfo.getKey(), dtdBuffer.toString());
    }

    /**
     * 获取节点属性值
     * 
     * @param name
     * @param node
     * @return
     */
    public static String getAttributeValue(String name, Node node) {
        NamedNodeMap nMap = node != null ? node.getAttributes() : null;
        if (nMap != null) {
            Node attrNode = nMap.getNamedItem(name);
            String attrVal = attrNode != null ? attrNode.getNodeValue() : null;
            return StringUtils.isNotBlank(attrVal) && (attrVal == null ? -1: attrVal.trim().length()) > 0 ?(attrVal == null ? null: attrVal.trim()): null;
        }
        return null;
    }

    /**
     * 设置节点属性值
     * @param name
     * @param value
     * @param node
     */
    public static void setAttrbuteValue(String name, String value, Node node) {
        NamedNodeMap nMap = node != null ? node.getAttributes() : null;
        if (nMap != null) {
            Node attrNode = nMap.getNamedItem(name);
            attrNode.setNodeValue(value);
        }
    }

    /**
     * 去除节点属性
     * @param name
     * @param node
     */
    public void removeAttriBute(String name, Node node) {
        if (node != null) {
            node.getAttributes().removeNamedItem(name);
        }
    }

    public Map<String, String> getDtdMap() {
        return dtdMap;
    }

    public void setDtdMap(Map<String, String> dtdMap) {
        this.dtdMap = dtdMap;
    }

    public static void main(String[] args) throws SAXException, IOException, TransformerException  {
        String text = "<ol><li>1\n</li><li>2</li></ol>";
        List<String> matchList = PersistUtils.getRegexpMathList("<li[\\s\\S]*?</li>", text);
        for (String m : matchList) {
            System.out.println(m);
        }
        String t2 = "<sql> select b.BIZ_Date as BIZ_Date from ${@com.gdsp.dev.core.common.AppConfig@getProperty(\"NSRMSVIEW\")}.VIS_HGT_TRAD_AND_LMT_USE_CNDT b "
                + "<if test=\"startTime != null and startTime!='' and endTime != null and endTime!=''\"> "
                + "where b.BIZ_Date between #{startTime} > and #{endTime} "
                + "</if> </sql>";
        Document doc = XmlParserUtils.getDocument(t2, false);
        Element root = doc != null ? doc.getDocumentElement() : null;
        NodeList nodeList = root.getChildNodes();
        Node item = null;
//        String nodeKey = null;
//        String daoNameScape = null;
//        String mergeTo = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            item = nodeList.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("NODE：" + item.getNodeName());
                for (int j = 0; item.hasChildNodes() && j < item.getChildNodes().getLength(); j++) {
                    Node c = item.getChildNodes().item(j);
                    if (c.getNodeType() == Node.ELEMENT_NODE) {
                        System.out.println("NODE:" + c.getNodeName());
                    }
                    if (c.getNodeType() == Node.TEXT_NODE) {
                        System.out.println("TEXT:" + c.getTextContent());
                        c.setTextContent(c.getTextContent().replace(">", ResourceMergeSqlParser.GT));
                    }
                }
            }
            if (item.getNodeType() == Node.TEXT_NODE) {
                System.out.println("TEXT:" + item.getTextContent());
            }
        }
        System.out.println(XmlParserUtils.getDocumentFullContent(doc));
    }
}
