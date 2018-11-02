package com.gdsp.ptbase.portal.model;

/**
 * portlet操作
 *
 * @author paul.yang
 * @version 1.0 2015-7-30
 * @since 1.6
 */
public interface IPortletAction {

    /**
     * 取得行为标识
     * @return 行为标识
     */
    String getActionId();

    /**
     * 取得操作行为内容
     */
    String getActionContent();
}
