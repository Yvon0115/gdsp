package com.gdsp.ptbase.appcfg.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 
* @ClassName: WidgetCdnTemplateVO
* (资源模板VO类)
* @author songxiang
* @date 2015年10月28日 下午2:08:37
*
 */
public class WidgetCdnTemplateVO extends AuditableEntity {

    /**
    * @Fields serialVersionUID (主键id)
    */
    private static final long serialVersionUID = -6490693704351421281L;
    /**
     * 名称
     */
    private String            name;
    /**
     * 说明
     */
    private String            comments;
    /**
     * 编码
     */
    private String            code;
    /**
     * 类型
     */
    private String            type;
    /**
     * 分类号
     */
    private int               sortnum;

    public int getSortnum() {
        return sortnum;
    }

    public void setSortnum(int sortnum) {
        this.sortnum = sortnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
