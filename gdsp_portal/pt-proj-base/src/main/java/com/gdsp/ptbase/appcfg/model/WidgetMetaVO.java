package com.gdsp.ptbase.appcfg.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gdsp.dev.core.data.json.Json2ObjectMapper;
import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.ptbase.portal.model.IPortletAction;
import com.gdsp.ptbase.portal.model.IPortletMeta;

/**
 * portlet通用描述信息
 *
 * @author paul.yang
 * @version 1.0 2015-8-3
 * @since 1.6
 */
public class WidgetMetaVO extends AuditableEntity implements IPortletMeta {

    /**
     * 序列id
     */
    private static final long   serialVersionUID = -7590870434237866862L;
    /**
     * 日志变量
     */
    private static Logger       logger           = LoggerFactory.getLogger(WidgetMetaVO.class);
    /**
     * 名称
     */
    private String              name;
    /**
     * 目录id
     */
    private String              dirId;
    /**
     * 描述
     */
    private String              memo;
    /**
     * 样式
     */
    private String              style;
    /**
     * portlet类型
     */
    private String              type;
    /**
     * 配置信息
     */
    private String              preference;
    /**
     * portlet加载url
     */
    private String              loadUrl;
    /**
     * 配置信息
     */
    private Map<String, String> prefMap;

    @Override
    public String getPortletId() {
        return getId();
    }

    @Override
    public String getPortletTitle() {
        return getName();
    }

    @Override
    public String getPortletURL() {
        return getLoadUrl();
    }

    @Override
    public String getPreference(String key) {
        if (prefMap != null) {
            return prefMap.get(key);
        }
        if (StringUtils.isNotEmpty(preference)) {
            try {
                prefMap = Json2ObjectMapper.getInstance().readValue(preference, Map.class);
                return prefMap.get(key);
            } catch (Exception e) {
                logger.error("parsing json to map occur error in preference", e);
                return null;
            }
        }
        return null;
    }

    public void setPreference(String key, String value) {
        getPreference(key);
        if (prefMap == null) {
            prefMap = new HashMap<>();
        }
        prefMap.put(key, value);
        preference = null;
    }

    @Override
    public List<IPortletAction> getActions() {
        return   Collections.emptyList();
    }

    @Override
    public String getTemplate() {
        return getStyle();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLoadUrl() {
        return loadUrl;
    }

    public void setLoadUrl(String loadUrl) {
        this.loadUrl = loadUrl;
    }

    public String getPreference() {
        if (preference == null && prefMap != null && prefMap.size() > 0) {
            try {
                preference = Json2ObjectMapper.getInstance().writeValueAsString(prefMap);
            } catch (JsonProcessingException e) {
                logger.error("parsing json to map occur error in preference", e);
                return null;
            }
        }
        return preference;
    }

    public void setPreference(String preference) {
        if (preference == null)
            prefMap = null;
        this.preference = preference;
    }
}
