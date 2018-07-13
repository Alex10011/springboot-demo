package com.alex10011.example.domain;

//配置通过config表提供，更方便热变更
public class SysConfig {
	private int id;
	private String key;
	private String value;
	private String info;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "PayGateConfig [id=" + id + ", key=" + key + ", value=" + value + ", info=" + info + "]";
	}

}
