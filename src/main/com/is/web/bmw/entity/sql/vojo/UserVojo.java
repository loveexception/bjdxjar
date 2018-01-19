package com.is.web.bmw.entity.sql.vojo;



import com.is.web.bmw.entity.sql.entity.User;
import com.is.web.bmw.entity.sql.extend.UserExtend;


public class UserVojo extends UserExtend {
	private static final long serialVersionUID = 1L;
	
	public UserVojo(){}

	public UserVojo(User user){
		if(null==user){
			return;
		}
		this.setId(user.getId()); 
		this.setName(user.getName()); 
		this.setPasswd(user.getPasswd()); 
		this.setSalt(user.getSalt()); 
		this.setCt(user.getCt()); 
		this.setUt(user.getUt()); 
	}
	
	public UserVojo(UserExtend userExtend){
		if(null==userExtend){
			return;
		}
		this.setId(userExtend.getId()); 
		this.setName(userExtend.getName()); 
		this.setPasswd(userExtend.getPasswd()); 
		this.setSalt(userExtend.getSalt()); 
		this.setCt(userExtend.getCt()); 
		this.setUt(userExtend.getUt()); 
		this.setIdMults(userExtend.getIdMults()); 
	}
}
