package com.alex10011.example.vo;

import java.io.File;
import java.net.URLEncoder;

public class ShowFileVo {
	private String fileName = "";
	private String path = "";
	private String info = "";

	public ShowFileVo() {

	}

	public ShowFileVo(File file) {
		fileName = file.getName();
		path = URLEncoder.encode(file.getPath());
		info = file.length() / 1024 + "K";
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
