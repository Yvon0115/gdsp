package com.gdsp.ptbase.appcfg.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdsp.ptbase.appcfg.model.LayoutColumnVO;
import com.gdsp.ptbase.appcfg.model.PageVO;
import com.gdsp.ptbase.appcfg.model.PageWidgetVO;
import com.gdsp.ptbase.appcfg.model.PorletLayoutBox;
import com.gdsp.ptbase.appcfg.service.ILayoutBoxBuilder;
import com.gdsp.ptbase.appcfg.service.ILayoutColumnService;
import com.gdsp.ptbase.appcfg.service.IWidgetMetaService;
import com.gdsp.ptbase.portal.model.IPortletLayoutBox;

/**
 * 列布局构建器
 *
 * @author paul.yang
 * @version 1.0 2015-8-5
 * @since 1.6
 */
@Service("layoutColumnBuilder")
public class LayoutColumnBuilder implements ILayoutBoxBuilder {

    /**
     * 布局管理服务
     */
    @Resource
    ILayoutColumnService layoutColumnService;
    /**
     * 布局管理服务
     */
    @Resource
    IWidgetMetaService   widgetMetaService;

    @Override
    public List<IPortletLayoutBox> build(PageVO page) {
        List<LayoutColumnVO> columns = layoutColumnService.findLayoutColumns(page.getLayout_id());
        if (columns == null || columns.size() == 0)
            return null;
        Map<String, List<PageWidgetVO>> widgetsMap = page.getWidgets();
        List<IPortletLayoutBox> boxes = new ArrayList<>();
        if (widgetsMap == null) {
            for (LayoutColumnVO column : columns) {
                PorletLayoutBox box = new PorletLayoutBox();
                box.setMeta(column);
                boxes.add(box);
            }
        }
        for (LayoutColumnVO column : columns) {
            PorletLayoutBox box = new PorletLayoutBox();
            box.setMeta(column);
            boxes.add(box);
            List<PageWidgetVO> portlets = null;
            if(widgetsMap != null){
            	portlets = widgetsMap.get(column.getColumn_id());
            }
            if (portlets == null || portlets.size() == 0) {
                continue;
            }
            box.setPortlets(portlets.toArray(new PageWidgetVO[0]));
        }
        return boxes;
    }
}
