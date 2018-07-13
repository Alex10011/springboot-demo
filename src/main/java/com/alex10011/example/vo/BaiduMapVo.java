package com.alex10011.example.vo;

public class BaiduMapVo {
	// 从，经度
	private double lngFrom;
	// 从，纬度
	private double latFrom;
	// 从，地址
	private String addressFrom;

	// 到，经度
	private double lngTo;
	// 到，纬度
	private double latTo;
	// 到，地址
	private String addressTo;

	// 起点和中点的距离
	private double distance;

	// 中心点，经度
	private double lngCent;
	// 中心点，纬度
	private double latCent;

	// 地图缩放比例
	private int zoom;

	public String toString() {
		return "从(" + lngFrom + "," + latFrom + ")到(" + lngTo + "," + latTo + ")距离" + distance + "米，中心点(" + lngCent
				+ "," + latCent + "),地图缩放值=" + zoom;
	}

	public double getLngFrom() {
		return lngFrom;
	}

	public void setLngFrom(double lngFrom) {
		this.lngFrom = lngFrom;
	}

	public double getLatFrom() {
		return latFrom;
	}

	public void setLatFrom(double latFrom) {
		this.latFrom = latFrom;
	}

	public String getAddressFrom() {
		return addressFrom;
	}

	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}

	public double getLngTo() {
		return lngTo;
	}

	public void setLngTo(double lngTo) {
		this.lngTo = lngTo;
	}

	public double getLatTo() {
		return latTo;
	}

	public void setLatTo(double latTo) {
		this.latTo = latTo;
	}

	public String getAddressTo() {
		return addressTo;
	}

	public void setAddressTo(String addressTo) {
		this.addressTo = addressTo;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getLngCent() {
		return lngCent;
	}

	public void setLngCent(double lngCent) {
		this.lngCent = lngCent;
	}

	public double getLatCent() {
		return latCent;
	}

	public void setLatCent(double latCent) {
		this.latCent = latCent;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

}
