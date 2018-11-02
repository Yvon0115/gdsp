package com.gdsp.platform.systools.indexanddim.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class IndexDicVO extends AuditableEntity {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -2449690591122790854L;
    /*指数代码*/
    private String            idx_cde;
    /*指数名称*/
    private String            idx_name;
    /*境内境外标志*/
    private String            dom_forn_indc;
    /*所属区域*/
    private String            blngs_area;
    /*数据源类型*/
    private String            data_src_type;
    /*是否计算市盈率*/
    private String            if_calc_pe;
    /*万得指数代码*/
    private String            wdi_idx_cde;

    /**
     * @return Returns the idx_cde.
     */
    public String getIdx_cde() {
        return idx_cde;
    }

    /**
     * @param idx_cde The idx_cde to set.
     */
    public void setIdx_cde(String idx_cde) {
        this.idx_cde = idx_cde;
    }

    /**
     * @return Returns the idx_name.
     */
    public String getIdx_name() {
        return idx_name;
    }

    /**
     * @param idx_name The idx_name to set.
     */
    public void setIdx_name(String idx_name) {
        this.idx_name = idx_name;
    }

    /**
     * @return Returns the dom_forn_indc.
     */
    public String getDom_forn_indc() {
        return dom_forn_indc;
    }

    /**
     * @param dom_forn_indc The dom_forn_indc to set.
     */
    public void setDom_forn_indc(String dom_forn_indc) {
        this.dom_forn_indc = dom_forn_indc;
    }

    /**
     * @return Returns the blngs_area.
     */
    public String getBlngs_area() {
        return blngs_area;
    }

    /**
     * @param blngs_area The blngs_area to set.
     */
    public void setBlngs_area(String blngs_area) {
        this.blngs_area = blngs_area;
    }

    /**
     * @return Returns the data_src_type.
     */
    public String getData_src_type() {
        return data_src_type;
    }

    /**
     * @param data_src_type The data_src_type to set.
     */
    public void setData_src_type(String data_src_type) {
        this.data_src_type = data_src_type;
    }

    /**
     * @return Returns the if_calc_pe.
     */
    public String getIf_calc_pe() {
        return if_calc_pe;
    }

    /**
     * @param if_calc_pe The if_calc_pe to set.
     */
    public void setIf_calc_pe(String if_calc_pe) {
        this.if_calc_pe = if_calc_pe;
    }

    /**
     * @return Returns the wdi_idx_cde.
     */
    public String getWdi_idx_cde() {
        return wdi_idx_cde;
    }

    /**
     * @param wdi_idx_cde The wdi_idx_cde to set.
     */
    public void setWdi_idx_cde(String wdi_idx_cde) {
        this.wdi_idx_cde = wdi_idx_cde;
    }
}
