package com.is.web.bmw.entity.sql.entity;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;


import com.is.web.bmw.entity.EntityQualification;

import com.is.web.bmw.entity.sql.entityenum.SubtopicsStatus;
/**
   数据对象：t_subtopics
*/
public class Subtopics extends EntityQualification implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("serial")
	public static final Map<String,String> columMap = new LinkedHashMap<String,String>(){{ 
		put("id","ID");put("tid","TID");put("context","CONTEXT");put("pid","PID");put("send","SEND");put("plan","PLAN");put("status","STATUS");put("pidName","PID_NAME");put("pidPassword","PID_PASSWORD");
	}};
	
	@SuppressWarnings("unused")
	private static final String NAME_OF_TABLE_IN_DATABASE = "t_subtopics";
	@SuppressWarnings("unused")
	private static final String CODE_OF_TABLE_IN_DATABASE = "T_SUBTOPICS";
	
	/**
	 * 初始化空<i>t_subtopics</i><br>
	 * @return Subtopics
	 */
	public Subtopics(){}
	
	/**
	 * 初始化设定好主键值的<i>t_subtopics</i><br>
	 * @return Subtopics
	 */
	public Subtopics(Integer id){
		this.id = id;
	}
	
	public Subtopics(Subtopics subtopics){
		if(null==subtopics){
			return;
		}
		this.setId(subtopics.getId()); 
		this.setTid(subtopics.getTid()); 
		this.setContext(subtopics.getContext()); 
		this.setPid(subtopics.getPid()); 
		this.setSend(subtopics.getSend()); 
		this.setPlan(subtopics.getPlan()); 
		this.setStatus(subtopics.getStatus()); 
	}
	
	public Map<String,Object> checkLength(Subtopics subtopics) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("result", true);
		if(StringUtils.isNotBlank(subtopics.getStatus()) && StringUtils.length(subtopics.getStatus())> 128){
			resultMap.put("result", false);
			resultMap.put("msg", "status[status]"+"：值太长。");
		}
		if(StringUtils.isNotBlank(subtopics.getStatus()) && !SubtopicsStatus.checkIsEnumValue(subtopics.getStatus())){
			resultMap.put("result", false);
			resultMap.put("msg", "状态[status]"+"：值不在正确范围内。");
		}
		return resultMap;
	}


 	/**
	 * <b>id</b> <i>int(32)</i> <b>不能为空，默认值为：0</b><br>
	 * id<br>
	 */
	protected Integer id; 
	
 	/**
	 * <b>tid</b> <i>int(32)</i><br>
	 * 回复帖子id<br>
	 */
	protected Integer tid; 
	
 	/**
	 * <b>context</b> <i>text</i><br>
	 * 回复内容<br>
	 */
	protected String context; 
	
 	/**
	 * <b>pid</b> <i>int(32)</i><br>
	 * person 表中主键，登陆人id<br>
	 * 表数据主键
	 */
	protected Integer pid; 
	
 	/**
	 * <b>send</b> <i>datetime</i><br>
	 * 实际发布时间<br>
	 */
		protected java.util.Date send; 
	
 	/**
	 * <b>plan</b> <i>datetime</i><br>
	 * 计划发布时间，当前时间在此时间之前的执行<br>
	 */
		protected java.util.Date plan; 
	
 	/**
	 * <b>status</b> <i>varchar(128)</i><br>
	 * RADIO_1:未执行,9:已发布,3:服务失败<br>
	 * 枚举字段，<i>SubtopicsStatus</i>
	 */
	protected String status; 
	
	
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
	 * <b>获取tid</b>
	 * @return Integer
	 */
	public Integer getTid() {
		return tid;
	}
	/**
	 * <b>设定tid</b>
	 * @param tid<br/>
	 */
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	/**
	 * <b>获取context</b>
	 * @return String
	 */
	public String getContext() {
		return context;
	}
	/**
	 * <b>设定context</b>
	 * @param context<br/>
	 */
	public void setContext(String context) {
		this.context = context;
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
	 * <b>获取status</b>
	 * @return String
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * <b>设定status</b>
	 * @param status<br/>
	 * 枚举字段，<i>SubtopicsStatus</i>
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
