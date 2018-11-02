package com.gdsp.dev.persist.ext.merge.handler;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.core.io.Resource;

import com.gdsp.dev.persist.ext.merge.info.ResourceMergeInfo;

/**
 * 实现文件方式的资源合并
 * 将多个merge xml 合并为一个 dao xml
 * @author xiangguo
 * @version 1.0 2016年3月26日
 * @since 1.8
 */
public class ResourceMergeHandlerFileImpl extends ResourceMergeHandler {

    private final Log  logger = LogFactory.getLog(getClass());
    /**
     * 待合并资源
     */
    private Resource[] mergeResources;

    public ResourceMergeHandlerFileImpl() {}

    public Resource[] getMergeResources() {
        return mergeResources;
    }

    public void setMergeResources(Resource[] mergeResources) {
        this.mergeResources = mergeResources;
    }

    @Override
    public void initAllMergeInfo(){
        boolean exists = this.mergeResources != null && mergeResources.length > 0;
        if (!exists) {
            return;
        }
        Resource resource = null;
        for (int i = 0; i < mergeResources.length; i++) {
            resource = mergeResources[i];
            if (resource != null) {
                ResourceMergeInfo mergeInfo = new ResourceMergeInfo();
                try {
                    this.getXmlParser().parse(resource, mergeInfo);
                } catch (Exception e) {
                    
                    logger.error(e.getMessage(), e);
                }
                this.getMergeInfoMap().put(mergeInfo.getKey(), mergeInfo);
                this.getMergeNodeMap().putAll(mergeInfo.getElementMap());
            }
        }
    }
}
