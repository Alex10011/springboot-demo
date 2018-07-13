package com.alex10011.example.domain;

public class SysUser {
	private int id;
	private String name;

	public String toString() {
		return id + "=" + name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
