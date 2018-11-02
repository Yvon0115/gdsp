package com.gdsp.dev.persist.ext.merge.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.persist.ext.merge.assistant.ResourceMergeSqlParser;
import com.gdsp.dev.persist.ext.merge.assistant.ResourceMergeXmlParser;
import com.gdsp.dev.persist.ext.merge.info.ResourceMergeDaoInfo;
import com.gdsp.dev.persist.ext.merge.info.ResourceMergeInfo;
import com.gdsp.dev.persist.utils.XmlParserUtils;

/**
 * 合并 merge 及 meage-{jdbc.dbtype} 目录下的 sql至 dao xml 
 * 拆分数据组与展现组在sql方面的职责，达到数据组维护sql，展示组处理参数及sql文件的映射关系
 * @author xiangguo
 * @version 1.0 2015年3月25日
 * @since 1.8
 */
public abstract class ResourceMergeHandler {

    private final Log                         logger       = LogFactory.getLog(getClass());
    /**
     * key值为filePath的 daoInfo 
     */
    private Map<String, ResourceMergeDaoInfo> daoInfoMap   = new HashMap<String, ResourceMergeDaoInfo>();
    /**
     * merge info 
     */
    private Map<String, ResourceMergeInfo>    mergeInfoMap = new HashMap<String, ResourceMergeInfo>();
    /**
     * merge node 
     */
    private Map<String, Node>                 mergeNodeMap = new HashMap<String, Node>();
    /**
     * xml 解析器
     */
    private ResourceMergeXmlParser            xmlParser    = new ResourceMergeXmlParser();
    /**
     * sql 解析器
     */
    private ResourceMergeSqlParser            sqlParser    = new ResourceMergeSqlParser();
    /**
     * dao 与 merge 的映射关系
     */
    private Map<String, String>               mergeMapping = new HashMap<String, String>();

    /**
     * 初始化dao及merge信息
     * @throws SAXException 
     * @throws DevException 
     * @throws IOException 
     */
    public void init(Resource[] mapperLocations) throws IOException, DevException, SAXException {
        //由于映射关系保存在merge xml中 需要先加载
        long time = System.currentTimeMillis();
        initAllMergeInfo();
        initAllDaoInfo(mapperLocations);
        time = System.currentTimeMillis() - time;
        if (logger.isDebugEnabled()) {
            logger.debug("merge合并init耗时：" + time + "毫秒");
        }
    }

    /**
     * 合并Resource节点
     * @param resource
     * @return
     * @throws DevException 
     * @throws TransformerException 
     * @throws SAXException 
     * @throws IOException 
     */
    public InputStream doMerge(String resourceFilePath) throws DevException, TransformerException, SAXException, IOException  {
        ResourceMergeDaoInfo daoInfo = this.daoInfoMap.get(resourceFilePath);
        if (daoInfo == null) {
            String errMsg = "DevException:找不到" + resourceFilePath;
            logger.error(errMsg);
            throw new DevException(errMsg);
        }
        if (daoInfo.isToMerge()) {
            long time = System.currentTimeMillis();
            //处理各SQL节点替换
            Node mergeNode = null;
            for (String sqlNodeKey : daoInfo.getMergeElementMap().keySet()) {
                mergeNode = this.getMergeNodeMap().get(sqlNodeKey);
                //注释无合并节点的异常
                /*
                if(mergeNode == null){
                	String errMsg = "找不到对应merge sql:"+sqlNodeKey;
                	logger.error(errMsg);
                	throw new DevException(errMsg);
                }
                */
                this.convertMergeSql(daoInfo.getKey(), sqlNodeKey, daoInfo.getMergeElementMap().get(sqlNodeKey), mergeNode);
            }
            //获取 document转化后的 xml
            String daoXml = XmlParserUtils.getDocumentFullContent(daoInfo.getDoc());
            //增加dtd
            daoXml = this.xmlParser.addDTDElement(daoInfo.getKey(), daoXml);
            //替换sql
            daoXml = this.sqlParser.reaplaceAllSqlFlag(daoInfo.getKey(), daoXml);
            daoXml = daoXml.replace(ResourceMergeSqlParser.GT, "&gt;").replace(ResourceMergeSqlParser.LT, "&lt;");
            InputStream xmlStream = this.xmlParser.convertStringToStream(daoXml);
            time = System.currentTimeMillis() - time;
            if (logger.isDebugEnabled()) {
                logger.debug("merge合并doMerge耗时：" + time + "毫秒->" + resourceFilePath);
            }
            return xmlStream;
        } else {
            //不合并则返回源数据流
            return ((Resource) daoInfo.getSource()).getInputStream();
        }
    }

