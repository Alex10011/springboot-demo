package com.alex10011.example.vo;

import com.github.crab2died.annotation.ExcelField;

public class TestExcel {
	@ExcelField(title = "版本描述", order = 3)
	private String info;

	@ExcelField(title = "版本类型", order = 4)
	private String ver;

	public String toString() {
		return "info=" + info + ",ver=" + ver;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

}
