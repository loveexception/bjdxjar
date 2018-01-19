package com.is.web.bmw.entity.sql.entity;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;


import com.is.web.bmw.entity.EntityQualification;

import com.is.web.bmw.entity.sql.entityenum.LoginsStauts;
/**
   数据对象：t_logins
*/
public class Logins extends EntityQualification implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("serial")
	public static final Map<String,String> columMap = new LinkedHashMap<String,String>(){{ 
		put("id","ID");put("pid","PID");put("ip","IP");put("port","PORT");put("stauts","STAUTS");put("plan","PLAN");put("send","SEND");put("uid","UID");put("updatetime","UPDATETIME");put("pidName","PID_NAME");put("pidPassword","PID_PASSWORD");
	}};
	
	@SuppressWarnings("unused")
	private static final String NAME_OF_TABLE_IN_DATABASE = "t_logins";
	@SuppressWarnings("unused")
	private static final String CODE_OF_TABLE_IN_DATABASE = "T_LOGINS";
	
	/**
	 * 初始化空<i>t_logins</i><br>
	 * @return Logins
	 */
	public Logins(){}
	
	/**
	 * 初始化设定好主键值的<i>t_logins</i><br>
	 * @return Logins
	 */
	public Logins(Integer id){
		this.id = id;
	}
	
	public Logins(Logins logins){
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
	
	public Map<String,Object> checkLength(Logins logins) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("result", true);
		if(StringUtils.isNotBlank(logins.getIp()) && StringUtils.length(logins.getIp())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "ip[ip]"+"：值太长。");
		}
		if(StringUtils.isNotBlank(logins.getPort()) && StringUtils.length(logins.getPort())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "port[port]"+"：值太长。");
		}
		if(StringUtils.isNotBlank(logins.getStauts()) && StringUtils.length(logins.getStauts())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "stauts[stauts]"+"：值太长。");
		}
		if(StringUtils.isNotBlank(logins.getStauts()) && !LoginsStauts.checkIsEnumValue(logins.getStauts())){
			resultMap.put("result", false);
			resultMap.put("msg", "状态[stauts]"+"：值不在正确范围内。");
		}
		return resultMap;
	}


 	/**
	 * <b>id</b> <i>int(32)</i> <b>不能为空，默认值为：0</b><br>
	 * id<br>
	 */
	protected Integer id; 
	
 	/**
	 * <b>pid</b> <i>int(32)</i><br>
	 * person 表中主键，登陆人id<br>
	 * 表数据主键
	 */
	protected Integer pid; 
	
 	/**
	 * <b>ip</b> <i>varchar(128)</i><br>
	 * ip<br>
	 */
	protected String ip; 
	
 	/**
	 * <b>port</b> <i>varchar(128)</i><br>
	 * port<br>
	 */
	protected String port; 
	
 	/**
	 * <b>stauts</b> <i>varchar(128)</i><br>
	 * RADIO_1:未执行,9:已发布,3:服务失败<br>
	 * 枚举字段，<i>LoginsStauts</i>
	 */
	protected String stauts; 
	
 	/**
	 * <b>plan</b> <i>datetime</i><br>
	 * 计划执行时间，当前时间在此时间之前的执行<br>
	 */
		protected java.util.Date plan; 
	
 	/**
	 * <b>send</b> <i>datetime</i><br>
	 * 实际操作时间<br>
	 */
		protected java.util.Date send; 
	
 	/**
	 * <b>uid</b> <i>int(32)</i><br>
	 * user 表中主键<br>
	 */
	protected Integer uid; 
	
 	/**
	 * <b>updateTime</b> <i>datetime</i><br>
	 * updateTime<br>
	 */
		protected java.util.Date updatetime; 
	
	
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
	 * <b>获取pid</b>
	 * @return Integer
	 */
	public Integer getPid() {
		return pid;
	}
	/**
	 * <b>设定pid</b>
	 * @param pid<br/>
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	/**
	 * <b>获取ip</b>
	 * @return String
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * <b>设定ip</b>
	 * @param ip<br/>
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * <b>获取port</b>
	 * @return String
	 */
	public String getPort() {
		return port;
	}
	/**
	 * <b>设定port</b>
	 * @param port<br/>
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 * <b>获取stauts</b>
	 * @return String
	 */
	public String getStauts() {
		return stauts;
	}
	/**
	 * <b>设定stauts</b>
	 * @param stauts<br/>
	 * 枚举字段，<i>LoginsStauts</i>
	 */
	public void setStauts(String stauts) {
		this.stauts = stauts;
	}
	/**
	 * <b>获取plan</b>
	 * @return java.util.Date
	 */
	public java.util.Date getPlan() {
		return plan;
	}
	/**
	 * <b>设定plan</b>
	 * @param plan<br/>
	 */
	public void setPlan(java.util.Date plan) {
		this.plan = plan;
	}
	/**
	 * <b>获取send</b>
	 * @return java.util.Date
	 */
	public java.util.Date getSend() {
		return send;
	}
	/**
	 * <b>设定send</b>
	 * @param send<br/>
	 */
	public void setSend(java.util.Date send) {
		this.send = send;
	}
	/**
	 * <b>获取uid</b>
	 * @return Integer
	 */
	public Integer getUid() {
		return uid;
	}
	/**
	 * <b>设定uid</b>
	 * @param uid<br/>
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/**
	 * <b>获取updateTime</b>
	 * @return java.util.Date
	 */
	public java.util.Date getUpdatetime() {
		return updatetime;
	}
	/**
	 * <b>设定updateTime</b>
	 * @param updatetime<br/>
	 */
	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}

}
