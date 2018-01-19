package com.is.web.bmw.entity.sql.entity;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;


import com.is.web.bmw.entity.EntityQualification;

/**
   数据对象：t_plans
*/
public class Plans extends EntityQualification implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("serial")
	public static final Map<String,String> columMap = new LinkedHashMap<String,String>(){{ 
		put("id","ID");put("name","NAME");put("count","COUNT");
	}};
	
	@SuppressWarnings("unused")
	private static final String NAME_OF_TABLE_IN_DATABASE = "t_plans";
	@SuppressWarnings("unused")
	private static final String CODE_OF_TABLE_IN_DATABASE = "T_PLANS";
	
	/**
	 * 初始化空<i>t_plans</i><br>
	 * @return Plans
	 */
	public Plans(){}
	
	/**
	 * 初始化设定好主键值的<i>t_plans</i><br>
	 * @return Plans
	 */
	public Plans(Integer id){
		this.id = id;
	}
	
	public Plans(Plans plans){
		if(null==plans){
			return;
		}
		this.setId(plans.getId()); 
		this.setName(plans.getName()); 
		this.setCount(plans.getCount()); 
	}
	
	public Map<String,Object> checkLength(Plans plans) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("result", true);
		if(StringUtils.isNotBlank(plans.getName()) && StringUtils.length(plans.getName())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "name[name]"+"：值太长。");
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
	 * <b>count</b> <i>int(32)</i><br>
	 * count<br>
	 */
	protected Integer count; 
	
	
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
	 * <b>获取count</b>
	 * @return Integer
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * <b>设定count</b>
	 * @param count<br/>
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

}
