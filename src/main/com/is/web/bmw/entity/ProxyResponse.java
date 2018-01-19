package com.is.web.bmw.entity;

import com.google.gson.annotations.SerializedName;

public class ProxyResponse {

	@SerializedName("Ip")
	public String ip;

	@SerializedName("Port")
	public String port;

	@SerializedName("Country")
	public String country;

	@SerializedName("FullAddres")
	public String fullAddres;

	@SerializedName("ProxyType")
	public String proxyType;

	@SerializedName("Sec")
	public String sec;

	@SerializedName("AnonymousType")
	public String anonymousType;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFullAddres() {
		return fullAddres;
	}

	public void setFullAddres(String fullAddres) {
		this.fullAddres = fullAddres;
	}

	public String getProxyType() {
		return proxyType;
	}

	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}

	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

	public String getAnonymousType() {
		return anonymousType;
	}

	public void setAnonymousType(String anonymousType) {
		this.anonymousType = anonymousType;
	}
	
	
}
