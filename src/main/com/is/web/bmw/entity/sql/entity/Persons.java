package com.is.web.bmw.entity.sql.entity;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import com.is.web.bmw.entity.EntityQualification;

/**
   数据对象：t_persons
*/
public class Persons extends EntityQualification implements Serializable{

	private static final long serialVersionUID = 1L;
	public String mobile;
	
	@SuppressWarnings("serial")
	public static final Map<String,String> columMap = new LinkedHashMap<String,String>(){{ 
		put("id","ID");put("name","NAME");put("password","PASSWORD");put("oldid","OLDID");
	}};
	
	@SuppressWarnings("unused")
	private static final String NAME_OF_TABLE_IN_DATABASE = "t_persons";
	@SuppressWarnings("unused")
	private static final String CODE_OF_TABLE_IN_DATABASE = "T_PERSONS";
	
	/**
	 * 初始化空<i>t_persons</i><br>
	 * @return Persons
	 */
	public Persons(){}
	
	/**
	 * 初始化设定好主键值的<i>t_persons</i><br>
	 * @return Persons
	 */
	public Persons(Integer id){
		this.id = id;
	}
	
	public Persons(Persons persons){
		if(null==persons){
			return;
		}
		this.setId(persons.getId()); 
		this.setName(persons.getName()); 
		this.setPassword(persons.getPassword()); 
		this.setOldid(persons.getOldid()); 
	}
	
	public Map<String,Object> checkLength(Persons persons) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("result", true);
		if(StringUtils.isNotBlank(persons.getName()) && StringUtils.length(persons.getName())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "name[name]"+"：值太长。");
		}
		if(StringUtils.isNotBlank(persons.getPassword()) && StringUtils.length(persons.getPassword())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "password[password]"+"：值太长。");
		}
		if(StringUtils.isNotBlank(persons.getOldid()) && StringUtils.length(persons.getOldid())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "oldid[oldid]"+"：值太长。");
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
	 * <b>password</b> <i>varchar(128)</i><br>
	 * password<br>
	 */
	protected String password; 
	
 	/**
	 * <b>oldid</b> <i>varchar(128)</i><br>
	 * ???  id<br>
	 */
	protected String oldid; 
	
	
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
	 * <b>获取password</b>
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * <b>设定password</b>
	 * @param password<br/>
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * <b>获取oldid</b>
	 * @return String
	 */
	public String getOldid() {
		return oldid;
	}
	/**
	 * <b>设定oldid</b>
	 * @param oldid<br/>
	 */
	public void setOldid(String oldid) {
		this.oldid = oldid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
}
