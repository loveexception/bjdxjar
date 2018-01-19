package com.is.web.bmw.entity.sql.entity;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;


import com.is.web.bmw.entity.EntityQualification;

/**
   数据对象：t_user
*/
public class User extends EntityQualification implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("serial")
	public static final Map<String,String> columMap = new LinkedHashMap<String,String>(){{ 
		put("id","ID");put("name","NAME");put("passwd","PASSWD");put("salt","SALT");put("ct","CT");put("ut","UT");
	}};
	
	@SuppressWarnings("unused")
	private static final String NAME_OF_TABLE_IN_DATABASE = "t_user";
	@SuppressWarnings("unused")
	private static final String CODE_OF_TABLE_IN_DATABASE = "T_USER";
	
	/**
	 * 初始化空<i>t_user</i><br>
	 * @return User
	 */
	public User(){}
	
	/**
	 * 初始化设定好主键值的<i>t_user</i><br>
	 * @return User
	 */
	public User(Integer id){
		this.id = id;
	}
	
	public User(User user){
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
	
	public Map<String,Object> checkLength(User user) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("result", true);
		if(StringUtils.isNotBlank(user.getName()) && StringUtils.length(user.getName())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "name[name]"+"：值太长。");
		}
		if(StringUtils.isNotBlank(user.getPasswd()) && StringUtils.length(user.getPasswd())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "passwd[passwd]"+"：值太长。");
		}
		if(StringUtils.isNotBlank(user.getSalt()) && StringUtils.length(user.getSalt())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "salt[salt]"+"：值太长。");
		}
		return resultMap;
	}


 	/**
	 * <b>id</b> <i>int(32)</i> <b>不能为空，默认值为：0</b><br>
	 * id<br>
	 */
	protected Integer id; 
	
 	/**
	 * <b>name</b> <i>varchar(128)</i><br>
	 * name<br>
	 */
	protected String name; 
	
 	/**
	 * <b>passwd</b> <i>varchar(128)</i><br>
	 * passwd<br>
	 */
	protected String passwd; 
	
 	/**
	 * <b>salt</b> <i>varchar(128)</i><br>
	 * salt<br>
	 */
	protected String salt; 
	
 	/**
	 * <b>ct</b> <i>datetime</i><br>
	 * ct<br>
	 */
		protected java.util.Date ct; 
	
 	/**
	 * <b>ut</b> <i>datetime</i><br>
	 * ut<br>
	 */
		protected java.util.Date ut; 
	
	
	/**
	 * <b>获取id</b>
	 * @return Integer
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * <b>设定id</b>
	 * @param id<br/>
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * <b>获取name</b>
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**
	 * <b>设定name</b>
	 * @param name<br/>
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * <b>获取passwd</b>
	 * @return String
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * <b>设定passwd</b>
	 * @param passwd<br/>
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	/**
	 * <b>获取salt</b>
	 * @return String
	 */
	public String getSalt() {
		return salt;
	}
	/**
	 * <b>设定salt</b>
	 * @param salt<br/>
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	/**
	 * <b>获取ct</b>
	 * @return java.util.Date
	 */
	public java.util.Date getCt() {
		return ct;
	}
	/**
	 * <b>设定ct</b>
	 * @param ct<br/>
	 */
	public void setCt(java.util.Date ct) {
		this.ct = ct;
	}
	/**
	 * <b>获取ut</b>
	 * @return java.util.Date
	 */
	public java.util.Date getUt() {
		return ut;
	}
	/**
	 * <b>设定ut</b>
	 * @param ut<br/>
	 */
	public void setUt(java.util.Date ut) {
		this.ut = ut;
	}

}
