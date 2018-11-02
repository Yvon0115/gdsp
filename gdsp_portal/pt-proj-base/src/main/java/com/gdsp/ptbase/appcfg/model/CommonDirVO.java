package com.gdsp.ptbase.appcfg.model;

import java.util.List;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class CommonDirVO extends AuditableEntity {

    private static final long serialVersionUID = 88775557888324512L;
    private String            dir_name;                             // varchar(128) NULL,
    private String            parent_id;                            // varchar(32) NULL,
    private int               sortnum;                              // int(11) NULL,
    private String            def1;
    private String            def2;
    private String            def3;
    private String            category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 下级目录数量
     */
    private int               subDirCount;
    /**
     * 目录叶子节点的数量
     */
    private int               leafCount;
    private List<CommonDirVO> subdir;     // 子目录
    private List<PageVO>      subpage;    // 子页面

    public String getDir_name() {
        return dir_name;
    }

    public void setDir_name(String dir_name) {
        this.dir_name = dir_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public int getSortnum() {
        return sortnum;
    }

    public void setSortnum(int sortnum) {
        this.sortnum = sortnum;
    }

    public List<CommonDirVO> getSubdir() {
        return subdir;
    }

    public void setSubdir(List<CommonDirVO> subdir) {
        this.subdir = subdir;
    }

    public String getDef1() {
        return def1;
    }

    public void setDef1(String def1) {
        this.def1 = def1;
    }

    public String getDef2() {
        return def2;
    }

    public void setDef2(String def2) {
        this.def2 = def2;
    }

    public String getDef3() {
        return def3;
    }

    public void setDef3(String def3) {
        this.def3 = def3;
    }

    public List<PageVO> getSubpage() {
        return subpage;
    }

    public void setSubpage(List<PageVO> subpage) {
        this.subpage = subpage;
    }
}
