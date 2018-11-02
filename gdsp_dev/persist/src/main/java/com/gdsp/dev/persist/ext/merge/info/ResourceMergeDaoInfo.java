package com.gdsp.dev.persist.ext.merge.info;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

/**
 * dao xml 信息
 * @author xiangguo
 * @version 1.0 2015年3月24日
 * @since 1.8
 */
public class ResourceMergeDaoInfo extends ResourceMergeBaseInfo {

    /**
     * 是否进行合并
     */
    private boolean           toMerge         = false;
    /**
     * 待合并节点
     */
    private Map<String, Node> mergeElementMap = new HashMap<String, Node>();

    public boolean isToMerge() {
        return toMerge;
    }

    public void setToMerge(boolean toMerge) {
        this.toMerge = toMerge;
    }

    public Map<String, Node> getMergeElementMap() {
        return mergeElementMap;
    }

    public void setMergeElementMap(Map<String, Node> mergeElementMap) {
        this.mergeElementMap = mergeElementMap;
    }
}
