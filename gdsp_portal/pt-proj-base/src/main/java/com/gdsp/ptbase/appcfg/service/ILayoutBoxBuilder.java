package com.gdsp.ptbase.appcfg.service;

import java.util.List;

import com.gdsp.ptbase.appcfg.model.PageVO;
import com.gdsp.ptbase.portal.model.IPortletLayoutBox;

/**
 * 布局块构建接口
 *
 * @author paul.yang
 * @version 1.0 2015-8-5
 * @since 1.6
 */
public interface ILayoutBoxBuilder {

    /**
     * 构建页面布局
     * @param page 页面布局
     * @return 布局容器列表
     */
    List<IPortletLayoutBox> build(PageVO page);
}
