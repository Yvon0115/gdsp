package com.gdsp.platform.systools.datadic.model;


import java.util.List;

import com.gdsp.dev.core.model.entity.AuditableEntity;
/**
* @ClassName: DataDicVO
* (数据字典类别VO)
* @author qishuo
* @date 2016年12月09日 
*
*/
public class DataDicVO extends AuditableEntity {


    private static final long serialVersionUID = 3768127946023696170L;
    /**
     * 数据字典名称
     */
    private String dic_name;
    /**
     * 数据字典编码
     */
    private String dic_code;
    /**
     *数据字典描述 
     */
    private String dic_desc;
    /**
     * 
     */
    private List<DataDicValueVO> dataDicValueVO;
    
    public List<DataDicValueVO> getDataDicValueVO() {
		return dataDicValueVO;
	}

	public void setDataDicValueVO(List<DataDicValueVO> dataDicValueVO) {
		this.dataDicValueVO = dataDicValueVO;
	}

	public String getDic_name() {
        return dic_name;
    }
    
    public void setDic_name(String dic_name) {
        this.dic_name = dic_name;
    }
    
    public String getDic_code() {
        return dic_code;
    }
    
    public void setDic_code(String dic_code) {
        this.dic_code = dic_code;
    }
    
    public String getDic_desc() {
        return dic_desc;
    }
    
    public void setDic_desc(String dic_desc) {
        this.dic_desc = dic_desc;
    }
}
