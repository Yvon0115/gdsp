package com.gdsp.dev.persist.ext.merge.info;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ResourceMergeBaseInfo {

    /**
     * 文件绝对路径
     */
    private String            key;
    /**
     * 原始整体内容
     */
    private String            originalContext;
    /**
     * 最新更新时间
     */
    private Long              lastModify;
    /**
     * 源对象
     */
    private Object            source;
    /**
     * 源的类型
     */
    private String            sourceType;
    /**
     * XML各节点(根及下层)
     */
    private Map<String, Node> elementMap = new HashMap<String, Node>();
    /**
     * 文档
     */
    private Document          doc        = null;
    /**
     * 根节点
     */
    private Element           root       = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOriginalContext() {
        return originalContext;
    }

    public void setOriginalContext(String originalContext) {
        this.originalContext = originalContext;
    }

    public Long getLastModify() {
        return lastModify;
    }

    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Map<String, Node> getElementMap() {
        return elementMap;
    }

    public void setElementMap(Map<String, Node> elementMap) {
        this.elementMap = elementMap;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public Element getRoot() {
        return root;
    }

    public void setRoot(Element root) {
        this.root = root;
    }
}
