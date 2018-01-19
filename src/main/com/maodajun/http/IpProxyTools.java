package com.maodajun.http;

import java.util.List;


public class IpProxyTools {

	public static void main(String[] args) {

	}
	public List proxy(String url){
		return null;
	}
	public List proxy(){
		StringBuffer url = new StringBuffer();
		url.append(HTTP.API)
		.append(HTTP.KEY)
		.append(HTTP.PAGE)
		.append(HTTP.NONAME)
		.append(HTTP.ADDRESS)
		.append(HTTP.LOCAL)
		.append(HTTP.TYPE);
		;
		return proxy(url.toString());
	}

}
