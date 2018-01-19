package com.is.web.bmw.entity.sql.extend;

import com.is.web.bmw.entity.sql.entity.User;

/**
   数据对象：t_user扩展数据
*/
public class UserExtend extends User {

	private static final long serialVersionUID = 1L;
	
	public UserExtend(){}
	
	public UserExtend(Integer id){
		this.id = id;
	}
	
	public UserExtend(User user){
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
	
	public UserExtend(UserExtend userExtend){
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
 	/**
	 * id多个值检索条件 <i>int(32)</i><br>
	 * id多个值检索条件<br>
	 */
	protected Integer[] idMults; 
	
	/**
	 * 获取id多个值检索条件
	 * @return Integer[]
	 */
	public Integer[] getIdMults() {
		return idMults;
	}
	/**
	 * 设定id多个值检索条件
	 * @param idMults<br/>
	 */
	public void setIdMults(Integer[] idMults) {
		this.idMults = idMults;
	}
}
