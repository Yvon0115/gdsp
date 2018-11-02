package com.gdsp.platform.common.model;

import com.gdsp.dev.core.model.entity.BaseEntity;

public class SortDataVO extends BaseEntity {

    /**
    * @Fields serialVersionUID 
    */
    private static final long serialVersionUID = -494154290415799585L;
    private String            id;
    private String            itemDesc;
    private Integer           sortNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
}
