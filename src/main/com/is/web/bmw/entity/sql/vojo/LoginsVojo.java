package com.is.web.bmw.entity.sql.vojo;



import com.is.web.bmw.entity.sql.entity.Logins;
import com.is.web.bmw.entity.sql.extend.LoginsExtend;


public class LoginsVojo extends LoginsExtend {
	private static final long serialVersionUID = 1L;
	
	public LoginsVojo(){}

	public LoginsVojo(Logins logins){
		if(null==logins){
			return;
		}
		this.setId(logins.getId()); 
		this.setPid(logins.getPid()); 
		this.setIp(logins.getIp()); 
		this.setPort(logins.getPort()); 
		this.setStauts(logins.getStauts()); 
		this.setPlan(logins.getPlan()); 
		this.setSend(logins.getSend()); 
		this.setUid(logins.getUid()); 
		this.setUpdatetime(logins.getUpdatetime()); 
	}
	
	public LoginsVojo(LoginsExtend loginsExtend){
		if(null==loginsExtend){
			return;
		}
		this.setId(loginsExtend.getId()); 
		this.setPid(loginsExtend.getPid()); 
		this.setIp(loginsExtend.getIp()); 
		this.setPort(loginsExtend.getPort()); 
		this.setStauts(loginsExtend.getStauts()); 
		this.setPlan(loginsExtend.getPlan()); 
		this.setSend(loginsExtend.getSend()); 
		this.setUid(loginsExtend.getUid()); 
		this.setUpdatetime(loginsExtend.getUpdatetime()); 
		this.setPidName(loginsExtend.getPidName()); 
		this.setPidPassword(loginsExtend.getPidPassword()); 
		this.setStautsChnval(loginsExtend.getStautsChnval()); 
		this.setIdMults(loginsExtend.getIdMults()); 
	}
}
