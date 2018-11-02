package com.gdsp.dev.core.model.param;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 重新实现Page接口,方便控制
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class PageSerRequest extends PageRequest {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 默认构造方法解决序列化问题
     */
    public PageSerRequest() {
        super(0, 10);
    }

    /**
     * 构造方法
     * @param page 请求页从0开始
     * @param size 每页大小
     */
    public PageSerRequest(int page, int size) {
        super(page, size);
    }

    /**
     * 构造方法
     * @param page 请求页从0开始
     * @param size 每页大小
     * @param direction 排序方向
     * @param properties 排序属性集
     */
    public PageSerRequest(int page, int size, Sort.Direction direction, String... properties) {
        super(page, size, direction, properties);
    }

    /**
     * 构造方法
     * @param page 请求页从0开始
     * @param size 每页大小
     * @param sort 排序对象
     */
    public PageSerRequest(int page, int size, Sort sort) {
        super(page, size, sort);
    }
}
