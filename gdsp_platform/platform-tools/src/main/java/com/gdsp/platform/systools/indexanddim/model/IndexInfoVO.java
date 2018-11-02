package com.gdsp.platform.systools.indexanddim.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 指标信息表
 * @author Administrator
 *
 */
public class IndexInfoVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            indexCode;            //指标编码
    private String            indexName;            //指标名称
    private String            indexCodeName;        //指标名 (数据库中指标编码)
    private String            indexColumnName;      //指标编码字段名(数据库中指标的列字段名)
    private String            indexTableName;       //指标表名称
    private String            indexTableId;         //指标表ID
    private String            statfreq;             //统计频度
    private String            datasource;           //数据来源
    private String            comedepart;           //归属部门
    private String            businessbore;         //业务口径描述
    private String            techbore;             //技术口径描述
    private int               peicision;            //精度
    private String            meterunit;            //计量单位
    private String            onlyflexiablequery;   //只给灵活查询用
    private String            isChecked;            //是否选中

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getOnlyflexiablequery() {
        return onlyflexiablequery;
    }

    public void setOnlyflexiablequery(String onlyflexiablequery) {
        this.onlyflexiablequery = onlyflexiablequery;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexCodeName() {
        return indexCodeName;
    }

    public void setIndexCodeName(String indexCodeName) {
        this.indexCodeName = indexCodeName;
    }

    public String getIndexColumnName() {
        return indexColumnName;
    }

    public void setIndexColumnName(String indexColumnName) {
        this.indexColumnName = indexColumnName;
    }

    public String getIndexTableName() {
        return indexTableName;
    }

    public void setIndexTableName(String indexTableName) {
        this.indexTableName = indexTableName;
    }

    public String getIndexTableId() {
        return indexTableId;
    }

    public void setIndexTableId(String indexTableId) {
        this.indexTableId = indexTableId;
    }

    private String remark; //备注

    public String getStatfreq() {
        return statfreq;
    }

    public void setStatfreq(String statfreq) {
        this.statfreq = statfreq;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getComedepart() {
        return comedepart;
    }

    public void setComedepart(String comedepart) {
        this.comedepart = comedepart;
    }

    public String getBusinessbore() {
        return businessbore;
    }

    public void setBusinessbore(String businessbore) {
        this.businessbore = businessbore;
    }

    public String getTechbore() {
        return techbore;
    }

    public void setTechbore(String techbore) {
        this.techbore = techbore;
    }

    public int getPeicision() {
        return peicision;
    }

    public void setPeicision(int peicision) {
        this.peicision = peicision;
    }

    public String getMeterunit() {
        return meterunit;
    }

    public void setMeterunit(String meterunit) {
        this.meterunit = meterunit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
