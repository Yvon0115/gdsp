package com.gdsp.dev.web.mvc.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.domain.Page;

/**
 * springmvc @ResponseBody 返回json串时列表包装类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class GridDataWrap {

    /**
     * 当前页
     */
    private int                    page         = 1;
    /**
     * 数据总条数
     */
    private long                   totalRecords = 0;
    /**
     * 总页数
     */
    private long                   totalPage    = 0;
    /**
     * 页大小
     */
    private int                    pageSize     = 0;
    /**
     * 封装的数据，列表中的元素 可以是Jaxb标记的javaBean，也可以是数组 
     */
    private List<? extends Object> data;

    /**
     * 构造方法
     * @param data 列表数据
     */
    public GridDataWrap(List<? extends Object> data) {
        this.data = data;
        if (data == null) {
            totalRecords = 0;
            pageSize = 0;
            totalPage = 0;
        } else {
            totalRecords = data.size();
            pageSize = data.size();
            totalPage = 1;
        }
    }

    /**
     * 传入数据集对象的构造方法，如果是后台分页， 该方法会自动从{@link Pagination}获取数据总条数和总显示条数
     * @param page 分页数据
     */
    public GridDataWrap(Page<? extends Object> page) {
        data = page.getContent();
        totalRecords = page.getTotalElements();
        totalPage = page.getTotalPages();
        pageSize = page.getSize();
        this.page = page.getNumber() + 1;
    }

    /**
     * 取得当前页码
     * @return 当前页码
     */
    public int getPage() {
        return page;
    }

    /**
     * 设值当前页码
     * @param page 当前页码
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 取得总记录数
     * @return 总记录数
     */
    public long getTotalRecords() {
        return totalRecords;
    }

    /**
     * 设置总记录数
     * @param totalRecords 总记录数
     */
    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * 取得总页数
     * @return 总页数
     */
    public long getTotalPage() {
        return totalPage;
    }

    /**
     * 设置总页数
     * @param totalPage 总页数
     */
    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 表格数据列表
     * @return 列表中的数据
     */
    public List<? extends Object> getData() {
        return data;
    }

    /**
     * 表格数据列表,列表中元素可以是Jaxb标记的javaBean，也可以是数组
     * @param data 数据
     * @return 表格数据列表
     */
    public void setData(List<? extends Object> data) {
        this.data = data;
    }

    /**
     * 取得每页大小
     * @return 每页大小
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 取得每页大小
     * @param pageSize 每页大小
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
