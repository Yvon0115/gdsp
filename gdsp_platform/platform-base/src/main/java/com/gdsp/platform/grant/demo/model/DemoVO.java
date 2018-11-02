package com.gdsp.platform.grant.demo.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class DemoVO extends AuditableEntity {
	private static final long serialVersionUID = 1L;
	private String name; // 姓名
	private String age; // 年龄
	private String tel; // 电话
	private String email; // 邮箱

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
