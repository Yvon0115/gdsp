package com.gdsp.platform.config.global.model;

import java.util.List;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 系统码表类型
 * @author songxiang
 * @date 2015年10月28日 下午2:03:02
 */
public class DefDocListVO extends AuditableEntity {

    private static final long serialVersionUID = -4760084188201452579L;
    /**
     * 类型编码
     */
    private String            type_code;                               // varchar(50),
    /**
     * 类型名称 
     */
    private String            type_name;                               // varchar(128),
    /**
     * 类型描述
     */
    private String            type_desc;                               // varchar(256),
    /**
     * 分类号
     */
    private int               sortnum;                                 // int,
    private List<DefDocVO>    defdocs;

    public List<DefDocVO> getDefdocs() {
        return defdocs;
    }

    public void setDefdocs(List<DefDocVO> defdocs) {
        this.defdocs = defdocs;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_desc() {
        return type_desc;
    }

    public void setType_desc(String type_desc) {
        this.type_desc = type_desc;
    }

    public int getSortnum() {
        return sortnum;
    }

    public void setSortnum(int sortnum) {
        this.sortnum = sortnum;
    }

}