    /**
     * 根据merge node 对 dao node 进行sql转化
     * @param daoNode
     * @param mergeNode
     * @param daoXml
     * @return
     * @throws DevException 
     * @throws TransformerException 
     * @throws IOException 
     * @throws SAXException 
     */
    private void convertMergeSql(String nameScape, String sqlNodeId, Node daoNode, Node mergeNode) throws DevException, SAXException, IOException, TransformerException  {
        if (mergeNode == null) {
            return;
        }
//        String nodeContent = daoNode.getTextContent();
        String mergeContext = mergeNode.getTextContent();
        if ((StringUtils.isBlank(mergeContext) || (mergeContext.trim().length() == 0))) {
            //由于mybatis当sql节点内容为空时在启动时并不报错，当调用时报错，按照mybatis方式处理
            /*
            //若无合并节点并且daoNode节点内容为空则
            if(StringUtils.isBlank(nodeContent) || nodeContent.trim().length()==0){
            	String errMsg = "发现dao节点内容为空，找不到对应合并节点或合并节点内容为空:"+nameScape+"."+sqlNodeId;
            	logger.error(errMsg);
            	throw new DevException(errMsg);
            }else{
            	//若合并节点为空，dao节点不为空则不做处理，保留dao节点内容
            }
            */
        } else {
            String keyMap = ResourceMergeXmlParser.getAttributeValue("mergeParamMap", daoNode);
            String mergeSql = this.sqlParser.convertMergeNodeToSql(mergeContext, keyMap);
            //设置sql替换标志
            daoNode.setTextContent(this.sqlParser.getSqlFlag(sqlNodeId));
            Map<String, String> mergeSqlMap = this.sqlParser.getSqlMap().get(nameScape);
            if (mergeSqlMap == null) {
                mergeSqlMap = new HashMap<String, String>();
            }
            if (StringUtils.isNotBlank(mergeSqlMap.get(sqlNodeId))) {
                String errMsg = "发现merge节点id重复:" + nameScape + "." + sqlNodeId;
                logger.error(errMsg);
                throw new DevException(errMsg);
            }
            mergeSqlMap.put(sqlNodeId, mergeSql);
            this.sqlParser.getSqlMap().put(nameScape, mergeSqlMap);
        }
    }

    /**
     * 初始化dao info
     * @throws SAXException 
     * @throws DevException 
     * @throws IOException 
     */
    private void initAllDaoInfo(Resource[] mapperLocations) throws IOException, DevException, SAXException  {
        boolean exists = mapperLocations != null && mapperLocations.length > 0;
        if (!exists) {
            return;
        }
        Resource resource = null;
        for (int i = 0; i < mapperLocations.length; i++) {
            resource = mapperLocations[i];
            if (resource != null) {
                ResourceMergeDaoInfo daoInfo = new ResourceMergeDaoInfo();
                //解析dao xml
                this.xmlParser.parse(resource, daoInfo);
                this.daoInfoMap.put(resource.getDescription(), daoInfo);
            }
        }
    }

    /**
     * 初始化merge info
     */
    public abstract void initAllMergeInfo();

    /**
     * 寻找merge标记
     * @param content
     * @return
     */
    protected String findMerge(Node root) {
        return ResourceMergeXmlParser.getAttributeValue(ResourceMergeXmlParser.MERGE_FLAG, root);
    }

    protected ResourceMergeXmlParser getXmlParser() {
        return xmlParser;
    }

    protected void setXmlParser(ResourceMergeXmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    protected ResourceMergeSqlParser getSqlParser() {
        return sqlParser;
    }

    protected void setSqlParser(ResourceMergeSqlParser sqlParser) {
        this.sqlParser = sqlParser;
    }

    protected Map<String, ResourceMergeInfo> getMergeInfoMap() {
        return mergeInfoMap;
    }

    protected void setMergeInfoMap(Map<String, ResourceMergeInfo> mergeInfoMap) {
        this.mergeInfoMap = mergeInfoMap;
    }

    protected Map<String, String> getMergeMapping() {
        return mergeMapping;
    }

    protected void setMergeMapping(Map<String, String> mergeMapping) {
        this.mergeMapping = mergeMapping;
    }

    protected Map<String, Node> getMergeNodeMap() {
        return mergeNodeMap;
    }

    protected void setMergeNodeMap(Map<String, Node> mergeNodeMap) {
        this.mergeNodeMap = mergeNodeMap;
    }
}
