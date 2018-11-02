package com.gdsp.platform.widgets.favorites.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 收藏夹Vo
 * @author gy
 * @date 2016年11月17日
 */
public class FavoritesVO extends AuditableEntity {

	private static final long serialVersionUID = 4275308557881587272L;
	/**
	 * 上级id
	 */
	private String pid;

	/**
	 * 收藏名字
	 */
	private String name;

	/**
	 * 收藏url
	 */
	private String url;

	/**
	 * 收藏类型
	 */
	private Integer fileType;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
