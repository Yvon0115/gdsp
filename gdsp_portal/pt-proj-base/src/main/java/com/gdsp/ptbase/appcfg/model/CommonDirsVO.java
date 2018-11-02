/**
 * 
 */
package com.gdsp.ptbase.appcfg.model;

/**目录vo
 * @author lian.yf
 *
 */
public class CommonDirsVO {
	
	private String                    id;			//目录id
	private String                    name;			//目录名称
	private String                    parent_id;	//目录的上级节点id
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

}
