/**  
* @Title: DsRegisterVO.java
* @Package com.gdsp.platform.systools.datasource.model
* (用一句话描述该文件做什么)
* @author Administrator
* @date 2017年6月30日 上午10:42:49
* @version V1.0  
*/
package com.gdsp.platform.systools.datasource.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
* @ClassName: DsRegisterVO
* (数据源注册服务VO)
* @author yuchenglong
* @date 2017年6月30日 上午10:42:49
*
*/
public class DsRegisterVO extends AuditableEntity {

    /**
    * @Fields serialVersionUID (序列化id)
    */
    private static final long serialVersionUID = -247787605721759975L;
    /**
     * 数据源外键
     */
    private String            pk_datasource;
    /**
     * 资源id
     */
    private String            res_id;
    /**
     * 资源名称
     */
    private String            res_name;
    /**
     * 描述
     */
    private String            memo;
    /**
     * 数据源VO
     */
    private DataSourceVO      dataSourceVO;

    public String getPk_datasource() {
        return pk_datasource;
    }

    public void setPk_datasource(String pk_datasource) {
        this.pk_datasource = pk_datasource;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public DataSourceVO getDataSourceVO() {
        return dataSourceVO;
    }

    public void setDataSourceVO(DataSourceVO dataSourceVO) {
        this.dataSourceVO = dataSourceVO;
    }
}
