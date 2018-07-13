package com.alex10011.example.vo;

public class TestVo {
	private String PROVINCE;
	private String CITY;
	private String ADDRESS;
	private String ZIP;

	public String toString() {
		return "PROVINCE=" + PROVINCE + ",CITY=" + CITY + ",ADDRESS=" + ADDRESS + ",ZIP=" + ZIP;
	}

	public String getPROVINCE() {
		return PROVINCE;
	}

	public void setPROVINCE(String pROVINCE) {
		PROVINCE = pROVINCE;
	}

	public String getCITY() {
		return CITY;
	}

	public void setCITY(String cITY) {
		CITY = cITY;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public String getZIP() {
		return ZIP;
	}

	public void setZIP(String zIP) {
		ZIP = zIP;
	}

}
