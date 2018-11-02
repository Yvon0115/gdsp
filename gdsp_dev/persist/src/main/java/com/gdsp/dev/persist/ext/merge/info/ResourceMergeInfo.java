package com.gdsp.dev.persist.ext.merge.info;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

/**
 * merge xml 信息
 * @author xiangguo
 * @version 1.0 2016-03-25
 * @since 1.8
 */
public class ResourceMergeInfo extends ResourceMergeBaseInfo {

    /**
     * key包裹了文件名
     * @return
     */
    public Map<String, Node> getNameScapeElementMap() {
        Map<String, Node> map = new HashMap<String, Node>();
        if (this.getElementMap() != null) {
            for (String elementKey : this.getElementMap().keySet()) {
                map.put(this.getKey() + elementKey, this.getElementMap().get(elementKey));
            }
        }
        return map;
    }
}
