package com.is.web.bmw.entity.sql.entity;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;


import com.is.web.bmw.entity.EntityQualification;

/**
   数据对象：t_statist
*/
public class Statist extends EntityQualification implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("serial")
	public static final Map<String,String> columMap = new LinkedHashMap<String,String>(){{ 
		put("id","ID");put("ymd","YMD");put("pageview","PAGEVIEW");put("userview", "USERVIEW");
	}};
	
	@SuppressWarnings("unused")
	private static final String NAME_OF_TABLE_IN_DATABASE = "t_statist";
	@SuppressWarnings("unused")
	private static final String CODE_OF_TABLE_IN_DATABASE = "T_STATIST";
	
	/**
	 * 初始化空<i>t_statist</i><br>
	 * @return Statist
	 */
	public Statist(){}
	
	/**
	 * 初始化设定好主键值的<i>t_statist</i><br>
	 * @return Statist
	 */
	public Statist(Integer id){
		this.id = id;
	}
	
	public Statist(Statist statist){
		if(null==statist){
			return;
		}
		this.setId(statist.getId());
		this.setYmd(statist.getYmd());
		this.setPageview(statist.getPageview());
		this.setUserview(statist.getUserview());
	}
	
	public Map<String,Object> checkLength(Statist statist) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("result", true);
		if(StringUtils.isNotBlank(statist.getYmd()) && StringUtils.length(statist.getYmd())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "ymd[ymd]"+"：值太长。");
		}
		return resultMap;
	}


 	/**
	 * <b>id</b> <i>int(32)</i> <b>不能为空，默认值为：0</b><br>
	 * id<br>
	 */
	protected Integer id; 
	
 	/**
	 * <b>ymd</b> <i>varchar(128)</i><br>
	 * ymd<br>
	 */
	protected String ymd; 
	
 	/**
	 * <b>pageview</b> <i>int(32)</i><br>
	 * pageview<br>
	 */
	protected Integer pageview; 
	
	/**
	 * <b>userview</b> <i>int(32)</i><br>
	 * userview<br>
	 */
	protected Integer userview; 
	
	
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
	 * <b>获取ymd</b>
	 * @return String
	 */
	public String getYmd() {
		return ymd;
	}
	/**
	 * <b>设定ymd</b>
	 * @param ymd<br/>
	 */
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	/**
	 * <b>获取pageview</b>
	 * @return Integer
	 */
	public Integer getPageview() {
		return pageview;
	}
	/**
	 * <b>设定pageview</b>
	 * @param pageview<br/>
	 */
	public void setPageview(Integer pageview) {
		this.pageview = pageview;
	}

	/**
	 * <b>获取userview</b>
	 * @return Integer
	 */
	public Integer getUserview(){
		return userview;
	}
	
	/**
	 * <b>设定userview</b>
	 * @param userview<br/>
	 */
	public void setUserview(Integer userview){
		this.userview = userview;
	}
}
